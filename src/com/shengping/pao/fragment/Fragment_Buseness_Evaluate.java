package com.shengping.pao.fragment;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shengping.pao.R;
import com.shengping.pao.adapter.Adapter_Business_Evaluate;
import com.shengping.pao.view.XListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 美食商家评价
 * @author Administrator
 *
 */
public class Fragment_Buseness_Evaluate  extends Fragment implements OnClickListener{
	private View contentView;
	private XListView mylist;
	private JSONArray datalist;
	private Adapter_Business_Evaluate adapter;
	private View listHeader;
	private ImageView img_btn;
	private boolean img_selected=true;//只看有内容的评论
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.fragment_business_evaluate, null);
		mylist=(XListView)contentView.findViewById(R.id.mylist);
		mylist.setPullLoadEnable(true);
		datalist=new JSONArray();
		JSONObject json;
		try{
			for(int i=0;i<10;i++){
				json=new JSONObject();
				json.put("id", i);
				json.put("username", "日月教主");
				json.put("time", new Date().getTime());
				json.put("content", "内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容");
				datalist.put(json);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		listHeader=inflater.inflate(R.layout.business_evaluate_list_header, null);
		img_btn=(ImageView)listHeader.findViewById(R.id.img_btn);
		img_btn.setOnClickListener(this);
		return contentView;
	}
	@Override
	public void onActivityCreated(Bundle b){
		super.onActivityCreated(b);
		adapter=new Adapter_Business_Evaluate(datalist, getContext());
		mylist.addHeaderView(listHeader);
		mylist.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.img_btn){
			if(img_selected){
				img_btn.setImageResource(R.drawable.select_no);
				img_selected=false;
			}else{
				img_btn.setImageResource(R.drawable.location_select);
				img_selected=true;
			}
		}
	}
}
