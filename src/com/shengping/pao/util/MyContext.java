package com.shengping.pao.util;

import java.util.Map;

public class MyContext {
	private final static String host="http://121.42.141.206:88/qpay/index/";//项目后台地址
	public final static String httpInterface_Login="checkUser";//登录接口名称
	public final static String httpInterface_getinfo="myinfo";//获取用户信息接口名称
	public final static String httpInterface_codePay="codePay";//付款接口名称
	public final static String httpInterface_getBillDetail="getBillDetail";//付款详情接口名称
	
	public static String GetUrl(String interface_type,Map<String ,String> params){//具体访问地址的拼接
		String url_temp=host+interface_type;
		if(params!=null){
		 url_temp=url_temp+"?";
		int i=0;
		for (String key : params.keySet()) {
			if(i==0)
			url_temp=url_temp+key +"="+params.get(key);
			else
				url_temp=url_temp+"&"+key +"="+params.get(key);
			i++;
		}
		}
		return url_temp;
	}
	public static String GetUrl(String host,String interface_type,Map<String ,String> params){//具体访问地址的拼接
		String url_temp=host+interface_type;
		if(params!=null){
		 url_temp=url_temp+"?";
		int i=0;
		for (String key : params.keySet()) {
			if(i==0)
			url_temp=url_temp+key +"="+params.get(key);
			else
				url_temp=url_temp+"&"+key +"="+params.get(key);
			i++;
		}
		}
		return url_temp;
	}
	public static String GetPOIUrl(String url,Map<String ,String> params){
		String url_temp=url;
		if(params!=null){
			 url_temp=url_temp+"?";
			int i=0;
			for (String key : params.keySet()) {
				if(i==0)
				url_temp=url_temp+key +"="+params.get(key);
				else
					url_temp=url_temp+"&"+key +"="+params.get(key);
				i++;
			}
			}
			return url_temp;
	}
}
