package com.shengping.pao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Coast_detail  extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coast_default);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("�����շ�˵��");
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
