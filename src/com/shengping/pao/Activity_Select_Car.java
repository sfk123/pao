package com.shengping.pao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.adapter.Adapter_Select_Car;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.view.XListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Select_Car extends Activity implements OnClickListener,OnItemClickListener {
	private XListView mylist;
	private ArrayList<String> select_cars;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_car);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("ѡ����");
		ImageView btn_ok=(ImageView)findViewById(R.id.my_market);
		btn_ok.setVisibility(View.VISIBLE);
		btn_ok.setOnClickListener(this);
		btn_ok.setImageResource(R.drawable.btn_ok);
		btn_ok.getLayoutParams().width=150;
		btn_ok.getLayoutParams().height=60;
		
		mylist=(XListView)findViewById(R.id.mylist);
		mylist.setPullLoadEnable(true);
		JSONArray cars=new JSONArray();
		JSONObject car;
		try{
		for(int i=0;i<10;i++){
			car=new JSONObject();
			car.put("price", 235);
			car.put("info", "��������");
			car.put("name", "����"+i);
			cars.put(car);
		}
		}catch(JSONException e){
			e.printStackTrace();
		}
		Adapter_Select_Car adapter=new Adapter_Select_Car(this,cars);
		mylist.setAdapter(adapter);
		mylist.setOnItemClickListener(this);
		select_cars=new ArrayList<String>();
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.my_market){
			if(select_cars.size()==0){
				MyUtil.alert("��ѡ������Ҫ�ĳ���", this);
			}else{
			Intent intent=new Intent();
			intent.putStringArrayListExtra("cars", select_cars);
			setResult(RESULT_OK, intent); //intentΪA�����Ĵ���Bundle��intent����ȻҲ�����Լ������µ�Bundle
			finish();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Adapter_Select_Car.viewholder holder=(Adapter_Select_Car.viewholder)arg1.getTag();
		if(holder.ico_select.getTag()==null||!(Boolean)holder.ico_select.getTag()){
			holder.ico_select.setImageResource(R.drawable.location_select);
			holder.ico_select.setTag(true);
			select_cars.add(holder.tv_name.getText().toString());
		}else{
			holder.ico_select.setImageResource(R.drawable.select_no);
			holder.ico_select.setTag(false);
			select_cars.remove(holder.tv_name.getText().toString());
		}
		
	}
}
