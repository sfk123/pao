package com.shengping.pao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.adapter.Adapter_Collection;
import com.shengping.pao.view.XListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的水果、我的超市、我的宠物、我额酒柜、我的鲜花、情趣店、我的药房
 * @author Administrator
 *
 */
public class Activity_HelpBuy_Other extends Activity implements OnClickListener,OnItemClickListener{
	private ImageView my_market;
	private XListView mylist;
	private Adapter_Collection adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helpbuy_other);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText(getIntent().getStringExtra("title"));
		my_market=(ImageView)findViewById(R.id.my_market);
		my_market.setVisibility(View.VISIBLE);
		
		mylist=(XListView)findViewById(R.id.mylist);
		mylist.setOnItemClickListener(this);
		mylist.setPullRefreshEnable(false);
		mylist.setPullLoadEnable(true);
		
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		JSONObject json=adapter.getItem(position);
		Intent intent=new Intent(this,Activity_HelpBuy_Other_Detail.class);
		try {
			intent.putExtra("business_name", json.getString("title_name"));
			startActivity(intent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
