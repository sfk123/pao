package com.shengping.pao;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.shengping.pao.model.Address;
import com.shengping.pao.util.MyHttp;
import com.shengping.pao.util.MyHttp.MyHttpCallBack;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.util.UrlUtil;
import com.shengping.pao.view.ClearEditText;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//添加收获地址
public class Activity_CreatAddress extends Activity implements OnClickListener,DrawableRightListener,MyHttpCallBack{

	private XEditText  tv_phone;
	private ClearEditText edt_name;
	private RadioButton btn_isdefault,radio_man,radio_lady;
	private EditText location_end_input;
	private TextView location_end_select,tv_btn_submit;
	private final int request_location=1;
	private final int request_contact=2;
	private JSONObject location;
	private String sex="先生";//先生或女士
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creat_address);
		findViewById(R.id.btn_back).setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("添加收货地址");
		
		tv_btn_submit=(TextView)findViewById(R.id.tv_btn_submit);
		btn_isdefault=(RadioButton)findViewById(R.id.btn_isdefault);
		btn_isdefault.setOnClickListener(this);
		edt_name=(ClearEditText)findViewById(R.id.edt_name);
		radio_man=(RadioButton)findViewById(R.id.radio_man);
		radio_man.setOnClickListener(this);
		radio_lady=(RadioButton)findViewById(R.id.radio_lady);
		radio_lady.setOnClickListener(this);
		location_end_input=(EditText)findViewById(R.id.location_end_input);
		tv_phone=(XEditText)findViewById(R.id.tv_phone);
		tv_phone.setDrawableRightListener(this);
		location_end_select=(TextView)findViewById(R.id.location_end_select);
		location_end_select.setOnClickListener(this);
		tv_btn_submit.setOnClickListener(this);
	}
	@Override
	public void onDrawableRightClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);  
        startActivityForResult(intent, request_contact);  
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.location_end_select){
			Intent intent=new Intent(this,Activity_SelectLocation.class);
			intent.putExtra("current_location", location_end_select.getText().toString());
			intent.putExtra("type", "end");
			intent.putExtra("title", "送到...");
			startActivityForResult(intent, request_location);
		}else if(v.getId()==R.id.tv_btn_submit){
			if(edt_name.getText().toString().equals("")){
				MyUtil.alert("请输入联系人姓名", this);
			}else if(tv_phone.getText().toString().equals("")){
				MyUtil.alert("请输入联系人电话", this);
			}else if(!MyUtil.isMobileNO(tv_phone.getText().toString())){
				MyUtil.alert("联系人电话输入有误", this);
			}else if(location==null){
				MyUtil.alert("请输入收货地址", this);
			}else{
				LoadingDialog.showWindow(this);
				Map<String, String> params=new HashMap<String, String>();
				params.put("token",MyApplication.getInstence().getUser().getToken());
				params.put("UserId", MyApplication.getInstence().getUser().getId()+"");
				params.put("RealName", edt_name.getText().toString());
				params.put("Address", location_end_select.getText().toString()+" "+location_end_input.getText().toString());
				params.put("Mobile", tv_phone.getText().toString());
				if(btn_isdefault.getTag().toString().equals("true"))
					params.put("OrderIndex", "-1");
				else
					params.put("OrderIndex", "0");
				params.put("sGPRS", location.toString());
				params.put("sex", sex);
				MyHttp http=new MyHttp(this);
				http.Http_post(UrlUtil.getUrl("crearAddress", UrlUtil.Service), params, this);
			}
		}else if(v.getId()==R.id.btn_isdefault){
			if(btn_isdefault.getTag().toString().equals("true")){
				btn_isdefault.setButtonDrawable(R.drawable.select_no);
				btn_isdefault.setTag("false");
			}else{
				btn_isdefault.setButtonDrawable(R.drawable.location_select);
				btn_isdefault.setTag("true");
			}
		}else if(v.getId()==R.id.radio_man){
			sex="先生";
			radio_man.setButtonDrawable(R.drawable.location_select);
			radio_lady.setButtonDrawable(R.drawable.select_no);
		}else if(v.getId()==R.id.radio_lady){
			sex="女士";
			radio_man.setButtonDrawable(R.drawable.select_no);
			radio_lady.setButtonDrawable(R.drawable.location_select);
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==request_location){
			   Bundle b=data.getExtras(); //data为B中回传的Intent
			   String address=b.getString("address");//str即为回传的值
			   double lat=b.getDouble("lat");
			   double latlong=b.getDouble("latlong");
			   location_end_select.setText(address);
			   location=new JSONObject();
			   try {
					location.put("lat", lat);
					location.put("long", latlong);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			}else if(requestCode==request_contact){
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
            // 获得联系人的ID号  
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);  
            String contactId = cursor.getString(idColumn);  
            // 获得联系人电话的cursor  
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
//                  switch (phone_type) {//此处请看下方注释  
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
	public void onResponse(JSONObject response) {
		if(LoadingDialog.isShowing()){
			LoadingDialog.dismiss();
		}
		try {
			Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
			if(response.getBoolean("status")){
				Address address=JSON.parseObject(response.getString("data"), Address.class);
				Activity_SelectAddress.getInstence().addAddress(address);
				finish();
//				System.out.println(address.getAddress());
			}
		} catch (JSONException e) {
			Toast.makeText(this, "出现错误："+e.getMessage(), Toast.LENGTH_LONG).show();
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
			if(error.networkResponse!=null)
			Log.e("Volley", new String(error.networkResponse.data, "GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(this, "出现错误，请检查网络后重试", Toast.LENGTH_LONG).show();
	}  
}
