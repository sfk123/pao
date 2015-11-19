package com.shengping.pao.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.Activity_Market;
import com.shengping.pao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Adapter_Market extends BaseAdapter {
	private LayoutInflater mInflater = null;  
	private JSONArray json_data;
	public Adapter_Market(Context context,JSONArray json_data){
		mInflater=LayoutInflater.from(context);
		this.json_data=json_data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return json_data.length();
	}

	@Override
	public Object getItem(int index) {
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
		ViewHolder holder=null;
		if(convertView == null){
			holder=new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_market, null);  
			holder.tv_index=(TextView)convertView.findViewById(R.id.tv_index);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_package=(TextView)convertView.findViewById(R.id.tv_package);
			holder.lable_product=(LinearLayout)convertView.findViewById(R.id.lable_product);
			holder.products=new ArrayList<>();
			JSONObject json;
			try {
				json = json_data.getJSONObject(position);
				JSONArray product=json.getJSONArray("products");
				for(int i=0;i<product.length();i++){
					View view=mInflater.inflate(R.layout.item_product, null);  
					holder.products.add(view);
					holder.lable_product.addView(view);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		try {
			JSONObject json=json_data.getJSONObject(position);
			holder.tv_index.setText((position+1)+"");
			holder.tv_name.setText("商家名称："+json.getString("name"));
			holder.tv_package.setText("包装费用：￥"+json.getString("package"));
			JSONArray product=json.getJSONArray("products");
			int i=0;
			for(View view:holder.products){
				TextView tv_product_name=(TextView)view.findViewById(R.id.tv_product_name);
				tv_product_name.setText(product.getJSONObject(i).getString("product_name"));
				TextView tv_price=(TextView)view.findViewById(R.id.tv_price);
				double price=product.getJSONObject(i).getDouble("product_price");
				int count=product.getJSONObject(i).getInt("product_count");
				tv_price.setText("￥"+price*count);
				TextView tv_count=(TextView)view.findViewById(R.id.tv_count);
				tv_count.setText(count+"");
				ImageTage tag=new ImageTage();
				tag.index=i;
				tag.position=position;
				ImageView img_jia=(ImageView)view.findViewById(R.id.img_jia);
				img_jia.setTag(tag);
				img_jia.setOnClickListener(new MyclickListener_Add(tv_count,tv_price,price));
				ImageView img_jian=(ImageView)view.findViewById(R.id.img_jian);
				img_jian.setTag(tag);
				img_jian.setOnClickListener(new MyclickListener_Minus(tv_count,tv_price,price));
				i++;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	class ViewHolder{
		LinearLayout lable_product;
		TextView tv_index,tv_name,tv_package;
		List<View> products;
	}
	class ImageTage{
		int position,index;
	}
	class MyclickListener_Add implements OnClickListener{
		private TextView tv_count,tv_price;
		private int position,index;
		private double price;
		public MyclickListener_Add(TextView tv,TextView tv_price,double price){
			tv_count=tv;
			this.tv_price=tv_price;
			this.price=price;
		}
		@Override
		public void onClick(View arg0) {
			ImageTage tag=(ImageTage)arg0.getTag();
			position=tag.position;
			index=tag.index;
			int old=Integer.parseInt(tv_count.getText().toString());
			tv_count.setText((old+1)+"");
			tv_price.setText("￥"+(old+1)*price);
			try {
				JSONObject temp=json_data.getJSONObject(position).getJSONArray("products").getJSONObject(index);
				temp.put("product_count", old+1);
				json_data.getJSONObject(position).getJSONArray("products").put(index, temp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Activity_Market.getInstence().reSetTotal(json_data);
		}
		
	}
	class MyclickListener_Minus implements OnClickListener{
		private TextView tv_count,tv_price;
		private int position,index;
		private double price;
		public MyclickListener_Minus(TextView tv,TextView tv_price,double price){
			tv_count=tv;
			this.tv_price=tv_price;
			this.price=price;
		}
		@Override
		public void onClick(View arg0) {
			if(tv_count.getText().toString().equals("0")){
				return;
			}
			ImageTage tag=(ImageTage)arg0.getTag();
			position=tag.position;
			index=tag.index;
			int old=Integer.parseInt(tv_count.getText().toString());
			tv_count.setText((old-1)+"");
			tv_price.setText("￥"+(old-1)*price);
			try {
				JSONObject temp=json_data.getJSONObject(position).getJSONArray("products").getJSONObject(index);
				temp.put("product_count", old-1);
				json_data.getJSONObject(position).getJSONArray("products").put(index, temp);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Activity_Market.getInstence().reSetTotal(json_data);
		}
		
	}
}
