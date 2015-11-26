package com.shengping.pao;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.shengping.pao.model.Address;
import com.shengping.pao.model.PaotuiInfo;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.LoadingDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ConfirmOrder  extends Activity implements OnClickListener,MyHttpCallBack{
	private ImageView img_select_1,img_select_2,img_select_3,img_select_4;
	private TextView tv_cost,//跑腿费
					money_total,//合计支付
					tv_remarkes,//备注
					tv_phone;//电话
	private Button btn_confirm;//结算按钮
	private String pay_type="pay_normal";
	private PaotuiInfo paotui;
	private double coast;//费用
	private JSONObject location_start,location_end;
	private String address_start,transportation;
	private Address address;
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("确定订单");
		
		address=(Address)getIntent().getSerializableExtra("address");
		TextView tv_start=(TextView)findViewById(R.id.tv_start);
		address_start=getIntent().getStringExtra("start");
		transportation=getIntent().getStringExtra("transportation");
		tv_start.setText(address_start);
		TextView tv_end=(TextView)findViewById(R.id.tv_end);
		tv_end.setText(address.getAddress());
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		tv_remarkes=(TextView)findViewById(R.id.tv_remarkes);
		tv_remarkes.setText(getIntent().getStringExtra("remarkes"));
		tv_phone=(TextView)findViewById(R.id.tv_phone);
		tv_phone.setText(address.getMobile());
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
		btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
		
		TextView money_total=(TextView)findViewById(R.id.money_total);
		TextView tv_cost=(TextView)findViewById(R.id.tv_cost);
		TextView tv_destence=(TextView)findViewById(R.id.tv_destence);
		double destence=getIntent().getDoubleExtra("destence", 0);
		tv_destence.setText("路程"+destence+"公里");
		//下面计算跑腿费
		try{
			paotui=MyApplication.getInstence().getPaotuiInfo();
			coast=paotui.getQibujia();//起步价
			Calendar cNow = Calendar.getInstance();
			Calendar cBegin = Calendar.getInstance();
			Calendar cEnd = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");//小写的mm表示的是分钟  
			JSONObject Midnight_time=paotui.getMidnight_time();
			Date date_start=sdf.parse(Midnight_time.getString("start"));  
			cBegin.setTime(date_start);
//			System.out.println("开始时间："+cBegin.get(Calendar.HOUR_OF_DAY)+":"+cBegin.get(Calendar.MINUTE));
			Date date_end=sdf.parse(Midnight_time.getString("end"));  
			cEnd.setTime(date_end);
			if (cNow.compareTo(cBegin) > 0 && cNow.compareTo(cEnd) < 0) {//夜间服务
				coast=coast+paotui.getMidnight_cost();
			}
			if(!paotui.getTianqi().equals("正常")){//恶劣天气费
				coast=coast+paotui.getTianqifei();
			}
			if(destence>paotui.getMRGLS()){//超出默认公里费用
				double d_value=destence-paotui.getMRGLS();
				int d_value_int=(int)d_value;
				if(d_value>d_value_int){
					d_value_int++;
				}
				coast=coast+d_value_int*paotui.getLJPTF();
			}
			tv_cost.setText("￥"+coast);
			money_total.setText(tv_cost.getText());
			location_start=new JSONObject();
			location_start.put("lat", getIntent().getDoubleExtra("lat_start", 0));
			location_start.put("long", getIntent().getDoubleExtra("long_start", 0));
			location_end=new JSONObject();
			location_end.put("lat", getIntent().getDoubleExtra("lat_end", 0));
			location_end.put("long", getIntent().getDoubleExtra("long_end", 0));
		}catch(Exception e){
			e.printStackTrace();
			MyUtil.alert("出现错误："+e.getMessage(), this);
		}
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
		}else if(v.getId()==R.id.btn_confirm){
			if(pay_type.equals("pay_express")){
				try{
				Map<String, String> params=new HashMap<String, String>();
				params.put("token", MyApplication.getInstence().getUser().getToken());
				params.put("UserId", MyApplication.getInstence().getUser().getId()+"");
				params.put("PayMoney", coast+"");
				params.put("FwType", "3");//帮我送类型
				params.put("FwType", "0");//货到付款
				params.put("Areaid", MyApplication.getInstence().getAreaId());//当前城市编码
				params.put("Ordercontent", tv_remarkes.getText().toString());//订单备注
				params.put("phone", tv_phone.getText().toString());//电话
				params.put("addressid", address.getId()+"");//收获地址
				params.put("StartLocation", location_start.toString());//
				params.put("StartAddress", address_start);//
				JSONObject attache=new JSONObject();
				attache.put("transportation", transportation);
				params.put("attache", attache.toString());//附属信息
				MyHttp http=new MyHttp(this);
				LoadingDialog.showWindow(this);
				http.Http_post(UrlUtil.getUrl("downOrder", UrlUtil.Service), params, this);
				}catch(JSONException e){
					e.printStackTrace();
					Toast.makeText(this, "出错了："+e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}else{
//				params.put("FwType", "1");
			}
		}
	}
	@Override
	public void onResponse(JSONObject response) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		try {
			Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
			if(response.getBoolean("status")){
				
			}
			
		} catch (JSONException e) {
			Toast.makeText(this, "出错了："+e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		error.printStackTrace();
		try {
			if(error.networkResponse!=null)
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, "出现错误，请检查网络后重试", Toast.LENGTH_LONG).show();
	}
}
