package com.halo.baidu;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by Frank1 on 2016/10/11.
 */

public class BaiduApi {

    private MapView mapView;
    private BaiduMap baiduMap = null;
    private Marker carMarker;
    private BitmapDescriptor bdA;
    private MarkerOptions ooA;
    private CarRunInfo carInfo;
    private MyBDLocationListener myBDLocationListener;

    public interface MyBDLocationListener{
        public void myBdLocation(BDLocation location);
    }
    /**
     * 初始化百度SDK
     * @param context
     */
    public static void initSDK(Context context){
        SDKInitializer.initialize(context.getApplicationContext());
    }

    /**
     * 初始化地图
     */
    public void initMap(MapView mapView) {
        this.mapView = mapView;
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setTrafficEnabled(true);

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
        baiduMap.setMapStatus(msu);


        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));

        initCarInfo();

    }

    /**
     * 初始化坐标图
     * @param resId
     */
    public void setMakerRes(int resId){
        bdA = BitmapDescriptorFactory.fromResource(resId);
    }

    /**
     * 我的位置的回调
     * @param myBDLocationListener
     */
    public void setMyLocationListener(MyBDLocationListener myBDLocationListener){
        this.myBDLocationListener = myBDLocationListener;
    }

    /**
     * 初始化坐标
     */
    public void initOverlay(LatLng latLng) {
        // add marker overlay
        LatLng llA = new LatLng(latLng.latitude, latLng.longitude);

        ooA = new MarkerOptions().position(llA).zIndex(9).draggable(false);
        if (bdA != null)
            ooA.icon(bdA);
        carMarker = (Marker) (baiduMap.addOverlay(ooA));
    }

    /**
     * 改变车头方向
     * @param latlng1 上一次位置
     * @param latLng2 当前位置
     */
    public void changeCarPosition(LatLng latlng1, LatLng latLng2) {
        changeCarOrientation(latlng1, latLng2, carMarker, 2);
        carMarker.setPosition(latlng1);
    }


    private void initCarInfo() {
        carInfo = new CarRunInfo();
    }



    /**
     * 获取下一个车的方向；
     * @param latlng1
     * @param latlng2
     * @return
     */
    private double getNextOrientation(LatLng latlng1, LatLng latlng2) {
        double angle;
        // 求出三边边长；
        double a, b, c, a1, b1;

        a1 = latlng2.latitude - latlng1.latitude;
        b1 = latlng2.longitude - latlng1.longitude;

        double latitudeDif = Math.abs(a1);
        double longitudeDif = Math.abs(b1);
        a = latitudeDif;// 纬度；
        b = longitudeDif;
        c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        if (a == 0) {
            // 车头朝向 0或 180；
            // 纬度共有90度。赤道为0度，向两极排列，圈子越小，度数越大。
            if ((latlng2.longitude - latlng1.longitude) < 0) {// 经度；
                angle = 0;
            } else {
                angle = 180;
            }
        } else if (b == 0) {
            if ((latlng2.latitude - latlng1.latitude) < 0) {// 纬度；
                angle = 90;
            } else {
                angle = 270;
            }
        } else {
            System.out.println("a1:" + a1 + ",b1:" + b1);
            System.out.println((a1 > 0) + "," + (b1 > 0));

            angle = Math.toDegrees(Math.acos(b / c));
            if (a1 < 0 && b1 > 0) {
                angle = 360 - angle;
            } else if (a1 > 0 && b1 > 0) {

            } else if (a1 > 0 && b1 < 0) {
                angle = 180 - angle;
            } else if (a1 < 0 && b1 < 0) {
                angle = 180 + angle;

            }

        }
        return angle;
    }

    /**
     * 转向
     *
     * @param latlng1
     * @param latlng2
     * @param carMarker
     * @param errorRange
     *            误差范围， 大于 errorRange 值 可视为 转向有效值；
     */
    private void changeCarOrientation(LatLng latlng1, LatLng latlng2, Marker carMarker, double errorRange) {

        if (latlng1.latitude == latlng2.latitude && latlng1.longitude == latlng2.longitude) {

        } else {

            // 获取下一个车头朝向；
            double nextOrientation = getNextOrientation(latlng1, latlng2);
            // 获取当前车头朝向；
            double currentAngle = carInfo.getAngle();
            // 逆时针 角度为负值；
            if (Math.abs(nextOrientation - currentAngle) > errorRange) {
                carInfo.setAngle(nextOrientation);
                System.out.println("nextOrientation:" + nextOrientation + ",currentAngle:" + currentAngle);
                float computAngle = (float) computRotateAngle(currentAngle, nextOrientation);
                carMarker.setRotate(carMarker.getRotate() + computAngle);// 逆时针；
                System.out.println("computAngle:" + computAngle);
            }
        }
    }

    /**
     *负值--顺时针旋转；
     */

    private double computRotateAngle(double currentAngle, double nextAngle) {

        return nextAngle - currentAngle;

    }


}
