package com.shengping.pao.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.PopupWindow.XmlParserHandler;
import com.shengping.pao.model.CityModel;
import com.shengping.pao.model.DistrictModel;
import com.shengping.pao.model.ProvinceModel;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class CityUtil {
	private Context context;
	public static CityUtil getInstence(Context context){
		return new CityUtil(context);
	}
	public CityUtil(Context context){
		this.context=context;
		initProvinceDatas();
	}
	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 
	protected void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			//*/
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// 遍历所有省的数据
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// 区/县对于的邮编，保存到mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// 市-区/县的数据，保存到mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}
	public JSONObject getByCode(String code){
		JSONObject json=new JSONObject();
		try{
			for (Map.Entry<String, String> entry : mZipcodeDatasMap.entrySet()) {  
	//			 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
				if(entry.getValue().equals(code)){
					json.put("district", entry.getKey());
					break;
				}
			}
			if(json.has("district")){
				L1:
				for (Map.Entry<String, String[]> entry : mDistrictDatasMap.entrySet()) {  
					String[] temp=entry.getValue();
					for(String s:temp){
						if(s.equals(json.getString("district"))){
							json.put("city", entry.getKey());
							break L1;
						}
					}
				}
			}else{
				Log.e("cmd", "当前城市数据不包含城市编码："+code);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return json;
	}
	public String getCityCode(String district){
		return mZipcodeDatasMap.get(district);
	}
}
