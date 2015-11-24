package com.shengping.pao;

import org.json.JSONException;

import com.shengping.pao.model.PaotuiInfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Coast_detail  extends Activity implements OnClickListener {

	private TextView tv_coast_detail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coast_default);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("�����շ�˵��");
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		
		PaotuiInfo paotui=MyApplication.getInstence().getPaotuiInfo();
		tv_coast_detail=(TextView)findViewById(R.id.tv_coast_detail);
		try {
			tv_coast_detail.setText(paotui.getMRGLS()+"���������ȷ�Ϊ"+paotui.getQibujia()+"Ԫ������ÿ�������"+paotui.getLJPTF()+"Ԫ;ҹ�����ѣ�"+
					paotui.getMidnight_time().getString("start")+"-"+paotui.getMidnight_time().getString("end")+"����"+paotui.getMidnight_cost()
					+"Ԫ������ҵ�����ȷ�����");
		} catch (JSONException e) {
			Toast.makeText(this, "���ִ���"+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
