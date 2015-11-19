package com.shengping.pao;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.shengping.pao.fragment.Fragment_Menu;
import com.shengping.pao.fragment.Fragment_content;
import com.shengping.pao.model.UserInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class MainActivity extends SlidingFragmentActivity{
	private Fragment_content mContent;
	private static MainActivity instence;
	private MyApplication application;
	private UserInfo user;
	
	
	public static MainActivity getInstence(){
		return instence;
	} 
	private boolean isloade=false;
	 @Override  
	 public void onWindowFocusChanged(boolean hasFocus){  
	        if (hasFocus&&!isloade)  
	        {  
	        	mContent.initPopup();
	        	isloade=true;
	        }  
	 }  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		application=MyApplication.getInstence();
		user=application.getUser();
		initSlidingMenu(savedInstanceState);
		
		instence=this;
	}
	/**
	 * 初始化侧边栏
	 */
	@SuppressWarnings("deprecation")
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
		if (savedInstanceState != null) {
			mContent =(Fragment_content)getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new Fragment_content();
		}
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.content_frame, mContent).commit();
		getSlidingMenu().showContent();
		// 设置左侧滑动菜单
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new Fragment_Menu()).commit();
		
		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单阴影的图像资源
		sm.setShadowDrawable(getResources().getDrawable(R.drawable.menu_bg_shadow));
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式,这里设置为全屏
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.0f);

	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void show(View v){
		toggle();
//		Toast.makeText(this, "here", Toast.LENGTH_LONG).show();
	}
	
}
