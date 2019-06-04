package com.sy.globletake_user.Other;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.sy.globletake_user.utils.SeachHistoryData;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

/**
 * Created by sunnetdesign3 on 2017/2/14.
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {

        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        initializeDB();
        instance = this;
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);
        SMSSDK.initSDK(this, "1bed98ae63813", "29410c5705c72f21ff092f3c03c997c4");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    protected void initializeDB() {
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(SeachHistoryData.class);
        ActiveAndroid.initialize(configurationBuilder.create());
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
