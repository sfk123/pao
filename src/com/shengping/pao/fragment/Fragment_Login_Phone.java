package com.shengping.pao.fragment;

import com.shengping.pao.R;
import com.shengping.pao.view.ClearEditText;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_Login_Phone extends Fragment{
	private View contentView;
	private ClearEditText edit_phone,edit_code;
	private Button send_code,btn_login;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_login_phone, null);
		edit_phone=(ClearEditText)contentView.findViewById(R.id.edit_phone);
		edit_code=(ClearEditText)contentView.findViewById(R.id.edit_code);
		
		send_code=(Button)contentView.findViewById(R.id.send_code);
		btn_login=(Button)contentView.findViewById(R.id.btn_login);
		edit_phone.addTextChangedListener(new editListener(send_code));
		edit_code.addTextChangedListener(new editListener(btn_login));
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
}
