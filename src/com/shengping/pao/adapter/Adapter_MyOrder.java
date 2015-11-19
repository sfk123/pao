package com.shengping.pao.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_MyOrder  extends BaseAdapter{
	private LayoutInflater mInflater = null;  
	private JSONArray json_data;
	private Context context;
	private OnClickListener clickListener;
	public Adapter_MyOrder(Context context,JSONArray json_data,OnClickListener clickListener){
		mInflater=LayoutInflater.from(context);
		this.json_data=json_data;
		this.context=context;
		this.clickListener=clickListener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return json_data.length();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		try {
			return json_data.get(index);
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
            convertView = mInflater.inflate(R.layout.item_myorder, null);  
            holder.tv_order_code=(TextView)convertView.findViewById(R.id.tv_order_code);
            holder.tv_status=(TextView)convertView.findViewById(R.id.tv_status);
            holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_money=(TextView)convertView.findViewById(R.id.tv_money);
            holder.tv_type=(TextView)convertView.findViewById(R.id.tv_type);
            holder.tv_button=(TextView)convertView.findViewById(R.id.tv_button);
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }
        try{
	        JSONObject json=json_data.getJSONObject(position);
	        holder.tv_order_code.setText("订单编号："+json.getString("code"));
	        if(json.getInt("status")==1){
	        	holder.tv_status.setText("订单进行中...");
	        	holder.tv_status.setTextColor(context.getResources().getColor(R.color.tab_select));
	        }else if(json.getInt("status")==2){
	        	holder.tv_status.setText("订单完成");
	        	holder.tv_status.setTextColor(context.getResources().getColor(R.color.text_black));
	        }else if(json.getInt("status")==3){
	        	holder.tv_status.setText("订单取消");
	        	holder.tv_status.setTextColor(context.getResources().getColor(R.color.text_black));
	        }
	        holder.tv_time.setText("订单时间："+json.getString("time"));
	        holder.tv_money.setText("订单金额："+json.getString("money"));
	        holder.tv_type.setText("付款方式："+json.getString("type"));
	        holder.tv_button.setTag(json.getString("id"));
	        holder.tv_button.setOnClickListener(clickListener);
        }catch(Exception e){
        	e.printStackTrace();
        }
		return convertView;
	}
	class ViewHolder{
		TextView tv_order_code,tv_status,tv_time,tv_money,tv_type,tv_button;
	}
}
