package com.shengping.pao;

import com.shengping.pao.adapter.Adapter_My_Driver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_My_Driver extends Activity implements OnClickListener,OnItemClickListener{

	private ListView mylist;
	private Adapter_My_Driver adapter;
	private String[] names;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_driver);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("�ҵ�˾��");
		
		names=getResources().getStringArray(R.array.date_my_driver);
		
		adapter=new Adapter_My_Driver(this);
		mylist=(ListView)findViewById(R.id.mylist);
		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if(names[position].equals("��Ҫ˾��")){
			Intent intent=new Intent(this,Activity_MyDriver_Driver.class);
			startActivity(intent);
		}else if(names[position].equals("��Ҫ�ó�")){
			Intent intent=new Intent(this,Activity_MyDriver_UseCar.class);
			intent.putExtra("type", "��Ҫ�ó�");
			startActivity(intent);
		}else if(names[position].equals("��Ҫר��")){
			Intent intent=new Intent(this,Activity_MyDriver_UseCar.class);
			intent.putExtra("type", "��Ҫר��");
			startActivity(intent);
		}else if(names[position].equals("��Ҫ�޳�")){
			Intent intent=new Intent(this,Activity_MyDriver_Repair.class);
			startActivity(intent);
		}
	}
}
