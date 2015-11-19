package com.shengping.pao;

import java.util.ArrayList;
import java.util.List;

import com.shengping.pao.adapter.Adapter_HomeGrid;
import com.shengping.pao.util.MyUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
/**
 * ������  ���Ұ� ҳ��
 * @author Administrator
 *
 */
public class Activity_Help_Buy extends Activity implements OnClickListener,OnItemClickListener{
	
	private GridView MyGrid;
	private Adapter_HomeGrid adapter;
	private ImageView img_dingzhi;
	public static int type_help_buy=1;
	public static int type_help_handle=2;
	private List<String> helpbuy_other;
	private int type;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_buy);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("������");
		
		MyGrid=(GridView)findViewById(R.id.MyGrid);
		MyGrid.setSelector(new BitmapDrawable());
		type=getIntent().getIntExtra("type", type_help_buy);
		adapter=new Adapter_HomeGrid(this,type);
		MyGrid.setAdapter(adapter);
		MyGrid.setOnItemClickListener(this);
		
		img_dingzhi=(ImageView)findViewById(R.id.img_dingzhi);
		img_dingzhi.setOnClickListener(this);
		if(type==type_help_handle){
			Drawable d=getResources().getDrawable(R.drawable.img_help_2);
			BitmapDrawable bd = (BitmapDrawable) d;
			img_dingzhi.setImageBitmap(MyUtil.resizeBitmap(bd.getBitmap(), MyUtil.getWindowSize(this)[0]));
		}else{
			Drawable d=getResources().getDrawable(R.drawable.help_buy_bottom);
			BitmapDrawable bd = (BitmapDrawable) d;
			img_dingzhi.setImageBitmap(MyUtil.resizeBitmap(bd.getBitmap(), MyUtil.getWindowSize(this)[0]));
		}
		helpbuy_other=new ArrayList<String>();
		helpbuy_other.add("�ҵ�ˮ��");
		helpbuy_other.add("�ҵĳ���");
		helpbuy_other.add("�ҵĳ���");
		helpbuy_other.add("�ҵľƹ�");
		helpbuy_other.add("�ҵ��ʻ�");
		helpbuy_other.add("�ҵİ���");
		helpbuy_other.add("�ҵ�ҩ��");
	}
	private void setHeight(){//����gridview����Ӧ�߶�
		 ListAdapter listAdapter = MyGrid.getAdapter();
		 if (listAdapter == null) {
			 return;
		 }
		 int height=0;
		 for (int i = 0; i < listAdapter.getCount()/2; i ++) {
			 View listItem = listAdapter.getView(i, null, MyGrid);
			 listItem.measure(0, 0);
			 height=height+listItem.getMeasuredHeight();
		 }
		 MyGrid.getLayoutParams().height=height+MyUtil.dip2px(this, 50);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.img_dingzhi){
			if(type==type_help_handle){
				Intent intent=new Intent(this,Activity_Handler_Dingzhi.class);
				startActivity(intent);
			}else{
			Intent intent=new Intent(this,Activity_Buy_Dingzhi.class);
			startActivity(intent);
			}
		}
		
	}
	private boolean isloaded=false;
	 @Override  
	 public void onWindowFocusChanged(boolean hasFocus)  {  
	        if (hasFocus&&!isloaded)  
	        {  
	        	setHeight();
	        }  
	 }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		String text=adapter.getItem(position);
		if(text.equals("�ҵ���ʳ")){
			Intent intent=new Intent(this,Activity_Food.class);
			startActivity(intent);
		}else if(helpbuy_other.contains(text)){
			Intent intent=new Intent(this,Activity_HelpBuy_Other.class);
			intent.putExtra("title", text);
			startActivity(intent);
		}else if(text.equals("�ҵ�˾��")){
			Intent intent=new Intent(this,Activity_My_Driver.class);
			startActivity(intent);
		}else if(text.equals("�ҵ�ϴ��")){
			Intent intent=new Intent(this,Activity_Wash.class);
			startActivity(intent);
		}else if(text.equals("�ҵļ���")){
			Intent intent=new Intent(this,Activity_Homemaking.class);
			startActivity(intent);
		}else if(text.equals("�ҵİ�֤")){
			Intent intent=new Intent(this,Activity_Certificates.class);
			startActivity(intent);
		}else if(text.equals("��Ҫ�Һ�")){
			Intent intent=new Intent(this,Activity_Registration.class);
			startActivity(intent);
		}else if(text.equals("��Ҫ���")){
			Intent intent=new Intent(this,Activity_MoveHouse.class);
			startActivity(intent);
		}
	}  
}
