package com.shengping.pao;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.shengping.pao.fragment.Fragment_Menu;
import com.shengping.pao.model.UserInfo;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.LoadingDialog;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Register extends Activity implements OnClickListener,MyHttpCallBack{
	private ImageView back_btn;
	private Button next;
	private TextView tishi2,//用户协议
					tv_btn_sendcode;//发送验证码按钮
	private EditText edt_code,//验证码
//						tv_name,//用户名
						tv_phone,//手机号
						edt_password,//密码
						edt_repassword;//确认密码
	private static Activity_Register instence;
	 private MyHttp http;
	 private int httpTpye=1;
	 private final int http_sendcode=1;
	 private final int http_testcode=2;
	 private final int http_submit=3;
	 private boolean phonecode_status=false;
	 private Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		back_btn=(ImageView)findViewById(R.id.btn_back);
		back_btn.setOnClickListener(this);
		TextView title=(TextView)findViewById(R.id.tv_title);
		title.setText("用户注册");
		
		tv_btn_sendcode=(TextView)findViewById(R.id.tv_btn_sendcode);
		tv_btn_sendcode.setOnClickListener(this);
		next=(Button)findViewById(R.id.next);
		next.setOnClickListener(this);
//		tv_name=(EditText)findViewById(R.id.register_name);//用户名
		tv_phone=(EditText)findViewById(R.id.loginaccount);
		edt_password=(EditText)findViewById(R.id.edt_password);
		edt_repassword=(EditText)findViewById(R.id.edt_repassword);
		edt_code=(EditText)findViewById(R.id.edt_code);
		edt_code.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (edt_code.hasFocus() == false) {//检查验证码输入
					if(edt_code.getText().toString().equals("")){
						Toast.makeText(Activity_Register.this, "请输入手机号", Toast.LENGTH_LONG).show();
						return;
					}
					Map<String,String> params=new HashMap<String, String>();
					params.put("phone", tv_phone.getText().toString());
					params.put("code", edt_code.getText().toString());
					String url=UrlUtil.getUrl("testPhoneCode", UrlUtil.Service);
					httpTpye=http_testcode;
					http.Http_post(url, params, Activity_Register.this);
				}

			}
			}); 
		tishi2=(TextView)findViewById(R.id.tishi2);
		tishi2.setOnClickListener(this);
		instence=this;
		http=new MyHttp(this);
	}
	public static Activity_Register getInstence(){
		return instence;
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_back:
			finish();
			break;
		case R.id.next:
			if(tv_phone.getText().toString().trim().equals("")){
				 Toast.makeText(getApplicationContext(), "请输入您的手机号!",Toast.LENGTH_SHORT).show();
			}else if(!MyUtil.isMobileNO(tv_phone.getText().toString().trim())){
				Toast.makeText(getApplicationContext(), "手机号输入有误!",Toast.LENGTH_SHORT).show();
			}else if(edt_password.getText().toString().trim().equals("")){
				Toast.makeText(getApplicationContext(), "请设置您的密码!",Toast.LENGTH_SHORT).show();
			}else if(edt_repassword.getText().toString().trim().equals("")){
				Toast.makeText(getApplicationContext(), "请重复您的密码!",Toast.LENGTH_SHORT).show();
			}else if(!edt_repassword.getText().toString().trim().equals(edt_password.getText().toString().trim())){
				Toast.makeText(getApplicationContext(), "密码输入不一致!",Toast.LENGTH_SHORT).show();
			}else if(edt_code.getText().toString().trim().equals("")){
				Toast.makeText(getApplicationContext(), "请输入验证码!",Toast.LENGTH_SHORT).show();
			}else if(!phonecode_status){
				MyUtil.alert("验证码输入错误！", this);
			}else{
				httpTpye=http_submit;
				LoadingDialog.showWindow(this);
				Map<String, String> params=new HashMap<String, String>();
				params.put("phone", tv_phone.getText().toString());
				params.put("pwd", edt_password.getText().toString());
				params.put("code", edt_code.getText().toString());
				String url=UrlUtil.getUrl("register", UrlUtil.Service);
				http.Http_post(url, params, this);
			}
			break;
		case R.id.tishi2:
//			Intent intent=new Intent(this,Activity_agreement.class);
//			startActivity(intent);
			break;
		case R.id.tv_btn_sendcode:
			if(tv_phone.getText().toString().trim().equals("")){
				 Toast.makeText(getApplicationContext(), "请输入您的手机号!",Toast.LENGTH_SHORT).show();
			}else if(!MyUtil.isMobileNO(tv_phone.getText().toString().trim())){
				Toast.makeText(getApplicationContext(), "手机号输入有误!",Toast.LENGTH_SHORT).show();
			}else{
				LoadingDialog.showWindow(this);
				Map<String, String> params=new HashMap<String, String>();
				params.put("phone", tv_phone.getText().toString());
				String url=UrlUtil.getUrl("sendCode", UrlUtil.Service);
				httpTpye=http_sendcode;
				http.Http_post(url, params, this);
			}
			break;
		}
		
	}
	private void cancleDialog(){
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
	}
	class runnable implements Runnable{  
    	int t=60;
        @Override    
        public void run() {       
        	if(t==0){
        		tv_btn_sendcode.setBackgroundResource(R.drawable.corner_bg);
        		tv_btn_sendcode.setTextColor(getResources().getColor(R.color.white));
        		tv_btn_sendcode.setClickable(true);
        		tv_btn_sendcode.setText("发送验证码");  
        		return;
        	}
        	tv_btn_sendcode.setText(t + "秒后重发"); 
            handler.postDelayed(this, 1000);  
        	t--;  
        	
        }    
    }  
	@Override
	public void onResponse(JSONObject response) {
		cancleDialog();
		try{
			if(httpTpye==http_sendcode){
				if(response.getBoolean("status")){
					tv_btn_sendcode.setBackgroundResource(R.drawable.corner_bg_gray);
					tv_btn_sendcode.setTextColor(getResources().getColor(R.color.text_black));
					tv_btn_sendcode.setClickable(false);
					handler.post(new runnable());
				}
					Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
				
			}else if(httpTpye==http_testcode){
				if(response.getBoolean("status")){
					phonecode_status=true;
					@SuppressWarnings("deprecation")
					Drawable drawable= getResources().getDrawable(R.drawable.ico_right);
					/// 这一步必须要做,否则不会显示.
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
					edt_code.setCompoundDrawables(null,null,drawable,null);
				}else{
					phonecode_status=false;
					Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
				}
			}else if(httpTpye==http_submit){
				if(response.getBoolean("status")){
					JSONObject data=response.getJSONObject("data");
					UserInfo user=new UserInfo();
					user.setId(data.getInt("m_ID"));
					user.setUserName(data.getString("m_Mobile"));
					user.setMoney(data.getDouble("m_SYMoney"));
					user.setToken(data.getString("token"));
					MyApplication.getInstence().setUser(user);
					Activity_Login.getInstence().finish();
					Fragment_Menu.getInstence().setName(tv_phone.getText().toString());
					finish();
				}
				Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
			}
		}catch(JSONException e){
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		cancleDialog();
		error.printStackTrace();
		try {
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyUtil.alert("请检查网络后重试", this);
	}

}
