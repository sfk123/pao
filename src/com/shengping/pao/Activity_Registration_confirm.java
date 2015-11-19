package com.shengping.pao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Registration_confirm extends Activity implements OnClickListener {

	private TextView tv_phone,tv_info,tv_address;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_confirm);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("π“∫≈Ω·À„");
		
		Button btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
		tv_phone=(TextView)findViewById(R.id.tv_phone);
		tv_info=(TextView)findViewById(R.id.tv_info);
		tv_address=(TextView)findViewById(R.id.tv_address);
		
		tv_phone.setText(getIntent().getStringExtra("phone"));
		tv_info.setText(getIntent().getStringExtra("message"));
		tv_address.setText(getIntent().getStringExtra("start"));
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
