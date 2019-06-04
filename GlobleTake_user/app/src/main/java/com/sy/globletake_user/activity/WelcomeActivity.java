package com.sy.globletake_user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends AppCompatActivity {

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Const.GO_GUIDE:
                    mIntent.setClass(WelcomeActivity.this,GuideActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
                case Const.GO_MAIN:
                    mIntent.setClass(WelcomeActivity.this,MainActivity.class);
                    startActivity(mIntent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private ImageView mImageView;
    public Intent mIntent=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        setContentView(R.layout.activity_welcome);
//        XuniKeyWord.assistActivity(findViewById(android.R.id.content));
        JPushInterface.setDebugMode(true);//如果时正式版就改成false

        String registrationID = JPushInterface.getRegistrationID(this);
        if (registrationID.isEmpty()){
            JPushInterface.init(this);
        }
        Log.e("tag","registrationID="+registrationID);
        mImageView= (ImageView) findViewById(R.id.welcome_pic);
        mImageView.startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.alpha));
        toNextActivity();
//        getHomeData();

    }
   /* private void registerXGtuison() {
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, true);
// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
// 具体可参考详细的开发指南
// 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

// 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);


// 其它常用的API：
// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
        XGPushManager.registerPush(context,"3181754424");
//        XGPushManager.registerPush(context,account, XGIOperateCallback);
// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
// 反注册（不再接收消息）：unregisterPush(context)
// 设置标签：setTag(context, tagName)
// 删除标签：deleteTag(context, tagName)
    }*/


    @Override
    protected void onDestroy() {
        if(null != mHandler) {
            mHandler.removeMessages(Const.GO_GUIDE);
            mHandler.removeMessages(Const.GO_MAIN);
            mHandler = null;
            Log.d("tag", "release Handler success");
        }
        super.onDestroy();
    }

    public void toNextActivity(){
        boolean is_first =  (Boolean) SharePreferenceUtils.getParam(getApplicationContext(), "is_first", false);
        if (!is_first) {
            //第一次启动进入引导页
            mHandler.sendEmptyMessageDelayed(Const.GO_GUIDE, Const.SPLASH_DELAY_TIME);

        }  else {
            //不是第一次启动直接进入首页
            mHandler.sendEmptyMessageDelayed(Const.GO_MAIN, Const.SPLASH_DELAY_TIME);
        }

    }
}
