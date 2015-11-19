package com.shengping.pao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.adapter.Adapter_MyOrder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_MyOrder extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的订单");
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		ListView mylist=(ListView)findViewById(R.id.mylist);
		JSONArray json_data=new JSONArray();
		JSONObject json;
		try {
			json=new JSONObject();
			json.put("id", "14654");
			json.put("code", "14654");
			json.put("status", 1);
			json.put("time", "2015-10-20 17:30");
			json.put("money", 50);
			json.put("type", "在线付款");
			json_data.put(json);
			
			json=new JSONObject();
			json.put("id", "14654");
			json.put("code", "14654");
			json.put("status", 2);
			json.put("time", "2015-10-20 17:30");
			json.put("money", 50);
			json.put("type", "货到付款");
			json_data.put(json);
			
			json=new JSONObject();
			json.put("id", "14654");
			json.put("code", "14654");
			json.put("status", 3);
			json.put("time", "2015-10-20 17:30");
			json.put("money", 50);
			json.put("type", "在线付款");
			json_data.put(json);
			
			json=new JSONObject();
			json.put("id", "14654");
			json.put("code", "14654");
			json.put("status", 2);
			json.put("time", "2015-10-20 17:30");
			json.put("money", 50);
			json.put("type", "在线付款");
			json_data.put(json);
			
			json=new JSONObject();
			json.put("id", "14654");
			json.put("code", "14654");
			json.put("status", 2);
			json.put("time", "2015-10-20 17:30");
			json.put("money", 50);
			json.put("type", "在线付款");
			json_data.put(json);
			
			json=new JSONObject();
			json.put("id", "14654");
			json.put("code", "14654");
			json.put("status", 2);
			json.put("time", "2015-10-20 17:30");
			json.put("money", 50);
			json.put("type", "在线付款");
			json_data.put(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Adapter_MyOrder adapter=new Adapter_MyOrder(this, json_data,this);
		mylist.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_button){
			String id=v.getTag().toString();
		}
	}
}
