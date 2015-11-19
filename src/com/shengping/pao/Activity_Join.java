package com.shengping.pao;

import com.baidu.mapapi.model.LatLng;
import com.shengping.pao.PopupWindow.TimePicker;
import com.shengping.pao.PopupWindow.TimePicker.SelectListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Activity_Join extends Activity implements OnClickListener,SelectListener{
	private TextView tv_time;
	private EditText tv_location;
	
	private final int request_start=1;
	private TimePicker timepicker;
	private LatLng location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("商家入驻");
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		tv_time=(TextView)findViewById(R.id.tv_time);
		tv_location=(EditText)findViewById(R.id.tv_location);
		tv_location.setOnClickListener(this);
		
		timepicker=new TimePicker(this);
		timepicker.setSelectListener(this);
		RelativeLayout lable_select_time=(RelativeLayout)findViewById(R.id.lable_select_time);
		lable_select_time.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.lable_select_time){
			timepicker=timepicker.builder();
			timepicker.show();
//			Intent intent=new Intent(this,Activity_SetShopTime.class);
//			startActivity(intent);
		}else if(v.getId()==R.id.tv_location){
			Intent intent=new Intent(this,Activity_SelectLocation.class);
			intent.putExtra("current_location", "");
			intent.putExtra("type", "start");
			intent.putExtra("title", "所在地");
			startActivityForResult(intent, request_start);
		}
	}

	@Override
	public void select_ok(String s) {
		tv_time.setText(s);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			   Bundle b=data.getExtras(); //data为B中回传的Intent
			   String address=b.getString("address");//str即为回传的值
			   tv_location.setText(address);
			   double lat=b.getDouble("lat");
			   double latlong=b.getDouble("latlong");
			   location=new LatLng(lat, latlong);
		}
	}
}
