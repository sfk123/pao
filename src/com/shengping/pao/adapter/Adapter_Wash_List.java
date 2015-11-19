package com.shengping.pao.adapter;

import java.util.List;

import com.shengping.pao.R;
import com.shengping.pao.Myinterface.MarketListener;
import com.shengping.pao.model.Clothes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_Wash_List extends BaseAdapter {
	private LayoutInflater mInflater = null;  
	private List<Clothes> json_data;
	private MarketListener marketListener;
	public Adapter_Wash_List(Context context,List<Clothes> json_data){
		mInflater=LayoutInflater.from(context);
		this.json_data=json_data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return json_data.size();
	}

	@Override
	public Clothes getItem(int arg0) {
		return json_data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		viewholder holder;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_helpbuy_other_list, null);
			holder=new viewholder();
			holder.food_image=(ImageView)convertView.findViewById(R.id.img);
			holder.img_jian=(ImageView)convertView.findViewById(R.id.img_jian);
			holder.img_jia=(ImageView)convertView.findViewById(R.id.img_jia);
			holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
			holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
			holder.tv_sell_count=(TextView)convertView.findViewById(R.id.tv_sell_count);
			holder.tv_count=(TextView)convertView.findViewById(R.id.tv_count);
			holder.tv_count.setTag(holder.img_jian);
			convertView.setTag(holder);
		}else {
			holder=(viewholder)convertView.getTag();
		}
		
			Clothes clothes=json_data.get(position);
			holder.img_jian.setTag(clothes);
			holder.img_jia.setTag(clothes);
			holder.img_jian.setOnClickListener(new CountClickListener(holder.tv_count, -1));
			holder.img_jia.setOnClickListener(new CountClickListener(holder.tv_count, 1));
			holder.tv_name.setText(clothes.getName());
			holder.tv_sell_count.setText(clothes.getSolgan());
			holder.tv_price.setText("гд"+clothes.getPrice());
		
		return convertView;
	}
	class viewholder{
		ImageView food_image,img_jian,img_jia;
		TextView tv_price,tv_name,tv_sell_count,tv_count;
	}
	public void setMarketListener(MarketListener marketListener) {
		this.marketListener = marketListener;
	}
	class CountClickListener implements OnClickListener{

		private TextView tv_count;
		private ImageView img_jian;
		private int type=1;
		public CountClickListener(TextView tv_count,int type){
			this.tv_count=tv_count;
			this.type=type;
			this.img_jian=(ImageView)tv_count.getTag();
		}
		@Override
		public void onClick(View arg0) {
			int currentCount=0;
			if(!tv_count.getText().toString().equals("")){
				currentCount=Integer.parseInt(tv_count.getText().toString());
			}
			if(type==1&&img_jian.getVisibility()==View.GONE){
				tv_count.setVisibility(View.VISIBLE);
				img_jian.setVisibility(View.VISIBLE);
			}
			Clothes clothes=(Clothes)arg0.getTag();
			if(type==1){
				marketListener.add(arg0, clothes);
			}else{
				marketListener.jian(clothes);
			}
			currentCount=currentCount+type;
			tv_count.setText(currentCount+"");
			if(currentCount==0){
				arg0.setVisibility(View.GONE);
				tv_count.setVisibility(View.GONE);
			}
		}
		
	}
}
