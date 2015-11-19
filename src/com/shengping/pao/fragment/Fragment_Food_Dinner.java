package com.shengping.pao.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.Activity_Business_Detail_Food;
import com.shengping.pao.R;
import com.shengping.pao.adapter.Adapter_Collection;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.view.XListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_Food_Dinner  extends Fragment implements OnItemClickListener{
	private View contentView;
	private XListView mylist;
	private Adapter_Collection adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_food_dinner, null);
		mylist=(XListView)contentView.findViewById(R.id.mylist);
		mylist.setOnItemClickListener(this);
		mylist.setPullRefreshEnable(false);
		mylist.setPullLoadEnable(true);
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle b){
		super.onActivityCreated(b);
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
		adapter=new Adapter_Collection(getContext(), json_data);
		mylist.setAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		JSONObject json=adapter.getItem(index);
		if(json!=null){
			try {
				Intent intent=new Intent(getContext(),Activity_Business_Detail_Food.class);
				intent.putExtra("title", json.getString("title_name"));
				startActivity(intent);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
