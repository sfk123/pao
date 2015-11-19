package com.shengping.pao;


import org.json.JSONObject;

import com.shengping.pao.fragment.Fragment_Buseness_Evaluate;
import com.shengping.pao.fragment.Fragment_Business_Detail;
import com.shengping.pao.fragment.Fragment_ChoseDishes;
import com.shengping.pao.util.MyUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Business_Detail_Food extends FragmentActivity implements OnClickListener {
	private TextView text1, text2, text3;
	private ViewPager pager;
	private ImageView tabLine;
	private ImageView my_market;//购物车图标改成收藏图标
	
	private int tabWidth ;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_detail_food);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText(getIntent().getStringExtra("title"));
		my_market=(ImageView)findViewById(R.id.my_market);
		my_market.setImageResource(R.drawable.ico_collection);
		my_market.setVisibility(View.VISIBLE);
		
		text1=(TextView)findViewById(R.id.text1);
		text1.setOnClickListener(this);
		text2=(TextView)findViewById(R.id.text2);
		text3=(TextView)findViewById(R.id.text3);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		tabLine = (ImageView) findViewById(R.id.tabLine);
		pager = (ViewPager) findViewById(R.id.pager);
		tabWidth = MyUtil.getWindowSize(this)[0] / 3;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_line);
		Bitmap b = MyUtil.resizeBitmap(bitmap, tabWidth,MyUtil.dip2px(this, 3f));// 设置tab的宽度和高度
		tabLine.setImageBitmap(b);
		
		pager.setOnPageChangeListener(mPageChangeListener);
		pager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.text1){
			pager.setCurrentItem(0);
		}else if(v.getId()==R.id.text2){
			pager.setCurrentItem(1);
		}else if(v.getId()==R.id.text3){
			pager.setCurrentItem(2);
		}
	}
	
	public class FragmentAdapter extends FragmentPagerAdapter {
		private Fragment_ChoseDishes frg_chose;
		private Fragment_Buseness_Evaluate frg_evaluate;
		private Fragment_Business_Detail frg_detail;
		public FragmentAdapter(FragmentManager fm) {
			super(fm);
			frg_chose=new Fragment_ChoseDishes();
			frg_evaluate=new Fragment_Buseness_Evaluate();
			frg_detail=new Fragment_Business_Detail();
		}

		@Override
		public Fragment getItem(int index) {
			if(index==0)
				return frg_chose;
			else if(index==1)
				return frg_evaluate;
			else if(index==2)
				return frg_detail;
			return null;
		}

		@Override
		public int getCount() {
			return 3;
		}
	}
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
        public void onPageSelected(int arg0)
        {
            if(arg0==0){
            	setCurrentSelect(text1);
            	setNoSelect(text2,text3);
            }else if(arg0==1){
            	setCurrentSelect(text2);
            	setNoSelect(text1,text3);
            }else if(arg0==2){
            	setCurrentSelect(text3);
            	setNoSelect(text1,text2);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        	Matrix matrix = new Matrix();
			// 设置激活条的最终位置
			switch (arg0) {
			case 0:
				// 使用set直接设置到那个位置
				matrix.setTranslate(0, 0);
				break;
			case 1:
				matrix.setTranslate(tabWidth, 0);
				break;
			case 2:
				matrix.setTranslate(tabWidth * 2, 0);
				break;
			}
			// 在滑动的过程中，计算出激活条应该要滑动的距离
			float t = (tabWidth) * arg1;
			// 使用post追加数值
			matrix.postTranslate(t, 0);
			tabLine.setImageMatrix(matrix);
        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {

        }
        private void setCurrentSelect(TextView tv){
        	tv.setTextColor(getResources().getColor(R.color.tab_select));
        }
        private void setNoSelect(TextView... tvs){
        	for(TextView tv:tvs){
        		tv.setTextColor(getResources().getColor(R.color.text_black));
        	}
        }
       
    };
}
