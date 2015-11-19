package com.shengping.pao.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.R;
import com.shengping.pao.util.MyUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Select_Car extends BaseAdapter {
	private JSONArray cars;
	private LayoutInflater mInflater = null; 
	private Context context;
	BitmapDrawable bd;
	@SuppressWarnings("deprecation")
	public Adapter_Select_Car(Context context,JSONArray json_data){
		mInflater=LayoutInflater.from(context);
		cars=json_data;
		this.context=context;
		bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.temp);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cars.length();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		try {
			return cars.getJSONObject(index);
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
	public View getView(int position, View convertView,ViewGroup arg2) {
		viewholder holder=null;
		if(convertView==null){
			holder=new viewholder();
			convertView = mInflater.inflate(R.layout.item_select_car, null);  
			holder.img=(ImageView)convertView.findViewById(R.id.img_car);
			holder.ico_select=(ImageView)convertView.findViewById(R.id.ico_select);
			holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
			holder.tv_info=(TextView)convertView.findViewById(R.id.tv_info);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		}else{
			holder=(viewholder)convertView.getTag();
		}
		holder.img.setImageBitmap(MyUtil.resizeBitmap(bd.getBitmap(), MyUtil.dip2px(context, 110), MyUtil.dip2px(context, 70)));
		try{
			JSONObject car=cars.getJSONObject(position);
			holder.tv_price.setText("¼Û¸ñ£º£¤"+car.getString("price"));
			holder.tv_info.setText(car.getString("info"));
			holder.tv_name.setText(car.getString("name"));
		}catch(JSONException e){
			e.printStackTrace();
		}
		return convertView;
	}
	public class viewholder{
		public ImageView img,ico_select;
		public TextView tv_price,tv_info,tv_name;
	}
}
