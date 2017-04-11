package com.honythink;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author
 * @version : 1.00
 * @Copyright http://www.onehome.cn/
 * @Create Time : 2016年10月12日 上午11:47:10
 * @Description : 常量类
 * @History：Editor version Time Operation Description*
 *
 */
public class Constants {
    /********************* 公用常量 start *********************************/
    // 成功
    public static final String RET_SUCCESS = "SUCCESS";
    public static final String RET_ERROR = "ERROR";
    public static final String HONYTHINK = "北京弘毅知行科技有限公司2";
    public static final String PATH_SEPERATOR = "/";
    public static final String TEMPLATE_HONYTHINK = "template_honythink";
    public static final String TEMPLATE_CSIX = "template_csix";
    public static final String TEMPLATE_YINGU = "template_yingu";
    public static final String TEMPLATE_BASE = "template_honythink.doc";
    public static final String RESUME_UPLOAD = "upload/";
    public static final String RESUME_TEMPLATE = "template/";
    public static final String RESUME_ZIP = "zip/";
    public static final String XLS_WORKBOOK_TEMPLATE_CUSTOMER = "template_summary_customer.xls";
    public static final String XLS_WORKBOOK_TEMPLATE_SELF = "template_summary_self.xls";
    public static final String XLS_WORKBOOK_TEMPLATE_CSIX = "template_summary_csix.xls";
    public static final String XLS_WORKBOOK_EXPORT_CUSTOMER = "customer.xls";
    public static final String XLS_WORKBOOK_EXPORT_SELF = "self.xls";
    public static final String XLS_WORKBOOK_EXPORT_CSIX = "csix.xls";
    public static final String FREFIX_ZIP = "简历自动生成-";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_XLS = ".xls";
    public static final String SUFFIX_XLSX = ".xlsx";
    public static final String SUFFIX_DOC = ".doc";
    public static final String SUFFIX_DOCX = ".docx";
    
    
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_HR = "ROLE_HR";
    public static final String ROLE_SELLER = "ROLE_SELLER";
    public static final String ROLE_ASSISTANT = "ROLE_ASSISTANT";
    
    public static final String MAIL_EXTENTIONS = "@honythink.com";
    public static final String MAIL_SUBJECT = "弘毅知行推荐邮件";
    
    public static final String ERROR_INCOMPLETE_PARAMS = "参数不完整";
    
    
    public static final String CUSTOMER_NAME_CSIX = "csix";
    
    
    public static final Map<String, String> CUSTOMER_TEMPLATE_WORD = new HashMap<String, String>();
    
    static {
        CUSTOMER_TEMPLATE_WORD.put("honythink", "template_honythink.doc");
        CUSTOMER_TEMPLATE_WORD.put("yingu", "template_yingu.doc");
        CUSTOMER_TEMPLATE_WORD.put("csix", "template_csix.doc");
    }
    
    public static final Map<String, String> CUSTOMER_TEMPLATE_NAME = new HashMap<String, String>();

    static {
        CUSTOMER_TEMPLATE_NAME.put("honythink", "弘毅知行模板");
        CUSTOMER_TEMPLATE_NAME.put("yingu", "银谷模板");
        CUSTOMER_TEMPLATE_NAME.put("csix", "软交所模板");
    }
    
    
    
}
