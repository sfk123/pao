package com.shengping.pao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 购物车进来的订单确认页面
 * @author Administrator
 *
 */
public class Activity_Market_Confirm extends Activity implements OnClickListener{
	private ImageView img_select_2,img_select_3,img_select_4;
	
	private String pay_type="pay_normal";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_market_confirm);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("订单确认");
		
		img_select_2=(ImageView)findViewById(R.id.img_select_2);
		img_select_3=(ImageView)findViewById(R.id.img_select_3);
		img_select_4=(ImageView)findViewById(R.id.img_select_4);
		
		setClickListener(findViewById(R.id.pay_express),findViewById(R.id.pay_aplay),findViewById(R.id.pay_wx));
		
	}
	private void setClickListener(View...views){
		for(View v:views){
			v.setOnClickListener(this);
		}
	}
	private void setPay_select(ImageView img){
		img.setImageResource(R.drawable.location_select);
	}
	private void setPay_noSelect(ImageView... images){
		for(ImageView img:images){
			img.setImageResource(R.drawable.select_no);
		}
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.pay_express){
			pay_type="pay_express";
			setPay_select(img_select_4);
			setPay_noSelect(img_select_2,img_select_3);
		}else if(v.getId()==R.id.pay_aplay){
			pay_type="pay_aplay";
			setPay_select(img_select_3);
			setPay_noSelect(img_select_2,img_select_4);
		}else if(v.getId()==R.id.pay_wx){
			pay_type="pay_wx";
			setPay_select(img_select_2);
			setPay_noSelect(img_select_3,img_select_4);
		}
	}
}
