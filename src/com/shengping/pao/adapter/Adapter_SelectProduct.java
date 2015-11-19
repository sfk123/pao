package com.shengping.pao.adapter;

import java.util.List;

import com.shengping.pao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_SelectProduct  extends BaseAdapter {
	  private List<String> productTypes;//产品类别
	  private LayoutInflater mInflater = null;  
	public Adapter_SelectProduct(Context context,List<String> productTypes){
		this.productTypes=productTypes;
		mInflater=LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productTypes.size();
	}

	@Override
	public String getItem(int arg0) {
		// TODO Auto-generated method stub
		return productTypes.get(arg0);
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
			 holder = new Viewholder();    
	         convertView = mInflater.inflate(R.layout.item_select_product_type, null);  
	         holder.img_select=(ImageView)convertView.findViewById(R.id.img_select);
	         holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
	         convertView.setTag(holder);
		}else{
			holder=(Viewholder)convertView.getTag();
		}
			if(position==0){
				holder.img_select.setVisibility(View.VISIBLE);
			}else{
				holder.img_select.setVisibility(View.GONE);
			}
			holder.tv_name.setText(getItem(position));
		return convertView;
	}
	class Viewholder{
		TextView tv_name;
		ImageView img_select;
	}
}
