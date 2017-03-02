package com.honythink.common.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfficeUtils {
//    public static void main(String[] args) {
//        try {
//            for(int i=2;i<3;i++){
//                InputStream is = new FileInputStream(new File("C:/"+i+".doc"));
//                String html = getContent(is);
////                findName(html);
////                findGender(html);
////                findAge(html);
////                findBirthday(html);
////                findSeniority(html);
////                findMarried(html);
////                findCity(html);
////                findResidence(html);
////                findCard(html);
////                findMobile(html); 
////                findSelf(html);
////                findWork(html);
//                findProjects(html);
////                  findSchool(html);
////                  findMajor(html);
////                  findEducation(html);
////                  findEducationtime(html);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * 把输入流里面的内容以UTF-8编码当文本取出。
     * 不考虑异常，直接抛出
     * @param ises
     * @return
     * @throws IOException
     */
    public static  String getContent(InputStream... ises) throws IOException {
       if (ises != null) {
          StringBuilder result = new StringBuilder();
          BufferedReader br;
          String line;
          for (InputStream is : ises) {
             br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             while ((line=br.readLine()) != null) {
                 result.append(line);
             }
          }
          return result.toString();
       }
       return null;
    }
    /**
     * 
     * @param html
     * @return
     * @about version ：1.00
     * @auther : 
     * @Description ：
     */
    public static String findName(String html){
        Pattern pattern = Pattern.compile("(<span style='font-size:16.0pt;font-family:\\s*\"微软雅黑\",\"sans-serif\"'>)([\u0391-\uFFE5]+)(<span lang=EN-US>)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            System.out.println("fuck,where is the name");
        }
        return "";
    }
    
    public static String findGender(String html){
        Pattern pattern = Pattern.compile("(mso-hansi-theme-font:minor-latin'>)([男|女]+)(&nbsp;*)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            System.out.println("fuck,where is the name");
        }
        return "";
    }
    public static String findAge(String html){
        Pattern pattern = Pattern.compile("(\\d{2}岁)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(0);
        }else {
            System.out.println("fuck,where is the name");
        }
        return "";
    }
    public static String findBirthday(String html){
        Pattern pattern = Pattern.compile("(\\d+年\\d+月)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(0);
        }else {
            System.out.println("fuck,where is the name");
        }
        return "";
    }
    public static String findSeniority(String html){
        Pattern pattern = Pattern.compile("(\\d+)(年工作经验)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(0);
        }else {
            System.out.println("fuck,where is the findSeniority");
        }
        return "";
    }
   
    
    public static String findMarried(String html){
        Pattern pattern = Pattern.compile("&nbsp;([已婚|未婚|离异]+)</span></b></p>"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(1);
        }else {
            System.out.println("fuck,where is the findEducation");
        }
        return "";
    }
    public static String findCity(String html){
        Pattern pattern = Pattern.compile("(现居住地：)([\u4E00-\u9FA5|\\s]+)(\\|)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            System.out.println("fuck,where is the findEducation");
        }
        return "";
    }
    public static String findResidence(String html){
        Pattern pattern = Pattern.compile("(户口：)([\u4E00-\u9FA5]+)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            System.out.println("fuck,where is the findEducation");
        }
        return "";
    }
    public static String findCard(String html){
        Pattern pattern = Pattern.compile("(身份证：)(\\d+)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            return "";
        }
    }
    public static String findMobile(String html){
        Pattern pattern = Pattern.compile("(手机：)(\\d+)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            return "";
        }
    }
    
    public static String findSelf(String html){
        Pattern pattern = Pattern.compile("(自我评价[\\s\\S]*)(工作经历)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            Pattern pattern1 = Pattern.compile("(mso-hansi-theme-font:minor-latin'>)([\\s\\S]*)(</span>[\\S]*<span lang=EN-US style='font-size:9.0pt'><o:p></o:p>)"); //中文括号 
            Matcher matcher1 = pattern1.matcher(matcher.group(1));
            if (matcher1.find()){
                return matcher1.group(2);
            }
        }else {
            System.out.println("fuck,where is the findSelf");
        }
        return "";
    }
    public static String findWork(String html){
        Pattern pattern = Pattern.compile("工作经历[\\s\\S]*?<table[\\s\\S]*?经历</span></b><b"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            Pattern pattern1 = Pattern.compile("<table[\\s\\S]*</span>  <span lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></p>  </td> </tr></table>"); //中文括号 
            Matcher matcher1 = pattern1.matcher(matcher.group(0));
            if (matcher1.find()){
                return matcher1.group(0);
            }
        }else {
            System.out.println("fuck,where is the findWork");
        }
        return "";
    }
    public static String findProjects(String html){
        Pattern pattern = Pattern.compile("项目经历[\\s\\S]*?<table[\\s\\S]*?经历</span></b><b"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            Pattern pattern1 = Pattern.compile("<table[\\s\\S]*lang=EN-US style='font-size:9.0pt'><o:p></o:p></span></p>  </td> </tr></table>"); //中文括号 
            Matcher matcher1 = pattern1.matcher(matcher.group(0));
            if (matcher1.find()){
                return matcher1.group(0);
            }
        }else {
            System.out.println("fuck,where is the findProjects");
        }
        return "";
    }

    public static String findSchool(String html){
        Pattern pattern = Pattern.compile("\\s(\\d{4}.\\d{2} - \\d{4}.\\d{2})&nbsp;&nbsp;([\u4E00-\u9FA5]+)&nbsp;&nbsp;([\u4E00-\u9FA5]+)&nbsp;&nbsp;([\u4E00-\u9FA5]+)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            return "";
        }
    }
    public static String findMajor(String html){
        Pattern pattern = Pattern.compile("\\s(\\d{4}.\\d{2} - \\d{4}.\\d{2})&nbsp;&nbsp;([\u4E00-\u9FA5]+)&nbsp;&nbsp;([\u4E00-\u9FA5]+)&nbsp;&nbsp;([\u4E00-\u9FA5]+)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(3);
        }else {
            return "";
        }
    }
    public static String findEducation(String html){
        Pattern pattern = Pattern.compile("(&nbsp;)([大专|本科|硕士|博士|中专|中技|高中|初中|其他]+)(&nbsp;)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(2);
        }else {
            System.out.println("fuck,where is the findEducation");
        }
        return "";
    }
    public static String findEducationtime(String html){
        Pattern pattern = Pattern.compile("\\s(\\d{4}.\\d{2} - \\d{4}.\\d{2})&nbsp;&nbsp;([\u4E00-\u9FA5]+)&nbsp;&nbsp;([\u4E00-\u9FA5]+)&nbsp;&nbsp;([\u4E00-\u9FA5]+)"); //中文括号 
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()){
            return matcher.group(1);
        }else {
            return "";
        }
    }
  
}