package com.shengping.pao.adapter;

import com.shengping.pao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Home_My  extends BaseAdapter{
	private LayoutInflater mInflater = null;  
	private adapterData data;
	public Adapter_Home_My(Context context,adapterData data){
		mInflater=LayoutInflater.from(context);
		this.data=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.getCount();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
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
            convertView = mInflater.inflate(R.layout.item_home_my, null);  
            holder.ico=(ImageView)convertView.findViewById(R.id.ico_img);
            holder.arrow_right=(ImageView)convertView.findViewById(R.id.arrow_right);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_total_money=(TextView)convertView.findViewById(R.id.tv_total_money);
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }
        if(position==0){
        	holder.arrow_right.setVisibility(View.GONE);
        	holder.tv_total_money.setVisibility(View.VISIBLE);
        }else{
        	holder.arrow_right.setVisibility(View.VISIBLE);
        	holder.tv_total_money.setVisibility(View.GONE);
        }
    	holder.ico.setImageResource(data.getIcons().get(position));
    	holder.tv_name.setText(data.getNames().get(position));
		return convertView;
	}
	class ViewHolder{
		ImageView ico,arrow_right;
		TextView tv_name,tv_total_money;
	}

}
