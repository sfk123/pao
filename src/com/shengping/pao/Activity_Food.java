package com.shengping.pao;

import com.shengping.pao.fragment.Fragment_Food_Breakfast;
import com.shengping.pao.fragment.Fragment_Food_Dinner;
import com.shengping.pao.fragment.Fragment_Food_Midnight;
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

public class Activity_Food  extends FragmentActivity implements OnClickListener{
	private TextView tv_dinner,tv_breakfast,tv_midnight;
	private ViewPager viewPager;
	private MyPagerAdapter adapter;
	private ImageView my_market;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的美食");
		my_market=(ImageView)findViewById(R.id.my_market);
		my_market.setVisibility(View.VISIBLE);
		viewPager=(ViewPager)findViewById(R.id.viewPager);
		adapter=new MyPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(mPageChangeListener);
		
		tv_dinner=(TextView)findViewById(R.id.tv_dinner);
		tv_dinner.setOnClickListener(this);
		tv_breakfast=(TextView)findViewById(R.id.tv_breakfast);
		tv_breakfast.setOnClickListener(this);
		tv_midnight=(TextView)findViewById(R.id.tv_midnight);
		tv_midnight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.tv_dinner){
			viewPager.setCurrentItem(0);
		}else if(v.getId()==R.id.tv_breakfast){
			viewPager.setCurrentItem(1);
		}else if(v.getId()==R.id.tv_midnight){
			viewPager.setCurrentItem(2);
		}
	}
	private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

		@Override
        public void onPageSelected(int arg0)
        {
            if(arg0==0){
            	setCurrentSelect(tv_dinner);
            	setNoSelect(tv_breakfast,tv_midnight);
            }else if(arg0==1){
            	setCurrentSelect(tv_breakfast);
            	setNoSelect(tv_dinner,tv_midnight);
            }else if(arg0==2){
            	setCurrentSelect(tv_midnight);
            	setNoSelect(tv_breakfast,tv_dinner);
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
        	tv.setBackgroundResource(R.drawable.bg_food_type_selected);
        }
        private void setNoSelect(TextView... tvs){
        	for(TextView tv:tvs){
        		tv.setTextColor(getResources().getColor(R.color.text_black));
            	tv.setBackgroundResource(R.drawable.bg_food_type);
        	}
        }
    };
	private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
    	private Fragment_Food_Dinner frg_dinner;
    	private Fragment_Food_Breakfast frg_breakfast;
    	private Fragment_Food_Midnight frg_midnight;
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
            frg_dinner=new Fragment_Food_Dinner();
            frg_breakfast=new Fragment_Food_Breakfast();
            frg_midnight=new Fragment_Food_Midnight();
        }

        @Override
        public Fragment getItem(int position)
        {
        	if(position==0){
        		return frg_dinner;
        	}else if(position==1){
        		
        		return frg_breakfast;
        	}else if(position==2){
        		return frg_midnight;
        	}
            return null;
        }

        @Override
        public int getCount()
        {
            return 3;
        }

    }
}
