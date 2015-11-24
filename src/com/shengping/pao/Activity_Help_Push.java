package com.shengping.pao;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.shengping.pao.PopupWindow.Select_Transportation;
import com.shengping.pao.PopupWindow.Select_Transportation.SelectListener;
import com.shengping.pao.fragment.Fragment_content;
import com.shengping.pao.model.PaotuiInfo;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.view.LoadingDialog;
import com.shengping.pao.view.XEditText;
import com.shengping.pao.view.XEditText.DrawableRightListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Help_Push extends Activity implements OnClickListener,DrawableRightListener,MyHttpCallBack{
	private ImageView btn_back;
	private Button btn_confirm;
	private TextView tv_title,tv_transportation,location_end_select;
	private EditText location_start_select;
	private EditText location_start_input,location_end_input,tv_message;
	private XEditText tv_phone;
	private SelectListener selectListener;
	private final int request_start=1;
	private LatLng start,end;
	private RoutePlanSearch mSearch;
	private double destence;//·��
	private boolean http_paotui=false;
	private boolean http_destence=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_push);
		btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("������");
		RelativeLayout lable_transportation=(RelativeLayout)findViewById(R.id.lable_transportation);
		lable_transportation.setOnClickListener(this);
		tv_transportation=(TextView)findViewById(R.id.tv_transportation);
		selectListener=new SelectListener() {
			
			@Override
			public void select_ok(String s) {
				tv_transportation.setText(s);
			}
		};
		location_start_select=(EditText)findViewById(R.id.location_start_select);
		location_start_select.setOnClickListener(this);
		location_end_select=(TextView)findViewById(R.id.location_end_select);
		location_end_select.setClickable(true);
		location_end_select.setOnClickListener(this);
		btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
		
		location_start_input=(EditText)findViewById(R.id.location_start_input);
		location_end_input=(EditText)findViewById(R.id.location_end_input);
		tv_phone=(XEditText)findViewById(R.id.tv_phone);
		tv_phone.setDrawableRightListener(this);
		tv_message=(EditText)findViewById(R.id.tv_message);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.lable_transportation){
			Select_Transportation select=new Select_Transportation(this).builder();
			select.setSelectListener(selectListener);
			select.show();
		}else if(v.getId()==R.id.location_start_select){
			Intent intent=new Intent(this,Activity_SelectLocation.class);
			intent.putExtra("current_location", location_start_select.getText().toString());
			intent.putExtra("type", "start");
			intent.putExtra("title", "����...");
			startActivityForResult(intent, request_start);
		}else if(v.getId()==R.id.location_end_select){
			Intent intent=new Intent(this,Activity_SelectLocation.class);
			intent.putExtra("current_location", location_start_select.getText().toString());
			intent.putExtra("type", "end");
			intent.putExtra("title", "����...");
			startActivityForResult(intent, request_start);
		}else if(v.getId()==R.id.btn_confirm){
			if(start==null){
				MyUtil.alert("��ѡ�񷢻���ַ",this);
				return;
			}else if(end==null){
				MyUtil.alert("��ѡ���ջ���ַ",this);
				return;
			}else if(tv_phone.getText().toString().equals("")){
				MyUtil.alert("����д�ջ��˵绰��",this);
			}else{
				http_destence=false;
				http_paotui=false;
				MyHttp http=new MyHttp(this);
				Map<String, String> params=new HashMap<String, String>();
				params.put("areaid", MyApplication.getInstence().getAreaId());
				http.Http_post(UrlUtil.getUrl("getpaotui", UrlUtil.Service), params, this);
				mSearch = RoutePlanSearch.newInstance();
			    mSearch.setOnGetRoutePlanResultListener(listener);
			    PlanNode stNode = PlanNode.withLocation(start);
		        PlanNode enNode = PlanNode.withLocation(end);
		        mSearch.drivingSearch((new DrivingRoutePlanOption())
		                .from(stNode)
		                .to(enNode));
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==request_start){
			   Bundle b=data.getExtras(); //dataΪB�лش���Intent
			   String address=b.getString("address");//str��Ϊ�ش���ֵ
			   String type=b.getString("type");
			   double lat=b.getDouble("lat");
			   double latlong=b.getDouble("latlong");
			   if(type.equals("start")){
				   location_start_select.setText(address);
				   start=new LatLng(lat, latlong);
			   }else{
				   location_end_select.setText(address);
				   end=new LatLng(lat, latlong);
			   }
			}else if(requestCode==2){
				Uri contactData = data.getData();  
                @SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(contactData, null, null, null,  
                        null);  
                cursor.moveToFirst();  
                String num = getContactPhone(cursor);  
                tv_phone.setText(num);
			}
		}
	}
	private String getContactPhone(Cursor cursor) {  
        // TODO Auto-generated method stub  
        int phoneColumn = cursor  
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);  
        int phoneNum = cursor.getInt(phoneColumn);  
        String result = "";  
        if (phoneNum > 0) {  
            // �����ϵ�˵�ID��  
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);  
            String contactId = cursor.getString(idColumn);  
            // �����ϵ�˵绰��cursor  
            Cursor phone = getContentResolver().query(  
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  
                    null,  
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="  
                            + contactId, null, null);  
            if (phone.moveToFirst()) {  
                for (; !phone.isAfterLast(); phone.moveToNext()) {  
                    int index = phone  
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);  
                    int typeindex = phone  
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);  
                    int phone_type = phone.getInt(typeindex);  
                    String phoneNumber = phone.getString(index);  
                    result = phoneNumber;  
//                  switch (phone_type) {//�˴��뿴�·�ע��  
//                  case 2:  
//                      result = phoneNumber;  
//                      break;  
//  
//                  default:  
//                      break;  
//                  }  
                }  
                if (!phone.isClosed()) {  
                    phone.close();  
                }  
            }  
        }  
        return result;  
    }  
	@Override
	public void onDrawableRightClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK,  
                ContactsContract.Contacts.CONTENT_URI);  
        startActivityForResult(intent, 2);  
	}
	@Override
	public void onResponse(JSONObject response) {
		try {
			System.out.println(response);
			if(response.getBoolean("status")){
				JSONObject data=response.getJSONObject("data");
				PaotuiInfo paotui=new PaotuiInfo();
				paotui.setId(data.getInt("id"));
				paotui.setKefu(data.getString("keFuTell"));
				paotui.setLJPTF(data.getDouble("ljptf"));
				paotui.setMidnight_cost(data.getDouble("midnight_cost"));
				paotui.setMidnight_time(new JSONObject(data.getString("midnight_time")));
				paotui.setMRGLS(data.getInt("mrgls"));
				paotui.setName(data.getString("companyName"));
				paotui.setQibujia(data.getDouble("qibujia"));
				paotui.setTianqi(data.getString("tianqi"));
				paotui.setTianqifei(data.getDouble("tianqifei"));
				paotui.setTime(data.getString("yinYeTime"));
				MyApplication.getInstence().setPaotuiInfo(paotui);
				Fragment_content.getInstence().refreshView();
				http_paotui=true;
				httpOK();
			}
		} catch (JSONException e) {
			clacleDialog();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
	@Override
	public void onErrorResponse(VolleyError error) {
		clacleDialog();
		error.printStackTrace();
		try {
			if(error.networkResponse!=null)
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, "���ִ����������������", Toast.LENGTH_LONG).show();
	}
	private void clacleDialog(){
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
	}
	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {  
	    public void onGetWalkingRouteResult(WalkingRouteResult result) {  
	        //    
	    }  
	    public void onGetTransitRouteResult(TransitRouteResult result) {  //ֻ��ȡ�ݳ�·��
	        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
	            Toast.makeText(Activity_Help_Push.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();  
	        }  
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {  
	            //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ  
	            //result.getSuggestAddrInfo()  
	            return;  
	        }  
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {  
	          
	        }  
	    }  
	    public void onGetDrivingRouteResult(DrivingRouteResult result) {  
	    	 if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	             Toast.makeText(Activity_Help_Push.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
	         }
	         if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	             //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ
	             //result.getSuggestAddrInfo()
	             return;
	         }
	         if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	        	 http_destence=true;
	        	 destence=(double)result.getRouteLines().get(0).getDistance()/1000;//·�̾���
	        	 httpOK();
	         }
	    }  
	};
	private void httpOK(){//����ȡ���룬�����ȹ�˾���ݶ����
		if(http_paotui&&http_destence){
			clacleDialog();
			Intent intent=new Intent(this,Activity_ConfirmOrder.class);
			intent.putExtra("lat_start", start.latitude);
			intent.putExtra("lat_end", end.latitude);
			intent.putExtra("long_start", start.longitude);
			intent.putExtra("long_end", end.longitude);
			intent.putExtra("start", location_start_select.getText().toString()+" "+location_start_input.getText().toString());
			intent.putExtra("end", location_end_select.getText().toString()+" "+location_end_input.getText().toString());
			intent.putExtra("phone", tv_phone.getText().toString());
			intent.putExtra("transportation", tv_transportation.getText().toString());
			intent.putExtra("remarkes", tv_message.getText().toString());
			intent.putExtra("destence", destence);
			startActivity(intent);
		}
	}
}
