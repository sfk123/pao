package com.shengping.pao.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.Myinterface.MarketListener;
import com.shengping.pao.adapter.SectionedAdapter;
import com.shengping.pao.model.Food;
import com.shengping.pao.model.Product;
import com.shengping.pao.view.PinnedHeaderListView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 点菜
 * @author Administrator
 *
 */
public class Fragment_ChoseDishes extends Fragment implements OnClickListener,MarketListener{
	private View contentView;
	private PinnedHeaderListView foodlistview;
	private LinearLayout lable_type;
	private TextView tv_market_count,tv_total;
	private ImageView img_market;
	private ImageView ball;// 小圆点
	private ViewGroup anim_mask_layout;//动画层
	private Button btn_ok;
	private JSONArray json_data;
	private LayoutInflater inflater;
	private List<View> list_type;
	private List<String> foodType=null;
	private SectionedAdapter adapter;
	private boolean isScroll = true;
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_chose_dishes, null);
		foodlistview=(PinnedHeaderListView)contentView.findViewById(R.id.country_lvcountry);
		lable_type=(LinearLayout)contentView.findViewById(R.id.lable_type);
		tv_market_count=(TextView)contentView.findViewById(R.id.tv_market_count);
		img_market=(ImageView)contentView.findViewById(R.id.img_market);
		tv_total=(TextView)contentView.findViewById(R.id.tv_total);
		btn_ok=(Button)contentView.findViewById(R.id.btn_ok);
		foodType=new ArrayList<>();
		list_type=new ArrayList<View>();
		foodType.add("茶叶蛋");
		for(int i=0;i<10;i++){
			foodType.add("类别"+i);
		}
		for(int i=0;i<foodType.size();i++){
			View food_type=inflater.inflate(R.layout.item_food_type, null);
			food_type.setTag(i);
			food_type.setOnClickListener(this);
			TextView tv_type_name=(TextView)food_type.findViewById(R.id.tv_type_name);
			if(i==0){
				food_type.setBackgroundResource(R.drawable.border_food_type_select);
				tv_type_name.setTextColor(getResources().getColor(R.color.tab_select));
//				Drawable drawable_food_type_selected= getResources().getDrawable(R.drawable.ico_hot);
//				/// 这一步必须要做,否则不会显示.
//				drawable_food_type_selected.setBounds(0, 0, drawable_food_type_selected.getMinimumWidth(), drawable_food_type_selected.getMinimumHeight());
//				tv_type_name.setCompoundDrawables(drawable_food_type_selected,null,null,null);
			}
			tv_type_name.setText(foodType.get(i));
			list_type.add(food_type);
			lable_type.addView(food_type);
		}
		Map<String,List<Food>> food_maplist=new HashMap<>();
		List<Food> foods=new ArrayList<>();
		for(int i=0;i<10;i++){
			Food food=new Food();
			food.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1360144131,474953039&fm=58");
			food.setName("茶叶蛋");
			food.setPrice(1.5);
			food.setSellCount(999);
			food.setId(i);
			foods.add(food);
		}
		food_maplist.put("茶叶蛋", foods);
		foods=new ArrayList<>();
		for(int i=0;i<10;i++){
			Food food=new Food();
			food.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1360144131,474953039&fm=58");
			food.setName("茶叶蛋");
			food.setPrice(1.5);
			food.setId(i);
			food.setSellCount(999);
			foods.add(food);
		}
		for(int i=0;i<10;i++)
		food_maplist.put("类别"+i, foods);
		adapter=new SectionedAdapter(food_maplist, foodType, getContext());
		adapter.setMarketListener(this);
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle b){
		super.onActivityCreated(b);
		
		foodlistview.setAdapter(adapter);
		foodlistview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(isScroll){
					for (int i = 0; i < foodType.size(); i++)
					{

						TextView tv_type_name=(TextView)lable_type.getChildAt(i).findViewById(R.id.tv_type_name);
						if (i == adapter.getSectionForPosition(firstVisibleItem))
						{
							lable_type.getChildAt(i).setBackgroundResource(R.drawable.border_food_type_select);
							tv_type_name.setTextColor(getResources().getColor(R.color.tab_select));
						} else
						{
							lable_type.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.food_type_bg));
							tv_type_name.setTextColor(getResources().getColor(R.color.text_gray));
						}
					}
						
				}else{
					isScroll = true;
				}
			}
		});
	}
	@Override
	public void onClick(View arg0) {
		isScroll = false;
		int position=(Integer)arg0.getTag();
		for (int i = 0; i < lable_type.getChildCount(); i++)
		{
			TextView tv_type_name=(TextView)lable_type.getChildAt(i).findViewById(R.id.tv_type_name);
			if(i==position){
				lable_type.getChildAt(i).setBackgroundResource(R.drawable.border_food_type_select);
				tv_type_name.setTextColor(getResources().getColor(R.color.tab_select));
			}else{
				lable_type.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.food_type_bg));
				tv_type_name.setTextColor(getResources().getColor(R.color.text_gray));
			}
		}

		int rightSection = 0;
		for(int i=0;i<position;i++){
			rightSection += adapter.getCountForSection(i)+1;
		}
		foodlistview.setSelection(rightSection);
	}
	@Override
	public void add(View v, Product f) {
		int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
		ball = new ImageView(getContext());// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
		ball.setImageResource(R.drawable.sign);// 设置buyImg的图片
		setAnim(ball, startLocation,f);// 开始执行动画
	}
	@Override
	public void jian(Product f) {
		List<Product> marketlist=MyApplication.getInstence().DeleteOfMarket(f);
		tv_market_count.setText(marketlist.size() + "");//
		if(marketlist.size()!=0){
		double total=0;
	    for(Product f1:marketlist){
	    	total=total+f1.getPrice();
	    }
	    tv_total.setText("共￥"+total);
		}else{
			tv_market_count.setVisibility(View.GONE);
			img_market.setImageResource(R.drawable.market_no);
			tv_total.setText("购物车是空的");
			tv_total.setTextColor(getResources().getColor(R.color.text_gray));
			btn_ok.setBackgroundResource(R.drawable.corner_bg_gray);
		    btn_ok.setTextColor(getResources().getColor(R.color.text_black));
		}
	}
	private void setAnim(final View v, int[] startLocation,final Product f) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);//把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				startLocation);
		int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
		img_market.getLocationInWindow(endLocation);// shopCart是那个购物车

		// 计算位移
		int endX = 0 - startLocation[0] + 60;// 动画位移的X坐标
		int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(500);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
				MyApplication.getInstence().AddMarket(f);//让购买数量加1
				List<Product> marketlist=MyApplication.getInstence().getMarket();
				tv_market_count.getLayoutParams().width=LayoutParams.WRAP_CONTENT;
				tv_market_count.setText(marketlist.size() + "");//
				tv_market_count.setVisibility(View.VISIBLE);
				tv_market_count.measure(0, 0);
				if(tv_market_count.getMeasuredHeight()>tv_market_count.getMeasuredWidth()){
					tv_market_count.getLayoutParams().width=tv_market_count.getMeasuredHeight();
				}else{
					tv_market_count.getLayoutParams().height=tv_market_count.getMeasuredWidth();
				}
				tv_market_count.setGravity(Gravity.CENTER);
				img_market.setImageResource(R.drawable.market_enable);
			    //初始化  
			    Animation scaleAnimation = new ScaleAnimation(0.9f, 1.0f,0.9f,1.0f);  
			    //设置动画时间  
			    scaleAnimation.setDuration(100);  
			    img_market.startAnimation(scaleAnimation);  
			    double total=0;
			    for(Product f1:marketlist){
			    	total=total+f1.getPrice();
			    }
			    tv_total.setText("共￥"+total);
			    tv_total.setTextColor(getResources().getColor(R.color.tab_select));
			    btn_ok.setBackgroundResource(R.drawable.corner_bg);
			    btn_ok.setTextColor(getResources().getColor(R.color.white));
			}
		});

	}
	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}
	private View addViewToAnimLayout(final ViewGroup parent, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}
	
}
