package com.shengping.pao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_ForgetPssword extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpassword);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("’“ªÿ√‹¬Î");
		
		Button btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.btn_confirm){
			Intent intent=new Intent(this,Activity_ForgetPwd_Reset.class);
			startActivity(intent);
			finish();
		}
	}
}
