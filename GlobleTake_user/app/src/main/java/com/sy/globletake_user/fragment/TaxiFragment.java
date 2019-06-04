package com.sy.globletake_user.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.sy.globletake_user.Bean.AreaCarsBean;
import com.sy.globletake_user.Bean.CallCarsBean;
import com.sy.globletake_user.Bean.CountryBean;
import com.sy.globletake_user.Bean.JifeiBean;
import com.sy.globletake_user.Bean.LatlngBean;
import com.sy.globletake_user.Bean.OrderInfoBean;
import com.sy.globletake_user.Bean.OrderReceived;
import com.sy.globletake_user.Bean.PolyLinesBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.activity.EndOrderActivity;
import com.sy.globletake_user.activity.FareRuleActivity;
import com.sy.globletake_user.activity.MyActivity;
import com.sy.globletake_user.activity.SearchPlaceActivity;
import com.sy.globletake_user.customview.TakerPhoneItemView;
import com.sy.globletake_user.utils.DialogUtils;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.StringUtils;
import com.sy.globletake_user.utils.ToastUtils;
import com.sy.globletake_user.utils.WGSTOGCJ02;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by sunnetdesign3 on 2017/1/20.
 */
public class TaxiFragment extends Fragment implements GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener, OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    @Bind(R.id.taxi_map)
    MapView mapView;
    @Bind(R.id.start_lay)
    LinearLayout start_lay;
    @Bind(R.id.end_lay)
    LinearLayout end_lay;
    @Bind(R.id.price_lay)
    LinearLayout price_lay;
    @Bind(R.id.float_lay)
    LinearLayout float_lay;
    @Bind(R.id.waitcar_tip)
    LinearLayout waitcar_tip;
    @Bind(R.id.orderreceived_lay)
    LinearLayout orderreceived_lay;
    @Bind(R.id.farerule_lay)
    LinearLayout farerule_lay;


    @Bind(R.id.qidian_tv)
    TextView qidian_tv;
    @Bind(R.id.zhondian_tv)
    TextView zhondian_tv;
    @Bind(R.id.recommend_price_tv)
    TextView recommend_price_tv;
    @Bind(R.id.start_text)
    TextView start_text;
    @Bind(R.id.end_text)
    TextView end_text;
    @Bind(R.id.start_iv)
    ImageView start_iv;
    @Bind(R.id.end_iv)
    ImageView end_iv;
    @Bind(R.id.sure_callcar_btn)
    Button sure_callcar_btn;


    @Bind(R.id.waittime_lay)
    LinearLayout waittime_lay;
    @Bind(R.id.nocar_lay)
    LinearLayout nocar_lay;
    @Bind(R.id.center_marker)
    ImageView center_marker;


    private GoogleMap taxi_map;

    private boolean isChina;
    private View mView;
    private LatLng latlng = new LatLng(36.061, 103.834);
    private Context context;
    private Activity mActivity;
    private Location mLocation;
    private String logTag = "taxitag";
    private String addressString = "";
    private String firstAddressString = "";
    private String firstShortAddressString = "";
    private String startPlace_key = "startPlace";
    private String endPlace_key = "endPlace";
    private String myLocation_key = "myLocation";
    double start_latitude = 0;//起点的纬度
    double start_longitude = 0;//起点的经度
    double end_latitude = 0;
    double end_longitude = 0;
    private String firstName="当前位置", lastName;
    private Marker myLocation, startPlace, endPlace;
    private double longitude = 0;//当前的经度
    private double latitude = 0;//当前的纬度
    private int intentTag;
    private Dialog dialog;
    private String TotalMileage;//起点到终点的路程（米）

    private CustomInfoAdapter customInfoAdapter;
    private CarFareInfoAdapter carFareInfoAdapter;
    private Dialog alertDialog;

    private LatLngBounds bounds;

    private boolean isWaitCar = false;//infowindow的状态，是否在等车
    private String tripStatus = "-1";
    private String tripId = "";
    /**
     * 定位监听
     */
    private LocationSource.OnLocationChangedListener mListener;
    private LocationManager locationManager;

    private GoogleApiClient mGoogleApiClient;
    private View infoWindow;
    private View infoWindowCarFare;
    private int minute = 3, second = 0;
    private String userId;
    private String timeStr = "3:00";

    private long startTime, endTime, exitTime;
    private boolean isJifei;
    private String carType;
    private boolean isLogin;
    private ExecutorService threadPool;
    private List<AreaCarsBean.ResultBean> cars_list = new ArrayList<>();
    private boolean isStart = false;
    private boolean isHaveLuxian;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100://倒计时开始
//                    synchronized ("axb"){
//                    }
                    if (isStart) {
                        minute = 3;
                        second = 0;
                        mHandler.post(mRunnable);
                        isStart = false;
                    }
                    break;
                case 007:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;

                case 428:
                    if (qidian_tv!=null){
                        qidian_tv.setText(firstName);
                    }
                    break;
                case 55:
                    //多久后在这里上车的提醒操作
                    waittime_lay.setVisibility(View.VISIBLE);
                    center_marker.setVisibility(View.VISIBLE);
                    LatLng sydney = new LatLng(latitude, longitude);
                    taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                    break;
                case 57:
                    waittime_lay.setVisibility(View.VISIBLE);
                    break;
                case  58:
                    Log.e("tag","cars="+cars_list.size());
                    if (!isHaveLuxian){
                        if (cars_list.size()==0){
                            nocar_lay.setVisibility(View.VISIBLE);
                            center_marker.setVisibility(View.VISIBLE);
                            waittime_lay.setVisibility(View.GONE);
                        }else {
                            nocar_lay.setVisibility(View.GONE);
                            center_marker.setVisibility(View.VISIBLE);
                            waittime_lay.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
        }
    };

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //跟新UI
            refreshUi();
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    private void refreshUi() {
        if (minute == 0) {
            if (second == 0) {
                timeStr = "0:00";
                notifyInfowindow();
//                mHandler.removeMessages(100);
//                mHandler=null;
                minute = 3;
                second = 0;

            } else {
                --second;
                if (second >= 10) {
                    timeStr = "0" + minute + ":" + second;
                    notifyInfowindow();
                } else {
                    timeStr = "0" + minute + ":0" + second;
                    notifyInfowindow();
                }
            }
        } else {
            if (second == 0) {
                second = 59;
                --minute;
                if (minute >= 10) {
                    timeStr = minute + ":" + second;
                    notifyInfowindow();
                } else {
                    timeStr = "0" + minute + ":" + second;
                    notifyInfowindow();
                }
            } else {
                --second;
                if (second >= 10) {
                    if (minute >= 10) {
                        timeStr = minute + ":" + second;
                        notifyInfowindow();
                    } else {
                        timeStr = "0" + minute + ":" + second;
                        notifyInfowindow();
                    }
                } else {
                    if (minute >= 10) {
                        timeStr = minute + ":0" + second;
                        notifyInfowindow();
                    } else {
                        timeStr = "0" + minute + ":0" + second;
                        notifyInfowindow();
                    }
                }
            }
        }
    }

    private void notifyInfowindow() {
        LatLng latLng = new LatLng(start_latitude, start_longitude);
        addOnlyoneMarker(startPlace_key, latLng, R.mipmap.shouye_qidian, 2);
        taxi_map.setInfoWindowAdapter(customInfoAdapter);
        startPlace.showInfoWindow();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_taxi, null);
        ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);
        context = getActivity();
        mActivity = getActivity();
        mapView.onCreate(savedInstanceState);
        //初始化
        userId = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int errorCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this.getActivity());
        Log.e("code", "errorCode=" + errorCode);

        if (ConnectionResult.SUCCESS != errorCode && ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED != errorCode) {
//            GooglePlayServicesUtil.getErrorDialog(errorCode,
//                    this.getActivity(), 0).show();
            //提示用户安装谷歌安装器
            dialogtip(LanguageUtil.getResText(R.string.tv_ggtip), "gg");
        } else {
            mapView.getMapAsync(this);
        }
        dialog = DialogUtils.initDialog(context);
        threadPool = Executors.newFixedThreadPool(2);//暂时只允许有两个子线程的线程池
        infoWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_contents, null);
        infoWindowCarFare = LayoutInflater.from(context).inflate(R.layout.moneyinfowindow, null);
        customInfoAdapter = new CustomInfoAdapter();
        carFareInfoAdapter = new CarFareInfoAdapter();
        initEvent();
        String registrationID = JPushInterface.getRegistrationID(context);
        Log.e("tag", "registrationID=" + registrationID);
        return mView;
    }

    private void initEvent() {
        start_lay.setOnClickListener(this);
        end_lay.setOnClickListener(this);
        sure_callcar_btn.setOnClickListener(this);
        farerule_lay.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void doWork() {

       /* Criteria criteria = new Criteria();
        // 获得最好的定位效果
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        // 使用省电模式
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 获得当前的位置提供者
        String provider = locationManager.getBestProvider(criteria, true);*/
        // 获得当前的位置
        taxi_map.setMyLocationEnabled(true);//地图小蓝点的显示
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // 获得当前的位置
        //在室外使用GPS定位

        //定位请求
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        mLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//获取定位对象
      /*  List<String> providerlist = locationManager.getAllProviders();
        LogUtils.Loge("size", "providerlist.size=" + providerlist.size());
        for (int i = 0; i < providerlist.size(); i++) {
            //在室内使用net定位
            LogUtils.Loge(logTag, "provider=" + providerlist.get(i));
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 1, this);
            mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//获取定位对象
        }*/

        if (mLocation == null) {
            //在室内使用net定位
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
            mLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//获取定位对象
        }
        if (mLocation != null) {
            String locationStr = mLocation.getLatitude() + "," + mLocation.getLongitude();
            Log.e("tag", "locationStr=" + locationStr);
            taxi_map.clear();
            getCountryName(locationStr);

        } else {
            Log.e("location", "location=" + mLocation);
            ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_locationfail));
        }
    }


    private void addOnlyoneMarker(String markerTag, LatLng sydney, int mipmapId, float tbnum) {
        switch (markerTag) {
            case "startPlace":
                if (startPlace != null) {
                    startPlace.remove();
                    startPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                } else {
                    startPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                }
                break;
            case "endPlace":
                if (endPlace != null) {
                    endPlace.remove();
                    endPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                } else {
                    endPlace = addMarker(markerTag, sydney, mipmapId, tbnum);
                }
                break;
            case "myLocation":
                if (myLocation != null) {
                    myLocation.remove();
                    myLocation = addMarker(markerTag, sydney, mipmapId, tbnum);
                } else {
                    myLocation = addMarker(markerTag, sydney, mipmapId, tbnum);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        mLocationClient.stopLocation();//停止定位
//        mLocationClient.onDestroy();//销毁定位客户端。
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
//        mapView.onDestroy();
        //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象
        if (mHandler != null) {
            mHandler.removeMessages(100);
            mHandler.removeMessages(007);
            mHandler.removeMessages(428);
            mHandler.removeCallbacks(mRunnable);
        }
    }

    //mainactivity返回键之后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String eventStr) {
        if (eventStr.equals("sure_callCars_back")) {
            Log.e("tripStatus", "tripStatus=" + tripStatus);
            String content = "";
            switch (tripStatus) {
                case "0"://行程已取消

                    chushihuafloatlay();

                    //重新添加覆盖物
                    taxi_map.clear();
                    LatLng sydney = new LatLng(latitude, longitude);
                    addOnlyoneMarker(myLocation_key, sydney, R.mipmap.shouye_gensui, 1);
//                    addOnlyoneMarker(startPlace_key, sydney, R.mipmap.shouye_qidian, 2);
//                    taxi_map.setInfoWindowAdapter(customInfoAdapter);
//                    startPlace.showInfoWindow();

                    waittime_lay.setVisibility(View.VISIBLE);
                    center_marker.setVisibility(View.VISIBLE);
                    end_latitude = 0;
                    end_longitude = 0;
                    //展示提示信息
                    isWaitCar = false;//非等待状态
                    isHaveLuxian = false;
                    break;
                case "1"://等待接单
                    content = LanguageUtil.getResText(R.string.dialog_tipsimple);
                    dialogtip(content, "order");
                    break;
                case "2"://已接单
                    content = LanguageUtil.getResText(R.string.dialog_cancelcontent);
                    dialogtip(content, "order");
                    break;
                case "3"://等待上车
                    content = LanguageUtil.getResText(R.string.dialog_cancelcontent);
                    dialogtip(content, "order");
                    break;
                case "4"://已上车

                    break;
                case "5"://完成

                    break;
                case "-1"://默认状态
                    chushihuafloatlay();

                    //重新添加覆盖物
                    taxi_map.clear();
                    LatLng sydney1 = new LatLng(latitude, longitude);
                    start_latitude = latitude;
                    start_longitude = longitude;
                    String addressStr = latitude + "," + longitude;
                    getAddress(addressStr);
                    addOnlyoneMarker(myLocation_key, sydney1, R.mipmap.shouye_gensui, 1);

//                    addOnlyoneMarker(startPlace_key, sydney1, R.mipmap.shouye_qidian, 2);
//                    taxi_map.setInfoWindowAdapter(customInfoAdapter);
//                    startPlace.showInfoWindow();
                    waittime_lay.setVisibility(View.VISIBLE);
                    center_marker.setVisibility(View.VISIBLE);
                    //展示提示信息
                    isWaitCar = false;//非等待状态
                    isHaveLuxian = false;
                    end_latitude = 0;
                    end_longitude = 0;
                    break;
            }
        }
    }

    private void dialogtip(final String content, final String tag) {
        //弹窗提示
        alertDialog = new Dialog(context, R.style.progress_dialog);
        alertDialog.setContentView(R.layout.dialog_itemview);
        alertDialog.setCancelable(true);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                DialogUtils.backgroundAlpaha(getActivity(), 1.0f);

            }
        });
        final TextView yes_tv = (TextView) alertDialog.findViewById(R.id.cancleOrder_yes);
        final TextView no_tv = (TextView) alertDialog.findViewById(R.id.cancleOrder_no);
        TextView content_tv = (TextView) alertDialog.findViewById(R.id.content_tv);
        content_tv.setText(content);
        if (tag.equals("gg")) {
            yes_tv.setText(LanguageUtil.getResText(R.string.tv_nowinstall));
            no_tv.setText(LanguageUtil.getResText(R.string.tv_yhinstall));
        }
        yes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tag.equals("gg")) {
                    //取消订单
                    if (!tripId.isEmpty()) {
                        isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
                        if (isLogin) {
                            cancleTrip(tripId);
                        } else {
//                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
                        }
                    } else {
                        ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_nosendtrip));
                    }
                } else {
                    alertDialog.dismiss();
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            downloadFile(Const.googlesevice, FileUtil.DIR_APK,"google安装器.apk");
                            OkHttpUtils//
                                    .get()//
                                    .url(Const.googlesevice)//
                                    .build()//
                                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "googleazq.apk")//
                                    {
                                        @Override
                                        public void inProgress(float progress) {
                                            Log.e("tag", "progress=" + progress);
                                        }

                                        @Override
                                        public void onError(Request request, Exception e) {
                                            Log.e("tag", "onError :" + e.getMessage());
                                        }

                                        @Override
                                        public void onResponse(File file) {
                                            Log.e("tag", "onResponse :" + file.getAbsolutePath());
                                            Log.e("tag", "文件下载成功");
                                            Log.e("tag", "file=" + file.length());
                                            Message msg = Message.obtain();
                                            msg.what = 007;
                                            mHandler.sendMessage(msg);
                                            if (file != null) {
                                                installApk(context, file);
                                            }
                                        }
                                    });

                        }
                    }).start();

                }
            }
        });
        no_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //不取消
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        DialogUtils.backgroundAlpaha(getActivity(), 0.5f);
    }

    //订单被司机接单后的操作
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent1(OrderReceived eventStr) {
        if (eventStr.getEventStr().equals("订单被接")) {
            float_lay.setVisibility(View.GONE);
            sure_callcar_btn.setVisibility(View.GONE);
            getOrderInfo(eventStr.getOrder_id());
            if (mHandler != null) {
                mHandler.removeMessages(100);
//                mHandler.getLooper().quit();
                mHandler.removeCallbacks(mRunnable);//结束handler的线程
                startPlace.hideInfoWindow();
                if (bounds != null) {
                    taxi_map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                }
            }
        }
    }

    //司机开始计费后的操作
    private String order_id;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent2(JifeiBean eventStr) {
        order_id = eventStr.getOrder_id();
        if (eventStr.getEventStr().equals("开始计费")) {
            startTime = getCurrentTime();
            isJifei = true;
            ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_startedjifei));
            LatLng sydney = new LatLng(latitude, longitude);
            addOnlyoneMarker(myLocation_key, sydney, R.mipmap.shouye_gensui, 4);
            taxi_map.setInfoWindowAdapter(new CarFareInfoAdapter());
            myLocation.showInfoWindow();
            taxi_map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    toFareRuleDetail();
                }
            });
            taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
            Log.e("tag", "taxiMarkers.size()=" + taxiMarkers.size());
            for (String key : taxiMarkers.keySet()) {
                taxiMarkers.get(key).remove();
            }
            taxiMarkers.clear();
            Log.e("tag", "motoMarkers.size()=" + motoMarkers.size());
            for (String key : motoMarkers.keySet()) {
                motoMarkers.get(key).remove();
            }
            motoMarkers.clear();
        } else if (eventStr.getEventStr().equals("结束订单")) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_endedorder));
            Intent intent = new Intent(getActivity(), EndOrderActivity.class);
            intent.putExtra("order_id", order_id);
            intent.putExtra("tag", "taxif");
            startActivityForResult(intent, 666);
        }
    }

    //点击不同的车辆类型
    private String currentCarType;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent5(String eventStr) {
        if (eventStr.equals(Const.Driving)) {
            getAreaCars(Const.CarType_taxi);//获取附近出租车
        } else if (eventStr.equals(Const.Moto)) {
            getAreaCars(Const.CarType_Moto);//获取附近摩托车
        }
    }

    private void getAreaCars(final String carType) {

        RequestParams params = new RequestParams(Const.getAreaCar);
        params.addBodyParameter("Pointw", String.valueOf(start_latitude));
        params.addBodyParameter("Pointj", String.valueOf(start_longitude));
        params.addBodyParameter("carType", carType);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                AreaCarsBean areaCarsBean = gson.fromJson(result, AreaCarsBean.class);
                if (areaCarsBean.getCode().equals(Const.reques_success)) {
                    if (areaCarsBean != null) {
                        List<AreaCarsBean.ResultBean> cars = areaCarsBean.getResult();
                        if (cars != null) {
                            cars_list.clear();
                            cars_list.addAll(cars);
                            //清除已添加的icon
                            for (String key : taxiMarkers.keySet()) {
                                taxiMarkers.get(key).remove();
                            }
                            for (String key : motoMarkers.keySet()) {
                                motoMarkers.get(key).remove();
                            }
                            //清除数据源
                            taxiMarkers.clear();
                            motoMarkers.clear();
                            //重新添加
                            for (int i = 0; i < cars_list.size(); i++) {
                                LatLng latlng = new LatLng(cars_list.get(i).getFdDriverlatitude(), cars_list.get(i).getFdDriverlongitude());
                                if (carType.equals(Const.CarType_taxi)) {
                                    addTaxiMarker(i + "", latlng, R.mipmap.taxiicon, 1);
                                } else {
                                    addMotoMarker(i + "", latlng, R.mipmap.motoicon, 1);
                                }
                            }

                            Message message = Message.obtain();
                            message.what = 58;
                            mHandler.sendMessage(message);
                        }
                    }
                } else {
                    ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_parsingexception));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (null == taxi_map) {
            taxi_map = googleMap;
        }
        taxi_map.setOnCameraIdleListener(this);
        taxi_map.setOnCameraMoveStartedListener(this);
        taxi_map.setOnCameraMoveListener(this);
        taxi_map.setOnCameraMoveCanceledListener(this);
        /**设置地图类型**/
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        taxi_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //地图点击的监听
       /* taxi_map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                taxi_map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
//                        ToastUtils.showLong(HouseStepTwoActivity.this,getString(R.string.house_add_step2_locationing));
                        double lat = latLng.latitude;
                        double lon = latLng.longitude;

                        String address = getAddress(context, lat, lon);
                        LatLng ll = new LatLng(lat, lon);
                        addOnlyoneMarker(startPlace_key,ll,R.mipmap.shouye_qidian,2);
                        taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));

                        taxi_map.setInfoWindowAdapter(customInfoAdapter);
                        startPlace.showInfoWindow();
                        Log.e("location", address);
//                        tv_address_detail.setText(address);
                    }
                });
            }
        });*/
        //定位按钮的监听
        taxi_map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng ll = new LatLng(latitude, longitude);
               /* taxi_map.clear();//先清除，已经有的锚点
                taxi_map.addMarker(new MarkerOptions().position(ll)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.shouye_gensui))
                        .title(firstShortAddressString));*/
                taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 13));

//                tv_address_detail.setText(firstAddressString);

                return false;
            }
        });

        UiSettings uiSettings = taxi_map.getUiSettings();
        //右下角的状态栏
        uiSettings.setMapToolbarEnabled(false);
        //缩放比例控件
        uiSettings.setZoomControlsEnabled(false);
        //指南针(无法强制始终显示指南针)
        uiSettings.setCompassEnabled(false);
        //支持所有手势
        uiSettings.setAllGesturesEnabled(true);
        //My Location 按钮(调用了mMap.setMyLocationEnabled(true)，才会显示)
        uiSettings.setMyLocationButtonEnabled(true);
        //设置权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    12);
        } else {
            if (isOPen(context)) {
                doWork();
            } else {
                // 转到手机设置界面，用户设置GPS
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
            }
        }

    }

    public boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    //位置变化的监听（实时定位）
    @Override
    public void onLocationChanged(Location location) {
//        ToastUtils.Toast_short1("位置变化了~~");
        if (location != null) {
            mLocation = location;
            LatlngBean transform = WGSTOGCJ02.transform(location.getLongitude(), location.getLatitude());
            if (isChina) {
                latitude = transform.getLat();
                longitude = transform.getLon();
            } else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            LatLng sydney = new LatLng(latitude, longitude);
            String latlngStr = latitude + "," + longitude;
            getAddress(latlngStr);

            addOnlyoneMarker(myLocation_key, sydney, R.mipmap.shouye_gensui, 1);
            if (isJifei) {
                addOnlyoneMarker(myLocation_key, sydney, R.mipmap.shouye_gensui, 4);
                taxi_map.setInfoWindowAdapter(carFareInfoAdapter);
                myLocation.showInfoWindow();
                taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
            }

           /* if (eventStr.equals(Const.Driving)) {
                getAreaCars(Const.CarType_taxi);//获取附近出租车
            } else if (eventStr.equals(Const.Moto)) {
                getAreaCars(Const.CarType_Moto);//获取附近摩托车
            }*/
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, SearchPlaceActivity.class);
        intent.putExtra("tag", "TaxiFragment");
        switch (view.getId()) {
            case R.id.start_lay:
                startActivityForResult(intent, 01);
                intentTag = 1;
                break;
            case R.id.end_lay:
                startActivityForResult(intent, 01);
                intentTag = 2;
                break;
            case R.id.sure_callcar_btn://确认叫车

                if (Build.VERSION.SDK_INT == 23) {

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)//权限未授予
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 40);//tip:跟activity中申请权限不一样，不需要ActivityCompat.requestPermissions
                    } else//已授予权限
                    {
                        sureCallCars();
                    }
                } else {
                    sureCallCars();
                }
                break;
            case R.id.farerule_lay:
                toFareRuleDetail();
                break;
        }
    }

    private void sureCallCars() {
        isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
        userId = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        String registrationID = JPushInterface.getRegistrationID(context);
        Log.e("tag", "registrationID=" + registrationID);
        if (!isLogin) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
        } else if (firstName == null || firstName.isEmpty()) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_inputstartplace));
        } else if (lastName == null || lastName.isEmpty()) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_inputendplace));
        } else if (userId.isEmpty() || userId.isEmpty()) {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_userid));
        } else if (registrationID == null || registrationID.isEmpty()) {
            ToastUtils.Toast_short1(LanguageUtil.getResText(R.string.toast_pushId));
        } else {
            callCars(userId, registrationID);
            Log.e("tag", "开启叫车请求");
        }
    }

    private void toFareRuleDetail() {
        isLogin = (boolean) SharePreferenceUtils.getParam(context, Const.isLogin, false);
        if (isLogin) {
            Intent intent1 = new Intent(context, FareRuleActivity.class);
            startActivity(intent1);
        } else {
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_notlogin));
        }
    }

    //发布行程
    private String tempStr = "";

    private void callCars(String userId, String pushCode) {

        if (!dialog.isShowing()) {
            dialog.show();
        }
        carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
        Log.e("tag", "carType=" + carType);
        RequestParams params = new RequestParams(Const.callCars);
        params.addBodyParameter("user_id", userId);//用户id
        params.addBodyParameter("firstPointj", start_longitude + "");//起点的经度start_longitude +
        params.addBodyParameter("firstPointw", start_latitude + "");//起点的纬度start_latitude +
        params.addBodyParameter("lastPointj", end_longitude + "");
        params.addBodyParameter("lastPointw", end_latitude + "");
        params.addBodyParameter("firstName", firstName);//起点的地名
        params.addBodyParameter("lastName", lastName);//终点的地名
        params.addBodyParameter("TotalMileage", TotalMileage);
        params.addBodyParameter("carType", carType);//车辆类型
        params.addBodyParameter("pushCode", pushCode);//极光推送的token
        params.addBodyParameter("pushId", "1");//平台识别码，1：Android，0：ios
        LanguageUtil.addLanguage(params);

        final StringBuffer buffer = new StringBuffer(Const.callCars);
        buffer.append("?user_id=" + userId);
        buffer.append("&firstPointj=" + start_longitude + "");
        buffer.append("&firstPointw=" + start_latitude + "");
        buffer.append("&lastPointj=" + end_longitude + "");
        buffer.append("&lastPointw=" + end_latitude + "");
        buffer.append("&firstName=" + firstName);
        buffer.append("&lastName=" + lastName);
        buffer.append("&TotalMileage=" + TotalMileage);
        buffer.append("&carType=" + carType);
        buffer.append("&pushCode=" + pushCode);
        buffer.append("&pushId=" + "1");
        Log.e("tag", "callCarUrl=" + buffer.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                tempStr = result;
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success) && obj.getString("message").equals(LanguageUtil.getResText(R.string.request_success))) {
                        //行程发布成功
                        Gson gson = new Gson();
                        CallCarsBean callCarsBean = null;
                        try {
                            callCarsBean = gson.fromJson(result, CallCarsBean.class);
                        } catch (Exception e) {
                            ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_parsingexception));
                        }

                        if (callCarsBean != null) {
                            float_lay.setVisibility(View.GONE);
                            EventBus.getDefault().post("等待应答");
                            waitcar_tip.setVisibility(View.VISIBLE);
                            LatLng sydney = new LatLng(start_latitude, start_longitude);
                            taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                            isWaitCar = true;
                            isStart = true;
                            addOnlyoneMarker(startPlace_key, sydney, R.mipmap.shouye_qidian, 2);
                            if (!startPlace.isInfoWindowShown()) {
                                startPlace.showInfoWindow();
                            }
//                        taxi_map.setInfoWindowAdapter(new CustomInfoAdapter());
                            //开启倒计时
                            tripStatus = callCarsBean.getResult().getFdStatus();
                            SharePreferenceUtils.setParam(context, Const.TripStatus, tripStatus);
                            tripId = callCarsBean.getResult().getFdId();
                            Message message = new Message();
                            message.what = 100;
                            mHandler.sendMessage(message);
                        }
                    } else {

                        ToastUtils.Toast_short(mActivity, obj.getString("message"));
                        Intent intent = new Intent(context, MyActivity.class);
                        intent.putExtra("logmsg", buffer.toString() + "\n" + tempStr);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_serverexception));
                Intent intent = new Intent(context, MyActivity.class);
                intent.putExtra("logmsg", buffer.toString() + "\n" + tempStr);
                startActivity(intent);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * <通过经纬度，获取当前位置。>
     *
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public String getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = null;
        if (LanguageUtil.isZh()) {
            geocoder = new Geocoder(context, Locale.getDefault());
        } else {
            geocoder = new Geocoder(context, Locale.ENGLISH);
        }
        try {
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String addressStr = "";
            /*if (!StringUtils.isEmpty(addresses.get(0).getCountryName())) {//"国家："
                addressStr = addressStr + addresses.get(0).getCountryName();
                addressString = addresses.get(0).getCountryName();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getAdminArea())) {//"省：" +
                addressStr = addressStr + addresses.get(0).getAdminArea();
                addressString = addressString + "-" + addresses.get(0).getAdminArea();
            }*/
            if (!StringUtils.isEmpty(addresses.get(0).getLocality())) {//"城市：" +
                addressStr = addressStr + addresses.get(0).getLocality();
            }
            if (!StringUtils.isEmpty(addresses.get(0).getAddressLine(1))) {//"区：" +
                addressStr = addressStr + addresses.get(0).getAddressLine(1);
            }
            /*if (!StringUtils.isEmpty(addresses.get(0).getAddressLine(2))) {//"街道：" +
                addressStr = addressStr + addresses.get(0).getAddressLine(2);
            }*/
//            TotalMileage = addressStr;
            return addressStr;
        } catch (Exception e) {
            e.printStackTrace();
//            return getResources().getString(R.string.house_add_step2_location_failed);
            return "";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 12) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (isOPen(context)) {
                        doWork();
                    } else {
                        // 转到手机设置界面，用户设置GPS
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                    }
                }
            } else {
                doWork();
            }
        }
        if (requestCode == 40) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("ss", "授权成功");
                sureCallCars();
            } else {
                // Permission Denied
                Log.e("ss", "授权失败");
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 01 && resultCode == 01 && data != null) {
            String addressName = data.getStringExtra("addressName");
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            LatLng sydney = new LatLng(latitude, longitude);
            if (addressName != null) {

                if (intentTag == 1) {//区分起点和终点的标记；1：起点，2：终点
                    qidian_tv.setText(addressName);
                    taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
                    /*taxi_map.addMarker(new MarkerOptions()
                            .title(addressName)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.shouye_qidian))
                            .position(sydney));*/
                    addOnlyoneMarker(startPlace_key, sydney, R.mipmap.shouye_qidian, 2);
                    start_latitude = latitude;
                    start_longitude = longitude;
                    firstName = addressName;
                    taxi_map.setInfoWindowAdapter(customInfoAdapter);
                    startPlace.showInfoWindow();
                } else {
                    zhondian_tv.setText(addressName);
                    zhondian_tv.setTextColor(getResources().getColor(R.color.moren_tvcolor));
//                    taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

                    /*taxi_map.addMarker(new MarkerOptions()
                            .title(addressName)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.shouye_zhongdian))
                            .position(sydney));*/
                    addOnlyoneMarker(endPlace_key, sydney, R.mipmap.shouye_zhongdian, 3);
                    //记录终点的经纬度
                    end_latitude = latitude;
                    end_longitude = longitude;
                    lastName = addressName;

                }

                waittime_lay.setVisibility(View.GONE);
                center_marker.setVisibility(View.GONE);
                isHaveLuxian = true;

                //路径规划
                String startStr = start_latitude + "," + start_longitude;
                String endStr = end_latitude + "," + end_longitude;
                if (start_latitude == 0 || start_longitude == 0 || end_latitude == 0 || end_longitude == 0) {

                } else {
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
                    if (carType.equals(Const.CarType_taxi)) {

                        getLuxianJson(startStr, endStr, Const.Driving);
                    } else if (carType.equals(Const.CarType_Moto)) {
                        getLuxianJson(startStr, endStr, Const.Moto);
                    }
                }
            }
        }

        if (requestCode == 666 && resultCode == 666) {
            orderreceived_lay.removeAllViews();
            chushihuafloatlay();
            taxi_map.clear();
            LatLng latLng = new LatLng(latitude, longitude);
            addOnlyoneMarker(myLocation_key, latLng, R.mipmap.shouye_gensui, 1);
            addOnlyoneMarker(startPlace_key, latLng, R.mipmap.shouye_qidian, 2);
            EventBus.getDefault().post("again");
            String carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
            if (carType.equals(Const.CarType_taxi)) {
                getAreaCars(Const.CarType_taxi);
            } else {
                getAreaCars(Const.CarType_Moto);
            }

            //还原标志位
            isJifei = false;
            SharePreferenceUtils.setParam(context, Const.TripStatus, "-1");
        }
    }

    private void chushihuafloatlay() {
        sure_callcar_btn.setVisibility(View.GONE);
        price_lay.setVisibility(View.GONE);
        end_iv.setVisibility(View.VISIBLE);
        end_text.setVisibility(View.GONE);
        start_text.setVisibility(View.GONE);
        start_text.setClickable(true);
        end_text.setClickable(true);
        start_iv.setVisibility(View.VISIBLE);
        float_lay.setVisibility(View.VISIBLE);
        waitcar_tip.setVisibility(View.GONE);
        zhondian_tv.setText(LanguageUtil.getResText(R.string.tv_wantaddress));
        zhondian_tv.setTextColor(getResources().getColor(R.color.addred));
    }

    // TODO: 2017/2/17 该接口用xutils很不稳定，建议后期得换个框架
    private void getLuxianJson(final String startStr, String endStr, String mode) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://maps.googleapis.com/maps/api/directions/json?origin=");
        buffer.append(startStr + "&destination=");
        buffer.append(endStr + "&sensor=false&mode=");
        buffer.append(mode);
        Log.e("url", "url=" + buffer.toString());
        RequestParams params = new RequestParams(buffer.toString());
//        RequestParams params = new RequestParams("https://maps.googleapis.com/maps/api/directions/json?origin=22.495562546852,113.9195796929&destination=22.535006648941,113.97465188433&sensor=false&mode=driving");
//        params.addBodyParameter("origin",startStr);
//        params.addBodyParameter("destination",endStr);
//        params.addBodyParameter("sensor","false");
//        params.addBodyParameter("mode",mode);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
//                Log.e("tag","result="+result);
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString("status").equals("OK")) {
                        Gson gson = new Gson();
                        PolyLinesBean polyLinesBean = gson.fromJson(result, PolyLinesBean.class);
                        double mileage = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
                        double times = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDuration().getValue();
                        List<PolyLinesBean.RoutesBean.LegsBean.StepsBean> steps = polyLinesBean.getRoutes().get(0).getLegs().get(0).getSteps();
                        PolylineOptions lineOptions = new PolylineOptions();
                        lineOptions.color(getResources().getColor(R.color.statecolor));
                        if (steps != null) {
                            for (int i = 0; i < steps.size(); i++) {
//                                LatLng latLng_mapstart = new LatLng(steps.get(i).getStart_location().getLat(),steps.get(i).getStart_location().getLng());
//                                LatLng latLng_end = new LatLng(steps.get(i).getEnd_location().getLat(),steps.get(i).getEnd_location().getLng());
//                                lineOptions.add(latLng_end);
                                List<LatLng> latLngs = decodePoly(steps.get(i).getPolyline().getPoints());
                                lineOptions.addAll(latLngs);
                            }
                            taxi_map.clear();//移除之前已经画好的路线图

                            addOnlyoneMarker(myLocation_key, new LatLng(latitude, longitude), R.mipmap.shouye_gensui, 1);
                            addOnlyoneMarker(startPlace_key, new LatLng(start_latitude, start_longitude), R.mipmap.shouye_qidian, 2);
                            addOnlyoneMarker(endPlace_key, new LatLng(end_latitude, end_longitude), R.mipmap.shouye_zhongdian, 2);
                            //展示提示信息
                            taxi_map.setInfoWindowAdapter(customInfoAdapter);
                            startPlace.showInfoWindow();

                            taxi_map.addPolyline(lineOptions);
                            carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
                            getAreaCars(carType);//获取附近车辆
                        }

                        double southWest_latitude = polyLinesBean.getRoutes().get(0).getBounds().getSouthwest().getLat();
                        double southWest_longitude = polyLinesBean.getRoutes().get(0).getBounds().getSouthwest().getLng();
                        double northEast_latitude = polyLinesBean.getRoutes().get(0).getBounds().getNortheast().getLat();
                        double northEast_longitude = polyLinesBean.getRoutes().get(0).getBounds().getNortheast().getLng();
                        LatLng southWest = new LatLng(southWest_latitude, southWest_longitude);
                        LatLng northEast = new LatLng(northEast_latitude, northEast_longitude);
                        bounds = new LatLngBounds(southWest, northEast);
                        TotalMileage = mileage + "";//起点到终点的距离
                        if (mileage + "" != null && times + "" != null) {
                            getRecommendPrice(mileage + "", times + "");
                        } else {
                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_errotriportime));
                        }
                        Log.e("tag", "bouds=" + bounds);
                        if (bounds != null) {
                            taxi_map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
                        }
                        //画好路线后改变行程状态（服务端并没有变，只是用来做返回键的判断条件）
                        SharePreferenceUtils.setParam(context, Const.TripStatus, "-2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_dataexception));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 返回的json数据，google的points数据使用了其它格式的编码，需要我们解析出来。
     *
     * @param encoded
     * @return List<LatLng>
     */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    private final Map<String, MarkerOptions> mMarkers = new ConcurrentHashMap<String, MarkerOptions>();
    private final Map<String, Marker> taxiMarkers = new ConcurrentHashMap<String, Marker>();
    private final Map<String, Marker> motoMarkers = new ConcurrentHashMap<String, Marker>();

    private Marker addMarker(String name, LatLng ll, int mipmapId, float tbnum) {
        final MarkerOptions option = new MarkerOptions().position(ll).title(name)
                .zIndex(tbnum)
                .icon(BitmapDescriptorFactory.fromResource(mipmapId));//覆盖物的图标
        mMarkers.put(name, option);
        Marker marker = taxi_map.addMarker(option);
        return marker;
    }

    private Marker addTaxiMarker(String name, LatLng ll, int mipmapId, float tbnum) {
        final MarkerOptions option = new MarkerOptions().position(ll).title(name)
                .zIndex(tbnum)
                .icon(BitmapDescriptorFactory.fromResource(mipmapId));//覆盖物的图标
        Marker marker = taxi_map.addMarker(option);
        taxiMarkers.put(name, marker);
        return marker;
    }

    private Marker addMotoMarker(String name, LatLng ll, int mipmapId, float tbnum) {
        final MarkerOptions option = new MarkerOptions().position(ll).title(name)
                .zIndex(tbnum)
                .icon(BitmapDescriptorFactory.fromResource(mipmapId));//覆盖物的图标
        Marker marker = taxi_map.addMarker(option);
        motoMarkers.put(name, marker);
        return marker;
    }

    private void getRecommendPrice(String mileage, String times) {
        RequestParams params = new RequestParams(Const.get_recommendPrice);
        params.addBodyParameter("mileage", mileage);//路程的长度   1000米就传1000
        params.addBodyParameter("times", times);//预计时长    1分钟 就传60
        params.addBodyParameter("carType", carType);//叫车类型    1出租车 2摩托车
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)) {
                        double rePrice = obj.getDouble("result");
                        String takePrice = new DecimalFormat("0.00").format(rePrice);
                        if (takePrice != null) {
                            //999999
                            start_text.setVisibility(View.VISIBLE);
                            start_text.setClickable(false);
                            start_iv.setVisibility(View.GONE);
                            end_iv.setVisibility(View.GONE);
                            end_text.setVisibility(View.VISIBLE);
                            end_text.setClickable(false);
                            price_lay.setVisibility(View.VISIBLE);
                            sure_callcar_btn.setVisibility(View.VISIBLE);
                            recommend_price_tv.setText(LanguageUtil.getResText(R.string.tv_ckprice) + takePrice + "R");
                            EventBus.getDefault().post("sure_callCars");

                        } else {
                            ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_getckfail));
                        }
                    } else {
                        ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_dataexception));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_serverexception));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //地图移动事件
    @Override
    public void onCameraIdle() {
        if (!isHaveLuxian) {
            CameraPosition cameraPosition = taxi_map.getCameraPosition();
            LatLng target = cameraPosition.target;
            start_latitude = target.latitude;
            start_longitude = target.longitude;
            String startplace = start_latitude + "," + start_longitude;
            getAddress(startplace);
            carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
            getAreaCars(carType);//获取附近车辆
        }
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {
        if (!isHaveLuxian){
            firstName = LanguageUtil.getResText(R.string.tv_getstartplace);
            Message message = Message.obtain();
            message.what = 428;
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onCameraMoveStarted(int i) {
        waittime_lay.setVisibility(View.GONE);
    }


    class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoContents(Marker arg0) {

            return null;
        }

        @Override
        public View getInfoWindow(Marker arg0) {
            displayView(arg0);
            return infoWindow;
        }
    }

    public void displayView(Marker arg0) {

        TextView timeafter_tv = (TextView) infoWindow.findViewById(R.id.timeafter_tv);
        RelativeLayout mid_relay = (RelativeLayout) infoWindow.findViewById(R.id.mid_relay);
        TextView takecar_tv = (TextView) infoWindow.findViewById(R.id.takecar_tv);

        if (isWaitCar) {
            mid_relay.setVisibility(View.GONE);
            timeafter_tv.setText(LanguageUtil.getResText(R.string.tv_callcartime));
            timeafter_tv.setTextColor(getResources().getColor(R.color.moren_tvcolor));
            takecar_tv.setText(timeStr);
            takecar_tv.setTextColor(getResources().getColor(R.color.daojishi_tvcolor));

        } else {
            mid_relay.setVisibility(View.VISIBLE);
            timeafter_tv.setText(LanguageUtil.getResText(R.string.tv_minute));
            timeafter_tv.setTextColor(getResources().getColor(R.color.statecolor));
            takecar_tv.setText(LanguageUtil.getResText(R.string.tv_heretake));
            takecar_tv.setTextColor(getResources().getColor(R.color.moren_tvcolor));
        }
    }

    class CarFareInfoAdapter implements GoogleMap.InfoWindowAdapter {

        private TextView distance_tv, integermoney_tv, decimalsmoney_tv;
        private String residueMileage;//剩余路程（米）
        private String orderPrice;//已产生的实时价格
        private ImageView jijiarule_iv;
        Handler madHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 142) {
                    Log.e("tag", "residueMileage=" + residueMileage);
                    Log.e("tag", "TotalMileage=" + TotalMileage);
                    Log.e("tag", "orderPrice=" + orderPrice);
                    if (residueMileage != null && orderPrice != null) {
                        distance_tv.setText(new DecimalFormat("0.00").format(Double.parseDouble(residueMileage) / 1000) + "KM");//剩余公里数
                        if (orderPrice.contains(".")) {
                            String[] tempmoneys = orderPrice.split("\\.");
                            integermoney_tv.setText(new DecimalFormat("0").format(Double.parseDouble(tempmoneys[0])));
                            decimalsmoney_tv.setText("." + new DecimalFormat("0").format(Double.parseDouble(tempmoneys[1])));
                        } else {
                            integermoney_tv.setText(orderPrice);
                            decimalsmoney_tv.setText(".00");
                        }
                    }
                }
            }
        };

        @Override
        public View getInfoContents(Marker arg0) {

            return null;
        }

        @Override
        public View getInfoWindow(Marker arg0) {
            getInfowindowView(arg0);
            return infoWindowCarFare;
        }

        public void getInfowindowView(Marker arg0) {
            distance_tv = (TextView) infoWindowCarFare.findViewById(R.id.distance_tv);
            integermoney_tv = (TextView) infoWindowCarFare.findViewById(R.id.integermoney_tv);
            decimalsmoney_tv = (TextView) infoWindowCarFare.findViewById(R.id.decimalsmoney_tv);
            jijiarule_iv = (ImageView) infoWindowCarFare.findViewById(R.id.jijiarule_iv);
            distance_tv.setText(new DecimalFormat("0.00").format(Double.parseDouble(TotalMileage) / 1000) + "KM");//剩余公里数
            String carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
            int startfare1 = (int) SharePreferenceUtils.getParam(context, Const.startingfare, 0);
            int startfare2 = (int) SharePreferenceUtils.getParam(context, Const.startingfare_moto, 0);
            Log.e("infostartfare", "startfare1=" + startfare1);
            Log.e("infostartfare", "startfare2=" + startfare2);
            Log.e("infostartfare", "carType=" + carType);
            if (carType.equals(Const.CarType_taxi)) {
                orderPrice = String.valueOf(startfare1);
            } else {
                orderPrice = String.valueOf(startfare2);
            }
            if (orderPrice.contains(".")) {
                String[] tempmoneys = orderPrice.split("\\.");
                integermoney_tv.setText(new DecimalFormat("0").format(Double.parseDouble(tempmoneys[0])));
                decimalsmoney_tv.setText("." + new DecimalFormat("0").format(Double.parseDouble(tempmoneys[1])));
            } else {
                integermoney_tv.setText(orderPrice);
                decimalsmoney_tv.setText(".00");
            }
            //获取司机端发送到服务器的价格
            if (order_id != null) {
                getOrderInfoInfowindow(order_id);
            } else {
                ToastUtils.Toast_short1("订单ID为null");
            }
            jijiarule_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FareRuleActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        public void getOrderInfoInfowindow(String order_id) {
            RequestParams params = new RequestParams(Const.getOrderInfo);
            params.addBodyParameter("order_id", order_id);
            LanguageUtil.addLanguage(params);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                LogUtils.Logd(logTag, "getOrderInfo_result=" + result);
                    Gson gson = new Gson();
                    OrderInfoBean orderInfoBean = gson.fromJson(result, OrderInfoBean.class);
                    if (orderInfoBean != null) {
                        if (orderInfoBean.getCode().equals(Const.reques_success)) {
                            List<OrderInfoBean.ResultBean> result_list = orderInfoBean.getResult();
                            OrderInfoBean.ResultBean.OrderBean orderBean = result_list.get(0).getOrder();
                            orderPrice = orderBean.getFdOrderPrice();
                            String cartype = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
                            final String myLocationStr = latitude + "," + longitude;
                            final String endStr = end_latitude + "," + end_longitude;
                            if (cartype.equals(Const.CarType_taxi)) {
                                //获取出租车路线数据（主要是得到距离终点的路程）
                                getResidueLuxianJson(myLocationStr, endStr, Const.Driving);
                            } else {
                                //获取摩托车路线数据
                                getResidueLuxianJson(myLocationStr, endStr, Const.Moto);
                            }
                        }
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });

        }

        public void getResidueLuxianJson(final String startStr, String endStr, String mode) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("https://maps.googleapis.com/maps/api/directions/json?origin=");
            buffer.append(startStr + "&destination=");
            buffer.append(endStr + "&sensor=false&mode=");
            buffer.append(mode);
            Log.e("url", "url=" + buffer.toString());
            RequestParams params = new RequestParams(buffer.toString());
//        RequestParams params = new RequestParams("https://maps.googleapis.com/maps/api/directions/json?origin=22.495562546852,113.9195796929&destination=22.535006648941,113.97465188433&sensor=false&mode=driving");
//        params.addBodyParameter("origin",startStr);
//        params.addBodyParameter("destination",endStr);
//        params.addBodyParameter("sensor","false");
//        params.addBodyParameter("mode",mode);
            x.http().post(params, new Callback.CommonCallback<String>() {

                @Override
                public void onSuccess(String result) {
//                Log.e("tag","result="+result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        if (obj.getString("status").equals("OK")) {
                            Gson gson = new Gson();
                            PolyLinesBean polyLinesBean = gson.fromJson(result, PolyLinesBean.class);
                            double mileage = polyLinesBean.getRoutes().get(0).getLegs().get(0).getDistance().getValue();
                            residueMileage = String.valueOf(mileage);
                            Message message = Message.obtain();
                            message.what = 142;
                            madHandler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_dataexception));
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    public void getOrderInfo(String order_id) {
        RequestParams params = new RequestParams(Const.getOrderInfo);
        params.addBodyParameter("order_id", order_id);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                LogUtils.Logd(logTag, "getOrderInfo_result=" + result);
                Gson gson = new Gson();
                OrderInfoBean orderInfoBean = gson.fromJson(result, OrderInfoBean.class);
                if (orderInfoBean != null) {
                    if (orderInfoBean.getCode().equals(Const.reques_success)) {
                        List<OrderInfoBean.ResultBean> result_list = orderInfoBean.getResult();
                        if (result_list.size() >= 1) {
                            OrderInfoBean.ResultBean resultBean = result_list.get(0);
                            TakerPhoneItemView takerPhoneItemView = new TakerPhoneItemView(context);
                            takerPhoneItemView.setData(resultBean);
                            waitcar_tip.setVisibility(View.GONE);
                            orderreceived_lay.addView(takerPhoneItemView);
                            startPlace.hideInfoWindow();
                            tripStatus = resultBean.getTrip().getFdStatus() + "";//已接单状态
                            SharePreferenceUtils.setParam(context, Const.TripStatus, tripStatus);
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    //取消行程
    private void cancleTrip(String trip_id) {
        RequestParams params = new RequestParams(Const.cancleTrip);
        params.addBodyParameter("trip_id", trip_id);
        params.addBodyParameter("user_id", userId);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    if (obj.getString(Const.Code).equals(Const.reques_success)) {
                        afterCancelTripRefresh();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                alertDialog.dismiss();
            }
        });
    }

    private void afterCancelTripRefresh() {
        SharePreferenceUtils.setParam(context, Const.TripStatus, "0");
        tripStatus = "0";
        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_tripcanceled));
        orderreceived_lay.removeAllViews();
        float_lay.setVisibility(View.VISIBLE);
        sure_callcar_btn.setVisibility(View.VISIBLE);
        waitcar_tip.setVisibility(View.GONE);
        isWaitCar = false;
        addOnlyoneMarker(startPlace_key, new LatLng(start_latitude, start_longitude), R.mipmap.shouye_qidian, 2);
        startPlace.showInfoWindow();
        if (bounds != null) {
            taxi_map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
        EventBus.getDefault().post("已接单或已取消行程");
        mHandler.removeMessages(100);
        mHandler.removeCallbacks(mRunnable);
    }

    //获取时间戳
    public Long getCurrentTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return time;
    }

    //行程被取消666666
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent4(String eventBusStr) {
        if (eventBusStr.equals("行程被取消")) {
            SharePreferenceUtils.setParam(context, Const.TripStatus, "1");//等待接单
            tripStatus = "1";
            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_tripcanceled));
            orderreceived_lay.removeAllViews();

            float_lay.setVisibility(View.GONE);
            EventBus.getDefault().post("等待应答");
            waitcar_tip.setVisibility(View.VISIBLE);
            LatLng sydney = new LatLng(start_latitude, start_longitude);
            taxi_map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
            isWaitCar = true;
            addOnlyoneMarker(startPlace_key, sydney, R.mipmap.shouye_qidian, 2);
            if (!startPlace.isInfoWindowShown()) {
                startPlace.showInfoWindow();
            }
//                        taxi_map.setInfoWindowAdapter(new CustomInfoAdapter());
            //开启倒计时
            Message message = new Message();
            message.what = 100;
            mHandler.sendMessage(message);
        }
    }

    private void getAddress(String latlngStr) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://maps.google.com/maps/api/geocode/json?sensor=false&latlng=");
        buffer.append(latlngStr);
        buffer.append("&language=");
        if (LanguageUtil.isZh()) {
            buffer.append(Const.zh);
        } else {
            buffer.append(Const.en);
        }
        RequestParams params = new RequestParams(buffer.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CountryBean countryBean = gson.fromJson(result, CountryBean.class);
                StringBuffer bu = new StringBuffer();
                if (countryBean != null) {
                    List<CountryBean.ResultsBean.AddressComponentsBean> addess_array = countryBean.getResults().get(0).getAddress_components();
                    if (addess_array != null) {
                        if (addess_array.size() >= 4) {
                            bu.append(addess_array.get(3).getLong_name());
                        }
                        if (addess_array.size() >= 3) {
                            bu.append(addess_array.get(2).getLong_name());
                        }
                        if (addess_array.size() >= 2) {
                            bu.append(addess_array.get(1).getLong_name());
                        }
                        if (addess_array.size() >= 1) {
                            bu.append(addess_array.get(0).getLong_name());
                        }
                        String formatted_address = bu.toString();
                        firstName = formatted_address;
                        if (formatted_address != null && !formatted_address.isEmpty()) {

                            Message msg = Message.obtain();
                            msg.what = 428;
                            mHandler.sendMessage(msg);

                            Message message = Message.obtain();
                            message.what = 57;
                            mHandler.sendMessage(message);
                        }
                    }
                } else {
                    ToastUtils.Toast_short(mActivity, LanguageUtil.getResText(R.string.toast_dataexception));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getCountryName(String latlngStr) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://maps.google.com/maps/api/geocode/json?sensor=false&latlng=");
        buffer.append(latlngStr);
        buffer.append("&language=");
        if (LanguageUtil.isZh()) {
            buffer.append(Const.zh);
        } else {
            buffer.append(Const.en);
        }
        RequestParams params = new RequestParams(buffer.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                CountryBean countryBean = gson.fromJson(result, CountryBean.class);
                if (countryBean.getStatus().equals("OK")) {
                    if (countryBean != null) {
                        String formatted_address = countryBean.getResults().get(0).getFormatted_address();
                        List<CountryBean.ResultsBean.AddressComponentsBean> address_components = countryBean.getResults().get(0).getAddress_components();

                        StringBuffer tempAddress = new StringBuffer();
                        if (address_components.size() >= 4) {
                            tempAddress.append(address_components.get(3).getLong_name());
                        }
                        if (address_components.size() >= 3) {
                            tempAddress.append(address_components.get(2).getLong_name());
                        }
                        if (address_components.size() >= 2) {
                            tempAddress.append(address_components.get(1).getLong_name());
                        }
                        if (address_components.size() >= 1) {
                            tempAddress.append(address_components.get(0).getLong_name());
                        }
                        if (formatted_address.contains("China") || formatted_address.contains("中国")) {
                            isChina = true;
                        } else {
                            isChina = false;
                        }
                        Log.e("tag", "ischina=" + isChina);
                        //地球坐标转换成火星坐标
                        LatlngBean transform = WGSTOGCJ02.transform(mLocation.getLongitude(), mLocation.getLatitude());
                        //重新赋值
                        if (isChina) {
                            latitude = transform.getLat();
                            longitude = transform.getLon();
                        } else {
                            latitude = mLocation.getLatitude();
                            longitude = mLocation.getLongitude();
                        }
                        Log.e("tag", "latitude=" + latitude);
                        Log.e("tag", "longitude=" + longitude);
                        //TODO
                        start_latitude = latitude;
                        start_longitude = longitude;
                        /*做标记,展示简单信息,例如：中国-北京*/
                        firstName = tempAddress.toString();
                        firstShortAddressString = addressString;
                        //TODO
                        LatLng sydney = new LatLng(latitude, longitude);

                        /*//添加覆盖物
                        addOnlyoneMarker(startPlace_key, sydney, R.mipmap.shouye_qidian, 2);

                        //展示提示信息
                        taxi_map.setInfoWindowAdapter(customInfoAdapter);
                        startPlace.showInfoWindow();*/

                        Message msg = Message.obtain();
                        msg.what = 55;
                        mHandler.sendMessage(msg);
                        getAreaCars(Const.CarType_taxi);//获取附近出租车

                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        Log.i("SiteMapFragment", "onDetach");
    }

    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);
        Log.i("SiteMapFragment", "onAttach");
    }

    public static void installApk(Context context, File apk) {
        Uri uri = Uri.fromFile(apk);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
