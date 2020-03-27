package com.xus.learning.export;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.util.stream.Collectors.toMap;


/**

 * @author 青越 2019/12/23
 */
public class export {

    public static void main(String[] args) {

        // * 1. 调用dubbo接口查询数据
        // * telnet localhost dubbo端口
        // * invoke com.tuya.sellercenter.client.merchant.service.IMerchantService.listRecords({"mallCode":"M9181dfbgyp8z","offset":0,"limit":200})
        // * invoke com.tuya.sellercenter.client.merchant.service.IMerchantService.countRecords({"mallCode":"M9181dfbgyp8z"})
        // * 2. 在kibana上着日志 服务器上的乱码
        // * 3. 拿出json数据通过程序把json数据拿到内存转化为只需要导出的数据 在导出json文件
        // * 4. 把json数据拿到在线转csv网站https://www.bejson.com/json/json2excel/
        bDataExport();

        //对数据库类目做多语言的匹配 再导出为json文件
        categoryExport();
    }


    /**
     * 对数据库导出的类目匹配多语言
     */
    public static void categoryExport() {
        List<CategoryModel>  categoryModelLangList = excel2Json("/Users/xus/Documents/涂鸦/项目/OEMAPP/CatetgoryLanguage.xls");
        Map<String, CategoryModel> categoryModelMap = categoryModelLangList.stream().collect(toMap(CategoryModel::getKey, e -> e));
        List<CategoryModel> categoryList = excel2Json("/Users/xus/Documents/涂鸦/项目/OEMAPP/CategoryDatabase.xls");
        for (CategoryModel categoryModel : categoryList) {
            CategoryModel categoryLang = categoryModelMap.get(categoryModel.getKey());
            if (categoryLang == null) {
                System.out.println("warn:" + categoryModel.getKey());
            } else {
                categoryModel.setEnglishValue(categoryLang.getEnglishValue());
            }
        }
        System.out.println(categoryList.size());
        try {
            writeJsonStream(categoryList, "/Users/xus/Documents/涂鸦/项目/链路监控/dataRes.json");
        } catch (Exception e){

        }

    }

    /**
     * 某大B下导出小b数据
     */
    public static void bDataExport() {
        File file=new File("/Users/xus/Documents/涂鸦/项目/链路监控/data.json");
        BufferedReader reader;
        try {
            Type srItemsStandardType = new TypeToken<List<Data>>() {}.getType();
            reader = new BufferedReader(new FileReader(file));
            Gson gson = new GsonBuilder().create();
            List<Data> dataList = gson.fromJson(reader, srItemsStandardType);
            System.out.println("总数量:" + dataList.size());
            List<DataRes> dataResList = new ArrayList<>();
            for (Data e : dataList) {
                DataRes dataRes = new DataRes();
                BeanUtils.copyProperties(e, dataRes);
                dataRes.setIdCard("身份证: " + e.getIdCard());
                Field[]  fields = dataRes.getClass().getDeclaredFields();
                for (Field  field :  fields) {
                    field.setAccessible(true);
                    try {
                        if (field.get(dataRes) ==  null)  {
                            field.set(dataRes,"");
                        }
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
                if (e.getApplyStatus() == 0) {
                    dataRes.setApplyStatus("待审核");
                } else if (e.getApplyStatus() == 1) {
                    dataRes.setApplyStatus("审核通过");
                } else if (e.getApplyStatus() == 2) {
                    dataRes.setApplyStatus("审核不通过");
                }
                if (e.getChannelType() == 0) {
                    dataRes.setChannelType("个人合作者");
                } else if (e.getChannelType() == 1) {
                    dataRes.setChannelType("授权体验店");
                } else if (e.getChannelType() == 2) {
                    dataRes.setChannelType("渠道合作商");
                }
                dataRes.setGmtCreate(new DateTime(Long.parseLong(e.getGmtCreate())).toString("yyyy-MM-dd HH:mm:ss"));
                dataResList.add(dataRes);
            }
            try {
                writeJsonStream(dataResList, "/Users/xus/Documents/涂鸦/项目/链路监控/dataRes.json");
            } catch (Exception e) {
            }

        } catch (FileNotFoundException ex) {

        } finally {

        }
    }

    public  static <T> void writeJsonStream(List<T> dataList, String path) throws IOException {

        File myFile = new File(path);
        myFile.createNewFile();
        FileOutputStream fOut = new FileOutputStream(myFile);
        Gson gson = new Gson();
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(fOut, "UTF-8"));
        writer.setIndent("  ");
        writer.beginArray();
        for (T message : dataList) {
            gson.toJson(message, DataRes.class, writer);
        }
        writer.endArray();
        writer.close();
    }


    public static List<CategoryModel>  excel2Json(String name) {
        Sheet sheet;
        Workbook book;
        Cell cell1, cell2, cell3, cell4, cell5,cell6,cell7,cell8;
        List<CategoryModel> categoryModelList = new ArrayList<>();
        try {
            //为要读取的excel文件名  "F://a.xls"
            book = Workbook.getWorkbook(new File(name));

            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
            sheet = book.getSheet(0);

            for (int i = 1; i < sheet.getRows(); i++) {
                CategoryModel categoryModel = new CategoryModel();
                //获取每一行的单元格
                cell1 = sheet.getCell(0, i);
                cell2 = sheet.getCell(1, i);
                cell3 = sheet.getCell(2, i);
                cell4 = sheet.getCell(3, i);

                categoryModel.setBase(cell1.getContents());
                categoryModel.setKey(cell2.getContents().trim());
                categoryModel.setChineseValue(cell3.getContents());
                categoryModel.setEnglishValue(cell4.getContents());
                categoryModelList.add(categoryModel);
            }
            System.out.println(categoryModelList.size());
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryModelList;
    }

    public static class CategoryModel {

        private String base;
        private String key;
        private String chineseValue;
        private String englishValue;

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getChineseValue() {
            return chineseValue;
        }

        public void setChineseValue(String chineseValue) {
            this.chineseValue = chineseValue;
        }

        public String getEnglishValue() {
            return englishValue;
        }

        public void setEnglishValue(String englishValue) {
            this.englishValue = englishValue;
        }
    }

    public class Data {
        private String uid;
        private String address;
        private String company;
        private String mobile;
        private String name;
        private String gmtCreate;
        private Integer channelType;
        private Integer applyStatus;
        private String idCard;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Integer getChannelType() {
            return channelType;
        }

        public void setChannelType(Integer channelType) {
            this.channelType = channelType;
        }

        public Integer getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(Integer applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }
    }

    public static class DataRes {
        private String uid="";
        private String address;
        private String company;
        private String mobile="";
        private String name="";
        private String gmtCreate="";
        private String channelType="";
        private String applyStatus="";
        private String idCard="";

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }



    }


}
