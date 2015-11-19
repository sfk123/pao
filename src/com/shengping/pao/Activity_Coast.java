package com.shengping.pao;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ���ȷѣ�·�̹滮��
 * @author Administrator
 *
 */
public class Activity_Coast extends Activity implements OnClickListener{
	private LatLng start,end;
	private MapView bmapView;
	private BaiduMap mBaiduMap;
	private RoutePlanSearch mSearch;
	private boolean useDefaultIcon=true;//�Զ���ͼ��
	private TextView destence;//·��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coast);
		ImageView btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		Button btn_detail=(Button)findViewById(R.id.btn_detail);
		btn_detail.setOnClickListener(this);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("���ȷ�");
		start=new LatLng(getIntent().getDoubleExtra("lat_start", 0), getIntent().getDoubleExtra("long_start", 0));
		end=new LatLng(getIntent().getDoubleExtra("lat_end", 0), getIntent().getDoubleExtra("long_end", 0));
		bmapView=(MapView)findViewById(R.id.bmapView);
		mBaiduMap = bmapView.getMap();
		
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(10.0f));
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(start);
        mBaiduMap.animateMapStatus(u);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        PlanNode stNode = PlanNode.withLocation(start);
        PlanNode enNode = PlanNode.withLocation(end);
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
        destence=(TextView)findViewById(R.id.destence);
	}
	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {  
	    public void onGetWalkingRouteResult(WalkingRouteResult result) {  
	        //    
	    }  
	    public void onGetTransitRouteResult(TransitRouteResult result) {  
	        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
	            Toast.makeText(Activity_Coast.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();  
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
	             Toast.makeText(Activity_Coast.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
	         }
	         if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	             //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ
	             //result.getSuggestAddrInfo()
	             return;
	         }
	         if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            
	             DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
//	             routeOverlay = overlay;
	             mBaiduMap.setOnMarkerClickListener(overlay);
	             overlay.setData(result.getRouteLines().get(0));
	             overlay.addToMap();
	             overlay.zoomToSpan();
	             mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
	             destence.setText("·��"+(double)result.getRouteLines().get(0).getDistance()/1000+"����");//·�̾���
	         }
	    }  
	};
	//����RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.ico_start_large);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.ico_end_large);
            }
            return null;
        }
    }
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        bmapView.onDestroy();  
    }  
    @Override  
    protected void onResume() {  
        super.onResume();  
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        bmapView.onResume();  
        }  
    @Override  
    protected void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        bmapView.onPause();  
    }
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_back){
			finish();
		}else if(v.getId()==R.id.btn_detail){
			Intent intent=new Intent(this,Activity_Coast_detail.class);
			startActivity(intent);
		}
	}
}
