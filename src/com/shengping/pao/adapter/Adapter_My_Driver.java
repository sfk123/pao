package com.shengping.pao.adapter;

import com.shengping.pao.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_My_Driver  extends BaseAdapter{
	private LayoutInflater mInflater = null;  
	private int[] images={R.drawable.driver1,R.drawable.driver2,R.drawable.driver3,R.drawable.driver4};//Í¼±êÎÄ¼þ
	private String[] names;
	private String[] solgans;
	public Adapter_My_Driver(Context context){
		mInflater=LayoutInflater.from(context);
		names=context.getResources().getStringArray(R.array.date_my_driver);
		solgans=context.getResources().getStringArray(R.array.date_my_driver_solgan);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return names[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Viewholder holder;
		if(convertView==null){
			holder=new Viewholder();
			convertView= mInflater.inflate(R.layout.item_my_driver, null);  
			holder.ico_img=(ImageView)convertView.findViewById(R.id.ico_img);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_solgan=(TextView)convertView.findViewById(R.id.tv_solgan);
			convertView.setTag(holder);
		}else{
			holder=(Viewholder)convertView.getTag();
		}
		holder.ico_img.setImageResource(images[position]);
		holder.tv_name.setText(names[position]);
		holder.tv_solgan.setText(solgans[position]);
		return convertView;
	}
	class Viewholder{
		TextView tv_name,tv_solgan;
		ImageView ico_img;
	}
}
