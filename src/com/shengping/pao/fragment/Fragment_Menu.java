package com.shengping.pao.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.shengping.pao.Activity_AboutUs;
import com.shengping.pao.Activity_Business_Enter;
import com.shengping.pao.Activity_Collection;
import com.shengping.pao.Activity_Join;
import com.shengping.pao.Activity_Login;
import com.shengping.pao.Activity_MyOrder;
import com.shengping.pao.Activity_Recharge;
import com.shengping.pao.Activity_Suggest;
import com.shengping.pao.Activity_Ticket;
import com.shengping.pao.MyApplication;
import com.shengping.pao.R;
import com.shengping.pao.adapter.Adapter_Home_My;
import com.shengping.pao.adapter.adapterData;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.LoadingDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Menu extends Fragment implements OnClickListener,OnItemClickListener,MyHttpCallBack{

	private View contentView;
	private TextView tv_username,//用户名
					tv_btn_logout;//退出按钮
	private ListView list_my;
	private int[] windowSize;
	private List<String> names;
	private Adapter_Home_My adapter;
	private boolean listViewInit=false;
	private static Fragment_Menu instence;
	public static Fragment_Menu getInstence(){
		return instence;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		contentView=inflater.inflate(R.layout.layout_menu, null);
		windowSize=MyUtil.getWindowSize(getActivity());
		initView(contentView);
		instence=this;
		if(MyApplication.getInstence().getUser().getToken()!=null){
			setName(MyApplication.getInstence().getUser().getUserName());
		}
		return contentView;
	}
	public void setName(String name){
		tv_username.setText(name);
		tv_btn_logout.setVisibility(View.VISIBLE);
	}
	private void logout(){
		tv_username.setText("请登陆/注册");
		tv_btn_logout.setVisibility(View.GONE);
		MyApplication.getInstence().setUser(null);
	}
	public void setMoney(){
		adapter.setTotalMoney(MyApplication.getInstence().getUser().getMoney());
		adapter.notifyDataSetChanged();
	}
	private void initView(View contentView){
		RelativeLayout lable_user=(RelativeLayout)contentView.findViewById(R.id.lable_user);
		lable_user.getLayoutParams().height=(int)(200*((double)windowSize[1]/1010));
		lable_user.setOnClickListener(this);
		list_my=(ListView)contentView.findViewById(R.id.list_my);
		List<Integer> icons=new ArrayList<>();
		icons.add(R.drawable.ico_1);
		icons.add(R.drawable.ico_2);
		icons.add(R.drawable.ico_3);
		icons.add(R.drawable.ico_4);
		icons.add(R.drawable.ico_5);
		icons.add(R.drawable.ico_6);
		icons.add(R.drawable.ico_7);
		icons.add(R.drawable.ico_8);
		icons.add(R.drawable.ico_9);
		icons.add(R.drawable.ico_10);
		names=new ArrayList<String>();
		names.add("我的余额");
		names.add("我要充值");
		names.add("我的代金券");
		names.add("我的订单");
		names.add("我的收藏");
		names.add("商家入驻");
		names.add("加盟申请");
		names.add("投诉建议");
		names.add("关于我们");
		names.add("我要分享");
		adapterData data=new adapterData();
		data.setIcons(icons);
		data.setNames(names);
		adapter=new Adapter_Home_My(getActivity(), data);
		list_my.setAdapter(adapter);
		list_my.setOnItemClickListener(this);
//		list_my.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				if(!listViewInit&&arg1){
//					listViewInit=true;
//					MyUtil.setListViewHeightBasedOnChildren(list_my,MyUtil.dip2px(getContext(), 1));
//				}
//				
//			}
//		});
		
		tv_username=(TextView)contentView.findViewById(R.id.tv_username);
		tv_btn_logout=(TextView)contentView.findViewById(R.id.tv_btn_logout);
		tv_btn_logout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.lable_user){
			Intent intent=new Intent(getContext(),Activity_Login.class);
			startActivity(intent);

		}else if(v.getId()==R.id.tv_btn_logout){
			LoadingDialog.showWindow(getContext());
			Map<String, String> params=new HashMap<>();
			params.put("token", MyApplication.getInstence().getUser().getToken());
			params.put("phone", MyApplication.getInstence().getUser().getUserName());
			MyHttp http=new MyHttp(getContext());
			http.Http_post(UrlUtil.getUrl("logout", UrlUtil.Service), params, this);
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if(names.get(position).equals("我要充值")){
			Intent intent=new Intent(getContext(),Activity_Recharge.class);
			startActivity(intent);
		}else if(names.get(position).equals("我的代金券")){
			Intent intent=new Intent(getContext(),Activity_Ticket.class);
			startActivity(intent);
		}else if(names.get(position).equals("我的订单")){
			Intent intent=new Intent(getContext(),Activity_MyOrder.class);
			startActivity(intent);
		}else if(names.get(position).equals("商家入驻")){
			Intent intent=new Intent(getContext(),Activity_Business_Enter.class);
			startActivity(intent);
		}else if(names.get(position).equals("我的收藏")){
			Intent intent=new Intent(getContext(),Activity_Collection.class);
			startActivity(intent);
		}else if(names.get(position).equals("投诉建议")){
			Intent intent=new Intent(getContext(),Activity_Suggest.class);
			startActivity(intent);
		}else if(names.get(position).equals("加盟申请")){
			Intent intent=new Intent(getContext(),Activity_Join.class);
			startActivity(intent);
		}else if(names.get(position).equals("关于我们")){
			Intent intent=new Intent(getContext(),Activity_AboutUs.class);
			startActivity(intent);
		}
	}
	@Override
	public void onResponse(JSONObject response) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		try {
			if(response.getBoolean("status")){
				logout();
			}else{
				Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyUtil.alert("请检查网络后重试", getActivity());
	}
}
