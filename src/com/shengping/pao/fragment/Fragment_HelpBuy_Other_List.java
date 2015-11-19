package com.shengping.pao.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shengping.pao.Activity_Product_Detail;
import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.Myinterface.MarketListener;
import com.shengping.pao.adapter.SectionedAdapter_HelpBuy_Other;
import com.shengping.pao.model.Food;
import com.shengping.pao.model.Product;
import com.shengping.pao.view.PinnedHeaderListView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_HelpBuy_Other_List extends Fragment implements MarketListener,OnItemClickListener{
	private View contentView;
	private PinnedHeaderListView mylist;
	private ImageView img_market;
	private TextView tv_market_count,tv_total;
	private Button btn_ok;
	private ImageView ball;// СԲ��
	private ViewGroup anim_mask_layout;//������
	
	private List<String> foodType=null;
	private SectionedAdapter_HelpBuy_Other adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_helpbuy_other_list, null);
		mylist=(PinnedHeaderListView)contentView.findViewById(R.id.country_lvcountry);
		img_market=(ImageView)contentView.findViewById(R.id.img_market);
		tv_market_count=(TextView)contentView.findViewById(R.id.tv_market_count);
		tv_total=(TextView)contentView.findViewById(R.id.tv_total);
		btn_ok=(Button)contentView.findViewById(R.id.btn_ok);
		foodType=new ArrayList<>();
		for(int i=0;i<10;i++){
			foodType.add("���"+i);
		}
		Map<String,List<Food>> food_maplist=new HashMap<>();
		List<Food> foods=new ArrayList<>();
		for(int i=0;i<10;i++){
			Food food=new Food();
			food.setImage("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1360144131,474953039&fm=58");
			food.setName("��Ҷ��");
			food.setPrice(1.5);
			food.setSellCount(999);
			food.setId(i);
			foods.add(food);
		}
		for(int i=0;i<10;i++)
			food_maplist.put("���"+i, foods);
		adapter=new SectionedAdapter_HelpBuy_Other(food_maplist, foodType, getContext());
		adapter.setMarketListener(this);
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle b){
		super.onActivityCreated(b);
		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(this);
	}
	@Override
	public void add(View v, Product f) {
		int[] startLocation = new int[2];// һ���������飬�����洢��ť������Ļ��X��Y����
		v.getLocationInWindow(startLocation);// ���ǻ�ȡ����ť������Ļ��X��Y���꣨��Ҳ�Ƕ�����ʼ�����꣩
		ball = new ImageView(getContext());// buyImg�Ƕ�����ͼƬ���ҵ���һ��С��R.drawable.sign��
		ball.setImageResource(R.drawable.sign);// ����buyImg��ͼƬ
		setAnim(ball, startLocation,f);// ��ʼִ�ж���
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
	    tv_total.setText("����"+total);
		}else{
			tv_market_count.setVisibility(View.GONE);
			img_market.setImageResource(R.drawable.market_no);
			tv_total.setText("���ﳵ�ǿյ�");
			tv_total.setTextColor(getResources().getColor(R.color.text_gray));
			btn_ok.setBackgroundResource(R.drawable.corner_bg_gray);
		    btn_ok.setTextColor(getResources().getColor(R.color.text_black));
		}
	}
	private void setAnim(final View v, int[] startLocation,final Product f) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);//�Ѷ���С����ӵ�������
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				startLocation);
		int[] endLocation = new int[2];// �洢��������λ�õ�X��Y����
		img_market.getLocationInWindow(endLocation);// shopCart���Ǹ����ﳵ

		// ����λ��
		int endX = 0 - startLocation[0] + 60;// ����λ�Ƶ�X����
		int endY = endLocation[1] - startLocation[1];// ����λ�Ƶ�y����
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// �����ظ�ִ�еĴ���
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// �����ظ�ִ�еĴ���
		translateAnimationX.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(500);// ������ִ��ʱ��
		view.startAnimation(set);
		// ���������¼�
		set.setAnimationListener(new AnimationListener() {
			// �����Ŀ�ʼ
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			// �����Ľ���
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);
				MyApplication.getInstence().AddMarket(f);//�ù���������1
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
			    //��ʼ��  
			    Animation scaleAnimation = new ScaleAnimation(0.9f, 1.0f,0.9f,1.0f);  
			    //���ö���ʱ��  
			    scaleAnimation.setDuration(100);  
			    img_market.startAnimation(scaleAnimation);  
			    double total=0;
			    for(Product f1:marketlist){
			    	total=total+f1.getPrice();
			    }
			    tv_total.setText("����"+total);
			    tv_total.setTextColor(getResources().getColor(R.color.tab_select));
			    btn_ok.setBackgroundResource(R.drawable.corner_bg);
			    btn_ok.setTextColor(getResources().getColor(R.color.white));
			}
		});

	}
	/**
	 * @Description: ����������
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
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent=new Intent(getContext(),Activity_Product_Detail.class);
		startActivity(intent);
	}
}
