package com.shengping.pao;

import java.util.ArrayList;
import java.util.List;

import com.shengping.pao.PopupWindow.HelpBuy_Other_ProductType;
import com.shengping.pao.fragment.Fragment_Buseness_Evaluate;
import com.shengping.pao.fragment.Fragment_HelpBuy_Other_List;
import com.shengping.pao.util.MyUtil;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_HelpBuy_Other_Detail extends FragmentActivity implements OnClickListener{
	private TextView tv_name,tv_price_min,push_time,tv_sell_time;
	private ViewPager pager;
	private TextView tv_type,tv_comment;
	private ImageView img_back;
	
	private HelpBuy_Other_ProductType popup_select;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_helpbuy_other_detail);
		img_back=(ImageView)findViewById(R.id.img_back);
		img_back.setOnClickListener(this);
		tv_name=(TextView)findViewById(R.id.tv_name);
		tv_name.setText(getIntent().getStringExtra("business_name"));
		tv_price_min=(TextView)findViewById(R.id.tv_price_min);
		tv_price_min.setText("起送价0");
		push_time=(TextView)findViewById(R.id.push_time);
		push_time.setText("配送时间30");
		tv_sell_time=(TextView)findViewById(R.id.tv_sell_time);
		tv_sell_time.setText("营业时间16:00-02:00");
		
		tv_type=(TextView)findViewById(R.id.tv_type);
		tv_comment=(TextView)findViewById(R.id.tv_comment);
		tv_type.setOnClickListener(this);
		tv_comment.setOnClickListener(this);
		pager=(ViewPager)findViewById(R.id.pager);
		pager.setOnPageChangeListener(mPageChangeListener);
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		List<String> foodType=new ArrayList<>();
		for(int i=0;i<10;i++){
			foodType.add("类别"+i);
		}
		popup_select=new HelpBuy_Other_ProductType(this, foodType,tv_type);
	}
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
        public void onPageSelected(int arg0)
        {
            if(arg0==0){
            	setCurrentSelect(tv_type);
            	setNoSelect(tv_comment);
            }else if(arg0==1){
            	setCurrentSelect(tv_comment);
            	setNoSelect(tv_type);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        	
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
	private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
    	private Fragment_HelpBuy_Other_List frg_list;
    	private Fragment_Buseness_Evaluate frg_comment;
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
            frg_list=new Fragment_HelpBuy_Other_List();
            frg_comment=new Fragment_Buseness_Evaluate();
        }

        @Override
        public Fragment getItem(int position)
        {
        	if(position==0){
        		return frg_list;
        	}else if(position==1){
        		
        		return frg_comment;
        	}
            return null;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

    }
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.tv_type){
			if(pager.getCurrentItem()==0){
				popup_select.builder(MyUtil.getWindowSize(this)[0]).show();
			}else
			pager.setCurrentItem(0);
		}else if(v.getId()==R.id.tv_comment){
			pager.setCurrentItem(1);
		}else if(v.getId()==R.id.img_back){
			finish();
		}
	}
}
