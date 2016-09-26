package com.halo.haloandroidframework.loggerExample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import common.android.tools.logger.Logger;

/**
 * Created by orson on 16/9/22.
 * log输出类测试
 */

public class LoggerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //首先开启Logger功能
        Logger.DEBUG = true;

        //可以输出的字符串的类型(通常用法)
        Logger.d("输出字符串");
        Logger.e("输出字符串");
        Logger.i("输出字符串");
        Logger.w("输出字符串");
        Logger.json("json 格式字符串");
    }
}
