package com.honythink.common;

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
    public static final String RET_SUCCESS = "200";
    // 系统繁忙，此时请开发者稍候再试
    public static final String RET_BUSY = "-1";
    //数字空
    public static final Integer NUM_NULL = -1;
    //空
    public static final String EMPTY_NULL = "";
    public static final String EMPTY_DEFAULT = "暂无";
    // 系统错误
    public static final String RET_UNKNOW_ERROR = "-1";
    public static final String RET_UNKNOW_ERROR_MSG = "系统错误";
    // 参数不完整
    public static final String RET_ARGUMENT_MISSING = "40001";
    public static final String RET_ARGUMENT_MISSING_MSG = "参数不完整";
    // 参数异常
    public static final String RET_ARGUMENT_ILLEGAL = "40002";
    public static final String RET_ARGUMENT_ILLEGAL_MSG = "参数异常";
    // 参数解密失败，请核实
    public static final String RET_DECRYPT_ERROR = "40003";
    public static final String RET_DECRYPT_ERROR_MSG = "参数解密失败，请核实";

    public static final String RET_CHECK_SIGN_ERROR = "40004";
    public static final String RET_CHECK_SIGN_ERROR_MSG = "验签异常";
    // 验签未通过
    public static final String RET_CHECK_SIGN_FAILED = "40005";
    public static final String RET_CHECK_SIGN_FAILED_MSG = "验签未通过";
    // 版本号错误
    public static final String RET_VERSION_ERROR = "40006";
    public static final String RET_VERSION_ERROR_MSG = "版本号错误";
    // 时间戳错误
    public static final String RET_TIMESTAMP_ERROR = "40007";
    public static final String RET_TIMESTAMP_ERROR_MSG = "时间戳错误";
    // 用户公钥错误
    public static final String RET_USER_PUBLIC_KEY_ERROR = "40008";
    public static final String RET_USER_PUBLIC_KEY_ERROR_MSG = "用户公钥或账号异常";
    // 对应人员姓名 证件号错误
    public static final String RET_HR_LINK_ERROR = "40009";
    public static final String RET_HR_LINK_ERROR_MSG = "人员姓名 证件号错误";
    // 开户名错误
    public static final String RET_HR_ACCOUNT_NAME_ERROR = "40010";
    public static final String RET_HR_ACCOUNT_NAME_ERROR_MSG = "人员姓名与银行开户名不一致";
    // 所属分公司错误
    public static final String RET_CORPID_ERROR = "40011";
    public static final String RET_CORPID_ERROR_MSG = "所属分公司与帐号不匹配";
    // 保险方案错误
    public static final String RET_CPID_ERROR = "40012";
    public static final String RET_CPID_ERROR_MSG = "保险方案与帐号不匹配";
    // 保险生效时间错误
    public static final String RET_SURRENDER_DATE_ERROR = "40013";
    public static final String RET_SURRENDER_DATE_ERROR_MSG = "人员做减保时，结束日期需要介于当前日期与商保生效时间之间";

    // 身份证
    public static final String RET_IDTYPE_ERROR = "40014";
    public static final String RET_IDTYPE_ERROR_MSG = "证件类型不符合规范";

    // 性别
    public static final String RET_GENDER_ERROR = "40015";
    public static final String RET_GENDER_ERROR_MSG = "性别不符合规范";
    // 人员关系
    public static final String RET_RELATIONS_ERROR = "40016";
    public static final String RET_RELATIONS_ERROR_MSG = "与本人关系类型不合规范";
    // 日期
    public static final String RET_DATE_ERROR = "40017";
    public static final String RET_DATE_ERROR_MSG = "日期不符合规范";
    // 邮箱
    public static final String RET_EMAIL_ERROR = "40018";
    public static final String RET_EMAIL_ERROR_MSG = "邮箱不符合规范";
    // 手机号
    public static final String RET_MOBILE_ERROR = "40019";
    public static final String RET_MOBILE_ERROR_MSG = "手机号不符合规范";
    // 通过身份证，保险计划，在保状态查询合同信息
    public static final String RET_EMPINFO_ERROR = "40021";
    public static final String RET_EMPINFO_ERROR_MSG = "查询不到对应人员的商保是否在保的信息";
    // 类型转换错误
    public static final String RET_CAST_ERROR = "40022";
    public static final String RET_CAST_ERROR_MSG = "json数据不符合规范,类型转换错误";
    // 已入保
    public static final String RET_INFO_EXIST_ERROR = "40023";
    public static final String RET_INFO_EXIST_ERROR_MSG = "当前人员已在保或已提交入保申请，请不要重复提交";
    // 查询到多条的数据
    public static final String RET_MANY_EXPECT_ONE_ERROR = "40024";
    public static final String RET_MANY_EXPECT_ONE_ERROR_MSG = "查询到多条的数据";
    // 临时表有待审核数据
    public static final String RET_TEMP_PENDING_ERROR = "40025";
    public static final String RET_TEMP_PENDING_ERROR_MSG = "数据重复或临时表有待审核数据";
    // 对应家属临时表有待审核数据
    public static final String RET_TEMP_FAMILY_PENDING_ERROR = "40026";
    public static final String RET_TEMP_FAMILY_PENDING_ERROR_MSG = "数据重复或对应家属临时表有待审核数据";
    // 合同
    public static final String RET_CONTRACT_ERROR = "40027";
    public static final String RET_CONTRACT_ERROR_MSG = "找不到人员对应的商保合同";
    // 入保生效日期
    public static final String RET_ADD_DATE_ERROR = "40028";
    public static final String RET_ADD_DATE_ERROR_MSG = "入保生效日期不符合规范";
    // 退保日期
    public static final String RET_DEL_DATE_ERROR = "40029";
    public static final String RET_DEL_DATE_ERROR_MSG = "减保日期不符合规范";
    // 无可减保信息 insuredStatus 1,2都不行
    public static final String RET_NO_CUT_CONTRACT_ERROR = "40030";
    public static final String RET_NO_CUT_CONTRACT_ERROR_MSG = "当前人员没有商保或是待核对或已提交减保申请，请不要重复提交";
    // 无可减保信息 insuredStatus 1,2都不行
    public static final String RET_DATE_EMPLOYEE_FAMILY_ERROR = "40031";
    public static final String RET_DATE_EMPLOYEE_FAMILY_ERROR_MSG = "员工入保生效日期要在家属的入保生效日期之前";
    // 员工减保会自动对家属进行减保,请删除家属信息再上传
    public static final String RET_EMPLOYEE_FAMILY_IN_BATCH_ERROR = "40032";
    public static final String RET_EMPLOYEE_FAMILY_IN_BATCH_ERROR_MSG = "员工减保会自动对家属进行减保,请删除家属信息再重新操作";
    // 流水号为空
    public static final String RET_SERIALNO_ERROR = "40033";
    public static final String RET_SERIALNO_ERROR_MSG = "流水号不能为空";
    // 批次不存在
    public static final String RET_BATCH_NOT_EXIST_ERROR = "40034";
    public static final String RET_BATCH_NOT_EXIST_ERROR_MSG = "查询不到批次";
    // 批次不合法,appkey与流水号不匹配
    public static final String RET_BATCH_NOT_LEGAL_ERROR = "40035";
    public static final String RET_BATCH_NOT_LEGAL_ERROR_MSG = "批次不合法";
    // 证件号为空
    public static final String RET_IDENTITY_ERROR = "40036";
    public static final String RET_IDENTITY_ERROR_MSG = "证件号不能为空";
    // 客户方 子公司已开通服务
    public static final String RET_ALREADY_ERROR = "40037";
    public static final String RET_ALREADY_ERROR_MSG = "已开通服务,请勿重复开通";

    // 询不到该人员在保信息
    public static final String RET_EMPLOYEE_NOT_EXIST_ERROR = "40038";
    public static final String RET_EMPLOYEE_NOT_EXIST_ERROR_MSG = "查询不到该人员在保信息";
    
    // 询不到该人员在保信息
    public static final String RET_BANK_ERROR = "40039";
    public static final String RET_BANK_ERROR_MSG = "开户行不符合规范,请使用系统提供开户行名称";
    
    // 查询不到对应员工信息
    public static final String RET_FAM_EMP_NOT_EXIST_ERROR = "40040";
    public static final String RET_FAM_EMP_NOT_EXIST_ERROR_MSG = "查询不到对应员工信息";
    
    // 有入保待核对家属  员工不可以减 需要等家属核对后再操作
    public static final String RET_FAMILY_CHECK_EXIST_ERROR = "40041";
    public static final String RET_FAMILY_CHECK_EXIST_ERROR_MSG = "有入保待核对家属,员工不能减保";
    
    //  入保减保计划和退保保险计划不一致
    public static final String RET_PLAN_DIFFER_ERROR = "40042";
    public static final String RET_PLAN_DIFFER_ERROR_MSG = "入保减保计划和退保保险计划不一致";
    
    //  php序列化 java 反序列化数据错误
    public static final String RET_SERIAL_ERROR = "40043";
    public static final String RET_SERIAL_ERROR_MSG = "数据错误";
    

    public static final String RET_RATE_LIMITE = "101";
    public static final String RET_RATE_LIMITE_MSG = "超过访问频次";

    public static final String RET_OBJECT_ERROR = "102";
    public static final String RET_OBJECT_ERROR_MSG = "参数为空";

    public static final String RET_APP_KEY_ERROR = "103";
    public static final String RET_APP_KEY_ERROR_MSG = "appkey异常";

    public static final String RET_ROUTE_ERROR = "104";
    public static final String RET_ROUTE_ERROR_MSG = "route异常";

    public static final String RET_HANDLER_ERROR = "105";
    public static final String RET_HANDLER_ERROR_MSG = "handler或客户号异常，请核对";

    // 保险批次操作
    public static final String HR_INSURED_STATUS_ADD = "add";
    public static final String HR_INSURED_STATUS_CUT = "cut";
    public static final String HR_INSURED_STATUS_EDIT = "edit";

    // 批次来源 0:saas 1:api
    public static final String SOURCE_SERVER_SAAS = "0";
    public static final String SOURCE_SERVER_API = "1";

    // 与本人关系
    public static final String HR_EMPLOYEE_RELATIONS_SELF = "0";
    public static final String HR_EMPLOYEE_RELATIONS_MATE = "1";
    public static final String HR_EMPLOYEE_RELATIONS_CHILDREN = "2";
    public static final String HR_EMPLOYEE_RELATIONS_PARENTS = "3";

    // 证件类型
    public static final String HR_EMPOYEE_IDENTITY_TYPE_IDCARD = "1";
    public static final String HR_EMPOYEE_IDENTITY_TYPE_PASSPORT = "2";
    public static final String HR_EMPOYEE_IDENTITY_TYPE_JUNREN = "3";
    public static final String HR_EMPOYEE_IDENTITY_TYPE_GANGAO = "4";
    public static final String HR_EMPOYEE_IDENTITY_TYPE_BIRTH = "5";

    // 批次状态
    public static final Integer HR_BATCH_TYPE_PENDING = 1;
    public static final Integer HR_BATCH_TYPE_CHECKED = 2;
    public static final Integer HR_BATCH_TYPE_EDIT = 3;

    /**
     * 查询批次状态
     */
    // 无批次
    public static final Integer RET_BATCH_TYPE_EMPTY = -1;
    // 待审核
    public static final Integer RET_BATCH_TYPE_PENDING = 1;
    // 通过
    public static final Integer RET_BATCH_TYPE_CHECKED = 2;
    // 驳回
    public static final Integer RET_BATCH_TYPE_REJECT = 3;
    // 作废
    public static final Integer RET_BATCH_TYPE_DEL = 4;

    /**
     * 查询人员状态
     */
    // 查询不到记录
    public static final Integer RET_STATUS_EMPTY = -1;
    // 在保
    public static final Integer RET_STATUS_ON = 0;
    // 不在保
    public static final Integer RET_STATUS_OFF = 1;
    // 入保待核对
    public static final Integer RET_STATUS_CHECK_ADD = 2;
    // 减保待核对
    public static final Integer RET_STATUS_CHECK_CUT = 3;

    /**
     * 批次员工状态
     */
    // 正常
    public static final Integer HR_BATCH_E_NORMAL = 0;
    // 删除
    public static final Integer HR_BATCH_E_DEL = 1;

    // 员工在保状态
    // 在保
    public static final Integer INSURED_STATUS_ON = 0;
    // 退保
    public static final Integer INSURED_STATUS_OFF = 1;
    // 带核对 入保
    public static final Integer INSURED_STATUS_CHECK_ADD = 2;
    // 待核对 退保
    public static final Integer INSURED_STATUS_CHECK_CUT = 3;

    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

    public static final String SEPERATOR = ",";
    
    //查询账单  人员增减数
    public static final String ADD_SUM = "addSum";
    public static final String CUT_SUM = "cutSum";
    
    //调用频次
    public static final int RATE_LIMITE = 5000;
    
    //日志类型 1批量 2单增3单减4修改10修改11批量
//    public static final int LOG_TYPE_BATCH = 1;
    public static final int LOG_TYPE_ADD = 2;
    public static final int LOG_TYPE_DEL = 3;
//    public static final int LOG_TYPE_EDIT = 4;
    public static final int LOG_TYPE_EDIT = 10;
    public static final int LOG_TYPE_BATCH = 11;

    public static final Map<String, String> HR_EMPOYEE_IDENTITY_TYPES = new HashMap<String, String>();

    static {
        HR_EMPOYEE_IDENTITY_TYPES.put("1", "身份证");
        HR_EMPOYEE_IDENTITY_TYPES.put("2", "护照");
        HR_EMPOYEE_IDENTITY_TYPES.put("3", "军人证");
        HR_EMPOYEE_IDENTITY_TYPES.put("4", "回乡证/港澳居民往来内地通行证");
        HR_EMPOYEE_IDENTITY_TYPES.put("5", "出生证");
    }

    public static final Map<String, String> HR_EMPLOYEE_SEXES = new HashMap<String, String>();

    static {
        HR_EMPLOYEE_SEXES.put("0", "男");
        HR_EMPLOYEE_SEXES.put("1", "女");
    }

    public static final Map<String, String> HR_EMPLOYEE_RELATIONS = new HashMap<String, String>();

    static {
        HR_EMPLOYEE_RELATIONS.put("0", "员工");
        HR_EMPLOYEE_RELATIONS.put("3", "父母");
        HR_EMPLOYEE_RELATIONS.put("2", "子女");
        HR_EMPLOYEE_RELATIONS.put("1", "配偶");
    }

    public static final Map<Integer, String> HTTP_RESP_CODE = new HashMap<Integer, String>();

    static {
        HTTP_RESP_CODE.put(200, "成功");
        HTTP_RESP_CODE.put(401, "您没有对应的权限");
        HTTP_RESP_CODE.put(403, "禁止访问");
        HTTP_RESP_CODE.put(404, "没有找到对应的资源");
        HTTP_RESP_CODE.put(405, "访问方式不正确");
        HTTP_RESP_CODE.put(500, "服务器内部错误，请联系我们");
    }

    public static final Map<String, String> QUERY_STATUS_BATCH = new HashMap<String, String>();

    static {
        QUERY_STATUS_BATCH.put("1", "未查询到此批次");
        QUERY_STATUS_BATCH.put("2", "待审核");
        QUERY_STATUS_BATCH.put("3", "通过");
        QUERY_STATUS_BATCH.put("4", "驳回");
    }

    public static final Map<String, String> QUERY_STATUS_EMPLOYEE = new HashMap<String, String>();

    static {
        QUERY_STATUS_EMPLOYEE.put("1", "未查询到此人信息");
        QUERY_STATUS_EMPLOYEE.put("2", "参保");
        QUERY_STATUS_EMPLOYEE.put("3", "退保");
        QUERY_STATUS_EMPLOYEE.put("4", "待核对");
    }
    public static final Map<String, String> BANK = new HashMap<String, String>();
    static {
        BANK.put("ICBC", "中国工商银行");
        BANK.put("ABC", "中国农业银行");
        BANK.put("BOC", "中国银行");
        BANK.put("CCB", "中国建设银行");
        BANK.put("BOCOM", "交通银行");
        BANK.put("CITIC", "中信银行");
        BANK.put("CEB", "中国光大银行");
        BANK.put("HXB", "华夏银行");
        BANK.put("CMBC", "中国民生银行");
        BANK.put("GDB", "广东发展银行");
        BANK.put("SDB", "深圳发展银行");
        BANK.put("CMB", "招商银行");
        BANK.put("CIB", "兴业银行");
        BANK.put("SPDB", "上海浦东发展银行");
        BANK.put("CCCB", "城市商业银行");
        BANK.put("RCB", "农村商业银行");
        BANK.put("EGBANK", "恒丰银行");
        BANK.put("CZB", "浙商银行");
        BANK.put("RCOB", "农村合作银行");
        BANK.put("CBHB", "渤海银行");
        BANK.put("HSBANK", "徽商银行");
        BANK.put("B0T", "村镇银行");
        BANK.put("CQSX", "重庆三峡银行股份有限公司");
        BANK.put("UCCO", "城市信用社");
        BANK.put("RCU", "农村信用联社");
        BANK.put("PSBC", "中国邮政储蓄银行有限责任公司");
        BANK.put("HSBC", "香港上海汇丰银行");
        BANK.put("PAB", "平安银行");
        BANK.put("SHRB", "上海农村商业银行");
        BANK.put("NBCB", "宁波银行股份有限公司");
        BANK.put("HZCB", "杭州银行股份有限公司");
        BANK.put("SZRB", "深圳农村商业银行");
        BANK.put("BON", "南京银行股份有限公司");
        BANK.put("BOS", "上海银行股份有限公司");
        BANK.put("BOB", "北京银行");
        BANK.put("BJRC", "北京农村商业银行");
        BANK.put("XACB", "西安市商业银行");
        BANK.put("JNCB", "济南市商业银行(齐鲁银行)");
        BANK.put("GYCB", "贵阳市商业银行");
        BANK.put("CDCB", "成都市商业银行(成都银行)");
        BANK.put("YTCB", "烟台市商业银行（烟台银行）");
        BANK.put("JYRB", "江苏江阴农村商业银行股份有限公司");
        BANK.put("CSRB", "江苏常熟农村商业银行股份有限公司");
        BANK.put("WJRB", "吴江农村商业银行");
        BANK.put("JDRCB", "江苏东吴农村商业银行股份有限公司");
        BANK.put("FZCB", "福州市商业银行");
        BANK.put("ZJRB", "张家港农村商业银行");
        BANK.put("ZZCB", "株洲市商业银行");
        BANK.put("ASCB", "鞍山市商业银行股份有限公司");
        BANK.put("QZCCB", "泉州市商业银行股份有限公司");
        BANK.put("SJCB", "石家庄市商业银行");
        BANK.put("UQCB", "乌鲁木齐市商业银行");
        BANK.put("SXCB", "绍兴银行股份有限公司");
        BANK.put("YCCB", "宜昌市商业银行");
        BANK.put("TJCB", "天津银行");
        BANK.put("DLCB", "大连银行");
        BANK.put("CQCB", "重庆银行");
        BANK.put("DGCB", "东莞银行");
        BANK.put("HBCB", "哈尔滨银行");
        BANK.put("BTCB", "包商银行");
        BANK.put("NCCB", "南昌银行");
        BANK.put("LZCB", "兰州银行");
        BANK.put("QDCB", "青岛银行");
        BANK.put("ZBCB", "齐商银行");
        BANK.put("WZCB", "温州银行");
        BANK.put("NXCB", "宁夏银行");
        BANK.put("LYCB", "临商银行");
        BANK.put("LYOB", "洛阳市商业银行(洛阳银行)");
        BANK.put("KMCB", "富滇银行");
        BANK.put("CSCB", "长沙银行");
        BANK.put("HXBC", "华融湘江银行");
        BANK.put("BBG", "北部湾银行");
        BANK.put("GZRCB", "广州农村商业银行");
        BANK.put("GZCB", "广州银行股份有限公司");
        BANK.put("X20004", "沧州银行股份有限公司");
        BANK.put("X20005", "长安银行股份有限公司");
        BANK.put("X20007", "朝阳银行股份有限公司");
        BANK.put("X20009", "承德银行股份有限公司");
        BANK.put("X20012", "丹东银行股份有限公司");
        BANK.put("X20013", "江苏银行股份有限公司");
        BANK.put("X20015", "金华银行股份有限公司");
        BANK.put("X20016", "锦州银行股份有限公司");
        BANK.put("X20017", "晋城银行股份有限公司");
        BANK.put("X20018", "九江银行股份有限公司");
        BANK.put("X20019", "昆仑银行股份有限公司");
        BANK.put("X20022", "廊坊银行股份有限公司");
        BANK.put("X20023", "德阳银行股份有限公司");
        BANK.put("X20026", "东营银行股份有限公司");
        BANK.put("X20027", "鄂尔多斯银行股份有限公司");
        BANK.put("X20031", "抚顺银行股份有限公司");
        BANK.put("X20034", "甘肃银行股份有限公司");
        BANK.put("X20035", "赣州银行股份有限公司");
        BANK.put("X20036", "广东南粤银行股份有限公司");
        BANK.put("X20038", "广西北部湾银行");
        BANK.put("X20039", "广州银行");
        BANK.put("X20041", "桂林银行股份有限公司");
        BANK.put("X20044", "邯郸银行股份有限公司");
        BANK.put("HKB", "汉口银行");
        BANK.put("X20049", "鹤壁银行股份有限公司");
        BANK.put("X20051", "湖州银行股份有限公司");
        BANK.put("X20052", "葫芦岛银行股份有限公司");
        BANK.put("X20057", "吉林银行股份有限公司");
        BANK.put("X20058", "济宁银行股份有限公司");
        BANK.put("X20059", "嘉兴银行股份有限公司");
        BANK.put("X20060", "辽阳银行股份有限公司");
        BANK.put("X20062", "龙江银行股份有限公司");
        BANK.put("X20065", "内蒙古银行股份有限公司");
        BANK.put("X20071", "平顶山银行股份有限公司");
        BANK.put("X20076", "青海银行股份有限公司");
        BANK.put("X20087", "盛京银行股份有限公司");
        BANK.put("X20088", "石嘴山银行股份有限公司");
        BANK.put("X20090", "台州银行股份有限公司");
        BANK.put("X20093", "潍坊银行股份有限公司");
        BANK.put("X20095", "乌海银行股份有限公司");
        BANK.put("X20097", "西藏银行股份有限公司");
        BANK.put("X20098", "厦门银行股份有限公司");
        BANK.put("X20100", "新疆汇和银行股份有限公司");
        BANK.put("X20101", "新乡银行股份有限公司");
        BANK.put("X20102", "信阳银行股份有限公司");
        BANK.put("X20104", "邢台银行股份有限公司");
        BANK.put("X20105", "许昌银行股份有限公司");
        BANK.put("X20107", "营口银行股份有限公司");
        BANK.put("X20114", "郑州银行股份有限公司");
        BANK.put("X20126", "周口银行股份有限公司");
        BANK.put("X20127", "珠海华润银行股份有限公司");
        BANK.put("X20133", "晋商银行股份有限公司");
        BANK.put("X20134", "莱商银行股份有限公司");
        BANK.put("X20137", "广东华兴银行股份有限公司");
        BANK.put("X20143", "湖北银行股份有限公司");
        BANK.put("X20146", "柳州银行股份有限公司");
        BANK.put("X20150", "日照银行股份有限公司");
        BANK.put("X20153", "上饶银行");
        BANK.put("X20155", "铁岭银行股份有限公司");
        BANK.put("X20158", "营口沿海银行股份有限公司");
        BANK.put("X20168", "驻马店银行股份有限公司");
        BANK.put("X30002", "安顺市商业银行");
        BANK.put("X30003", "安阳银行");
        BANK.put("X30004", "白银市商业银行");
        BANK.put("X30006", "保定银行");
        BANK.put("X30008", "本溪市商业银行");
        BANK.put("X30012", "长治市商业银行");
        BANK.put("X30017", "达州市商业银行");
        BANK.put("X30019", "大同市商业银行");
        BANK.put("X30022", "德州银行");
        BANK.put("X30028", "阜新银行");
        BANK.put("X30032", "广东华兴银行");
        BANK.put("X30041", "哈密市商业银行");
        BANK.put("X30056", "江苏长江商业银行");
        BANK.put("X30058", "焦作市商业银行");
        BANK.put("X30063", "晋中市商业银行");
        BANK.put("X30064", "景德镇市商业银行");
        BANK.put("X30066", "开封市商业银行");
        BANK.put("X30067", "库尔勒市商业银行");
        BANK.put("X30072", "乐山市商业银行");
        BANK.put("X30073", "凉山州商业银行");
        BANK.put("X30078", "六盘水市商业银行");
        BANK.put("X30081", "绵阳市商业银行");
        BANK.put("X30083", "南充市商业银行");
        BANK.put("X30086", "南阳市商业银行");
        BANK.put("X30091", "攀枝花市商业银行");
        BANK.put("X30092", "盘锦市商业银行");
        BANK.put("X30095", "平凉市商业银行");
        BANK.put("X30099", "秦皇岛市商业银行");
        BANK.put("X30102", "曲靖市商业银行");
        BANK.put("X30105", "三门峡市商业银行");
        BANK.put("X30106", "商丘市商业银行");
        BANK.put("X30115", "泰安市商业银行");
        BANK.put("X30116", "唐山市商业银行");
        BANK.put("X30119", "威海市商业银行");
        BANK.put("X30132", "雅安市商业银行");
        BANK.put("X30134", "阳泉市商业银行");
        BANK.put("X30135", "宜宾市商业银行");
        BANK.put("X30138", "玉溪市商业银行");
        BANK.put("X30139", "枣庄市商业银行");
        BANK.put("X30140", "张家口市商业银行");
        BANK.put("X30141", "浙江稠州商业银行");
        BANK.put("X30142", "浙江民泰商业银行");
        BANK.put("X30143", "浙江泰隆商业银行");
        BANK.put("X30149", "自贡市商业银行");
        BANK.put("X30150", "遵义市商业银行");
        BANK.put("X30151", "泸州市商业银行");
        BANK.put("X30152", "漯河市商业银行");
        BANK.put("X30153", "濮阳市商业银行");
        BANK.put("X40037", "衡水市商业银行");
        BANK.put("X40038", "衡阳市商业银行");
        BANK.put("X40089", "遂宁市商业银行");
        BANK.put("X40112", "岳阳市商业银行");
        BANK.put("X40114", "湛江市商业银行");
        BANK.put("X40124", "珠海市商业银行");
        BANK.put("X50001", "荆州市商业银行");
        BANK.put("X50002", "广西壮族自治区农村信用社联合社");
        BANK.put("X50004", "云南省农村信用社联合社");
        BANK.put("X50005", "宁夏黄河农村商业银行股份有限公司");
        BANK.put("X50007", "宁波鄞州农村合作银行");
        BANK.put("X50009", "福建省农村信用社联合社");
        BANK.put("X50011", "天津农村合作银行");
        BANK.put("X50012", "广州农村商业银行股份有限公司");
        BANK.put("X50013", "佛山顺德农村商业银行股份有限公司");
        BANK.put("X50014", "重庆农村商业银行股份有限公司");
        BANK.put("X50015", "昆山农村商业银行");
        BANK.put("X50016", "东莞农村商业银行股份有限公司");
        BANK.put("X50017", "河北银行股份有限公司");
        BANK.put("X50020", "烟台银行股份有限公司");
        BANK.put("X50021", "吉林省农村信用社联合社");
        BANK.put("X50022", "山东省农村信用社联合社");
        BANK.put("X50023", "浙江省农村信用社联合社");
        BANK.put("X50024", "江西省农村信用社联合社");
        BANK.put("X50025", "湖南省农村信用社联合社");
        BANK.put("X50140", "天津农村商业银行股份有限公司");
        BANK.put("X50141", "天津滨海农村商业银行股份有限公司");
        BANK.put("X50150", "贵州银行股份有限公司");
        BANK.put("haikou", "海口联合农村商业银行");
        BANK.put("X1000002", "芜湖扬子农村商业银行股份有限公司");
        BANK.put("zanwu", "暂无");
    }
}