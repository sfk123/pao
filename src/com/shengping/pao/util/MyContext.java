package com.shengping.pao.util;

import java.util.Map;

public class MyContext {
	private final static String host="http://121.42.141.206:88/qpay/index/";//��Ŀ��̨��ַ
	public final static String httpInterface_Login="checkUser";//��¼�ӿ�����
	public final static String httpInterface_getinfo="myinfo";//��ȡ�û���Ϣ�ӿ�����
	public final static String httpInterface_codePay="codePay";//����ӿ�����
	public final static String httpInterface_getBillDetail="getBillDetail";//��������ӿ�����
	
	public static String GetUrl(String interface_type,Map<String ,String> params){//������ʵ�ַ��ƴ��
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
	public static String GetUrl(String host,String interface_type,Map<String ,String> params){//������ʵ�ַ��ƴ��
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
