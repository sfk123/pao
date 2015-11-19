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

public class Activity_MoveHouse_Confirm  extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movehouse_confirm);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("È·¶¨¶©µ¥");
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
		btn_confirm.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
