package com.shengping.pao.fragment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.model.UserInfo;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.ClearEditText;
import com.shengping.pao.view.LoadingDialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment_Login_Phone extends Fragment implements OnClickListener,MyHttpCallBack{
	private View contentView;
	private ClearEditText edit_phone,edit_code;
	private Button send_code,btn_login;
	private int httpTpye=1;
	private final int http_sendcode=1;
	private MyHttp http;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_login_phone, null);
		edit_phone=(ClearEditText)contentView.findViewById(R.id.edit_phone);
		edit_code=(ClearEditText)contentView.findViewById(R.id.edit_code);
		
		send_code=(Button)contentView.findViewById(R.id.send_code);
		btn_login=(Button)contentView.findViewById(R.id.btn_login);
		edit_phone.addTextChangedListener(new editListener(send_code));
		edit_code.addTextChangedListener(new editListener(btn_login));
		http=new MyHttp(getContext());
		return contentView;
	}
	class editListener implements TextWatcher{
		private Button btn;
		public editListener(Button btn){
			this.btn=btn;
		}
		@Override
		public void afterTextChanged(Editable arg0) {
			if(arg0.toString().equals("")){
				btn.setBackgroundColor(getResources().getColor(R.color.btn_unuse));
				btn.setEnabled(false);
			}else{
				btn.setBackgroundColor(getResources().getColor(R.color.tab_select));
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.send_code){
			if(edit_phone.getText().toString().equals("")){
				MyUtil.alert("请输入手机号！", getContext());
			}else if(!MyUtil.isMobileNO(edit_phone.getText().toString())){
				MyUtil.alert("手机号输入有误！", getContext());
			}else{
				LoadingDialog.showWindow(getContext());
				Map<String, String> params=new HashMap<String, String>();
				params.put("phone", edit_phone.getText().toString());
				String url=UrlUtil.getUrl("sendCode", UrlUtil.Service);
				httpTpye=http_sendcode;
				http.Http_post(url, params, this);
			}
		}
	}
	private void cancleDialog(){
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
	}
	@Override
	public void onResponse(JSONObject response) {
		cancleDialog();
		try{
		if(httpTpye==http_sendcode){
			Toast.makeText(getContext(),response.getString("message"), Toast.LENGTH_LONG).show();
			
		}else{
			
		}
		}catch(JSONException e){
			Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
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
		MyUtil.alert("请检查网络后重试", getContext());
	}
}
