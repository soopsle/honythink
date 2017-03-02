///***
// * company ： honythink., Ltd.
// * copyright ：  2009-2010,  honythink., Ltd.
// * @since：JDK1.6
// * @version：1.0
// * @author zhouxing
// * @see：
// ***/
//package com.honythink.common.util;
//
//import java.io.IOException;
//import java.util.Date;
//
//import org.apache.commons.lang.time.DateFormatUtils;
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.JsonSerializer;
//import org.codehaus.jackson.map.SerializerProvider;
//
///**
// * @author zhouxing
// * @version : 1.00
// * @copyright 2009-2010, honythink., Ltd.
// * @Create Time : 2012-7-10 上午10:50:19
// * @Description : java日期对象经过Jackson库转换成JSON日期格式化自定义类
// * @History：Editor　　　version　　　Time　　　　　Operation　　　　　　　Description*
// * 
// */
//public class JsonDateTimeSSerializer extends JsonSerializer<Date> {
//
//	@Override
//	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
//		jgen.writeString(DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss"));
//	}
//}