package com.shengping.pao.adapter;

import com.shengping.pao.Activity_Help_Buy;
import com.shengping.pao.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_HomeGrid extends BaseAdapter {
	 private Context context; 
	 private Integer[] images = null;  
	 private String[] texts =null;  
	 private LayoutInflater inflater;
	 public Adapter_HomeGrid(Context context,int type) {  
	  this.context=context;  
	  inflater = LayoutInflater.from(context);  
	  if(type==Activity_Help_Buy.type_help_buy){
		  images =new Integer [8];  
		  images[0]=R.drawable.icon_home_1;
		  images[1]=R.drawable.icon_home_2;
		  images[2]=R.drawable.icon_home_3;
		  images[3]=R.drawable.icon_home_4;
		  images[4]=R.drawable.icon_home_5;
		  images[5]=R.drawable.icon_home_6;
		  images[6]=R.drawable.icon_home_7;
		  images[7]=R.drawable.icon_home_8;
		  texts=new String[8];
		  texts[0]="�ҵ���ʳ";
		  texts[1]="�ҵ�ˮ��";
		  texts[2]="�ҵĳ���";
		  texts[3]="�ҵĳ���";
		  texts[4]="�ҵľƹ�";
		  texts[5]="�ҵ��ʻ�";
		  texts[6]="�ҵİ���";
		  texts[7]="�ҵ�ҩ��";
	  }else{
		  images =new Integer [6];  
		  images[0]=R.drawable.ico_ban_1;
		  images[1]=R.drawable.ico_ban_2;
		  images[2]=R.drawable.ico_ban_3;
		  images[3]=R.drawable.ico_ban_4;
		  images[4]=R.drawable.ico_ban_5;
		  images[5]=R.drawable.ico_ban_6;
		  texts=new String[6];
		  texts[0]="�ҵ�˾��";
		  texts[1]="�ҵ�ϴ��";
		  texts[2]="�ҵļ���";
		  texts[3]="�ҵİ�֤";
		  texts[4]="��Ҫ���";
		  texts[5]="��Ҫ�Һ�";
	  }
	 }  
	 @Override  
	 public int getCount() {  
	  return images.length;  
	 }  
	 
	 @Override  
	 public String getItem(int position) {  
	  return texts[position];  
	 }  
	 
	 //get the current selector's id number  
	 @Override  
	 public long getItemId(int position) {  
	  return position;  
	 }  
	 @SuppressLint("InflateParams")
	@Override  
	 public View getView(int position, View view, ViewGroup viewgroup) {  
		 holder holder;  
	  if(view==null) {  
		   holder = new holder();  
		   view = inflater.inflate(R.layout.item_home_grid, null);  
		   holder.imageView=(ImageView)view.findViewById(R.id.image);
		   holder.textView=(TextView)view.findViewById(R.id.text);
		   view.setTag(holder);  
		   view.setPadding(15, 15, 15, 15);  //ÿ��ļ��  
	  } else {  
		  holder = (holder)view.getTag();  
	  }  
	  if(images==null){
		  System.out.println("images is null");
	  }
	  if(holder.imageView==null){
		  System.out.println("imageView is null");
	  }
	  if(holder==null){
		  System.out.println("holder is null");
	  }
	  try{
		  holder.imageView.setImageResource(images[position]);  
		  holder.textView.setText(texts[position]);  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  return view;  
	 }  
	 
	class holder{
		ImageView imageView;  
		 TextView textView; 
	}
}
