package com.shengping.pao.fragment;

import com.shengping.pao.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Business_Detail extends Fragment {
	private View contentView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_business_detail, null);
		
		return contentView;
	}
}
