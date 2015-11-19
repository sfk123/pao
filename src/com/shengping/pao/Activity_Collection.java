package com.shengping.pao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.adapter.Adapter_Collection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Collection  extends Activity implements OnClickListener{
	private Adapter_Collection adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collection);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的收藏");
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		ListView mylist=(ListView)findViewById(R.id.mylist);
		JSONArray json_data=new JSONArray();
		JSONObject json;
		try {
			json=new JSONObject();
			json.put("title_name", "大润发超市");
			json.put("sell_count", 550);
			json.put("address", "(解放西路350号)");
			json.put("sell_count", 512);
			json_data.put(json);
			
			json=new JSONObject();
			json.put("title_name", "大润发超市");
			json.put("sell_count", 550);
			json.put("address", "(解放西路350号)");
			json.put("sell_count", 512);
			json_data.put(json);
			json=new JSONObject();
			json.put("title_name", "大润发超市");
			json.put("sell_count", 550);
			json.put("address", "(解放西路350号)");
			json.put("sell_count", 512);
			json_data.put(json);
			json=new JSONObject();
			json.put("title_name", "大润发超市");
			json.put("sell_count", 550);
			json.put("address", "(解放西路350号)");
			json.put("sell_count", 512);
			json_data.put(json);
			json=new JSONObject();
			json.put("title_name", "大润发超市");
			json.put("sell_count", 550);
			json.put("address", "(解放西路350号)");
			json.put("sell_count", 512);
			json_data.put(json);
			json=new JSONObject();
			json.put("title_name", "大润发超市");
			json.put("sell_count", 550);
			json.put("address", "(解放西路350号)");
			json.put("sell_count", 512);
			json_data.put(json);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter=new Adapter_Collection(this, json_data);
		mylist.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
