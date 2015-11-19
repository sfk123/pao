package com.shengping.pao.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Collection  extends BaseAdapter{
	private LayoutInflater mInflater = null;  
	private JSONArray json_data;
	public Adapter_Collection(Context context,JSONArray json_data){
		mInflater=LayoutInflater.from(context);
		this.json_data=json_data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return json_data.length();
	}

	@Override
	public JSONObject getItem(int index) {
		// TODO Auto-generated method stub
		try {
			return json_data.getJSONObject(index);
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
		ViewHolder holder = null;    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.item_my_collection, null);  
            holder.image=(ImageView)convertView.findViewById(R.id.image);
            holder.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
            holder.tv_sell_count=(TextView)convertView.findViewById(R.id.tv_sell_count);
            holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
            holder.tv_destence=(TextView)convertView.findViewById(R.id.tv_destence);
            convertView.setTag(holder);
        }else{
        	holder = (ViewHolder) convertView.getTag();    
        }
        try {
			JSONObject json=json_data.getJSONObject(position);
			holder.tv_title.setText(json.getString("title_name"));
			holder.tv_sell_count.setText("月售"+json.getInt("sell_count")+"单");
			holder.tv_address.setText(json.getString("address"));
			holder.tv_destence.setText("距离："+json.getDouble("destence")+"米");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	class ViewHolder{
		ImageView image;
		TextView tv_title,tv_sell_count,tv_address,tv_destence;
	}
}
