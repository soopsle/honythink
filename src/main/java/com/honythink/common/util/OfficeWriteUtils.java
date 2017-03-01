package com.honythink.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.springframework.util.ResourceUtils;

import com.honythink.Constants;
import com.honythink.db.entity.Resume;

public class OfficeWriteUtils {
//    public static void main(String[] args) {
//        try {
//            InputStream is =  new FileInputStream(ResourceUtils.getURL("classpath:template_csix.doc").getPath());
//            HWPFDocument doc;
//            try {
//                doc = new HWPFDocument(is); 
//                Range range = doc.getRange();
//                // 把range范围内的${reportDate}替换为当前的日期
//                range.replaceText("${name}", "aaa");
//                OutputStream os = new FileOutputStream("弘毅知行-"+"aaa"+"-"+"template_csix.doc");
//                doc.write(os);
//                closeStream(os);
//                closeStream(is);
//                closeDoc(doc);
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//           
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    /**
     * 
     * @param path
     * @param record
     * @return
     * @about version ：1.00
     * @auther : 
     * @Description ：
     */
    public static String templateResume(String base,Resume record) {
        InputStream is;
        String[] paths = Constants.TEMPLATE_ALL;
        StringBuffer sb = new StringBuffer();
        for (String templatePath : paths) {
            try {
                is = new FileInputStream(base+templatePath);
                HWPFDocument doc = new HWPFDocument(is);
                Range range = doc.getRange();
                // 把range范围内的${reportDate}替换为当前的日期
                range.replaceText("${name}", record.getName()==null?"":record.getName());
                range.replaceText("${gender}", record.getGender()==null?"":record.getGender());
                range.replaceText("${birthday}", record.getBirthday()==null?"":record.getBirthday());
                range.replaceText("${age}", record.getAge()==null?"":record.getAge());
                range.replaceText("${marriage }", record.getMarriage()==null?"":record.getMarriage());
                range.replaceText("${city}", record.getCity()==null?"":record.getCity());
                range.replaceText("${self}", record.getSelf()==null?"":record.getSelf());
                range.replaceText("${work}", record.getWork()==null?"":record.getWork());
                range.replaceText("${project}",record.getProject()==null?"":record.getProject());
                range.replaceText("${educationtime}",record.getEducationtime()==null?"":record.getEducationtime());
                range.replaceText("${school}",record.getSchool()==null?"":record.getSchool());
                range.replaceText("${major}",record.getMajor()==null?"":record.getMajor());
                range.replaceText("${education}",record.getEducation()==null?"":record.getEducation());
                range.replaceText("${train}",record.getTrain()==null?"":record.getTrain());
                String filename = "弘毅知行-"+record.getName()+"-"+templatePath;
                OutputStream os = new FileOutputStream(base+filename);
                sb.append(filename).append("@@@@");
                // 把doc输出到输出流中
                doc.write(os);
                closeStream(os);
                closeStream(is);
                closeDoc(doc);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        if(sb.indexOf("@@@@")>0){
            return sb.substring(0,sb.length()-4);
        }else{
            return sb.toString();
        }
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