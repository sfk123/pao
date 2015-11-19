package com.shengping.pao.PopupWindow;

import java.util.ArrayList;
import java.util.List;
import com.shengping.pao.R;
import com.shengping.pao.adapter.Adapter_SelectProduct;
import com.shengping.pao.util.MyUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class HelpBuy_Other_ProductType {
	 	private Context context;

	    private List<String> productTypes;//交通工具列表
	    private PopupWindow popupWindow;
	    private View topview;
	    private int windowWidth;//屏幕宽度
	    private SelectListener selectListtener;
	    private Adapter_SelectProduct adapter;
	    public void setSelectListener(SelectListener selectListtener){
	    	this.selectListtener=selectListtener;
	    }
	    public HelpBuy_Other_ProductType(Activity activity,List<String> productTypes,View v){
	    	this.context = activity;
	    	this.productTypes=productTypes;
	    	topview=v;
	    	adapter=new Adapter_SelectProduct(context, productTypes);
	    }
	    @SuppressWarnings("deprecation")
		@SuppressLint("InflateParams")
		public HelpBuy_Other_ProductType builder(int windowWidth){

	        // PopView
	        ListView view = (ListView)LayoutInflater.from(context).inflate(
	                R.layout.popup_select_product_type, null);
	        
	        loadData(view);
	// android.view.View#getWindowToken()
	        popupWindow = new PopupWindow(view, windowWidth/2, ViewGroup.LayoutParams.WRAP_CONTENT,false);
	        popupWindow.setFocusable(true);
	        popupWindow.setOutsideTouchable(true);
	        popupWindow.setBackgroundDrawable(new BitmapDrawable());
	        popupWindow.setOnDismissListener(new PopDismissListener());
	        popupWindow.showAsDropDown(topview, 0,MyUtil.dip2px(context, 10));
	        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
	        return this;
	    }
	    public  void loadData(ListView view){
	    	view.setAdapter(adapter); 
	    }
	    /**
	     * 显示
	     */
	    public void show(){
	        if (null != popupWindow){
//	        	popupWindow.setBackgroundDrawable();
	            setBackgroundAlpha(1f);
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
