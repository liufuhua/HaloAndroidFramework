package com.halo.haloandroidframework.eventbusExample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by orson on 16/9/22.
 * 测试eventbus功能
 */

public class EventBusActivity extends AppCompatActivity{
    EventBus eventBus;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //首先获取EventBus对象
        eventBus = EventBus.getDefault();
        //利用EventBus对象注册接收事件的对象
        eventBus.register(this);
        //发布事件(发送)
        eventBus.post(new ReceiveEvent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //这里需要取消事件的订阅
        eventBus.unregister(this);
    }

    /**
     * 这里是订阅 ReceiveEvent 事件
     * @param event
     */
    @Subscribe
    public void onReceiveEvent(ReceiveEvent event){
        //TODO 这里处理该事件
    }
}
