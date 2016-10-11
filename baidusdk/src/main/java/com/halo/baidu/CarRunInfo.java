package com.halo.baidu;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Frank1 on 2016/10/11.
 */

public class CarRunInfo {
    private LatLng carPosition = new LatLng(39.977552, 116.320331);
    private double angle = 270;

    public LatLng getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(LatLng carPosition) {
        this.carPosition = carPosition;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
