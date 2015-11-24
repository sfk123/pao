package com.shengping.pao.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.shengping.pao.Activity_Help_Buy;
import com.shengping.pao.Activity_Help_Push;
import com.shengping.pao.Activity_Market;
import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.PopupWindow.Select_City;
import com.shengping.pao.PopupWindow.Select_City.cityselectListener;
import com.shengping.pao.adapter.Adapter_HomeList;
import com.shengping.pao.model.PaotuiInfo;
import com.shengping.pao.util.CityUtil;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_content extends Fragment implements OnItemClickListener,OnClickListener,cityselectListener,MyHttpCallBack{
	private View contentView;
	private TextView tv_current_city,//当前城市
							tv_name,//跑腿公司名称
							hot_line,//服务热线
							tv_time;//营业时间
	private ListView mylist;
	private List<JSONObject> listdata;
	private Select_City select_city;
	
	private ImageView btn_market;
	
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private static Fragment_content instence;
	public static Fragment_content getInstence(){
		return instence;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_main_content, null);
		initView(contentView);
		instence=this;
		return contentView;
	}
	private void initView(View rootView){
		mylist=(ListView)rootView.findViewById(R.id.mylist);
		listdata=new ArrayList<>();
		JSONObject json_data;
		try {
			json_data=new JSONObject();
			json_data.put("title", "帮我送");
			json_data.put("slogan", "跑腿哥同城快速送货，40分钟左右送达");
			json_data.put("img", R.drawable.help_song);
			listdata.add(json_data);
			
			json_data=new JSONObject();
			json_data.put("title", "帮我买");
			json_data.put("slogan", "跑腿哥买啥都行，方便极了");
			json_data.put("img", R.drawable.help_buy);
			listdata.add(json_data);
			
			json_data=new JSONObject();
			json_data.put("title", "帮我办");
			json_data.put("slogan", "跑腿哥办事情，省心又放心");
			json_data.put("img", R.drawable.help_conduct);
			listdata.add(json_data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Adapter_HomeList adapter=new Adapter_HomeList(getContext(), listdata);
		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(this);
		TextView btn_select_city=(TextView)rootView.findViewById(R.id.btn_select_city);
		btn_select_city.setOnClickListener(this);
		btn_market=(ImageView)rootView.findViewById(R.id.btn_market);
		btn_market.setOnClickListener(this);
		
		tv_current_city=(TextView)rootView.findViewById(R.id.tv_current_city);
		tv_name=(TextView)rootView.findViewById(R.id.tv_name);
		hot_line=(TextView)rootView.findViewById(R.id.hot_line);
		tv_time=(TextView)rootView.findViewById(R.id.tv_time);
	}
	public void refreshView(){
		MyApplication application =MyApplication.getInstence();
		tv_name.setText(application.getPaotuiInfo().getName());
		hot_line.setText("客服热线："+application.getPaotuiInfo().getKefu());
		tv_time.setText("营业时间："+application.getPaotuiInfo().getTime());
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try {
			if(listdata.get(arg2).getString("title").equals("帮我送")){
				Intent intent=new Intent(getContext(),Activity_Help_Push.class);
				startActivity(intent);
			}else if(listdata.get(arg2).getString("title").equals("帮我买")){
				Intent intent=new Intent(getContext(),Activity_Help_Buy.class);
				intent.putExtra("type", Activity_Help_Buy.type_help_buy);
				startActivity(intent);
			}else if(listdata.get(arg2).getString("title").equals("帮我办")){
				Intent intent=new Intent(getContext(),Activity_Help_Buy.class);
				intent.putExtra("type", Activity_Help_Buy.type_help_handle);
				startActivity(intent);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_select_city){
//			select_city=select_city.builder();
			select_city.show();
		}else if(v.getId()==R.id.btn_market){
			Intent intent=new Intent(getContext(),Activity_Market.class);
			startActivity(intent);
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		  super.onActivityCreated(savedInstanceState);
		  select_city=new Select_City(getContext());
		  select_city.setListener(this);
		  mLocationClient = new LocationClient(getContext());
		  mLocationClient.registerLocationListener( myListener );    //注册监听函数
		  initLocation();
	}
	private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
	public void initPopup(){
		select_city=select_city.builder();
	}
	private void getPaotui(String district){
		String areaid=CityUtil.getInstence(getContext()).getCityCode(district);
		MyApplication.getInstence().setAreaId(areaid);
		Map<String, String> params=new HashMap<String, String>();
		params.put("areaid", areaid);
		MyHttp http=new MyHttp(getContext());
		http.Http_post(UrlUtil.getUrl("getpaotui", UrlUtil.Service), params, this);
	}
	@Override
	public void selectok(JSONObject json) {
		try {
			tv_current_city.setText(json.getString("mCurrentCityName")+" "+json.getString("mCurrentDistrictName"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class MyLocationListener implements BDLocationListener {
		 
        @SuppressWarnings("unused")
		@Override
        public void onReceiveLocation(BDLocation location) {
           
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
              
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
             
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                Log.e("cmd", "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Log.e("cmd", "网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            	 Log.e("cmd", "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }

            if (location == null) {
                return;
            }
            tv_current_city.setText(location.getCity()+" "+location.getDistrict());
            mLocationClient.stop();
            getPaotui(location.getDistrict());
            }
           
        
	}
	@Override
	public void onResponse(JSONObject response) {
		try {
			System.out.println(response);
			if(response.getBoolean("status")){
				if(response.has("data")&&!response.getString("data").equals("null")){
				JSONObject data=response.getJSONObject("data");
				PaotuiInfo paotui=new PaotuiInfo();
				paotui.setId(data.getInt("id"));
				paotui.setKefu(data.getString("keFuTell"));
				paotui.setLJPTF(data.getDouble("ljptf"));
				paotui.setMidnight_cost(data.getDouble("midnight_cost"));
				paotui.setMidnight_time(new JSONObject(data.getString("midnight_time")));
				paotui.setMRGLS(data.getInt("mrgls"));
				paotui.setName(data.getString("companyName"));
				paotui.setQibujia(data.getDouble("qibujia"));
				paotui.setTianqi(data.getString("tianqi"));
				paotui.setTianqifei(data.getDouble("tianqifei"));
				paotui.setTime(data.getString("yinYeTime"));
				MyApplication.getInstence().setPaotuiInfo(paotui);
				refreshView();
				}else{
					new AlertDialog.Builder(getActivity())  
	                .setTitle("系统提示")  
	                .setMessage("您所在的地区尚未开通跑腿哥服务，切换到默认城市？")  
	                .setPositiveButton(  
	                        "确定",  
	                        new DialogInterface.OnClickListener() {  

	                            public void onClick(DialogInterface dialog,int which) {  
	                            	Map<String, String> params=new HashMap<String, String>();
	                        		params.put("areaid", "330703");
	                        		MyApplication.getInstence().setAreaId("330703");
	                        		tv_current_city.setText("金华市  金东区");
	                        		MyHttp http=new MyHttp(getContext());
	                        		http.Http_post(UrlUtil.getUrl("getpaotui", UrlUtil.Service), params, Fragment_content.this);
	                            }  
	                        })  
	                .setNegativeButton(  
	                        "取消",  
	                        new DialogInterface.OnClickListener() {  

	                            public void onClick(  
	                                    DialogInterface dialog,  
	                                    int which) {  
	                               getActivity().finish();
	                            }  
	                        }).show();  
				}
			}
		} catch (JSONException e) {
			Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		error.printStackTrace();
		try {
			if(error.networkResponse!=null)
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(getContext(), "出现错误，请检查网络后重试", Toast.LENGTH_LONG).show();
	}

}
