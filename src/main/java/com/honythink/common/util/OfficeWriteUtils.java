package com.honythink.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.springframework.core.io.ClassPathResource;
import com.honythink.Constants;
import com.honythink.biz.system.dto.InterviewDto;
import com.honythink.db.entity.Resume;

public class OfficeWriteUtils {
    /**
     * 
     * @param path
     * @param record
     * @return
     * @throws IOException
     * @about version ：1.00
     * @auther :
     * @Description ：
     */
    public static String templateResume(String base, Resume record,List<String> templatePaths) throws IOException {
        InputStream is;
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = sdf.format(new Date());
        for (String templatePath : templatePaths) {
            try {
                is = new ClassPathResource(templatePath).getInputStream();
                Map<String, String> replaces = new HashMap<String, String>();
                replaces.put("\\$\\{name\\}", record.getName() == null ? "" : record.getName());
                replaces.put("\\$\\{gender\\}", record.getGender() == null ? "" : record.getGender());
                replaces.put("\\$\\{birthday\\}", record.getBirthday() == null ? "" : record.getBirthday());
                replaces.put("\\$\\{age\\}", record.getAge() == null ? "" : record.getAge());
                replaces.put("\\$\\{marriage\\}", record.getMarriage() == null ? "" : record.getMarriage());
                replaces.put("\\$\\{city\\}", record.getCity() == null ? "" : record.getCity());
                replaces.put("\\$\\{self\\}", record.getSelf() == null ? "" : record.getSelf());
                replaces.put("\\$\\{work\\}", record.getWork() == null ? "" : record.getWork());
                replaces.put("\\$\\{project\\}", record.getProject() == null ? "" : record.getProject());
                replaces.put("\\$\\{educationtime\\}", record.getEducationtime() == null ? "" : record.getEducationtime());
                replaces.put("\\$\\{school\\}", record.getSchool() == null ? "" : record.getSchool());
                replaces.put("\\$\\{major\\}", record.getMajor() == null ? "" : record.getMajor());
                replaces.put("\\$\\{education\\}", record.getEducation() == null ? "" : record.getEducation());
                replaces.put("\\$\\{train\\}", record.getTrain() == null ? "" : record.getTrain());
                String filename = "弘毅知行-"+ record.getName() +now+ "-" + templatePath;
                File f = new File(base);
                if (!f.exists()) {
                    f.mkdirs();
                }
                String str = replay(is, "\\$\\{name\\}", replaces);
                OutputStream os = new FileOutputStream(base + filename);
                inputStreamToWord(new ByteArrayInputStream(str.getBytes("UTF-8")), os);
                sb.append(filename).append("@@@@");
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            }
        }
        if (sb.indexOf("@@@@") > 0) {
            return sb.substring(0, sb.length() - 4);
        } else {
            return sb.toString();
        }
    }

    /**
     * 把is写入到对应的word输出流os中
     * 不考虑异常的捕获，直接抛出
     * 
     * @param is
     * @param os
     * @throws IOException
     */
    private static void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem();
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
        fs.close();
    }

    public static String replay(InputStream in, String pattern, Map<String, String> replaces) throws IOException {
        String content;
        try {
            content = OfficeUtils.getContent(in);
            for (Map.Entry<String, String> entry : replaces.entrySet()) {
                content = content.replaceAll(entry.getKey(), entry.getValue());
            }
            return content;
        } catch (IOException e) {
            throw e;
        }
    }

    public static void writeExcelSummaryCsix(String uuidName, List<InterviewDto> records) throws IOException {
        // ## 重复利用 的对象 ##//
        Workbook wb = null;
        FileOutputStream fileOut = null;
        int startRow = 0;
        /**
         * EXCEL早期版本
         */
        try {
            // excel模板路径
            POIFSFileSystem fs = new POIFSFileSystem(new ClassPathResource(Constants.XLS_WORKBOOK_TEMPLATE_CSIX).getInputStream());
            // ## 创建早期EXCEL的Workbook ##//
            wb = new HSSFWorkbook(fs);
            // ## 获取HSSF和XSSF的辅助类 ##//
            // CreationHelper createHelper = wb.getCreationHelper();
            // ## 创建一个名为“New Sheet”的Sheet ##//
            Sheet sheet = wb.getSheetAt(0);
            short sheetHeight = sheet.getRow(0).getHeight();
            CellStyle cellStyle = wb.createCellStyle();
            DataFormat format= wb.createDataFormat();  
            cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));  
            for (InterviewDto record : records) {
                startRow++;
                /** 第一行 --- CELL创建，数据填充及日期格式 **/
                Row row = sheet.createRow(startRow);
                row.setHeight(sheetHeight);
                // ## 在相应的位置填充数据 ##//
                // 推荐日期
                Cell datecell = row.createCell(0);
                datecell.setCellStyle(cellStyle);
                datecell.setCellValue(record.getRecommendTime() == null ? null : record.getRecommendTime());
                // 推荐客户名称
                 row.createCell(1).setCellValue(record.getName() == null ? "" : record.getName());
                // 推荐供应商
                 row.createCell(2).setCellValue(Constants.HONYTHINK);
                // 推荐职位
                 row.createCell(3).setCellValue(record.getPosition() == null ? "" : record.getPosition());
                // 姓名
                row.createCell(4).setCellValue(record.getResumeName() == null ? "" : record.getResumeName());
                // 性别
                row.createCell(5).setCellValue(record.getResumeGender()== null ? "" : record.getResumeGender());
                // 电话
                row.createCell(6).setCellValue(record.getResumeMobile() == null ? "" : record.getResumeMobile());
                // 工作状态（在职、离职）
                 row.createCell(7).setCellValue(record.getStatus() == null ? "" : record.getStatus());
                // 工作年限
                row.createCell(8).setCellValue(record.getResumeSeniority() == null ? "" : record.getResumeSeniority());
                // 学校
                row.createCell(9).setCellValue(record.getResumeSchool() == null ? "" : record.getResumeSchool());
                // 学位
                row.createCell(10).setCellValue(record.getEducation() == null ? "" : record.getEducation());
                // 基本情况（工作意向、态度、家庭住址等）
                row.createCell(11).setCellValue(record.getResumeSelf() == null ? "" : record.getResumeSelf());
                // 薪资
                 row.createCell(12).setCellValue(record.getSalary() == null ? 0 : record.getSalary());
                // 报价
                 row.createCell(13).setCellValue(null == record.getCover()? 0 : record.getCover());
            }
            // ## 将文件写到硬盘上 ##//
            File f = new File(Constants.RESUME_TEMPLATE + uuidName + Constants.PATH_SEPERATOR);
            if (!f.exists()) {
                f.mkdirs();
            }
            fileOut = new FileOutputStream(Constants.RESUME_TEMPLATE + uuidName + Constants.PATH_SEPERATOR + Constants.XLS_WORKBOOK_EXPORT_CSIX);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
    public static void writeExcelSummarySelf(String uuidName, List<InterviewDto> records) throws IOException {
        // ## 重复利用 的对象 ##//
        Workbook wb = null;
        FileOutputStream fileOut = null;
        int startRow = 0;
        /**
         * EXCEL早期版本
         */
        try {
            // excel模板路径
            POIFSFileSystem fs = new POIFSFileSystem(new ClassPathResource(Constants.XLS_WORKBOOK_TEMPLATE_SELF).getInputStream());
            // ## 创建早期EXCEL的Workbook ##//
            wb = new HSSFWorkbook(fs);
            // ## 获取HSSF和XSSF的辅助类 ##//
            // CreationHelper createHelper = wb.getCreationHelper();
            // ## 创建一个名为“New Sheet”的Sheet ##//
            Sheet sheet = wb.getSheetAt(0);
            short sheetHeight = sheet.getRow(1).getHeight();
            CellStyle cellStyle = wb.createCellStyle();
            DataFormat format= wb.createDataFormat();  
            cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));  
            for (InterviewDto record : records) {
                startRow++;
                /** 第一行 --- CELL创建，数据填充及日期格式 **/
                Row row = sheet.createRow(startRow);
                row.setHeight(sheetHeight);
                // ## 在相应的位置填充数据 ##//
                // 推荐日期
                Cell datecell = row.createCell(0);
                datecell.setCellStyle(cellStyle);
                datecell.setCellValue(record.getRecommendTime() == null ? null : record.getRecommendTime());
                // 姓名
                row.createCell(1).setCellValue(record.getResumeName() == null ? "" : record.getResumeName());
                // 电话
                row.createCell(2).setCellValue(record.getResumeMobile() == null ? "" : record.getResumeMobile());
                // 职位
                row.createCell(3).setCellValue(record.getPosition() == null ? "" : record.getPosition());
                // 客户
                row.createCell(4).setCellValue(record.getName() == null ? "" : record.getName());
                // 推荐人
                row.createCell(5).setCellValue(record.getRealnameHr() == null ? "" : record.getRealnameHr());
                // 学历
                row.createCell(6).setCellValue(record.getEducation() == null ? "" : record.getEducation());
                // 毕业时间
                row.createCell(7).setCellValue(record.getEducationtime() == null ? "" : record.getEducationtime());
                // 薪资
                row.createCell(8).setCellValue(record.getSalary() == null ? 0 : record.getSalary());
                // 到岗时间
                row.createCell(9).setCellValue(record.getWorkTime() == null ? "" : record.getWorkTime());
            }
            // ## 将文件写到硬盘上 ##//
            File f = new File(Constants.RESUME_TEMPLATE + uuidName + Constants.PATH_SEPERATOR);
            if (!f.exists()) {
                f.mkdirs();
            }
            fileOut = new FileOutputStream(Constants.RESUME_TEMPLATE + uuidName + Constants.PATH_SEPERATOR + Constants.XLS_WORKBOOK_EXPORT_SELF);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
    
    public static void writeExcelSummaryCustomer(String uuidName, List<InterviewDto> records) throws IOException {
        // ## 重复利用 的对象 ##//
        Workbook wb = null;
        FileOutputStream fileOut = null;
        int startRow = 0;
        /**
         * EXCEL早期版本
         */
        try {
            // excel模板路径
            POIFSFileSystem fs = new POIFSFileSystem(new ClassPathResource(Constants.XLS_WORKBOOK_TEMPLATE_CUSTOMER).getInputStream());
            // ## 创建早期EXCEL的Workbook ##//
            wb = new HSSFWorkbook(fs);
            // ## 获取HSSF和XSSF的辅助类 ##//
            // CreationHelper createHelper = wb.getCreationHelper();
            // ## 创建一个名为“New Sheet”的Sheet ##//
            Sheet sheet = wb.getSheetAt(0);
            short sheetHeight = sheet.getRow(1).getHeight();
            CellStyle cellStyle = wb.createCellStyle();
            DataFormat format= wb.createDataFormat();  
            cellStyle.setDataFormat(format.getFormat("yyyy年m月d日"));  
            for (InterviewDto record : records) {
                startRow++;
                /** 第一行 --- CELL创建，数据填充及日期格式 **/
                Row row = sheet.createRow(startRow);
                row.setHeight(sheetHeight);
                // ## 在相应的位置填充数据 ##//
                // 推荐日期
                Cell datecell = row.createCell(0);
                datecell.setCellStyle(cellStyle);
                datecell.setCellValue(record.getRecommendTime() == null ? null : record.getRecommendTime());
                // 姓名
                row.createCell(1).setCellValue(record.getResumeName() == null ? "" : record.getResumeName());
                // 职位
                row.createCell(2).setCellValue(record.getPosition() == null ? "" : record.getPosition());
                // 客户
                row.createCell(3).setCellValue(record.getName() == null ? "" : record.getName());
                // 推荐人
                row.createCell(4).setCellValue(record.getRealnameHr() == null ? "" : record.getRealnameHr());
                // 学历
                row.createCell(5).setCellValue(record.getEducation() == null ? "" : record.getEducation());
                // 毕业时间
                row.createCell(6).setCellValue(record.getEducationtime() == null ? "" : record.getEducationtime());
                // 到岗时间
                row.createCell(7).setCellValue(record.getWorkTime() == null ? "" : record.getWorkTime());
            }
            // ## 将文件写到硬盘上 ##//
            File f = new File(Constants.RESUME_TEMPLATE + uuidName + Constants.PATH_SEPERATOR);
            if (!f.exists()) {
                f.mkdirs();
            }
            fileOut = new FileOutputStream(Constants.RESUME_TEMPLATE + uuidName + Constants.PATH_SEPERATOR + Constants.XLS_WORKBOOK_EXPORT_CUSTOMER);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
}