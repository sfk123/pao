package com.shengping.pao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.adapter.Adapter_Market;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_Market extends Activity implements OnClickListener{
	private ListView mylist;
	private TextView tv_money_count;
	
	private JSONArray list_data;
	private static Activity_Market instence;
	public static Activity_Market getInstence(){
		return instence;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		findViewById(R.id.btn_go_buy).setOnClickListener(this);
		findViewById(R.id.btn_go_account).setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("ÎÒµÄ¹ºÎï³µ");
		mylist=(ListView)findViewById(R.id.mylist);
		tv_money_count=(TextView)findViewById(R.id.tv_money_count);
		
		list_data=new JSONArray();
		JSONObject json;
		
		try {
			json=new JSONObject();
			json.put("name", "¿ÏµÂ»ù");
			json.put("package", "3.00");
			JSONArray products=new JSONArray();
			JSONObject p=new JSONObject();
			p.put("product_name", "±ùºì²è");
			p.put("product_price", "3");
			p.put("product_count", 1);
			products.put(p);
			
			p=new JSONObject();
			p.put("product_name", "»ÆìË¼¦Ã×·¹Ì×²Í");
			p.put("product_price", "20");
			p.put("product_count", 5);
			products.put(p);
			p=new JSONObject();
			p.put("product_name", "¿§à¬·¹Ì×²Í");
			p.put("product_price", "30");
			p.put("product_count", 1);
			products.put(p);
			json.put("products", products);
			list_data.put(json);
			
			json=new JSONObject();
			json.put("name", "¿ÏµÂ»ù");
			json.put("package", "3.00");
			products=new JSONArray();
			p=new JSONObject();
			p.put("product_name", "±ùºì²è");
			p.put("product_price", "3");
			p.put("product_count", 1);
			products.put(p);
			
			p=new JSONObject();
			p.put("product_name", "¿§à¬·¹Ì×²Í");
			p.put("product_price", "30");
			p.put("product_count", 1);
			products.put(p);
			json.put("products", products);
			list_data.put(json);
			
			json=new JSONObject();
			json.put("name", "¿ÏµÂ»ù");
			json.put("package", "3.00");
			products=new JSONArray();
			p=new JSONObject();
			p.put("product_name", "±ùºì²è");
			p.put("product_price", "3");
			p.put("product_count", 1);
			products.put(p);
			
			p=new JSONObject();
			p.put("product_name", "»ÆìË¼¦Ã×·¹Ì×²Í");
			p.put("product_price", "20");
			p.put("product_count", 5);
			products.put(p);
			p=new JSONObject();
			p.put("product_name", "¿§à¬·¹Ì×²Í");
			p.put("product_price", "30");
			p.put("product_count", 1);
			products.put(p);
			json.put("products", products);
			list_data.put(json);
			
			json=new JSONObject();
			json.put("name", "¿ÏµÂ»ù");
			json.put("package", "3.00");
			products=new JSONArray();
			p=new JSONObject();
			p.put("product_name", "±ùºì²è");
			p.put("product_price", "3");
			p.put("product_count", 1);
			products.put(p);
			
			p=new JSONObject();
			p.put("product_name", "»ÆìË¼¦Ã×·¹Ì×²Í");
			p.put("product_price", "20");
			p.put("product_count", 5);
			products.put(p);
			p=new JSONObject();
			p.put("product_name", "ÎÀÉúÖ½");
			p.put("product_price", "10");
			p.put("product_count", 1);
			products.put(p);
			p=new JSONObject();
			p.put("product_name", "¿§à¬·¹Ì×²Í");
			p.put("product_price", "30");
			p.put("product_count", 1);
			products.put(p);
			json.put("products", products);
			list_data.put(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Adapter_Market adapter=new Adapter_Market(this, list_data);
		mylist.setAdapter(adapter);
		reSetTotal(list_data);
		instence=this;
	}
	public void reSetTotal(JSONArray list_data){//Ë¢ÐÂ×Ü¶î
		double total=0;
		try {
			for(int i=0;i<list_data.length();i++){
				JSONArray products=list_data.getJSONObject(i).getJSONArray("products");
				total=total+list_data.getJSONObject(i).getDouble("package");
				for(int j=0;j<products.length();j++){
					total=total+products.getJSONObject(j).getInt("product_count")*products.getJSONObject(j).getDouble("product_price");
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_money_count.setText(total+"Ôª");
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back||v.getId()==R.id.btn_go_buy){
			finish();
		}else if(v.getId()==R.id.btn_go_account){
			Intent intent=new Intent(this,Activity_Market_Confirm.class);
			intent.putExtra("list_data", list_data.toString());
			startActivity(intent);
		}
		
	}
}
