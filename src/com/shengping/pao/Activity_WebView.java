package com.shengping.pao;

import java.util.ArrayList;
import java.util.List;

import com.shengping.pao.view.ProgressWebView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_WebView extends Activity implements OnClickListener{

	private List<String> historys;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText(getIntent().getStringExtra("title"));
		historys=new ArrayList<>();
		ProgressWebView webview=(ProgressWebView)findViewById(R.id.myweb);
		 webview.setWebViewClient(new WebViewClient() {      
	            @Override      
	            public boolean shouldOverrideUrlLoading(WebView view, String url)      
	            {     
	            	if(historys.contains(url)){
	            		return false;
	            	}else{
		              view.loadUrl(url);   
		              historys.add(url);
		              return true;    
	            	}
	            }      
	          });  
		 webview.loadUrl(getIntent().getStringExtra("url"));
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}
	}
}
