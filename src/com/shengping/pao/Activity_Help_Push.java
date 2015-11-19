package com.shengping.pao;

import com.baidu.mapapi.model.LatLng;
import com.shengping.pao.PopupWindow.Select_Transportation;
import com.shengping.pao.PopupWindow.Select_Transportation.SelectListener;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.view.XEditText;
import com.shengping.pao.view.XEditText.DrawableRightListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Activity_Help_Push extends Activity implements OnClickListener,DrawableRightListener{
	private ImageView btn_back;
	private Button btn_confirm;
	private TextView tv_title,tv_transportation;
	private EditText location_start_select,location_end_select;
	private EditText location_start_input,location_end_input,tv_message;
	private XEditText tv_phone;
	private SelectListener selectListener;
	private final int request_start=1;
	private LatLng start,end;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__help__push);
		btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("帮我送");
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
		location_end_select=(EditText)findViewById(R.id.location_end_select);
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
			intent.putExtra("title", "货在...");
			startActivityForResult(intent, request_start);
		}else if(v.getId()==R.id.location_end_select){
			Intent intent=new Intent(this,Activity_SelectLocation.class);
			intent.putExtra("current_location", location_start_select.getText().toString());
			intent.putExtra("type", "end");
			intent.putExtra("title", "货到...");
			startActivityForResult(intent, request_start);
		}else if(v.getId()==R.id.btn_confirm){
			if(start==null){
				MyUtil.alert("请选择发货地址",this);
				return;
			}else if(end==null){
				MyUtil.alert("请选择收货地址",this);
				return;
			}else if(tv_phone.getText().toString().equals("")){
				MyUtil.alert("请填写收货人电话！",this);
			}else{
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
				startActivity(intent);
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==request_start){
			   Bundle b=data.getExtras(); //data为B中回传的Intent
			   String address=b.getString("address");//str即为回传的值
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
	public void onDrawableRightClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK,  
                ContactsContract.Contacts.CONTENT_URI);  
        startActivityForResult(intent, 2);  
	}
}
