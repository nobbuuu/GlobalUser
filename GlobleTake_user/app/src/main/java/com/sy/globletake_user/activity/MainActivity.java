package com.sy.globletake_user.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.JifeiBean;
import com.sy.globletake_user.Bean.OrderReceived;
import com.sy.globletake_user.Bean.PriceRuleBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.fragment.CoachFragment;
import com.sy.globletake_user.fragment.MinibusFragment;
import com.sy.globletake_user.fragment.MotorcycleFragment;
import com.sy.globletake_user.fragment.TaxiFragment;
import com.sy.globletake_user.utils.AppManager;
import com.sy.globletake_user.utils.CallPhoneutil;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.LogUtils;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.ToastUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    /**
     * 添加的覆盖物标志
     */
    @Bind(R.id.main_drawerlayout)
    DrawerLayout main_drawerlay;
    @Bind(R.id.main_menu_layout)
    LinearLayout main_menu_layout;
    @Bind(R.id.cars_viewlay)
    LinearLayout cars_viewlay;

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.main_titletv)
    TextView main_titletv;
    @Bind(R.id.more_tv)
    TextView more_tv;
    @Bind(R.id.user_name)
    TextView user_name;

    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.view2)
    View view2;
    @Bind(R.id.view3)
    View view3;
    @Bind(R.id.view4)
    View view4;


    @Bind(R.id.main_head_icon)
    ImageView main_head_icon;
    @Bind(R.id.main_backiv)
    RelativeLayout main_backiv;
    @Bind(R.id.head_icon)
    ImageView head_icon;
    @Bind(R.id.root_relay)
    LinearLayout root_relay;
    @Bind(R.id.cars_lay)
    LinearLayout cars_lay;
    @Bind(R.id.head_lay)
    LinearLayout head_lay;
    @Bind(R.id.my_address)
    LinearLayout my_address;
    @Bind(R.id.my_trip)
    LinearLayout my_trip;

    private FragmentManager mFragmentManager;
    private TaxiFragment taxiFragment;
    private MotorcycleFragment motorcycleFragment;
    private CoachFragment coachFragment;
    private MinibusFragment minibusFragment;

    private Fragment currentFragment;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);
        XuniKeyWord.setShiPei(this, root_relay);
        context = this;
        String sha1 = getSHA1(this);
        //初始化行程状态
        SharePreferenceUtils.setParam(context,Const.TripStatus,"-1");
        SharePreferenceUtils.setParam(context,Const.CarType,Const.CarType_taxi);//存叫车类型
        Log.e("tag","sha1="+sha1);
        mFragmentManager = getFragmentManager();
        if (savedInstanceState!=null){
            taxiFragment = (TaxiFragment) mFragmentManager.findFragmentByTag("taxi");
            motorcycleFragment = (MotorcycleFragment) mFragmentManager.findFragmentByTag("moto");
            coachFragment = (CoachFragment) mFragmentManager.findFragmentByTag("coach");
            minibusFragment = (MinibusFragment) mFragmentManager.findFragmentByTag("minbus");
        }else {
            initFragment();
        }
        addFragment();
        Loginset();
        setEvent();
        getPriceRuleMoto(Const.motoPriceRule);
    }

    private void Loginset(){
        String userIcon_url = (String) SharePreferenceUtils.getParam(context, Const.User_HeadIcon, "");
        String userName = (String) SharePreferenceUtils.getParam(context, Const.User_NickName, "");

        ImageOptions options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .setUseMemCache(true)
                .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                .build();
        if (!userIcon_url.isEmpty()){
            x.image().bind(head_icon,Const.root_url+userIcon_url,options);
        }else {
            head_icon.setImageResource(R.mipmap.tanchuang_touxiang);
        }
        boolean isLogin = (boolean) SharePreferenceUtils.getParam(context,Const.isLogin,false);
        if (isLogin){
            user_name.setText(userName);
        }else {
            user_name.setText(LanguageUtil.getResText(R.string.tv_notlogin));
        }
    }
    private void addFragment(){
        mFragmentManager.beginTransaction()
                .add(R.id.main_fragment_layout,taxiFragment,"taxi")
                .show(taxiFragment)
                .add(R.id.main_fragment_layout,motorcycleFragment,"moto")
                .hide(motorcycleFragment)
                .add(R.id.main_fragment_layout,minibusFragment,"minbus")
                .hide(minibusFragment)
                .add(R.id.main_fragment_layout,coachFragment,"coach")
                .hide(coachFragment)
                .commit();
        currentFragment = taxiFragment;
    }

    private void initFragment(){
        taxiFragment = new TaxiFragment();
        motorcycleFragment = new MotorcycleFragment();
        coachFragment = new CoachFragment();
        minibusFragment = new MinibusFragment();

    }

    private void startFragment(String tag){
        switch (tag){
            case "taxi" :
                if (currentFragment!=taxiFragment){
                    mFragmentManager.beginTransaction()
                            .show(taxiFragment)
                            .hide(currentFragment)
                            .commit();
                    currentFragment = taxiFragment;
                }
                break;
            case "moto" :
                if (currentFragment!=motorcycleFragment){
                    mFragmentManager.beginTransaction()
                            .show(motorcycleFragment)
                            .hide(currentFragment)
                            .commit();
                    currentFragment = motorcycleFragment;
                }
                break;
            case "minbus" :
                if (currentFragment!=minibusFragment){
                    mFragmentManager.beginTransaction()
                            .show(minibusFragment)
                            .hide(currentFragment)
                            .commit();
                    currentFragment = minibusFragment;
                }
                break;
            case "coach" :
                if (currentFragment!=coachFragment){
                    mFragmentManager.beginTransaction()
                            .show(coachFragment)
                            .hide(currentFragment)
                            .commit();
                    currentFragment = coachFragment;
                }
                break;

        }
    }
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void chushihuaView() {
        tv1.setTextColor(getResources().getColor(R.color.color333));
        tv2.setTextColor(getResources().getColor(R.color.color333));
        tv3.setTextColor(getResources().getColor(R.color.color333));
        tv4.setTextColor(getResources().getColor(R.color.color333));

        view1.setBackgroundResource(R.color.white);
        view2.setBackgroundResource(R.color.white);
        view3.setBackgroundResource(R.color.white);
        view4.setBackgroundResource(R.color.white);
    }


    public void setEvent() {
        //侧滑菜单的监听事件
        main_drawerlay.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    //输入终点后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent1(String eventBusStr) {
        if (eventBusStr.equals("sure_callCars")){
            main_titletv.setText(LanguageUtil.getResText(R.string.sure_callCars));
            main_backiv.setVisibility(View.VISIBLE);
            main_head_icon.setVisibility(View.GONE);
            cars_lay.setVisibility(View.GONE);
            cars_viewlay.setVisibility(View.GONE);
        }
    }
    //确认叫车后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent2(String eventBusStr) {
        if (eventBusStr.equals("等待应答")){
            main_titletv.setText(LanguageUtil.getResText(R.string.tv_waitanswer));
            main_backiv.setVisibility(View.VISIBLE);
            main_head_icon.setVisibility(View.GONE);
            cars_lay.setVisibility(View.GONE);
            cars_viewlay.setVisibility(View.GONE);
            more_tv.setVisibility(View.VISIBLE);
        }
    }
    //已接单或已取消行程后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent3(String eventBusStr) {
        if (eventBusStr.equals("已接单或已取消行程")){
            main_titletv.setText(LanguageUtil.getResText(R.string.sure_callCars));
            main_backiv.setVisibility(View.VISIBLE);
            main_head_icon.setVisibility(View.GONE);
            cars_lay.setVisibility(View.GONE);
            cars_viewlay.setVisibility(View.GONE);
            more_tv.setVisibility(View.GONE);
        }
    }
    //订单被司机接单后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent1(OrderReceived eventStr) {
        if (eventStr.getEventStr().equals("订单被接")) {
            main_titletv.setText(LanguageUtil.getResText(R.string.tv_waitjiejia));
        }
    }
    //开始计费后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent5(JifeiBean eventStr) {
        if (eventStr.getEventStr().equals("开始计费")) {
            more_tv.setVisibility(View.GONE);
            main_titletv.setText(LanguageUtil.getResText(R.string.tv_serving));
            main_backiv.setVisibility(View.GONE);
            main_head_icon.setVisibility(View.GONE);
            SharePreferenceUtils.setParam(context,Const.TripStatus,"-6");//使得用户点击返回键无反应
        }
    }

    //重新下单
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent4(String eventBusStr) {
        if (eventBusStr.equals("again")){
            main_titletv.setText("Global Take");
            main_backiv.setVisibility(View.GONE);
            main_head_icon.setVisibility(View.VISIBLE);
            cars_lay.setVisibility(View.VISIBLE);
            cars_viewlay.setVisibility(View.VISIBLE);
            more_tv.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4,R.id.my_address,R.id.main_head_icon,R.id.main_backiv,R.id.more_tv,R.id.head_lay,R.id.my_trip,R.id.help_layout,R.id.tousu_layout,R.id.about_layout})
    public void onClick(View view) {
        Intent intent_web = new Intent(MainActivity.this,WebAvtivity.class);
        switch (view.getId()) {
            case R.id.tv1:
                chushihuaView();
                tv1.setTextColor(getResources().getColor(R.color.statecolor));
                view1.setBackgroundResource(R.color.statecolor);
                startFragment("taxi");
                SharePreferenceUtils.setParam(context,Const.CarType,Const.CarType_taxi);//存叫车类型
                EventBus.getDefault().post(Const.Driving);
                break;
            case R.id.tv2:
                chushihuaView();
                tv2.setTextColor(getResources().getColor(R.color.statecolor));
                view2.setBackgroundResource(R.color.statecolor);
                startFragment("taxi");
                SharePreferenceUtils.setParam(context,Const.CarType,Const.CarType_Moto);//存叫车类型
                EventBus.getDefault().post(Const.Moto);
                break;
            case R.id.tv3:
                chushihuaView();
                tv3.setTextColor(getResources().getColor(R.color.statecolor));
                view3.setBackgroundResource(R.color.statecolor);
                startFragment("minbus");
                break;
            case R.id.tv4:
                chushihuaView();
                tv4.setTextColor(getResources().getColor(R.color.statecolor));
                view4.setBackgroundResource(R.color.statecolor);
                startFragment("coach");
                break;

            case R.id.main_head_icon:
                main_drawerlay.openDrawer(main_menu_layout);
                break;
            case R.id.main_backiv:
                String tripStatus = (String) SharePreferenceUtils.getParam(context, Const.TripStatus, "-1");
                LogUtils.Loge("status","tripStatus="+tripStatus);
                switch (tripStatus){
                    case "0"://行程已取消
                        main_titletv.setText("Global Take");
                        main_backiv.setVisibility(View.GONE);
                        main_head_icon.setVisibility(View.VISIBLE);
                        cars_lay.setVisibility(View.VISIBLE);
                        cars_viewlay.setVisibility(View.VISIBLE);
                        more_tv.setVisibility(View.GONE);
                        EventBus.getDefault().post("sure_callCars_back");
                        break;
                    case "1"://等待接单
                        EventBus.getDefault().post("sure_callCars_back");
                        break;
                    case "2"://已接单
                        EventBus.getDefault().post("sure_callCars_back");
                        break;
                    case "3"://等待上车

                        break;
                    case "4"://已上车

                        break;
                    case "5"://完成

                        break;
                    case "-1"://还没发布行程
                        main_titletv.setText("Global Take");
                        main_backiv.setVisibility(View.GONE);
                        main_head_icon.setVisibility(View.VISIBLE);
                        cars_lay.setVisibility(View.VISIBLE);
                        cars_viewlay.setVisibility(View.VISIBLE);
                        more_tv.setVisibility(View.GONE);
                        EventBus.getDefault().post("sure_callCars_back");
                        break;
                    case "-2"://还没发布行程
                        main_titletv.setText("Global Take");
                        main_backiv.setVisibility(View.GONE);
                        main_head_icon.setVisibility(View.VISIBLE);
                        cars_lay.setVisibility(View.VISIBLE);
                        cars_viewlay.setVisibility(View.VISIBLE);
                        more_tv.setVisibility(View.GONE);
                        EventBus.getDefault().post("sure_callCars_back");
                        SharePreferenceUtils.setParam(context,Const.TripStatus,"-1");
                        break;
                }
                break;
            case R.id.more_tv:
                //更多的点击事件
                if (more_tv.getText().toString().equals("更多")||more_tv.getText().toString().equals("More")){
                    onPingJiaYiZhouClick();
                }
                break;
            case R.id.my_address://常用地址
                Intent intent1 = new Intent(MainActivity.this,UsualAddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.head_lay:
                boolean isLogin = (boolean) SharePreferenceUtils.getParam(context,Const.isLogin,false);
                if (isLogin){
                    Intent intent = new Intent(MainActivity.this,UserInfoActivity.class);
                    startActivityForResult(intent,704);
                }else {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,369);
                }

                break;

            case R.id.my_trip:
                Intent intent = new Intent(this,MyTripActivity.class);
                startActivity(intent);
                break;
            case R.id.help_layout:

                intent_web.putExtra("webUrl",Const.webRootUrl+Const.KeyHelp);
                intent_web.putExtra("tag","help");
                startActivity(intent_web);

                break;
            case R.id.about_layout:

                intent_web.putExtra("webUrl",Const.webRootUrl+Const.KeyAbout);
                intent_web.putExtra("tag","about");
                startActivity(intent_web);

                break;
            case R.id.tousu_layout:
                Intent intent_tousu = new Intent(MainActivity.this,TousuActivity.class);
                startActivity(intent_tousu);
                break;
        }
    }

    private boolean isExpand;
    private Dialog dialog;

    private void onPingJiaYiZhouClick() {
        if (!isExpand) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.cancelorder_poplay);
            LinearLayout cancel_order = (LinearLayout) dialog.findViewById(R.id.cancel_order);
            LinearLayout contact_service = (LinearLayout) dialog.findViewById(R.id.contact_service);
            final TextView poptop_tv = (TextView) dialog.findViewById(R.id.poptop_tv);
           final String trip_status = (String) SharePreferenceUtils.getParam(context,Const.TripStatus,"-1");
            if (trip_status.equals("1")){
                poptop_tv.setText(LanguageUtil.getResText(R.string.tv_canceltrip));
            }else {
                poptop_tv.setText(LanguageUtil.getResText(R.string.tv_cancelorder));
            }
            if (trip_status.equals("4")){
                cancel_order.setVisibility(View.GONE);
            }else {
                cancel_order.setVisibility(View.VISIBLE);
            }
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post("sure_callCars_back");
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });

            //联系客服
            contact_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phone = (String) SharePreferenceUtils.getParam(context,Const.waiterPhone,"");
                    CallPhoneutil.callPhone(context,phone);
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    isExpand = false;
                }
            });
            Window dialogWindow = dialog.getWindow();
//            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            lp.dimAmount = 0f;
            int[] location = new int[2];
            more_tv.getLocationOnScreen(location);
            Rect out = new Rect();
            more_tv.getWindowVisibleDisplayFrame(out);
            lp.x = location[0];
            lp.y = location[1] - out.top + more_tv.getHeight();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
            dialog.show();
            isExpand = true;
        } else {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    private long exitTime;

    //返回键的同步操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            String tripStatus = (String) SharePreferenceUtils.getParam(context, Const.TripStatus, "-1");
            LogUtils.Logd("status","tripStatus="+tripStatus);
            switch (tripStatus){
                case "0"://行程已取消
                    main_titletv.setText("Global Take");
                    main_backiv.setVisibility(View.GONE);
                    main_head_icon.setVisibility(View.VISIBLE);
                    cars_lay.setVisibility(View.VISIBLE);
                    cars_viewlay.setVisibility(View.VISIBLE);
                    more_tv.setVisibility(View.GONE);
                    EventBus.getDefault().post("sure_callCars_back");
                    SharePreferenceUtils.setParam(context,Const.TripStatus,"-1");
                    break;
                case "1"://等待接单
                    EventBus.getDefault().post("sure_callCars_back");
                    break;
                case "2"://已接单
                    EventBus.getDefault().post("sure_callCars_back");
                    break;
                case "3"://等待上车

                    break;
                case "4"://已上车

                    break;
                case "5"://完成

                    break;
                case "-2"://还没发布行程
                    main_titletv.setText("Global Take");
                    main_backiv.setVisibility(View.GONE);
                    main_head_icon.setVisibility(View.VISIBLE);
                    cars_lay.setVisibility(View.VISIBLE);
                    cars_viewlay.setVisibility(View.VISIBLE);
                    more_tv.setVisibility(View.GONE);
                    EventBus.getDefault().post("sure_callCars_back");
                    SharePreferenceUtils.setParam(context,Const.TripStatus,"-1");
                    break;
                case "-1":
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(MainActivity.this, LanguageUtil.getResText(R.string.toast_exitapp), Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        finish();
                        AppManager.getAppManager().AppExit(this);
                    }
                    break;
                case "6":

                    break;
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==369||resultCode==704){
            Loginset();
        }
    }

    private void getPriceRuleMoto(String url){
        RequestParams params = new RequestParams(url);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PriceRuleBean ruleBean = gson.fromJson(result,PriceRuleBean.class);
                if (ruleBean!=null){
                    if (ruleBean.getCode().equals(Const.reques_success)){
                        PriceRuleBean.ResultBean priceBean = ruleBean.getResult();
                        savaPriceRule(priceBean.getJourney(),Const.journey_moto);
                        savaPriceRule(priceBean.getMin(),Const.meter_moto);
                        savaPriceRule(priceBean.getMoneybymin(),Const.moneybymeter_moto);
                        savaPriceRule(priceBean.getMoneybytime(),Const.moneybyminute_moto);
                        int startingfare = priceBean.getStartingfare();
                        Log.e("startfare","startingfare="+startingfare);
                        savaPriceRule(startingfare,Const.startingfare_moto);

//                        Intent intent = new Intent(context,MainActivity.class);
//                        startActivity(intent);
//                        setResult(369);
//                        finish();
                    }
                }else {
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_parsingexception));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                ToastUtils.Toast_short(context,"服务器异常异常");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }
    public void savaPriceRule(Object info,String key){
        if (info!=null){
            SharePreferenceUtils.setParam(context,key,info);
        }
    }
}
