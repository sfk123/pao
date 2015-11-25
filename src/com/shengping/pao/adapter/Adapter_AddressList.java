package com.shengping.pao.adapter;

import java.util.List;

import com.shengping.pao.R;
import com.shengping.pao.model.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Adapter_AddressList extends BaseAdapter{
	private LayoutInflater mInflater = null;  
	private List<Address> data_list;
	private Context context;
	public Adapter_AddressList(List<Address> data_list,Context context){
		this.data_list=data_list;
		mInflater=LayoutInflater.from(context);
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		viewHolder holder;
		if(convertView==null){
			holder=new viewHolder();
			convertView = mInflater.inflate(R.layout.item_address_list, null); 
			holder.img_default=(ImageView)convertView.findViewById(R.id.img_default);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_phone=(TextView)convertView.findViewById(R.id.tv_phone);
			holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
			holder.lable_content=(LinearLayout)convertView.findViewById(R.id.lable_content);
			holder.lable_right=(RelativeLayout)convertView.findViewById(R.id.lable_right);
			convertView.setTag(holder);
		}else{
			holder=(viewHolder)convertView.getTag();
		}
		Address address=data_list.get(position);
		if(address.getOrderIndex()==-1){
			convertView.setBackgroundColor(context.getResources().getColor(R.color.address_default));
			holder.img_default.setVisibility(View.VISIBLE);
			int color=context.getResources().getColor(R.color.white);
			holder.tv_name.setTextColor(color);
			holder.tv_phone.setTextColor(color);
			holder.tv_address.setTextColor(color);
			holder.tv_address.setText("(д╛хо)"+address.getAddress());
		}else{
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
			holder.img_default.setVisibility(View.GONE);
			holder.tv_name.setTextColor(context.getResources().getColor(R.color.text_black));
			holder.tv_phone.setTextColor(context.getResources().getColor(R.color.text_black));
			holder.tv_address.setTextColor(context.getResources().getColor(R.color.text_gray));
			holder.tv_address.setText(address.getAddress());
		}
		holder.tv_name.setText(address.getRealName());
		holder.tv_phone.setText(address.getMobile());
		
		holder.lable_content.measure(0, 0);
		holder.lable_right.getLayoutParams().height=holder.lable_content.getMeasuredHeight();
		return convertView;
	}
	class viewHolder{
		TextView tv_name,tv_phone,tv_address;
		ImageView img_default;
		LinearLayout lable_content;
		RelativeLayout lable_right;
	}
}
