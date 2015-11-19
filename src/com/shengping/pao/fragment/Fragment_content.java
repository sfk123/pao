package com.shengping.pao.fragment;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.shengping.pao.R;
import com.shengping.pao.PopupWindow.Select_City;
import com.shengping.pao.PopupWindow.Select_City.cityselectListener;
import com.shengping.pao.adapter.Adapter_HomeList;

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

public class Fragment_content extends Fragment implements OnItemClickListener,OnClickListener,cityselectListener{
	private View contentView;
	private TextView tv_current_city;
	private ListView mylist;
	private List<JSONObject> listdata;
	private Select_City select_city;
	
	private ImageView btn_market;
	
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_main_content, null);
		initView(contentView);
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
            }
           
        
	}

}
