package com.shengping.pao;

import com.shengping.pao.fragment.Fragment_Wash_Normal;
import com.shengping.pao.fragment.Fragment_Wash_Textile;

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

public class Activity_Wash extends FragmentActivity implements OnClickListener{
	
	private ViewPager viewpager;
	private TextView tv_normal,tv_textile;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wash);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("ÎÒµÄÏ´µÓ");
		
		viewpager=(ViewPager)findViewById(R.id.viewPager);
		viewpager.setOnPageChangeListener(mPageChangeListener);
		viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tv_normal=(TextView)findViewById(R.id.tv_normal);
		tv_textile=(TextView)findViewById(R.id.tv_textile);
		tv_normal.setOnClickListener(this);
		tv_textile.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_normal){
			viewpager.setCurrentItem(0);
		}else if(v.getId()==R.id.tv_textile){
			viewpager.setCurrentItem(1);
		}
	}
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        @SuppressWarnings("deprecation")
		@Override
        public void onPageSelected(int arg0)
        {
            if(arg0==0){
            	tv_normal.setBackgroundColor(getResources().getColor(R.color.white));
            	tv_normal.setTextColor(getResources().getColor(R.color.tab_select));
            	tv_textile.setBackgroundColor(getResources().getColor(R.color.login_select));
            	tv_textile.setTextColor(getResources().getColor(R.color.text_black));
            }else if(arg0==1){
            	
            	
            	tv_textile.setBackgroundColor(getResources().getColor(R.color.white));
            	tv_textile.setTextColor(getResources().getColor(R.color.tab_select));
            	tv_normal.setBackgroundColor(getResources().getColor(R.color.login_select));
            	tv_normal.setTextColor(getResources().getColor(R.color.text_black));
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
    };
	private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
    	private Fragment_Wash_Normal frg_normal;
    	private Fragment_Wash_Textile frg_textile;
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
            frg_normal=new Fragment_Wash_Normal();
            frg_textile=new Fragment_Wash_Textile();
        }

        @Override
        public Fragment getItem(int position)
        {
        	if(position==0){
        		return frg_normal;
        	}else if(position==1){
        		
        		return frg_textile;
        	}
            return null;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

    }
}
