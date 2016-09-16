package com.halo.haloandroidframework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halo.haloandroidframework.TabViewIndicatorExample.example.ExampleMainActivity;

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
            default:
                break;
        }
    }
}
