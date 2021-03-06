package com.shengping.pao.util;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MyHttp {
	private RequestQueue mQueue ;
	private MyHttpCallBack callback;
	private DefaultRetryPolicy policy;
	public MyHttp(Context context){
		mQueue = Volley.newRequestQueue(context);  
		policy=new DefaultRetryPolicy(10000, //设置默认超时时间
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	}
	public void Http_get(String url,MyHttpCallBack mycallback){
		this.callback=mycallback;
		Log.i("volley", url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,  
		        new Response.Listener<JSONObject>() {  
		            @Override  
		            public void onResponse(JSONObject response) {  
		            	callback.onResponse(response);  
		            }  
		        }, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		            	callback.onErrorResponse(error);  
		            }  
		        }); 
		jsonObjectRequest.setRetryPolicy(policy);
		mQueue.add(jsonObjectRequest);  
	}
	public void Http_post(String url,Map<String, String> params,MyHttpCallBack mycallback){
		this.callback=mycallback;
		Request<JSONObject> request = new NormalPostRequest(url,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			        	callback.onResponse(response);
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			        	callback.onErrorResponse(error);
			        }
			    }, params);
		request.setRetryPolicy(policy);
		mQueue.add(request);  
	}
	public interface MyHttpCallBack{
		public void onResponse(JSONObject response);
		public void onErrorResponse(VolleyError error);
	}
}
