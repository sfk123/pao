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
	private TextView tv_current_city,//��ǰ����
							tv_name,//���ȹ�˾����
							hot_line,//��������
							tv_time;//Ӫҵʱ��
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
			json_data.put("title", "������");
			json_data.put("slogan", "���ȸ�ͬ�ǿ����ͻ���40���������ʹ�");
			json_data.put("img", R.drawable.help_song);
			listdata.add(json_data);
			
			json_data=new JSONObject();
			json_data.put("title", "������");
			json_data.put("slogan", "���ȸ���ɶ���У����㼫��");
			json_data.put("img", R.drawable.help_buy);
			listdata.add(json_data);
			
			json_data=new JSONObject();
			json_data.put("title", "���Ұ�");
			json_data.put("slogan", "���ȸ�����飬ʡ���ַ���");
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
		hot_line.setText("�ͷ����ߣ�"+application.getPaotuiInfo().getKefu());
		tv_time.setText("Ӫҵʱ�䣺"+application.getPaotuiInfo().getTime());
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try {
			if(listdata.get(arg2).getString("title").equals("������")){
				Intent intent=new Intent(getContext(),Activity_Help_Push.class);
				startActivity(intent);
			}else if(listdata.get(arg2).getString("title").equals("������")){
				Intent intent=new Intent(getContext(),Activity_Help_Buy.class);
				intent.putExtra("type", Activity_Help_Buy.type_help_buy);
				startActivity(intent);
			}else if(listdata.get(arg2).getString("title").equals("���Ұ�")){
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
		  mLocationClient.registerLocationListener( myListener );    //ע���������
		  initLocation();
	}
	private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
        int span=1000;
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        option.setIgnoreKillProcess(false);//��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
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
           
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
              
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
                
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
             
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                Log.e("cmd", "��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                Log.e("cmd", "���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            	 Log.e("cmd", "�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
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
	                .setTitle("ϵͳ��ʾ")  
	                .setMessage("�����ڵĵ�����δ��ͨ���ȸ�����л���Ĭ�ϳ��У�")  
	                .setPositiveButton(  
	                        "ȷ��",  
	                        new DialogInterface.OnClickListener() {  

	                            public void onClick(DialogInterface dialog,int which) {  
	                            	Map<String, String> params=new HashMap<String, String>();
	                        		params.put("areaid", "330703");
	                        		MyApplication.getInstence().setAreaId("330703");
	                        		tv_current_city.setText("����  ����");
	                        		MyHttp http=new MyHttp(getContext());
	                        		http.Http_post(UrlUtil.getUrl("getpaotui", UrlUtil.Service), params, Fragment_content.this);
	                            }  
	                        })  
	                .setNegativeButton(  
	                        "ȡ��",  
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
		Toast.makeText(getContext(), "���ִ����������������", Toast.LENGTH_LONG).show();
	}

}
