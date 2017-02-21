package controllers.v1;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import controllers.api.API;
import edu.ustb.security.domain.vo.ecc.Pair;
import edu.ustb.security.service.ecc.CpkMatrixsFactory;
import edu.ustb.security.service.ecc.impl.CpkCoresImpl;
import models.Company;
import models.Module;
import models.SeedMatrix;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import play.Logger;
import play.Play;
import play.data.binding.As;
import play.data.validation.Range;
import play.data.validation.Required;
import play.libs.Images;
import utils.SafeGuard;

import java.io.*;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static play.modules.jongo.BaseModel.getCollection;

/**
 * 证书管理
 * Created by xudongmei on 2016/12/13.
 */
public class Certs extends API {

    private static CpkCoresImpl cpkCores;
    private static final String CERTS_PATH = Play.configuration.getProperty("attachments.path") + File.separator + "certs";
    public static String certFilesPath = CERTS_PATH + File.separator + "cpks"; //生成CPK证书保存地址
    public static String uploadsPath = CERTS_PATH + File.separator + "uploads"; //企业上传文件保存地址
    public static String qrs = CERTS_PATH + File.separator + "qrs"; //二维码保存地址
    public static String pattern = Play.configuration.getProperty("date.format");

    /**
     * 1.政府内部用户-离线信息录入（申请证书）
     */
    public static void apply() {
        Company company = readBody(Company.class);
        Company companySave = getCollection(Company.class).findOne("{companyName:#}",company.getCompanyName()).as(Company.class);
        if(companySave == null) {
            company.save();
            created(company);
            renderJSON("{\"success\":\"ok\"}");
        }else{
            render("{\"msg\":\"此公司名已被注冊！\"}");
        }
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
        String companyName = params.get(0);
        Date startDate = sdf.parse(params.get(1));
        Date endDate =  sdf.parse(params.get(2));
        Long status = Long.valueOf(params.get(3));

        MongoCursor<Company> mongoCursor = getCollection(Company.class).find(filters,companyName,startDate,endDate,status).limit(limit).skip(offset).as(Company.class);
        response.setHeader("X-Total-Count",String.valueOf(mongoCursor.count()));

        List<Company> companies = StreamSupport.stream(mongoCursor.spliterator(),false).collect(Collectors.toList());
        renderJSON(companies);
    }

    /**
     * 2.1 政府内部用户-证书审批
     * @param ids 证书Ids
     * @Description 修改证书的状态（是否审批通过）
     *              若审批通过，生成相应的tradeSK,productSK,matrix保存到数据库中
     */
    public static void approve(@Required String ids) throws IOException {

        Integer status = Integer.valueOf(request.params.get("status"));//获取操作状态
        String[] idArr = ids.split(",");
        Date modifyTime = new Date();

        for(String id : idArr) {
            if(status == 1) {//审批通过（status=1）：生成相应的CPK证书信息
                /**
                 * 调用接口生成 tradeSk,productSK,Matrix
                 */
                Company company = getCollection(Company.class).findOne("{companyId:#}",id).as(Company.class);
//                Logger.info(cpkCores.generateSkById(""));
                String tradeSK = cpkCores.generateSkById(company.getTradeSK()).toString();
                String productSK = cpkCores.generateSkById(company.getProductSK()).toString();
                String matrix = CpkMatrixsFactory.generateCpkMatrix().toJson();
                /**
                 * 更新记录
                 */
                getCollection(Company.class).update("{companyId:#}",id).multi().with("{$set:{status:#,modifyTime:#,tradeSK:#,productSK:#}}",status,modifyTime,tradeSK,productSK).isUpdateOfExisting();

                /**
                 * 保存相应的种子矩阵信息
                 */
                SeedMatrix seedMatrix = new SeedMatrix();
                seedMatrix.setCertId(id);
                seedMatrix.setMatrix(matrix);
                seedMatrix.save();//seedMatrix應該不會重複？

            }else {//只更新状态
                getCollection(Company.class).update("{companyId:#}",id).multi().with("{$set:{status:#,modifyTime:#}}",status,modifyTime).isUpdateOfExisting();
            }
        }
    }

    /**
     * 3.查询企业的证书信息
     * @param companyId 企业的Id
     */
    public static void get(@Required String companyId) {
        Company company =  getCollection(Company.class).findOne("{companyId:#}",companyId).as(Company.class);
        if (company == null) {
            notFound(companyId);
        }else {
            renderJSON(company);
        }
    }

    /**
     * 4.证书导入
     * @param attachment 导入的Zip包
     * @param companyId 企业Id
     */
    public static void certsImport(@Required Integer companyId,@Required File attachment) throws IOException {
        String remark = request.params.get("remark");
        Company company = getCollection(Company.class).findOne("{companyId:#}",String.valueOf(companyId)).as(Company.class);
        if(StringUtils.isEmpty(remark)) {
            getCollection(Company.class).update(new ObjectId(company.getIdAsStr())).multi().with("{$set:{certRemark:#}}",remark).isUpdateOfExisting();
        }
        unZip(attachment);
        renderJSON("{\"success\":\"ok\"}");
    }

    /**
     * 5.附件上传
     * @param attachment 上传的附件
     */
    public static void attachment(File attachment) throws IOException {
        Boolean isZip = attachment.getName().endsWith(".zip");
        if(isZip) {//上传的是zip,需要解压
            unZip(attachment);
            renderJSON("{\"success\":\"ok\"}");
        }else {
            renderJSON("{\"fail\":\"ok\"}");
        }
    }

    /**
     * 通用的zip解压
     * @param attachment 上传的文件
     * @throws IOException
     */
    static void unZip(File attachment) throws IOException {
        String storeDir = uploadsPath + File.separator + attachment.getName().substring(0,attachment.getName().indexOf(".")); //上传文件的保存目录
        ZipFile zipFile = new ZipFile(attachment); //实例化ZipFile对象
        InputStream input = null; //定义输入流，读取每一个ZipEntry
        ZipEntry entry = null; //每一个压缩实体
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(attachment)); //实例化ZipInputStream
        while((entry = zipInput.getNextEntry()) != null) { //得到一个压缩实体
            File outFile = new File(storeDir + File.separator + entry.getName()); //定义输出的文件路径
            if(!outFile.getParentFile().exists()) {  //如果输出文件夹不存在
                outFile.getParentFile().mkdirs(); //创建文件夹
            }
            if(!outFile.exists()) {  //判断输出文件是否存在
                outFile.createNewFile(); //创建文件
            }
            input = zipFile.getInputStream(entry); //得到每一个实体的输入流
            OutputStream out = new FileOutputStream(outFile); //实例化文件输出流
            int temp = 0;
            while((temp=input.read())!=-1) {
                out.write(temp);
            }
            input.close(); //关闭输入流
            out.close(); //关闭输出流
        }
        input.close() ;
    }
    /**
     * 6.证书(压缩包文件)下载：仅针对审批通过的证书
     * @param ids 证书 ids
     */
    public static void download(@Required String ids) throws IOException {
        Company company = getCollection(Company.class).findOne("{companyId:#}",String.valueOf(ids)).as(Company.class);//获取相应的证书信息
        SeedMatrix seedMatrix = getCollection(SeedMatrix.class).findOne("{certId:#}",ids).as(SeedMatrix.class);//读取相应的种子矩阵
        notFoundIfNull(company);
        String[] strs = {company.getTradeSK(),company.getProductSK(),seedMatrix.getMatrix()};
        String[] names ={"tradeSK","productSK","seedMatrix"};
        /**
         * 将证书信息写入到 zip
         */
        File zipFile = new File(certFilesPath + File.separator + company.getCompanyName() + ".zip");//定义压缩文件
        if(!zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }else if(!zipFile.exists()) {
            zipFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos, Charsets.UTF_8);
        for (int i = 0; i < names.length; i++) {
            zos.putNextEntry(new ZipEntry(names[i]));
            IOUtils.write(strs[i].getBytes(), zos);//将字节数组写入到输出流
            zos.closeEntry();
        }
        zos.close();
        renderBinary(new File(zipFile.getName()));
    }

    /**
     * 单个二维码生成
     * @param moduleId 流水号
     */
    public static void singleCode(@Required String moduleId) throws IOException {
        /**
         * 调用接口生成私钥、生成签名
         */
        //BigInteger sk = cpkCores.generateSkById(moduleId);
        BigInteger sk = new BigInteger("10257143042367729756995237081169812210796950325350267523786909699787585200702");
        Pair sign = new Pair(new BigInteger("9310921905917252303491162144013145326732598251108068678882953262814947792966"),
                new BigInteger("31086022497327937304262673378360316583063609211805046364923412540687273505634"));
        /**
         * 调用 generateQRcode(OutputStream fos,String moduleId,Pair pair)生成二维码
         */
        FileOutputStream fos = null;
        File file = new File(certFilesPath + File.separator + "qr.png");
        if(!file.exists()) {
            file.createNewFile();
        }
        fos = new FileOutputStream(file);
        //生成二维码接口
        //cpkCores.generateQRcode(fos, moduleId , sign);
        Logger.info("picture turn to base64: ",Images.toBase64(file));
        fos.close();
        /**
         * 将 moduleId、qr 保存到数据库并返回
         */
        Module module = getCollection(Module.class).findOne("{moduleId:#}",moduleId).as(Module.class);
        if(module == null) {
            Module module1 = new Module();
            module1.setModuleId(moduleId);
            module1.setQr(Images.toBase64(file));
            Logger.info("picture turn to base64-----: ",Images.toBase64(file));
            module1.save();
        }
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
