package com.shengping.pao.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_Business_Evaluate  extends BaseAdapter{
	private JSONArray listdata;
	private LayoutInflater mInflater = null;  
	private SimpleDateFormat format;
	@SuppressLint("SimpleDateFormat")
	public Adapter_Business_Evaluate(JSONArray listdata,Context context){
		this.listdata=listdata;
		mInflater=LayoutInflater.from(context);
		format = new SimpleDateFormat("yyyy-MM-dd");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		try {
			return listdata.getJSONObject(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Viewholder holder=null;
		if(convertView==null){
			holder=new Viewholder();
			convertView = mInflater.inflate(R.layout.item_business_evaluate, null);  
			holder.tv_username=(TextView)convertView.findViewById(R.id.tv_username);
			holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
			holder.tv_content=(TextView)convertView.findViewById(R.id.tv_content);
			convertView.setTag(holder);
		}else{
			holder=(Viewholder)convertView.getTag();
		}
		try {
			JSONObject json=listdata.getJSONObject(position);
			String userName=json.getString("username");
			if(userName.length()>2){
			String userName_first=userName.substring(0,1);
			String userName_end=userName.substring(userName.length()-1);
			String middle="";
			for(int i=0;i<userName.length()-2;i++){
				middle=middle+"*";
			}
			holder.tv_username.setText(userName_first+middle+userName_end);
			}else{
				String middle="";
				for(int i=0;i<userName.length();i++){
					middle=middle+"*";
				}
				holder.tv_username.setText(middle);
			}
			holder.tv_time.setText(format.format(new Date(json.getLong("time"))));
			holder.tv_content.setText(json.getString("content"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	class Viewholder{
		TextView tv_username,tv_time,tv_content;
	}
}
