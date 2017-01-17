package controllers.v1;

import com.google.common.base.Strings;
import controllers.api.API;
import edu.ustb.security.domain.vo.ecc.Pair;
import edu.ustb.security.service.ecc.impl.CpkCoresImpl;
import edu.ustb.security.domain.vo.ecc.Pair;
import models.Cert;
import models.Module;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import play.Logger;
import play.Play;
import play.data.binding.As;
import play.data.validation.Range;
import play.data.validation.Required;
import play.libs.Files;
import play.libs.IO;
import play.libs.Images;
import play.vfs.VirtualFile;
import utils.SafeGuard;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.System.in;
import static play.libs.Files.zip;
import static play.modules.jongo.BaseModel.getCollection;

/**
 * 证书管理
 * Created by xudongmei on 2016/12/13.
 */
public class Certs extends API {


    private static CpkCoresImpl cpkCores;
    public static String certFilesPath = Play.configuration.getProperty("attachments.path") + File.separator + "certs";
    public static String uploadsPath = certFilesPath + File.separator + "uploads";//上传的法人证书保存地址
    public static String producesPath = certFilesPath + File.separator + "produces";//保存审批后生成法人CPK证书

    public static String QRPath = certFilesPath + File.separator + "QRs";//二维码图片存放地址
    public static String pattern = "yyyy-MM-dd";

    /**
     * 1.政府内部用户-离线信息录入（申请证书）
     */
    public static void apply() {
        Cert cert = readBody(Cert.class);
        cert.save();
        created(cert);
    }

    /**
     * 2.政府内部用户-申请状态查询（status=0待审批；status=1审批通过；status=2审批不通过）
     * 通过 status 的取值进入到待审批列表、已审批列表
     */
    public static void list(String filters, @As(value = ",") List<String> params, @Range(min = 0,max = 100) Integer limit, @Range(min = 0) Integer offset) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(Strings.isNullOrEmpty(filters)) {
            filters="{companyName: {$regex: #},_created:{$gte:#},_created:{$lte:#},status:#}";
        }else {
            filters = SafeGuard.safeFilters(filters);
        }
        if(StringUtils.countMatches(filters,"#") != params.size()) {
            badRequest("filters args size should equals params size!");
        }
        //todo: 处理 params中的数据类型
        String companyName = params.get(0);
        Date startDate = sdf.parse(params.get(1));
        Date endDate =  sdf.parse(params.get(2));
        Long status = Long.valueOf(params.get(3));

        MongoCursor<Cert> mongoCursor = getCollection(Cert.class).find(filters,companyName,startDate,endDate,status).limit(limit).skip(offset).as(Cert.class);
        response.setHeader("X-Total-Count",String.valueOf(mongoCursor.count()));

        List<Cert> certs = StreamSupport.stream(mongoCursor.spliterator(),false).collect(Collectors.toList());
        renderJSON(certs);
    }

    /**
     * 2.1 政府内部用户-证书审批
     * @param ids 证书Ids
     */
    public static void approve(@Required String ids) throws IOException {

        Integer status = Integer.valueOf(request.params.get("status"));
        String[] idArr = ids.split(",");
        Date modifyTime = new Date();

        //todo: 证书审批后,需要生成证书(zip压缩包格式)文件并保存
        for(String id : idArr) {
            getCollection(Cert.class).update(new ObjectId(id)).multi().with("{$set:{status:#,modifyTime:#}}",status,modifyTime).isUpdateOfExisting();
            /**
             * @Description
             *  1.调用接口生成 tradeSk,productSK,Matrix
             *  2.将3个字符串各生成一个文件
             *  3.将2中的文件压缩成一个zip包
             */
           /* String tradeSk = cpkCores.generateSkById(cert.getCreditCode()).toString();
            String productSK = cpkCores.generateSkById(cert.getVendorCode()).toString();
            String matrix = CpkMatrixsFactory.generateCpkMatrix().toJson();*/
            String tradeSk = "demo-1";
            String productSK = "demo-2";
            String matrix = "demo-3";
            /**
             * 将 3个字符串分别写入到 3个文件中
             */
            String[] strs = {tradeSk,productSK,matrix};
            for(String str: strs) {
                IO.write(str.getBytes(),new File(producesPath + File.separator + UUID.randomUUID() + ".txt"));
            }
            /**
             * 将这 3个文件打包为一个zip,并删除初始的 3个文件
             */
            File file = new File(producesPath);
            File[] fileList = file.listFiles();//读取produces目录下生成的3个CPK证书
            File zipFile = new File(certFilesPath + File.separator + UUID.randomUUID() + ".zip");

            /*           for(int i=0;i<fileList.length;i++) {
                Files.copy(fileList[i],zipFile);
                //Files.delete(fileList[i]);
            }*/


            Files.zip(file,zipFile);
            Files.delete(file);
         /*   for(int i=0;i<fileList.length;i++) {
                Files.delete(fileList[i]);
            }*/

/*
            FileOutputStream outputStream = new FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
            FileInputStream fis = new FileInputStream(file);

            for(int i=0;i<fileList.length;i++) {
                //Files.copy(fileList[i],zipFile);
                //Files.delete(fileList[i]);
                out.putNextEntry(new ZipEntry(""));
                //进行写操作
                int j = 0;
                byte[] buffer = new byte[1024];
                while((j = fis.read(buffer)) > 0) {
                    out.write(buffer,0,j);
                }
                //关闭输入流
                fis.close();
                out.close();
            }*/
        }
    }



    /**
     * 3.查询企业的证书信息
     * @param companyId 企业的Id
     */
    public static void get(@Required String companyId) {
        Cert cert =  getCollection(Cert.class).findOne("{companyId:#}",companyId).as(Cert.class);
        if (cert == null) {
            notFound(companyId);
        }else {
            renderJSON(cert);
        }
    }

    /**
     * 4.证书导入
     * @param attachment 导入的Zip包
     * @param companyId 企业Id
     */
    public static void certsImport(@Required Integer companyId,@Required File attachment) throws IOException {
        String remark = request.params.get("remark");
        Cert cert =  getCollection(Cert.class).findOne("{companyId:#}",companyId).as(Cert.class);
        cert.certRemark = remark;
        cert.save();
        String storePath = certFilesPath + File.separator + companyId + File.separator + UUID.randomUUID();
        File storeFile = new File(storePath);
        Files.copy(attachment, storeFile);
        //对storeFile进行解压
        //Files.delete(storeFile);
        renderJSON("{\"success\":\"ok\"}");
    }

    /**
     * 5.附件上传
     * @param attachment 上传的附件
     */
    public static void attachment(File attachment) throws IOException {
        //todo: 通用的附件上传
        File storeFile = new File(uploadsPath + File.separator + UUID.randomUUID());//上传文件的保存地址
        Logger.info("upload file name：" + attachment.getName());
        Boolean b = attachment.getName().endsWith(".zip");
        if(b) {//上传的是zip,需要解压
           // Files.unzip(attachment,new File(uploadsPath));
            File pathFile = new File(uploadsPath);
            if(!pathFile.exists()) {
                pathFile.mkdirs();
            }
            ZipFile zip = new ZipFile(attachment);
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                String outPath = (uploadsPath + File.separator + zipEntryName).replaceAll("\\*","/");
                File file = new File(outPath.substring(0,outPath.lastIndexOf("/")));

                if(!file.exists()) {
                    file.mkdirs();
                }else if(new File(outPath).isDirectory()) {
                    continue;
                }
                Logger.debug("file's Path: " + outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf = new byte[1024];
                int len;
                while((len = in.read(buf)) > 0) {
                    out.write(buf,0,len);
                }
                in.close();
                out.close();
            }
            Files.delete(attachment);
        }
        Files.copy(attachment, storeFile);
        renderJSON("{\"success\":\"ok\"}");
    }

    /**
     *  证书(压缩包文件)下载
     * @param id 证书id
     */
    public static void download(@Required String id) {
        Cert cert = getCollection(Cert.class).findOne(new ObjectId(id)).as(Cert.class);
        notFoundIfNull(cert);
        renderBinary(new ByteArrayInputStream(VirtualFile.fromRelativePath(certFilesPath+File.separator+cert.getCompanyName()).content()),cert.getCompanyName()+".zip");
    }

    /**
     * 单个二维码生成
     * @param moduleId 流水号
     */
    public static void singleCode(@Required String moduleId) throws IOException {
        /**
         * 调用sign(BigInteger sk,String moduleId)接口生成签名
         */
        BigInteger sk = new BigInteger("9898493849389849");//BigInteger类型的私钥
        Pair pair = cpkCores.sign(sk,moduleId);//根据私钥、电池编号(流水号)生成签名信息

        /**
         * 调用 generateQRcode(OutputStream fos,String moduleId,Pair pair)生成二维码
         */
        File file = new File(QRPath);
        OutputStream fos = new FileOutputStream(file);
        cpkCores.generateQRcode(fos,moduleId,pair);//生成二维码到指定输出流
        /**
         * 调用 Images.toBase64(File image);
         * 将二维码图片转化为 base64字符串
         */
        Images.toBase64(file);
        fos.close();
    }

    /**
     * 批量二维码生成
     * @param beginModuleId 初始流水号
     * @param endModuleId 截止流水号
     */
    public static void batchCode(@Required String beginModuleId,@Required String endModuleId) throws IOException {
        List<Module> modules = StreamSupport.stream(getCollection(Module.class).find("moduleId:{$gte:#},moduleId:{$lte:#}}",beginModuleId,endModuleId).as(Module.class).spliterator(),false).collect(Collectors.toList());
        for(int i=0;i<modules.size();i++) {
            singleCode(modules.get(i).getModuleId());
        }
    }

}
