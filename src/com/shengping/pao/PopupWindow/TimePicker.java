package com.shengping.pao.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shengping.pao.R;
import com.shengping.pao.util.MyUtil;
import com.shengping.pao.view.WheelView;

public class TimePicker {
	 private Context context;
	    /**
	     * 取消按钮
	     */
	    private Button btnCancel;

	    /**
	     * 确定按钮
	     */
	    private Button btnConfirm;

	    private WheelView hour_start_am,hour_end_am,minute_start_am,minute_end_am;
	    private WheelView hour_start_pm,hour_end_pm,minute_start_pm,minute_end_pm;
	    private List<String> hours;//时间列表
	    private List<String> minutes;//时间列表
	    private PopupWindow popupWindow;
	    private SelectListener selectListtener;
	    private int[] windowSize;
	    public void setSelectListener(SelectListener selectListtener){
	    	this.selectListtener=selectListtener;
	    }
	    public TimePicker(Activity activity){
	    	this.context = activity;
	    	windowSize=MyUtil.getWindowSize(activity);
	    }
	    @SuppressWarnings("deprecation")
		@SuppressLint({ "InflateParams", "NewApi" })
		public TimePicker builder(){

	        // PopView
	        View view = LayoutInflater.from(context).inflate(
	                R.layout.popup_time_picker, null);

	        Init(view);
	// android.view.View#getWindowToken()
	        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,false);
	        popupWindow.setFocusable(true);
	        popupWindow.setOutsideTouchable(true);
	        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popup_bg));
	        popupWindow.setOnDismissListener(new PopDismissListener());
	        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
	        popupWindow.setAnimationStyle(R.style.PopAnimationDownUp);
	        TextView tv1=(TextView)view.findViewById(R.id.tv_lable);
	        TextView tv2=(TextView)view.findViewById(R.id.tv_text);
	        setWith((windowSize[0]-tv1.getLayoutParams().width*2-tv2.getLayoutParams().width)/4,hour_start_am,hour_end_am,minute_start_am,minute_end_am
	        		,hour_start_pm,hour_end_pm,minute_start_pm,minute_end_pm);
	        return this;
	    }
	    private void setWith(int width,View...views ){
	    	for(View v:views){
	    		v.getLayoutParams().width=width;
	    	}
	    }
	    public  void Init(View view){
	    	btnCancel = (Button) view.findViewById(R.id.btn_cancel);
	        btnCancel.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                dismiss();
	            }
	        });
	        
	        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
	        btnConfirm.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	if(selectListtener!=null)
	            	selectListtener.select_ok(hour_start_am.getSeletedItem()+":"+minute_start_am.getSeletedItem()+" - "+hour_end_am.getSeletedItem()+":"+minute_end_am.getSeletedItem()
	            			+" | "+hour_start_pm.getSeletedItem()+":"+minute_start_pm.getSeletedItem()+" - "+hour_end_pm.getSeletedItem()+":"+minute_end_pm.getSeletedItem());
	            	dismiss();
	            }
	        });
	        hour_start_am=(WheelView)view.findViewById(R.id.hour_start_am);
	        hour_end_am=(WheelView)view.findViewById(R.id.hour_end_am);
	        minute_start_am=(WheelView)view.findViewById(R.id.minute_start_am);
	        minute_end_am=(WheelView)view.findViewById(R.id.minute_end_am);
	        
	        hour_start_pm=(WheelView)view.findViewById(R.id.hour_start_pm);
	        hour_end_pm=(WheelView)view.findViewById(R.id.hour_end_pm);
	        minute_start_pm=(WheelView)view.findViewById(R.id.minute_start_pm);
	        minute_end_pm=(WheelView)view.findViewById(R.id.minute_end_pm);
	        hours=new ArrayList<>();
	        hours.add("00");
	        hours.add("01");
	        hours.add("02");
	        hours.add("03");
	        hours.add("04");
	        hours.add("05");
	        hours.add("06");
	        hours.add("07");
	        hours.add("08");
	        hours.add("09");
	        hours.add("10");
	        hours.add("11");
	        hours.add("12");
	        hours.add("13");
	        hours.add("14");
	        hours.add("15");
	        hours.add("16");
	        hours.add("17");
	        hours.add("18");
	        hours.add("19");
	        hours.add("20");
	        hours.add("21");
	        hours.add("22");
	        hours.add("23");
	        hours.add("24");
	        minutes=new ArrayList<>();
	        for(int i=0;i<60;i++){
	        	if(i<10){
	        		minutes.add("0"+i);
	        	}else{
	        		minutes.add(""+i);
	        	}
	        }
	        hour_start_am.setItems(hours);
	        minute_start_am.setItems(minutes);
	        hour_end_am.setItems(hours);
	        minute_end_am.setItems(minutes);
	        
	        hour_start_pm.setItems(hours);
	        minute_start_pm.setItems(minutes);
	        hour_end_pm.setItems(hours);
	        minute_end_pm.setItems(minutes);
	    }
	    /**
	     * 显示
	     */
	    public void show(){
	        if (null != popupWindow){
	            setBackgroundAlpha(0.5f);
	            popupWindow.update();
	        }
	    }

	    /**
	     * 撤销
	     */
	    public void dismiss(){
	        if (null != popupWindow){
	            popupWindow.dismiss();
	        }
	    }

	    /**
	     * 设置背景透明度
	     * @param alpha 背景的Alpha
	     */
	    private void setBackgroundAlpha(float alpha){
	        WindowManager.LayoutParams lp =  ((Activity)context).getWindow().getAttributes();
	        lp.alpha = alpha;
	        ((Activity)context).getWindow().setAttributes(lp);
	    }
	    private class PopDismissListener implements PopupWindow.OnDismissListener{

	        @Override
	        public void onDismiss() {
	            //更改背景透明度
	            setBackgroundAlpha(1.0f);
	        }
	    }
	    public interface SelectListener{
	    	public void select_ok(String s);
	    }
}
