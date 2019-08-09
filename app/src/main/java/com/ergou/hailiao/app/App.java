package com.ergou.hailiao.app;

import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.ergou.hailiao.di.component.AppComponent;
import com.ergou.hailiao.di.component.DaggerAppComponent;
import com.ergou.hailiao.di.module.AppMoudle;
import com.ergou.hailiao.mvp.ui.activity.MainActivity;
import com.ergou.hailiao.utils.CrashUtils;
import com.ergou.hailiao.utils.LogUtils;
import com.ergou.hailiao.utils.Utils;


/**
 * Created by LuoCy on 2017/11/06.
 */

public class App extends Application {
    private static App instance;
    private static SLHandler handler;
    public static MainActivity activity;


    public static StringBuilder payloadData = new StringBuilder();

    public static synchronized App getInstance() {
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {

        super.onCreate();

        if (handler == null) {
            handler = new SLHandler();
        }
        instance = this;
        //Realm初始化

        //初始化错误收集
        CrashUtils.getInstance().init(getApplicationContext());

        // android 7.0系统解决拍照的问题
        LogUtils.e("系版本=" + Build.VERSION.RELEASE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        Utils.init(instance);

    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appMoudle(new AppMoudle(instance))
                .build();
    }

    public static class SLHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (activity != null) {
                        payloadData.append((String) msg.obj);
                        payloadData.append("\n");
//                        if (MainActivity.tLogView != null) {
//                            MainActivity.tLogView.append(msg.obj + "\n");
//                        }
                        LogUtils.e("心好累");
                    }
                    break;

                case 1:
                    if (activity != null) {
                        LogUtils.e("心好累AA");
//                        if (MainActivity.tLogView != null) {
//                            MainActivity.tView.setText((String) msg.obj);
//                        }
                    }
                    break;
            }
        }
    }


}
