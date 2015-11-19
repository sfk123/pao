package com.shengping.pao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Handler_Dingzhi_Detail extends Activity implements OnClickListener{
	
	private TextView tv_start,tv_end,tv_phone,tv_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handler_dingzhi_detail);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("È·¶¨¶©µ¥");
		
		tv_start=(TextView)findViewById(R.id.tv_start);
		tv_end=(TextView)findViewById(R.id.tv_end);
		tv_phone=(TextView)findViewById(R.id.tv_phone);
		tv_detail=(TextView)findViewById(R.id.tv_detail);
		
		tv_start.setText(getIntent().getStringExtra("start"));
		tv_end.setText(getIntent().getStringExtra("end"));
		tv_phone.setText(getIntent().getStringExtra("phone"));
		tv_detail.setText(getIntent().getStringExtra("message"));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
