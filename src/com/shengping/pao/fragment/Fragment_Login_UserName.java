package com.shengping.pao.fragment;

import com.shengping.pao.Activity_ForgetPssword;
import com.shengping.pao.R;
import com.shengping.pao.view.ClearEditText;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_Login_UserName extends Fragment implements TextWatcher{
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
}
