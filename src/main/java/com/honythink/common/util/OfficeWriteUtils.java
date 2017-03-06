package com.honythink.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.honythink.Constants;
import com.honythink.db.entity.Resume;

public class OfficeWriteUtils {


    /**
     * 
     * @param path
     * @param record
     * @return
     * @about version ：1.00
     * @auther :
     * @Description ：
     */
    public static String templateResume(String base, Resume record) {
        InputStream is;
        String[] paths = Constants.TEMPLATE_ALL;
        StringBuffer sb = new StringBuffer();
        for (String templatePath : paths) {
            try {
                is = new ClassPathResource(templatePath).getInputStream();
                // is = new FileInputStream(templatePath);
                // HWPFDocument doc = new HWPFDocument(is);
                // Range range = doc.getRange();
                // // 把range范围内的${reportDate}替换为当前的日期
                // range.replaceText("${name}", record.getName() == null ? "" : record.getName());
                // range.replaceText("${gender}", record.getGender() == null ? "" : record.getGender());
                // range.replaceText("${birthday}", record.getBirthday() == null ? "" : record.getBirthday());
                // range.replaceText("${age}", record.getAge() == null ? "" : record.getAge());
                // range.replaceText("${marriage }", record.getMarriage() == null ? "" : record.getMarriage());
                // range.replaceText("${city}", record.getCity() == null ? "" : record.getCity());
                // range.replaceText("${self}", record.getSelf() == null ? "" : record.getSelf());
                // range.replaceText("${work}", record.getWork() == null ? "" : record.getWork());
                // range.replaceText("${project}", record.getProject() == null ? "" : record.getProject());
                // range.replaceText("${educationtime}", record.getEducationtime() == null ? "" :
                // record.getEducationtime());
                // range.replaceText("${school}", record.getSchool() == null ? "" : record.getSchool());
                // range.replaceText("${major}", record.getMajor() == null ? "" : record.getMajor());
                // range.replaceText("${education}", record.getEducation() == null ? "" : record.getEducation());
                // range.replaceText("${train}", record.getTrain() == null ? "" : record.getTrain());
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
                String filename = "弘毅知行-"+ record.getId() + record.getName() + "-" + templatePath;
                File f = new File(base);
                if (!f.exists()) {
                    f.mkdirs();
                }
                String str = replay(is, "\\$\\{name\\}", replaces);
                // String str ="<html><head><style></style></head><body></body></html>";
                OutputStream os = new FileOutputStream(base + filename);
                inputStreamToWord(new ByteArrayInputStream(str.getBytes("UTF-8")), os);
                // OutputStream os = new FileOutputStream(base + filename);
                sb.append(filename).append("@@@@");
                // 把doc输出到输出流中
                // doc.write(os);
                // closeStream(os);
                // closeStream(is);
                // closeDoc(doc);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
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
        // 对应于org.apache.poi.hdf.extractor.WordDocument
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
    }

    public static String replay(InputStream in, String pattern, Map<String, String> replaces) {
        String content;
        try {
            content = OfficeUtils.getContent(in);
            for (Map.Entry<String, String> entry : replaces.entrySet()) {
                content = content.replaceAll(entry.getKey(), entry.getValue());
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeExcel(String uuidName,List<Resume> resumes) {
        // ## 重复利用 的对象 ##//
        Workbook wb = null;
        FileOutputStream fileOut = null;
        int startRow = 0;
        File fi;
        /**
         * EXCEL早期版本
         */
        try {
            //excel模板路径  
            fi=new ClassPathResource(Constants.XLS_WORKBOOK_TEMPLATE).getFile();
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fi));  
            // ## 创建早期EXCEL的Workbook ##//
            wb = new HSSFWorkbook(fs);
            // ## 获取HSSF和XSSF的辅助类 ##//
//            CreationHelper createHelper = wb.getCreationHelper();
            // ## 创建一个名为“New Sheet”的Sheet ##//
            Sheet sheet = wb.getSheetAt(0);
            short sheetHeight = sheet.getRow(1).getHeight();
            for(Resume record:resumes){
                startRow++;
                /** 第一行 --- CELL创建，数据填充及日期格式 **/
                Row row = sheet.createRow(startRow);
                row.setHeight(sheetHeight);
                // ## 在相应的位置填充数据 ##//
                //推荐日期
//                row.createCell(0).setCellValue(record.getName() == null ? "" : record.getName());
                //推荐客户名称
//                row.createCell(1).setCellValue(record.getName() == null ? "" : record.getName());
                //推荐供应商
//                row.createCell(2).setCellValue(record.getName() == null ? "" : record.getName());
                //推荐职位
//                row.createCell(3).setCellValue(record.getName() == null ? "" : record.getName());
                //姓名
                row.createCell(4).setCellValue(record.getName() == null ? "" : record.getName());
                //性别
                row.createCell(5).setCellValue(record.getGender() == null ? "" : record.getGender());
                //电话
                row.createCell(6).setCellValue(record.getMobile() == null ? "" : record.getMobile());
                //工作状态（在职、离职）
//                row.createCell(7).setCellValue(record.getResidence() == null ? "" : record.getGender());
                //工作年限
                row.createCell(8).setCellValue(record.getSeniority() == null ? "" : record.getSeniority());
                //学校
                row.createCell(9).setCellValue(record.getSchool() == null ? "" : record.getSchool());
                //学位
                row.createCell(10).setCellValue(record.getEducation() == null ? "" : record.getEducation());
                //基本情况（工作意向、态度、家庭住址等）
//                row.createCell(11).setCellValue(createHelper.createRichTextString(record.getSelf() == null ? "" : record.getSelf()));
                row.createCell(11).setCellValue(record.getSelf() == null ? "" : record.getSelf());
                //薪资
//                row.createCell(12).setCellValue(record.getGender() == null ? "" : record.getGender());
                //报价
//                row.createCell(13).setCellValue(createHelper.createRichTextString("CreationHelper---字符串"));
            }
            // ## 将文件写到硬盘上 ##//
            fileOut = new FileOutputStream(Constants.RESUME_TEMPLATE+uuidName+Constants.XLS_WORKBOOK_EXPORT);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建相应格式的CELL
     */
    public void createCell(Workbook wb, Row row, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        // ## 给CELL赋值 ##//
        cell.setCellValue("对齐排列");
        CellStyle cellStyle = wb.createCellStyle();
        // ## 设置水平对齐方式 ##//
        cellStyle.setAlignment(halign);
        // ## 设置垂直对齐方式 ##//
        cellStyle.setVerticalAlignment(valign);
        // ## 添加CELL样式 ##//
        cell.setCellStyle(cellStyle);
    }

    /**
     * 关闭输入流
     * 
     * @param is
     */
    private static void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * 
     * @param os
     */
    private static void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * 
     * @param os
     */
    private static void closeDoc(HWPFDocument doc) {
        if (doc != null) {
            try {
                doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}