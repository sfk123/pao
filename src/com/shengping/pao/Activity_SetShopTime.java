package com.shengping.pao;

import com.shengping.pao.util.MyUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Activity_SetShopTime extends Activity implements OnClickListener{
	
	private TextView tv_text1,tv_text2;
	private LinearLayout lable_start_pm,lable_end_pm,lable_start_am,lable_end_am;
	
	private boolean isloaded=false;
	private int[] windowSize;
	 @Override  
	 public void onWindowFocusChanged(boolean hasFocus)  {  
	        if (hasFocus&&!isloaded)  
	        {  
	        	isloaded=true;
	        	int width=windowSize[0]-MyUtil.dip2px(this, 40)-tv_text1.getMeasuredWidth()-tv_text2.getMeasuredWidth();
	        	width=width/2;
	        	setWidth(width,lable_start_pm,lable_end_pm,lable_end_am,lable_start_am);
	        }  
	}  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_shoptime);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("设置营业时间");
		tv_text1=(TextView)findViewById(R.id.tv_text1);
		tv_text2=(TextView)findViewById(R.id.tv_text2);
		lable_start_pm=(LinearLayout)findViewById(R.id.lable_start_pm);
		lable_end_pm=(LinearLayout)findViewById(R.id.lable_end_pm);
		lable_start_am=(LinearLayout)findViewById(R.id.lable_start_am);
		lable_end_am=(LinearLayout)findViewById(R.id.lable_end_am);
		
		windowSize=MyUtil.getWindowSize(this);
		
	}
	private void setWidth(int width,View... views){
		for(View view:views){
			view.getLayoutParams().width=width;
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
