package com.shengping.pao.util;

public class UrlUtil {
	private final static String host="http://192.168.10.113:8012/paotui";
//	private final static String host="http://192.168.8.2:8012/paotui";
	public final static String Service="/customerservice/";
	public final static String Business_Logo="/images/business/logo/";
	public final static String Business_product="/images/business/products/";
	public final static String Business_pusher="/images/pusher/";

	public static String getUrl(String method,String service){
		return host+service+method;
	}
	public static String getAgreementUrl(){//用户协议地址
		return host+"/html/agreement.html";
	}
	public static String getUrl(String service){
		return host+service;
	}
}
