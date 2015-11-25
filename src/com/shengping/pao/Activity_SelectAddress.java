package com.shengping.pao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.shengping.pao.adapter.Adapter_AddressList;
import com.shengping.pao.model.Address;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.LoadingDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_SelectAddress extends Activity implements OnClickListener,MyHttpCallBack,OnItemClickListener{

	private TextView address_null;
	private ListView mylist;
	private List<Address> dataList;
	private boolean isinit=false;
	private Adapter_AddressList adapter;
	private static Activity_SelectAddress instence;
	public static Activity_SelectAddress getInstence(){
		return instence;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_address);
		findViewById(R.id.btn_back).setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("选择收货地址");
		
		findViewById(R.id.layout_add_address).setOnClickListener(this);
		mylist=(ListView)findViewById(R.id.mylist);
		mylist.setOnItemClickListener(this);
		address_null=(TextView)findViewById(R.id.address_null);
		instence=this;
	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) { 
	    super.onWindowFocusChanged(hasFocus);
	    if(!isinit&&hasFocus){
	    	isinit=true;
	    	LoadingDialog.showWindow(this);
	    	MyHttp http=new MyHttp(this);
	    	Map<String, String> params=new HashMap<String, String>();
	    	params.put("token", MyApplication.getInstence().getUser().getToken());
	    	params.put("userid", MyApplication.getInstence().getUser().getId()+"");
	    	http.Http_post(UrlUtil.getUrl("getAddressList", UrlUtil.Service), params, this);
	    }
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.layout_add_address){
			Intent intent=new Intent(this,Activity_CreatAddress.class);
			startActivity(intent);
		}
	}

	public void addAddress(Address address){
		if(address.getOrderIndex()==-1){
			for(Address a:dataList){
				if(a.getOrderIndex()==-1){
					a.setOrderIndex(0);
					break;
				}
			}
		}
		dataList.add(address);
		sort();
		adapter.notifyDataSetChanged();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sort(){
		Collections.sort(dataList, new Comparator() {
		      @SuppressLint("UseValueOf")
			@Override
		      public int compare(Object o1, Object o2) {
		    	  if(((Address)o1).getOrderIndex()==-1){
		    		  return -1;
		    	  }
		        return 0;
		      }
		    });
	}
	@Override
	public void onResponse(JSONObject response) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		try {
			if(response.getBoolean("status")){
				dataList=JSON.parseArray(response.getString("data"),Address.class);
				sort();
				adapter=new Adapter_AddressList(dataList, this);
				mylist.setAdapter(adapter);
				if(dataList.size()>0){
					address_null.setVisibility(View.GONE);
				}
			}else{
				Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			Toast.makeText(this, "出现错误："+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}


	@Override
	public void onErrorResponse(VolleyError error) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		error.printStackTrace();
		try {
			if(error.networkResponse!=null)
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, "出现错误，请检查网络后重试", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent=new Intent();
		intent.putExtra("address", dataList.get(arg2));
		setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
		finish();
	}
}
