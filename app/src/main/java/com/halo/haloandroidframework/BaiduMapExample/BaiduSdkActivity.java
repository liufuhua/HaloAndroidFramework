package com.halo.haloandroidframework.BaiduMapExample;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.halo.baidu.BaiduApi;
import com.halo.haloandroidframework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank1 on 2016/10/11.
 */

public class BaiduSdkActivity extends Activity implements BaiduApi.MyBDLocationListener,View.OnClickListener{//OnGetGeoCoderResultListener
    private MapView mMapView = null;
    private GeoCoder mSearch = null;
    private Marker carMarker;
    private BitmapDescriptor bdA;
    private MarkerOptions ooA;

    private LatLng latlng0 = new LatLng(39.977552, 116.320331); // 人民大学;左上
    private LatLng latlng1 = new LatLng(39.977552, 116.460036); // 香河园街道 右上
    private LatLng latlng2 = new LatLng(39.855805, 116.320331); // 左下
    private LatLng latlng3 = new LatLng(39.855805, 116.460036); // 右下；

    private List<LatLng> latlngsTop = new ArrayList<LatLng>();
    private List<LatLng> latlngsLeft = new ArrayList<LatLng>();
    private List<LatLng> latlngsRight = new ArrayList<LatLng>();
    private List<LatLng> latlngsBottom = new ArrayList<LatLng>();

//    private CarRunInfo carInfo;
    private Thread t;

    private BaiduApi baiduApi;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        BaiduApi.initSDK(this);

        setContentView(R.layout.baidusdk_example_activity);





        mMapView = (MapView) findViewById(R.id.bmapView);
        findViewById(R.id.btn_car_run).setOnClickListener(this);

        baiduApi = new BaiduApi();
        baiduApi.initMap(mMapView);
        baiduApi.setMakerRes(R.mipmap.qiche);
        baiduApi.initOverlay(latlng0);
        baiduApi.setMyLocationListener(this);
        getPointData();

    }



    /**
     * 模拟一个四边形。
     */
    private void getPointData() {
        latlngsTop = computEdgepoints(latlng0, latlng1, latlngsTop, Edge.Top);
        latlngsLeft = computEdgepoints(latlng0, latlng2, latlngsLeft, Edge.Left);
        latlngsRight = computEdgepoints(latlng1, latlng3, latlngsRight, Edge.Right);
        latlngsBottom = computEdgepoints(latlng2, latlng3, latlngsBottom, Edge.Bottom);
    }

    @Override
    public void myBdLocation(BDLocation location) {

    }

    enum Edge {
        Top, Left, Right, Bottom;
    }

    /**
     * 生成四个边的模拟数据；
     *
     * @param latlng1
     * @param latlng2
     * @param latlngs
     * @param edge
     * @return
     */
    public List<LatLng> computEdgepoints(LatLng latlng1, LatLng latlng2, List<LatLng> latlngs, Edge edge) {
        // 每边采集300个点，每秒采集10个点；
        double distance = 0;

        if (edge == Edge.Top) {
            distance = latlng2.longitude - latlng1.longitude;
            for (int i = 0; i < 300; i++) {
                latlngs.add(new LatLng(latlng1.latitude, latlng1.longitude + (i + 1) * (distance / 300)));
            }
        } else if (edge == Edge.Left) {
            distance = latlng1.latitude - latlng2.latitude;
            for (int i = 0; i < 300; i++) {
                latlngs.add(new LatLng(latlng2.latitude + (i + 1) * (distance / 300), latlng2.longitude));
            }
        } else if (edge == Edge.Right) {
            distance = latlng2.latitude - latlng1.latitude;
            for (int i = 0; i < 300; i++) {
                latlngs.add(new LatLng(latlng1.latitude + (i + 1) * (distance / 300), latlng2.longitude));
            }
        } else if (edge == Edge.Bottom) {
            distance = latlng1.longitude - latlng2.longitude;
            for (int i = 0; i < 300; i++) {
                latlngs.add(new LatLng(latlng1.latitude, latlng2.longitude + (i + 1) * (distance / 300)));
            }
        }

        return latlngs;

    }

    private boolean isRun = false;

    public void carRun() {
        if (!isRun) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    isRun = true;
                    SystemClock.sleep(2000);
                    runPoints(latlngsTop);
                    runPoints(latlngsRight);
                    runPoints(latlngsBottom);
                    runPoints(latlngsLeft);
                    isRun = false;
                }

            });
            t.start();
        }
    }

    private void runPoints(List<LatLng> latlngs) {
        try {
            for (int i = 0; i < latlngs.size()-1; i++) {
                Thread.sleep(50);
                baiduApi.changeCarPosition(latlngs.get(i), latlngs.get(i+1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        carRun();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
