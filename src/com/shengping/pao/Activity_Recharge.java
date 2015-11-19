package com.shengping.pao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我要充值页面
 * @author Administrator
 *
 */
public class Activity_Recharge extends Activity implements OnClickListener{
	private Button btn_500,btn_1000,btn_2000,btn_5000;
	private ImageView select_aplay,select_wx;
	
	private int currentSelectValue=500;
	private String pay_type="wx";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("我要充值");
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_500=(Button)findViewById(R.id.btn_500);
		btn_1000=(Button)findViewById(R.id.btn_1000);
		btn_2000=(Button)findViewById(R.id.btn_2000);
		btn_5000=(Button)findViewById(R.id.btn_5000);
		RelativeLayout lable_aplay=(RelativeLayout)findViewById(R.id.lable_aplay);
		RelativeLayout lable_xm=(RelativeLayout)findViewById(R.id.lable_xm);
		select_aplay=(ImageView)findViewById(R.id.select_aplay);
		select_wx=(ImageView)findViewById(R.id.select_wx);
		setClickListener(btn_back,btn_500,btn_1000,btn_2000,btn_5000,lable_aplay,lable_xm);
	}
	private void setClickListener(View... views){
		for(View v:views){
			v.setOnClickListener(this);
		}
	}
	private void setButtonNormal(Button...buttons ){
		for(Button btn:buttons){
			btn.setBackgroundResource(R.drawable.border_btn_recharge);
			btn.setTextColor(getResources().getColor(R.color.text_black));
		}
	}
	private void setButtonCurrent(Button btn){
		btn.setBackgroundResource(R.drawable.border_btn_recharge_seleted);
		btn.setTextColor(getResources().getColor(R.color.white));
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.btn_500){
			currentSelectValue=500;
			setButtonCurrent(btn_500);
			setButtonNormal(btn_1000,btn_2000,btn_5000);
		}else if(v.getId()==R.id.btn_1000){
			currentSelectValue=1000;
			setButtonCurrent(btn_1000);
			setButtonNormal(btn_500,btn_2000,btn_5000);
		}else if(v.getId()==R.id.btn_2000){
			currentSelectValue=2000;
			setButtonCurrent(btn_2000);
			setButtonNormal(btn_500,btn_1000,btn_5000);
		}else if(v.getId()==R.id.btn_5000){
			currentSelectValue=5000;
			setButtonCurrent(btn_5000);
			setButtonNormal(btn_500,btn_1000,btn_2000);
		}else if(v.getId()==R.id.lable_xm){
			pay_type="wx";
			select_aplay.setImageResource(R.drawable.select_no);
			select_wx.setImageResource(R.drawable.location_select);
		}else if(v.getId()==R.id.lable_aplay){
			pay_type="aplay";
			select_aplay.setImageResource(R.drawable.location_select);
			select_wx.setImageResource(R.drawable.select_no);
		}
	}
}
