package com.shengping.pao.adapter;

import java.util.List;
import java.util.Map;

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

public class Adapter_HomeList  extends BaseAdapter{
	private LayoutInflater mInflater = null;  
	private List<JSONObject> data;
	public Adapter_HomeList(Context context,List<JSONObject> data){
		mInflater=LayoutInflater.from(context);
		this.data=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.item_home_list, null);  
            holder.ico_img=(ImageView)convertView.findViewById(R.id.ico_img);
            holder.tv_slogan=(TextView)convertView.findViewById(R.id.tv_slogan);
            holder.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }
        JSONObject json_data=data.get(position);
        try {
			holder.ico_img.setImageResource(json_data.getInt("img"));
			holder.tv_slogan.setText(json_data.getString("slogan"));
			holder.tv_title.setText(json_data.getString("title"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return convertView;  
	}
	class ViewHolder{
		ImageView ico_img;
		TextView tv_title,tv_slogan;
	}
}
