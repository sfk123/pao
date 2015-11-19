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
	 * ��ʼ�������
	 */
	@SuppressWarnings("deprecation")
	private void initSlidingMenu(Bundle savedInstanceState) {
		// ��������״̬��Ϊ����õ�֮ǰ�����Fragment������ʵ����MyFragment
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
		// ������໬���˵�
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new Fragment_Menu()).commit();
		
		// ʵ���������˵�����
		SlidingMenu sm = getSlidingMenu();
		// ���ÿ������һ����Ĳ˵�
		sm.setMode(SlidingMenu.LEFT);
		// ���û�����Ӱ�Ŀ��
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// ���û����˵���Ӱ��ͼ����Դ
		sm.setShadowDrawable(getResources().getDrawable(R.drawable.menu_bg_shadow));
		// ���û����˵���ͼ�Ŀ��
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ý��뽥��Ч����ֵ
		sm.setFadeDegree(0.35f);
		// ���ô�����Ļ��ģʽ,��������Ϊȫ��
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// �����·���ͼ���ڹ���ʱ�����ű���
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
