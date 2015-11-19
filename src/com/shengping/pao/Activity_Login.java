package com.shengping.pao;

import com.shengping.pao.fragment.Fragment_Login_Phone;
import com.shengping.pao.fragment.Fragment_Login_UserName;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Login extends FragmentActivity implements OnClickListener{
	 private ViewPager mViewPager;
	 private PagerAdapter mPagerAdapter;
	 
	 private TextView tv_login_username,tv_login_phone;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		tv_login_username=(TextView)findViewById(R.id.tv_login_username);
		tv_login_username.setOnClickListener(this);
		tv_login_phone=(TextView)findViewById(R.id.tv_login_phone);
		tv_login_phone.setOnClickListener(this);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
	}
	 private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

	        @SuppressWarnings("deprecation")
			@Override
	        public void onPageSelected(int arg0)
	        {
	            if(arg0==0){
	            	tv_login_username.setBackgroundColor(getResources().getColor(R.color.login_select));
	            	tv_login_username.setTextColor(getResources().getColor(R.color.text_black));
	            	tv_login_phone.setBackgroundColor(getResources().getColor(R.color.white));
	            	tv_login_phone.setTextColor(getResources().getColor(R.color.tab_select));
	            }else if(arg0==1){
	            	
	            	
	            	tv_login_username.setBackgroundColor(getResources().getColor(R.color.white));
	            	tv_login_username.setTextColor(getResources().getColor(R.color.tab_select));
	            	tv_login_phone.setBackgroundColor(getResources().getColor(R.color.login_select));
	            	tv_login_phone.setTextColor(getResources().getColor(R.color.text_black));
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
    	private Fragment_Login_UserName frg_username;
    	private Fragment_Login_Phone frg_phone;
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
            frg_username=new Fragment_Login_UserName();
            frg_phone=new Fragment_Login_Phone();
        }

        @Override
        public Fragment getItem(int position)
        {
        	if(position==0){
        		return frg_username;
        	}else if(position==1){
        		
        		return frg_phone;
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
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_login_username){
			mViewPager.setCurrentItem(1);
		}else if(v.getId()==R.id.tv_login_phone){
			mViewPager.setCurrentItem(0);
		}
	}
}
