package com.halo.haloandroidframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halo.haloandroidframework.Badge.BadgeActivity;
import com.halo.haloandroidframework.BaiduMapExample.BaiduSdkActivity;
import com.halo.haloandroidframework.TabViewIndicatorExample.example.ExampleMainActivity;
import com.halo.haloandroidframework.expandLayout.ExpandLayoutActivity;
import com.halo.haloandroidframework.materialDesign.MaterialDesignMainActivity;
import com.halo.haloandroidframework.retrofitSamples.activity.RetrofitActivity;
import com.halo.haloandroidframework.rxJava.RxJavaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_view_indicator:
                startActivity(new Intent(this, ExampleMainActivity.class));
                break;

            case R.id.tab_material_design:
                startActivity(new Intent(this, MaterialDesignMainActivity.class));
                break;
            case R.id.tab_expandlayout:
                startActivity(new Intent(this, ExpandLayoutActivity.class));
                break;
            case R.id.tab_baidu:
                startActivity(new Intent(this, BaiduSdkActivity.class));
                break;
            case R.id.tab_retrofit:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.tab_rxjava:
                startActivity(new Intent(this, RxJavaActivity.class));
                break;

            case R.id.tab_badge:
                startActivity(new Intent(this, BadgeActivity.class));
                break;
            default:
                break;
        }
    }
}
