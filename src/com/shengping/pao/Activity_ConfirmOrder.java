package com.shengping.pao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Activity_ConfirmOrder  extends Activity implements OnClickListener{
	private ImageView img_select_1,img_select_2,img_select_3,img_select_4;
	private String pay_type="pay_normal";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("ȷ������");
		TextView tv_start=(TextView)findViewById(R.id.tv_start);
		tv_start.setText(getIntent().getStringExtra("start"));
		TextView tv_end=(TextView)findViewById(R.id.tv_end);
		tv_end.setText(getIntent().getStringExtra("end"));
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		TextView tv_remarkes=(TextView)findViewById(R.id.tv_remarkes);
		tv_remarkes.setText(getIntent().getStringExtra("remarkes"));
		TextView tv_phone=(TextView)findViewById(R.id.tv_phone);
		tv_phone.setText(getIntent().getStringExtra("phone"));
		Button btn_confirm=(Button)findViewById(R.id.btn_confirm);
		RelativeLayout lable_coast=(RelativeLayout)findViewById(R.id.lable_coast);
		RelativeLayout pay_express=(RelativeLayout)findViewById(R.id.pay_express);
		RelativeLayout pay_aplay=(RelativeLayout)findViewById(R.id.pay_aplay);
		RelativeLayout pay_wx=(RelativeLayout)findViewById(R.id.pay_wx);
		RelativeLayout pay_normal=(RelativeLayout)findViewById(R.id.pay_normal);
		RelativeLayout lable_ticket=(RelativeLayout)findViewById(R.id.lable_ticket);
		setClickListener(pay_normal,pay_wx,pay_aplay,pay_express,lable_coast,btn_confirm,btn_back,lable_ticket);
		img_select_1=(ImageView)findViewById(R.id.img_select_1);
		img_select_2=(ImageView)findViewById(R.id.img_select_2);
		img_select_3=(ImageView)findViewById(R.id.img_select_3);
		img_select_4=(ImageView)findViewById(R.id.img_select_4);
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
		}else if(v.getId()==R.id.lable_coast){
			Intent intent=new Intent(this,Activity_Coast.class);
			intent.putExtra("lat_start", getIntent().getDoubleExtra("lat_start", 0));
			intent.putExtra("lat_end", getIntent().getDoubleExtra("lat_end", 0));
			intent.putExtra("long_start", getIntent().getDoubleExtra("long_start", 0));
			intent.putExtra("long_end", getIntent().getDoubleExtra("long_end", 0));
			startActivity(intent);
		}else if(v.getId()==R.id.pay_express){
			pay_type="pay_express";
			setPay_select(img_select_4);
			setPay_noSelect(img_select_1,img_select_2,img_select_3);
		}else if(v.getId()==R.id.pay_aplay){
			pay_type="pay_aplay";
			setPay_select(img_select_3);
			setPay_noSelect(img_select_1,img_select_2,img_select_4);
		}else if(v.getId()==R.id.pay_wx){
			pay_type="pay_wx";
			setPay_select(img_select_2);
			setPay_noSelect(img_select_1,img_select_3,img_select_4);
		}else if(v.getId()==R.id.pay_normal){
			pay_type="pay_normal";
			setPay_select(img_select_1);
			setPay_noSelect(img_select_2,img_select_3,img_select_4);
		}else if(v.getId()==R.id.lable_ticket){
			Intent intent=new Intent(this,Activity_Ticket.class);
			startActivity(intent);
		}
	}
}
