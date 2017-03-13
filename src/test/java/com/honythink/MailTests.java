package com.honythink;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.honythink.biz.system.service.MailService;

public class MailTests extends BasicUtClass{  
    @Autowired  
    private MailService mailService;  
      
    private String to = "zhouxing@honythink.com";  
    private String cc = "zhouxing@honythink.com";  
      
    @Test  
    public void sendSimpleMail() {  
        mailService.sendSimpleMail(to, "主题：简单邮件", "测试邮件内容");  
    }  
      
//    @Autowired  
//    VelocityEngine velocityEngine;  
      
//    @Test  
//    public void sendHtmlMail() {  
//        Map<String, Object> model = new HashMap<String, Object>();  
//        model.put("time", XDateUtils.nowToString());  
//        model.put("message", "这是测试的内容。。。");  
//        model.put("toUserName", "张三");  
//        model.put("fromUserName", "老许");  
//        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "welcome.vm", "UTF-8", model);  
//          
//        mailService.sendHtmlMail(to, "主题：html邮件", content);  
//    }  
  
    @Test  
    public void sendAttachmentsMail() {  
        StringBuffer content = new StringBuffer();  
        content.append("<h2><font color=red>推荐列表</font></h2>");  
        content.append("<hr>");  
        content.append("<table border='1'>");  
        content.append("<tr>");  
        content.append("<td>姓名</td>");  
        content.append("<td>电话</td>");  
        content.append("<td>岗位</td>");  
        content.append("<td>客户名称</td>");  
        content.append("<td>推荐人</td>");  
        content.append("<td>最高学历</td>");  
        content.append("<td>毕业日期</td>");  
        content.append("<td>期望薪资</td>");  
        content.append("<td>到岗时间</td>");  
        content.append("</tr>");  
        
        content.append("<tr>");  
        content.append("<td>王波</td>");  
        content.append("<td>15808322882</td>");  
        content.append("<td>java</td>");  
        content.append("<td>北京弘毅知行科技有限公司</td>");  
        content.append("<td>刘阳</td>");  
        content.append("<td>本科</td>");  
        content.append("<td>2017.1.1-2017.1.1</td>");  
        content.append("<td>55555</td>");  
        content.append("<td>随时</td>");  
        content.append("</tr>"); 
        content.append("</table>");  
        mailService.sendAttachmentsMail(to,cc,"主题：带附件的邮件",content.toString(), "C:\\1.doc");  
    }  
      
//    @Test  
//    public void sendInlineResourceMail() {  
//        String rscId = "rscId001";  
//        mailService.sendInlineResourceMail(to,  
//                "主题：嵌入静态资源的邮件",  
//                "<html><body>这是有嵌入静态资源：<img src=\'cid:" + rscId + "\' ></body></html>",  
//                "C:\\Users\\Xu\\Desktop\\csdn\\1.png",  
//                rscId);  
//    }  
}  