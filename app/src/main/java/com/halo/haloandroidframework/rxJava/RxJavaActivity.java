package com.halo.haloandroidframework.rxJava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Frank1 on 2016/10/22.
 */

public class RxJavaActivity extends AppCompatActivity{
    private static  String TAG = "RxJavaActivity";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG,"Completed");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG,s);
            }
        };
        //使用Observable.create()创建被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onCompleted();
            }
        });

        observable.subscribe(observer);

    }

    private void doAction(){
        Observable.just("hello","world").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        });
    }
}
