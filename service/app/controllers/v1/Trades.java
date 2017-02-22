package controllers.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.mongodb.DBObject;
import controllers.api.API;
import models.*;
import models.Package;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import org.jongo.RawResultHandler;
import play.Logger;
import play.Play;
import play.data.binding.As;
import play.data.validation.Range;
import play.data.validation.Required;
import utils.SafeGuard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static play.modules.jongo.BaseModel.getCollection;
import static play.modules.jongo.BaseModel.getJongo;
/**
 * Created by xudongmei on 2016/12/13.
 */
public class Trades extends API {

    public static String pattern = Play.configuration.getProperty("date.format");

    /**
     * 1.新增交易信息
     */
    public static void save() {
        Trade trade = readBody(Trade.class);
        trade.save();
        created(trade);
    }

    /**
     * 2.获取交易信息
     */
    public static void list(String filters, @As(value = ",") List<String> params, @Range(min = 0,max = 100) Integer limit, @Range(min = 0) Integer offset) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if(Strings.isNullOrEmpty(filters)){
            filters="{from: {$regex: #},to: {$regex: #},_created:{$gte:#},_created:{$lte:#},id:{$gte:#},id:{$lte:#}}";
        }else {
            filters = SafeGuard.safeFilters(filters);
        }
        if(StringUtils.countMatches(filters,"#") != params.size()){
            badRequest("filters args size should equals params size!");
        }
        //todo: 处理 params中的数据类型
        String from = params.get(0);
        String to = params.get(1);
        Date startDate = sdf.parse(params.get(2));
        Date endDate = sdf.parse(params.get(3));
        String startModuleId = params.get(4);
        String endModuleId = params.get(5);

        MongoCursor<Trade> mongoCursor = getCollection(Trade.class).find(filters,from,to,startDate,endDate,startModuleId,endModuleId).limit(limit).skip(offset).as(Trade.class);
        response.setHeader("X-Total-Count",String.valueOf(mongoCursor.count()));

        List<Trade> trades = StreamSupport.stream(mongoCursor.spliterator(),false).collect(Collectors.toList());
        renderJSON(trades);
    }

    /**
     * 3.获取单个交易信息
     * @param id
     */
    public static void get(@Required String id) {
        Trade trade =  getCollection(Trade.class).findOne(new ObjectId(id)).as(Trade.class);
        if (trade == null) {
            notFound(id);
        }else {
            renderJSON(trade);
        }
    }

    /**
     * 4.获取流量分析图
     */
    public static void flowAnalysis(String filters, @As(value = ",") List<String> params) throws ParseException {
        if(Strings.isNullOrEmpty(filters)){
            filters="{year: {$regex: #},from: {$regex: #}}";
        }else {
            filters = SafeGuard.safeFilters(filters);
        }
        if(StringUtils.countMatches(filters,"#") != params.size()){
            badRequest("filters args size should equal param's size!");
        }
        String year = params.get(0);
        String companyName = params.get(1);
        Double longitude = null;
        Double latitude = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date startDate = sdf.parse(Integer.parseInt(year) + "-01-01");
        Date endDate = sdf.parse(Integer.parseInt(year) + "-12-31");
        Company company = getCollection(Company.class).findOne("{companyName: #}",companyName).as(Company.class);
        if (company == null) {
            notFound(companyName);
        }else {
            longitude = company.getLongitude();
            latitude = company.getLatitude();
        }
        String command = "{aggregate: \"trade\",pipeline: [{ $project: {from:1, to: 1,_created:1}},{$match:{from:{$regex: #},_created:{$gte:#},_created:{$lte:#}}},{$group: { _id: \"$to\", count: { $sum : 1}}}],cursor: { }}";
        DBObject result = getJongo().runCommand(command,companyName,startDate,endDate).map(new RawResultHandler<DBObject>());
        DBObject retval = (DBObject) result.get("cursor");
        String ultiresult = retval.get("firstBatch").toString();
        renderJSON("{"+"\"geos\": {\"name\": "+"\""+companyName+"\""+",\"longitude\":" +longitude+",\"latitude\": "+latitude+"}" + ","+"\"data\":" + ultiresult +"}");
    }

    /**
     * 5.获取密度分析图
     */
    public static void densityAnalysis(String filters, @As(value = ",") List<String> params) throws ParseException {
        if (Strings.isNullOrEmpty(filters)) {
            filters = "{year: {$regex: #},companyName: {$regex: #}}";
        } else {
            filters = SafeGuard.safeFilters(filters);
        }
        if (StringUtils.countMatches(filters, "#") != params.size()) {
            badRequest("filters args size should equal param's size!");
        }
        String year = params.get(0);
        String companyName = params.get(1);
        Double longitude = null;
        Double latitude = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date startDate = sdf.parse(Integer.parseInt(year) + "-01-01");
        Date endDate = sdf.parse(Integer.parseInt(year) + "-12-31");
        Company company = getCollection(Company.class).findOne("{companyName: #}", companyName).as(Company.class);
        if (company == null) {
            notFound(companyName);
        } else {
            longitude = company.getLongitude();
            latitude = company.getLatitude();
        }
//        List<Trade> trades = Lists.newArrayList((Iterable<Trade>) getCollection(Trade.class).aggregate("{$match:{from: #，_created:{$gte:#},_created:{$lte:#}}}",companyName,startDate,endDate)
//                .and("{$group:{_id: '$to',count:{$sum:1}}}").and("{$project:{province: #,count}}",getCollection(Company.class).findOne().as(Company.class).getProvince()));
        String command = "{aggregate: \"trade\",pipeline: [{ $project: {from:1, to: 1,_created:1}},{$match:{from:{$regex: #},_created:{$gte:#},_created:{$lte:#}}},{$group: { _id: \"$to\", count: { $sum : 1}}}],cursor: { }}";
       /* DB db = new MongoClient().getDB("local");
        Jongo jongo = new Jongo(db);*/
        DBObject firstBatch = (DBObject)((DBObject)getJongo().runCommand(command, companyName, startDate, endDate).map(new RawResultHandler<DBObject>()).get("cursor")).get("firstBatch");
        List l=(List) firstBatch;
        JSONArray jsonArray = JSON.parseArray(l.toString());
        Map<String,Integer> map = new HashMap<>();

        for(int i = 0;i<l.size();i++)
        {
            jsonArray.getJSONObject(i).put("_id",getCollection("company").findOne("{companyName: #}",jsonArray.getJSONObject(i).get("_id")).as(Company.class).getProvince());
//            getCollection(Company.class).insert(jsonArray.getJSONObject(i).values());
        }
        Logger.info(jsonArray.toString());
//        Integer point = null;
        for(int i = 0;i<jsonArray.size();i++) {
            Object key = jsonArray.getJSONObject(i).get("_id");
            Object value = Integer.parseInt(jsonArray.getJSONObject(i).get("count").toString());
            if (map.containsKey(key)) {
                map.put(key.toString(), map.get(key).intValue() + Integer.parseInt(value.toString()));
            } else {
                map.put(key.toString(), Integer.parseInt(value.toString()));
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String result  = null;
        try {
            result  = objectMapper.writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        JSONArray jsonArray1 = new JSONArray();
//        for(int i = 0;i<map.size();i++)
//        {
//            jsonArray1.add(i,map.entrySet());
//        }
//        Logger.info(jsonArray1.toString());

//        getCollection(Company.class).aggregate("{ $project : {_id:0, 0 : 1 , 1 : 1 } }").and("{$group:{_id:\"$1\",count:{$sum:\"$0\"}}}");
//        String countProvinceCommond = "{aggregate: \"company\",pipeline: [{ $project: {_id:0, 0 : 1 , 1 : 1 } },{$group:{_id:\"$1\",count:{$sum:\"$0\"}}}],cursor: { }}";
//        DBObject retval = (DBObject)((DBObject)getJongo().runCommand(countProvinceCommond).map(new RawResultHandler<DBObject>()).get("cursor")).get("firstBatch");
//        JSONArray countProvince = JSON.parseArray(retval.toString());
//        getCollection(Company.class).remove("{0: {$lt:#}}",Integer.MAX_VALUE);//模组数超过Integer.MAX_VALUE会错统
//        countProvince.remove(countProvince.size()-1);
        renderJSON("{"+"\"geos\": {\"name\": "+"\""+companyName+"\""+",\"longitude\":" +longitude+",\"latitude\": "+latitude+"}" + ","+"\"data\":" + result +"}");
    }

    /**
     * 6.上传电池包（与模组对应关系）数据
     */
    public static void packageAndModule() {
        Package packages = readBody(Package.class);
        /**
         * 如果数据库中存在该条信息，默认不保存
         */
        Package pack = getCollection(Package.class).findOne("{packageId:#}",packages.getPackageId()).as(Package.class);
        if(pack == null) {
            packages.save();
            renderJSON("{\"success\":\"ok\"}");
        }else{
            render("{\"msg\":\"该条记录已存在！\"}");
        }
    }

    /**
     * 6.1 包、模组、汽车对应关系离线文件上传-excel
     * @param attachment excel文件
     */
    public static void importExcel(@Required File attachment) throws IOException {
        Integer companyType = Integer.valueOf(request.params.get("companyType"));
        HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(attachment));
        HSSFSheet sheet = wookbook.getSheet("Sheet1");
        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行
        Logger.info("rows : " + rows);
        if (companyType == 0) {//对应电池厂：上传模组与电池包的对应关系
            for (int i = 1; i <= rows; i++) {
                // 读取左上端单元格
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    // 获取到Excel文件中的所有的列
                    int maxcell = row.getLastCellNum();
                    // **读取cell**
                    String packageId = trimZero(getCellValue(row.getCell((short) 0)));// 包ID
                    String packageSpec = trimZero(getCellValue(row.getCell((short) 1)));// 包描述
                    String moduleId = trimZero(getCellValue(row.getCell((short) 2)));// 模组ID
                    String moduleSpec = trimZero(getCellValue(row.getCell((short) 3)));// 模组描述
                    String moduleFacturer = getCellValue(row.getCell((short) 4));// 模组制造商
                    String moduleSec = getCellValue(row.getCell((short) 5));// 模组seconds
                    String moduleNan = getCellValue(row.getCell((short) 6));// 模组nanos
                    String packageFacturer = getCellValue(row.getCell((short) 7));// 包制造商
                    String packageSec = getCellValue(row.getCell((short) 8));// 包seconds
                    String packageNan = getCellValue(row.getCell((short) 9));// 包nanos
                    // 验证 赋值
                    // 如果为空白行则跳出本次循环
                    if (StringUtils.isEmpty(packageId)
                            && StringUtils.isEmpty(moduleId)
                            && StringUtils.isEmpty(moduleFacturer)
                            && StringUtils.isEmpty(moduleSec)
                            && StringUtils.isEmpty(moduleNan)
                            && StringUtils.isEmpty(packageFacturer)
                            && StringUtils.isEmpty(packageSec)
                            && StringUtils.isEmpty(packageNan)) {
                        break;
                    }

                    if (maxcell < 8) {
                        throw new IOException("第" + i + "行的那条数据---数据列数不正确");
                    }
                    // 包ID，模组ID，模组制造商，模组seconds，模组nanos，包制造商，包seconds，包nanos
                    if (StringUtils.isEmpty(packageId)
                            || StringUtils.isEmpty(moduleId)
                            || StringUtils.isEmpty(moduleFacturer)
                            || StringUtils.isEmpty(moduleSec)
                            || StringUtils.isEmpty(moduleNan)
                            || StringUtils.isEmpty(packageFacturer)
                            || StringUtils.isEmpty(packageSec)
                            || StringUtils.isEmpty(packageNan)) {
                        throw new IOException(
                                "第" + i + "行的那条数据数据不能为空（包ID，模组ID，模组制造商，模组seconds，模组nanos，包制造商，包seconds，包nanos这8列不能为空）");
                    }
                    // 包Id必须是大于0的正整数
                    if (!packageId.matches("^\\d+$")) {
                        throw new IOException("第" + i + "行的那条数据包ID必须是大于0的正整数");
                    }
                    // 模组Id必须是大于0的正整数
                    if (!moduleId.matches("^\\d+$")) {
                        throw new IOException("第" + i + "行的那条数据模组ID必须是大于0的正整数");
                    }
                    Package pack = getCollection(Package.class).findOne("{packageId:#}", packageId).as(Package.class);
                    if (pack == null) {
                        /**
                         * 保存信息，并将模组信息保存到数据库
                         */
                        Package pack1 = new Package();
                        pack1.setPackageId(packageId);
                        pack1.setPackageSpec(packageSpec);

                        Module module = new Module();
                        module.setModuleId(moduleId);
                        module.setModuleSpec(moduleSpec);
                        module.setManufacturer(moduleFacturer);
                        module.setTimestamp(new Timestamp(Long.valueOf(moduleSec), Integer.valueOf(moduleNan)));
                        module.save();

                        pack1.addModulesItem(module);
                        pack1.setManufacturer(packageFacturer);
                        pack1.setTimestamp(new Timestamp(Long.valueOf(packageSec), Integer.valueOf(packageNan)));
                        pack1.save();
                    } else {
                        List<Module> moduleOlds = pack.getModules();
                        List<String> moduleOldIds = new ArrayList<>();
                        for (Module temp : moduleOlds) {//将所有已经存在的模组Id保存到List中
                            moduleOldIds.add(temp.getModuleId());
                        }
                        if (!moduleOldIds.contains(moduleId)) {
                            Module module = new Module();
                            module.setModuleId(moduleId);
                            module.setModuleSpec(moduleSpec);
                            module.setManufacturer(moduleFacturer);
                            module.setTimestamp(new Timestamp(Long.valueOf(moduleSec), Integer.valueOf(moduleNan)));
                            module.save();
                            pack.addModulesItem(module);
                            getCollection(Package.class).update(pack.getId()).multi().with("{$set:{modules:#}}", pack.getModules()).isUpdateOfExisting();
                        }
                        //如果已存在，不添加
                    }
                }
            }
        } else if (companyType == 1) { //对应汽车上：上传汽车与模组的对应关系
            for (int i = 1; i <= rows; i++) {
                // 读取左上端单元格
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    // 获取到Excel文件中的所有的列
                    int maxcell = row.getLastCellNum();
                    // **读取cell**
                    String carId = trimZero(getCellValue(row.getCell((short) 0)));// 汽车ID
                    String vin = trimZero(getCellValue(row.getCell((short) 1)));// 车架号
                    String packageId = trimZero(getCellValue(row.getCell((short) 2)));// 包ID
                    String carSpec = trimZero(getCellValue(row.getCell((short) 3)));// 汽车描述
                    String carFacturer = getCellValue(row.getCell((short) 4));// 汽车生产商
                    String carSec = getCellValue(row.getCell((short) 5));// 汽车seconds
                    String carNan = getCellValue(row.getCell((short) 6));// 汽车nanos

                    if (maxcell < 7) {
                        throw new IOException("第" + i + "行的那条数据---数据列数不正确");
                    }
                    // 汽车ID，车架号，包ID，汽车描述，汽车生产商，汽车seconds，汽车nanos
                    if (StringUtils.isEmpty(carId)
                            || StringUtils.isEmpty(vin)
                            || StringUtils.isEmpty(packageId)
                            || StringUtils.isEmpty(carSpec)
                            || StringUtils.isEmpty(carFacturer)
                            || StringUtils.isEmpty(carSec)
                            || StringUtils.isEmpty(carNan)) {
                        throw new IOException(
                                "第" + i + "行的那条数据数据不能为空（汽车ID，车架号，包ID，汽车描述，汽车生产商，汽车seconds，汽车nanos这7列均不能为空）");
                    }
                    Car car = getCollection(Car.class).findOne("{carId:#}", carId).as(Car.class);
                    if (car == null) {//没有该车辆与包的对应关系
                        //添加数据
                        Car car1 = new Car();
                        car1.setCarId(carId);
                        car1.setVin(vin);
                        car1.setPackageId(packageId);
                        car1.setCarSpec(carSpec);
                        car1.setManufacturer(carFacturer);
                        car1.setTimestamp(new Timestamp(Long.valueOf(carSec), Integer.valueOf(carNan)));
                        car1.save();
                    }//如果已存在，不添加
                }
            }
        }
    }


    /**
     * 7.汽车制造商上传汽车与电池包的对应关系
     */
    public static void carAndPackage() {
        Car cars = readBody(Car.class);
        Car car = getCollection(Car.class).findOne("{carId:#}",cars.getCarId()).as(Car.class);
        if(car== null){
            cars.save();
            renderJSON("{\"success\":\"ok\"}");
        }else{
            render("{\"msg\":\"该条记录已存在！\"}");
        }
    }

    /**
     * 8.获取电池包信息
     * @param id packageId
     */
    public static void getPackage(@Required String id) {
        Package Package =  getCollection(Package.class).findOne("{packageId:#}",id).as(Package.class);
        if (Package == null) {
            notFound(id);
        }else {
            renderJSON(Package);
        }
    }

    /**
     * 9.获取汽车信息
     * @param id carId
     */
    public static void getCar(@Required String id) {
        Car car =  getCollection(Car.class).findOne("{carId:#}",id).as(Car.class);
        if (car == null) {
            notFound(id);
        }else {
            renderJSON(car);
        }
    }

    /**
     * 10.新增二维码扫描记录
     */
    public static void addRecord() {
        Scan scan = readBody(Scan.class);
        scan.save();
        created(scan);
    }

    /**
     * 11.查询二维码扫描记录
     */
    public static void getRecord(String filters,Integer limit,Integer offset) {
        filters= SafeGuard.safeFilters(filters);
        limit = SafeGuard.safeLimit(limit);
        offset= SafeGuard.safeOffset(offset);

        MongoCursor<Scan> mongoCursor = getCollection(Scan.class).find(filters).limit(limit).skip(offset).as(Scan.class);
        response.setHeader("X-Total-Count",String.valueOf(mongoCursor.count()));

        List<Scan> scans = StreamSupport.stream(mongoCursor.spliterator(),false).collect(Collectors.toList());
        renderJSON(scans);
    }

    /**
     * 获得Hsscell内容
     * @param cell
     * @return
     */
    public static String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    value = cell.getNumericCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }

    static String trimZero(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return str.replace(".0", "");
        }
        return str;
    }
}
