package com.shengping.pao.fragment;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.shengping.pao.Activity_ForgetPssword;
import com.shengping.pao.Activity_Register;
import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.model.UserInfo;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.ClearEditText;
import com.shengping.pao.view.LoadingDialog;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Login_UserName extends Fragment implements TextWatcher,OnClickListener,MyHttpCallBack{
	private View contentView;
	private ClearEditText tv_name,tv_password;
	private TextView tv_forget;
	private Button btn_login;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_login_username, null);
		tv_name=(ClearEditText)contentView.findViewById(R.id.edit_username);
		tv_name.addTextChangedListener(this);
		tv_password=(ClearEditText)contentView.findViewById(R.id.edit_password);
		tv_password.addTextChangedListener(this);
		btn_login=(Button)contentView.findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
		tv_forget=(TextView)contentView.findViewById(R.id.tv_forget);
		tv_forget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(getContext(),Activity_ForgetPssword.class);
				startActivity(intent);
			}
		});
		return contentView;
	}
	@Override
	public void afterTextChanged(Editable edt) {
		if(!tv_name.getText().toString().equals("")&&!tv_password.getText().toString().equals("")){
			btn_login.setBackgroundColor(getResources().getColor(R.color.tab_select));
		}else{
			btn_login.setBackgroundColor(getResources().getColor(R.color.btn_unuse));
		}
	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_login){
			if(tv_name.getText().toString().equals("")){
				Toast.makeText(getContext(), "«Î ‰»Îƒ˙µƒ ÷ª˙∫≈", Toast.LENGTH_LONG).show();
			}else if(tv_password.getText().toString().equals("")){
				Toast.makeText(getContext(), "«Î ‰»Î√‹¬Î", Toast.LENGTH_LONG).show();
			}else{
				LoadingDialog.showWindow(getContext());
				MyHttp http=new MyHttp(getContext());
				Map<String,String> params=new HashMap<String, String>();
				params.put("phone", tv_name.getText().toString());
				params.put("pwd", MyUtil.stringToMD5(tv_password.getText().toString()));
				String url=UrlUtil.getUrl("login_pwd", UrlUtil.Service);
				http.Http_post(url, params,this);
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
		try {
			Toast.makeText(getContext(),response.getString("message"), Toast.LENGTH_LONG).show();
			if(response.getBoolean("status")){
				JSONObject data=response.getJSONObject("data");
				UserInfo user=new UserInfo();
				user.setUserName(data.getString("m_Mobile"));
				user.setMoney(data.getDouble("m_SYMoney"));
				user.setToken(data.getString("token"));
				Fragment_Menu.getInstence().setName(data.getString("m_Mobile"));
				MyApplication.getInstence().setUser(user);
				getActivity().finish();
			}
			
		} catch (JSONException e) {
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
		MyUtil.alert("«ÎºÏ≤ÈÕ¯¬Á∫Û÷ÿ ‘", getContext());
	}
}
