//package com.yi.core.payment.alipay;
//
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.apache.commons.collections.MapUtils;
//
///**
// * Created by Aidy_He on 14-7-18.
// */
//public class AlipayHelper {
//
//	public static Map<String, String> buildCallBackParamMap(Map requestParams) throws UnsupportedEncodingException {
//		if (MapUtils.isEmpty(requestParams)) {
//			throw new NullPointerException("the requestParamMap is empty");
//		}
//		// 获取支付宝GET过来反馈信息
//		Map<String, String> params = new HashMap<String, String>();
//		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//			}
//			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
//			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			params.put(name, valueStr);
//		}
//		return params;
//	}
//}
