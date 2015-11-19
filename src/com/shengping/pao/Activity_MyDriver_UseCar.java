package com.shengping.pao;

import java.util.ArrayList;

import com.baidu.mapapi.model.LatLng;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.view.XEditText;
import com.shengping.pao.view.XEditText.DrawableRightListener;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Activity_MyDriver_UseCar extends Activity implements OnClickListener,DrawableRightListener{
	private Button btn_confirm;
	private EditText location_start_select,location_start_input,tv_message;
	private XEditText tv_phone;
	private TextView tv_carname;
	private final int request_start=1;
	private LatLng start;
	private String type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mydriver_usecar);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		type=getIntent().getStringExtra("type");
		tv_title.setText(type);
		
		location_start_select=(EditText)findViewById(R.id.location_start_select);
		location_start_select.setOnClickListener(this);
		RelativeLayout lable_select=(RelativeLayout)findViewById(R.id.lable_select);
		lable_select.setOnClickListener(this);
		tv_carname=(TextView)findViewById(R.id.tv_carname);
		tv_phone=(XEditText)findViewById(R.id.tv_phone);
		tv_phone.setDrawableRightListener(this);
		location_start_input=(EditText)findViewById(R.id.location_start_input);
		tv_message=(EditText)findViewById(R.id.tv_message);
		
		btn_confirm=(Button)findViewById(R.id.btn_confirm);
		btn_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.location_start_select){
			Intent intent=new Intent(this,Activity_SelectLocation.class);
			intent.putExtra("current_location", location_start_select.getText().toString());
			intent.putExtra("type", "start");
			intent.putExtra("title", "我在...");
			startActivityForResult(intent, request_start);
		}else if(v.getId()==R.id.lable_select){
			Intent intent=new Intent(this,Activity_Select_Car.class);
			startActivityForResult(intent, 2);
		}else if(v.getId()==R.id.btn_confirm){
			if(start==null){
				MyUtil.alert("请选择你在哪",this);
			}else if(tv_carname.getText().toString().equals("")){
				MyUtil.alert("请选择您需要的车辆",this);
			}else if(tv_phone.getText().toString().equals("")){
				MyUtil.alert("请输入您的联系电话",this);
			}else{
				Intent intent=new Intent(this,Activity_MyDriver_UseCar_Detail.class);
				intent.putExtra("start", location_start_select.getText().toString()+" "+location_start_input.getText().toString());
				intent.putExtra("cars", tv_carname.getText().toString());
				intent.putExtra("phone", tv_phone.getText().toString());
				intent.putExtra("message", tv_message.getText().toString());
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
			   }
			}else if(requestCode==2){
				ArrayList<String> cars=data.getStringArrayListExtra("cars");
				String car_str=null;
				for(String s:cars){
					if(car_str==null)
					car_str=s;
					else
						car_str=car_str+","+s;
				}
				tv_carname.setText(car_str);
			}else if(requestCode==3){
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
        startActivityForResult(intent, 3);  
	}
}
