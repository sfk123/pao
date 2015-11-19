package com.shengping.pao;

import java.util.List;

import com.shengping.pao.model.Product;
import com.shengping.pao.util.MyUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商品详情
 * @author Administrator
 *
 */
public class Activity_Product_Detail extends Activity implements OnClickListener{
	
	private ImageView img_product,img_jia,img_jian;
	private TextView tv_detail,tv_count,tv_market_count,tv_total;
	private Button btn_ok;
	private ImageView img_market;
	private ImageView ball;// 小圆点
	private ViewGroup anim_mask_layout;//动画层
	private Product p;
	private List<Product> marketlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		findViewById(R.id.img_back).setOnClickListener(this);
		int[] windowSize=MyUtil.getWindowSize(this);
		
		img_product=(ImageView)findViewById(R.id.img_product);
		@SuppressWarnings("deprecation")
		Bitmap bp=((BitmapDrawable)getResources().getDrawable(R.drawable.temp_product)).getBitmap();
		img_product.setImageBitmap(MyUtil.resizeBitmap(bp, windowSize[0]));
		tv_detail=(TextView)findViewById(R.id.tv_detail);
		tv_detail.setText("商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情商品详情");
		img_jia=(ImageView)findViewById(R.id.img_jia);
		img_jian=(ImageView)findViewById(R.id.img_jian);
		img_jia.setOnClickListener(this);
		img_jian.setOnClickListener(this);
		tv_count=(TextView)findViewById(R.id.tv_count);
		img_market=(ImageView)findViewById(R.id.img_market);
		tv_market_count=(TextView)findViewById(R.id.tv_market_count);
		tv_total=(TextView)findViewById(R.id.tv_total);
		btn_ok=(Button)findViewById(R.id.btn_ok);
		p=new Product();
		p.setPrice(25);
		marketlist=MyApplication.getInstence().getMarket();
		if(marketlist.size()>0){
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
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.img_back){
			finish();
		}else if(v.getId()==R.id.img_jia){
			int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
			v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
			ball = new ImageView(this);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
			ball.setImageResource(R.drawable.sign);// 设置buyImg的图片
			setAnim(ball, startLocation,p);// 开始执行动画
			if(tv_count.getText().equals("")){
				tv_count.setText("1");
			}else {
				int temp=Integer.parseInt(tv_count.getText().toString());
				temp++;
				tv_count.setText(temp+"");
			}
			tv_count.setVisibility(View.VISIBLE);
			img_jian.setVisibility(View.VISIBLE);
		}else if(v.getId()==R.id.img_jian){
			int temp=Integer.parseInt(tv_count.getText().toString());
			temp--;
			tv_count.setText(temp+"");
			if(temp==0){
				img_jian.setVisibility(View.GONE);
				tv_count.setVisibility(View.GONE);
			}
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
				marketlist=MyApplication.getInstence().getMarket();
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
		ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(this);
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
