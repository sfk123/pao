package com.shengping.pao.adapter;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.Myinterface.MarketListener;
import com.shengping.pao.model.Food;
import com.shengping.pao.view.SectionedBaseAdapter;


public class SectionedAdapter_HelpBuy_Other extends SectionedBaseAdapter {
	private Map<String,List<Food>> food_maplist;
	private List<String> foodType;
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	private ImageLoader imageloader;
	private MarketListener marketListener;
	
	public SectionedAdapter_HelpBuy_Other(Map<String,List<Food>> food_maplist,List<String> foodType,Context context){
		this.food_maplist=food_maplist;
		this.foodType=foodType;
		inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageloader=MyApplication.getInstence().getmImageLoader();
		options=MyApplication.getInstence().getOptions();
	}

	@Override
	public Object getItem(int section, int position) {
		// TODO Auto-generated method stub
		return food_maplist.get(foodType.get(section));
	}

	@Override
	public long getItemId(int section, int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getSectionCount() {
		// TODO Auto-generated method stub
		return foodType.size();
	}

	@Override
	public int getCountForSection(int section) {
		// TODO Auto-generated method stub
		return food_maplist.get(foodType.get(section)).size();
	}

	@Override
	public View getItemView(int section, int position, View convertView,
			ViewGroup parent) {
		viewholder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.item_helpbuy_other_list, null);
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
		Food food=food_maplist.get(foodType.get(section)).get(position);
		holder.img_jian.setTag(food);
		holder.img_jia.setTag(food);
		holder.img_jian.setOnClickListener(new CountClickListener(holder.tv_count, -1));
		holder.img_jia.setOnClickListener(new CountClickListener(holder.tv_count, 1));
		holder.tv_name.setText(food.getName());
		holder.tv_sell_count.setText("“—œ˙ €"+food.getSellCount());
		holder.tv_price.setText(food.getPrice()+"");
		return convertView;
	}
	class viewholder{
		ImageView food_image,img_jian,img_jia;
		TextView tv_price,tv_name,tv_sell_count,tv_count;
	}
	@Override
	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent) {
		 LinearLayout layout = null;
	        if (convertView == null) {
	            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            layout = (LinearLayout) inflator.inflate(R.layout.food_list_header_item, null);
	        } else {
	            layout = (LinearLayout) convertView;
	        }
	        layout.setClickable(false);
	        ((TextView) layout.findViewById(R.id.textItem)).setText(foodType.get(section));
	        return layout;
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
			Food food=(Food)arg0.getTag();
			if(type==1){
				marketListener.add(arg0, food);
			}else{
				marketListener.jian(food);
			}
			currentCount=currentCount+type;
			tv_count.setText(currentCount+"");
			if(currentCount==0){
				arg0.setVisibility(View.GONE);
				tv_count.setVisibility(View.GONE);
			}
		}
		
	}
	public MarketListener getMarketListener() {
		return marketListener;
	}

	public void setMarketListener(MarketListener marketListener) {
		this.marketListener = marketListener;
	}
}
