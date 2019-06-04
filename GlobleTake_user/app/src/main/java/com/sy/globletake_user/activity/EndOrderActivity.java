package com.sy.globletake_user.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.OrderInfoBean;
import com.sy.globletake_user.Bean.TripDetailBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.CallPhoneutil;
import com.sy.globletake_user.utils.DialogUtils;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.PopWindowUtil;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.ToastUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sunnetdesign3 on 2017/2/24.
 */
public class EndOrderActivity extends AppCompatActivity {

    @Bind(R.id.next_ordertv)
    TextView next_ordertv;
    @Bind(R.id.endorder_billingrule)
    TextView endorder_billingrule;
    @Bind(R.id.mileage_twotv)
    TextView mileage_twotv;
    @Bind(R.id.mileage_moneytwo)
    TextView mileage_moneytwo;
    @Bind(R.id.mileage_onetv)
    TextView mileage_onetv;
    @Bind(R.id.mileage_moneyone)
    TextView mileage_moneyone;
    @Bind(R.id.order_money)
    TextView order_money;
    @Bind(R.id.taker_endplace)
    TextView taker_endplace;
    @Bind(R.id.taker_startplace)
    TextView taker_startplace;
    @Bind(R.id.credit_numtv)
    TextView driver_score;
    @Bind(R.id.gopj_tv)
    TextView gopj_tv;
    @Bind(R.id.titlebar_shouche)
    TextView titlebar_shouche;
    @Bind(R.id.titlebar_centertv)
    TextView titlebar_centertv;
    @Bind(R.id.taker_startplace2)
    TextView taker_startplace2;
    @Bind(R.id.credit_numtv2)
    TextView credit_numtv2;
    @Bind(R.id.ispj_tv)
    TextView ispj_tv;
    @Bind(R.id.endorder_tip)
    TextView endorder_tip;

    @Bind(R.id.taker_headicon)
    ImageView taker_headicon;
    @Bind(R.id.taker_phone)
    ImageView taker_phone;
    @Bind(R.id.taker_headicon2)
    ImageView taker_headicon2;

    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.licheng_lay)
    LinearLayout licheng_lay;
    @Bind(R.id.time_lay)
    LinearLayout time_lay;
    @Bind(R.id.credit_lay)
    LinearLayout credit_lay;
    @Bind(R.id.farerule_lay)
    LinearLayout farerule_lay;
    @Bind(R.id.star_lay)
    LinearLayout star_lay;
    @Bind(R.id.mytrip_toplay)
    LinearLayout mytrip_toplay;
    @Bind(R.id.credit_lay2)
    LinearLayout credit_lay2;
    @Bind(R.id.star_lay2)
    LinearLayout star_lay2;
    @Bind(R.id.pjstatus_lay)
    LinearLayout pjstatus_lay;
    @Bind(R.id.endorder_backrelay)
    RelativeLayout endorder_backrelay;
    @Bind(R.id.endorder_toprelay)
    RelativeLayout endorder_toprelay;

    private Dialog mPrdialog;

    private Context context;
    private OrderInfoBean orderInfoBean;
    private String order_id, order_status, trip_id, trip_status;
    private String tag;
    private String phoneNumber;
    private boolean isEvaluated;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 37:
                    refreshUi1();
                    break;
                case 38:
                    String evaluate = (String) msg.obj;
                    refreshUi2(evaluate);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endorder);
        ButterKnife.bind(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
        XuniKeyWord.setShiPei(this, root_lay);
        order_id = getIntent().getStringExtra("order_id");
        tag = getIntent().getStringExtra("tag");
        mPrdialog = DialogUtils.initDialog(this);
        if (tag.equals("MyTripActivity")) {
            mytrip_toplay.setVisibility(View.VISIBLE);
            endorder_toprelay.setVisibility(View.GONE);
            endorder_tip.setVisibility(View.GONE);
            gopj_tv.setVisibility(View.GONE);
            titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_mytrip));
            trip_id = getIntent().getStringExtra("trip_id");
        } else {
            mytrip_toplay.setVisibility(View.GONE);
            endorder_toprelay.setVisibility(View.VISIBLE);
            titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_endorder));
            gopj_tv.setVisibility(View.VISIBLE);
            endorder_tip.setVisibility(View.VISIBLE);
        }
        if (order_id != null) {
            Log.e("orderid", order_id);
            getOrderInfo(order_id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mHandler!=null){
            mHandler.removeMessages(37);
            mHandler.removeMessages(38);
            mHandler = null;
        }
    }

    private void getOrderInfo(String order_id) {
        RequestParams params = new RequestParams(Const.getOrderInfo);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter(Const.Language, Const.zh);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                orderInfoBean = gson.fromJson(result, OrderInfoBean.class);
                if (orderInfoBean.getCode().equals(Const.reques_success)) {

                    Message message = new Message();
                    message.what = 37;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {
                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_serverexception));
            }

            @Override
            public void onFinished() {

            }
        });
    }


    @OnClick({R.id.endorder_backrelay, R.id.gopj_tv, R.id.titlebar_shouche, R.id.taker_phone, R.id.farerule_lay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.endorder_backrelay:
                if (tag.equals("MyTripActivity")) {
                    setResult(520);
                } else {
                    setResult(666);
                }
                finish();
                break;
            case R.id.gopj_tv:
                if (isEvaluated){
                    ToastUtils.Toast_short1(LanguageUtil.getResText(R.string.toast_alredypj));
                }else {
                    initPopWindow();
                    PopWindowUtil.openPopWindow_Center(popupWindow, endorder_backrelay);
                }
                break;

            case R.id.titlebar_shouche:
                String shouche_text = titlebar_shouche.getText().toString();
                if (shouche_text.equals("更多") || shouche_text.equals("More")) {
                    onPingJiaYiZhouClick();
                }
                break;

            case R.id.taker_phone:
                CallPhoneutil.callPhone(context, phoneNumber);
                break;
            case R.id.farerule_lay:
                Intent intent = new Intent(context, FareRuleActivity.class);
                startActivity(intent);
                break;
        }
    }

    private View contentView;
    private PopupWindow popupWindow;

    /**
     * 初始化popWindow
     */
    private void initPopWindow() {

        PopWindowUtil.backgroundAlpaha(EndOrderActivity.this, 0.5f);
        //加载弹出框的布局
        contentView = LayoutInflater.from(EndOrderActivity.this).inflate(
                R.layout.endorder_popwindowlay, null);
        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //亮屏
                PopWindowUtil.backgroundAlpaha(EndOrderActivity.this, 1.0f);

            }
        });

        ImageView taker_headicon = (ImageView) contentView.findViewById(R.id.taker_headicon);
        ImageView colse = (ImageView) contentView.findViewById(R.id.popwindow_closeiv);
        TextView taker_startplace = (TextView) contentView.findViewById(R.id.taker_startplace);
        TextView taker_endplace = (TextView) contentView.findViewById(R.id.taker_endplace);
        final TextView credit_numtv = (TextView) contentView.findViewById(R.id.pj_numtv);
        LinearLayout credit_lay = (LinearLayout) contentView.findViewById(R.id.credit_lay);
        final Button submit_pjbtn = (Button) contentView.findViewById(R.id.submit_pjbtn);
        credit_numtv.setText("5" + LanguageUtil.getResText(R.string.unit_minute));
        final ImageView pj_iv1 = (ImageView) contentView.findViewById(R.id.pj_iv1);
        final ImageView pj_iv2 = (ImageView) contentView.findViewById(R.id.pj_iv2);
        final ImageView pj_iv3 = (ImageView) contentView.findViewById(R.id.pj_iv3);
        final ImageView pj_iv4 = (ImageView) contentView.findViewById(R.id.pj_iv4);
        final ImageView pj_iv5 = (ImageView) contentView.findViewById(R.id.pj_iv5);

        pj_iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pj_iv1.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv2.setImageResource(R.mipmap.stargray);
                pj_iv3.setImageResource(R.mipmap.stargray);
                pj_iv4.setImageResource(R.mipmap.stargray);
                pj_iv5.setImageResource(R.mipmap.stargray);
                credit_numtv.setText("1" + LanguageUtil.getResText(R.string.unit_minute));
            }
        });

        pj_iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pj_iv1.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv2.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv3.setImageResource(R.mipmap.stargray);
                pj_iv4.setImageResource(R.mipmap.stargray);
                pj_iv5.setImageResource(R.mipmap.stargray);
                credit_numtv.setText("2" + LanguageUtil.getResText(R.string.unit_minute));
            }
        });
        pj_iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pj_iv1.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv2.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv3.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv4.setImageResource(R.mipmap.stargray);
                pj_iv5.setImageResource(R.mipmap.stargray);
                credit_numtv.setText("3" + LanguageUtil.getResText(R.string.unit_minute));
            }
        });
        pj_iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pj_iv1.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv2.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv3.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv4.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv5.setImageResource(R.mipmap.stargray);
                credit_numtv.setText("4" + LanguageUtil.getResText(R.string.unit_minute));
            }
        });
        pj_iv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pj_iv1.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv2.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv3.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv4.setImageResource(R.mipmap.wodexingcheng_start);
                pj_iv5.setImageResource(R.mipmap.wodexingcheng_start);
                credit_numtv.setText("5" + LanguageUtil.getResText(R.string.unit_minute));
            }
        });
        if (orderInfoBean != null) {
            OrderInfoBean.ResultBean.TripBean tripBean = orderInfoBean.getResult().get(0).getTrip();
            OrderInfoBean.ResultBean.DriverBean driverBean = orderInfoBean.getResult().get(0).getDriver();
            OrderInfoBean.ResultBean.VehicleBean vehicleBean = orderInfoBean.getResult().get(0).getVehicle();
            ImageOptions options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                    .setCircular(true)
                    .setUseMemCache(true)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                    .build();
            if (tripBean.getPassenger_fdIconUrl() != null) {
                x.image().bind(taker_headicon, Const.root_url + driverBean.getFdIconUrl(), options);
            }

            if (driverBean != null) {
                if (LanguageUtil.isZh()) {
                    taker_startplace.setText(driverBean.getFdUserName().substring(0, 1) + LanguageUtil.getResText(R.string.tv_mdriver) + vehicleBean.getFdVehicleNumber() + ")");
                } else {
                    taker_startplace.setText(LanguageUtil.getResText(R.string.tv_mdriver) + driverBean.getFdUserName().substring(0, 1) + "(" + vehicleBean.getFdVehicleNumber() + ")");

                }
            }

            if (vehicleBean != null) {
                taker_endplace.setText(vehicleBean.getFdVehiclecolor() + "   " + vehicleBean.getFdVehicleName());
            }

        }

        //提交评价
        submit_pjbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalStr = credit_numtv.getText().toString();
                String pjStr = "";
                if (order_id != null) {
                    if (goalStr.isEmpty()) {
                        ToastUtils.Toast_short1("请给司机");
                        pjStr = "5";
                    } else {
                        if (goalStr.contains(".")) {
                            pjStr = goalStr.substring(0, 2);
                        } else {
                            pjStr = goalStr.substring(0, 1);
                        }
                    }
                    Log.e("tag", "pjStr=" + pjStr);
                    if (!mPrdialog.isShowing()){
                        mPrdialog.show();
                    }
                    submitevaluate(pjStr);
                } else {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_noorder));
                }
            }
        });

        colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopwindow();
            }
        });
    }


    /* *//*该方法在popwindow中无效，因为它依赖于activity*//*

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获得触摸的坐标
        float x = event.getX();
        float y = event.getY();
        Log.e("tag","touchX="+x);
        Log.e("tag","touchY="+y);
        return super.onTouchEvent(event);
    }
*/
    private void closePopwindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void submitevaluate(final String evaluate) {
        RequestParams params = new RequestParams(Const.submitevaluate);
        params.addBodyParameter("order_id", order_id);
        params.addBodyParameter("content", evaluate);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)) {

                        Message message = Message.obtain();
                        message.what = 38;
                        message.obj = evaluate;
                        mHandler.sendMessage(message);
                        isEvaluated = true;
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
                if (mPrdialog.isShowing()){
                    mPrdialog.dismiss();
                }
            }
        });
    }


    private boolean isExpand;
    private Dialog dialog;

    private void onPingJiaYiZhouClick() {
        if (!isExpand) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.cancelorder_poplay);
            final LinearLayout cancel_order = (LinearLayout) dialog.findViewById(R.id.cancel_order);
            LinearLayout contact_service = (LinearLayout) dialog.findViewById(R.id.contact_service);
            //根据行程状态做相关处理
            cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //联系司机
                    CallPhoneutil.callPhone(context, phoneNumber);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });

            //联系客服
            contact_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = (String) SharePreferenceUtils.getParam(context, Const.waiterPhone, "");
                    CallPhoneutil.callPhone(context, phoneNumber);
                    if (dialog.isShowing()) {
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
            titlebar_shouche.getLocationOnScreen(location);
            Rect out = new Rect();
            titlebar_shouche.getWindowVisibleDisplayFrame(out);
            lp.x = location[0];
            lp.y = location[1] - out.top + titlebar_shouche.getHeight();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (tag.equals("MyTripActivity")) {
                setResult(520);
            } else {
                setResult(666);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void refreshUi1() {
        final OrderInfoBean.ResultBean.TripBean tripBean = orderInfoBean.getResult().get(0).getTrip();
        final OrderInfoBean.ResultBean.DriverBean driverBean = orderInfoBean.getResult().get(0).getDriver();
        final OrderInfoBean.ResultBean.VehicleBean vehicleBean = orderInfoBean.getResult().get(0).getVehicle();
        final OrderInfoBean.ResultBean.OrderBean orderBean = orderInfoBean.getResult().get(0).getOrder();

        if (tripBean != null) {
            ImageOptions options = new ImageOptions.Builder().setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                    .setCircular(true)
                    .setUseMemCache(true)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .build();

            if (tripBean.getPassenger_fdIconUrl() != null) {
                if (tag.equals("MyTripActivity")) {
                    x.image().bind(taker_headicon2, Const.root_url + driverBean.getFdIconUrl(), options);
                } else {

                    x.image().bind(taker_headicon, Const.root_url + driverBean.getFdIconUrl(), options);
                }
            }

            if (driverBean != null) {
                if (tag.equals("MyTripActivity")) {
                    if (LanguageUtil.isZh()) {
                        taker_startplace2.setText(driverBean.getFdUserName().substring(0, 1) + LanguageUtil.getResText(R.string.tv_mdriver) + vehicleBean.getFdVehicleNumber() + "）");
                    } else {
                        taker_startplace2.setText(LanguageUtil.getResText(R.string.tv_mdriver) + driverBean.getFdUserName().substring(0, 1) + " (" + vehicleBean.getFdVehicleNumber() + ")");
                    }
                } else {
                    if (LanguageUtil.isZh()) {
                        taker_startplace.setText(driverBean.getFdUserName().substring(0, 1) + LanguageUtil.getResText(R.string.tv_mdriver) + vehicleBean.getFdVehicleNumber() + "）");
                    } else {
                        taker_startplace.setText(LanguageUtil.getResText(R.string.tv_mdriver) + driverBean.getFdUserName().substring(0, 1) + " (" + vehicleBean.getFdVehicleNumber() + ")");
                    }
                    if (vehicleBean != null) {
                        taker_endplace.setText(vehicleBean.getFdVehiclecolor() + "   " + vehicleBean.getFdVehicleName());
                    }
                    phoneNumber = driverBean.getFdPhone();

                    //设置信用值
                    double fdDriverAvgSocre = driverBean.getFdDriverAvgSocre();
                    double zhengshu = 0;
                    double xiaoshu = 0;
                    star_lay.removeAllViews();
                    if (fdDriverAvgSocre + "" != null) {
                        if (String.valueOf(fdDriverAvgSocre).contains(".")) {
                            String[] strs = String.valueOf(fdDriverAvgSocre).split("\\.");
                            zhengshu = Double.parseDouble(strs[0]);
                            xiaoshu = Double.parseDouble(strs[1]);
                        } else {
                            zhengshu = fdDriverAvgSocre;
                        }
                        if (zhengshu!=0){
                            for (int i = 1; i <= zhengshu; i++) {
                                ImageView imgv = new ImageView(context);
                                imgv.setImageResource(R.mipmap.yonghuduanyijiedan_strar);
                                star_lay.addView(imgv);
                            }
                            if (String.valueOf(fdDriverAvgSocre).contains(".") && xiaoshu != 0) {
                                ImageView imgv = new ImageView(context);
                                imgv.setImageResource(R.mipmap.yonghuduanyijiedan_halfstrar);
                                star_lay.addView(imgv);
                            }
                        }else {
                            for (int i = 1; i <= 5; i++) {
                                ImageView imgv = new ImageView(context);
                                imgv.setImageResource(R.mipmap.stargray);
                                star_lay.addView(imgv);
                            }
                        }
                        driver_score.setText(fdDriverAvgSocre + LanguageUtil.getResText(R.string.unit_minute));
                    }
                }
            }

            String fdIs_driverscore = orderBean.getFdIs_driverscore();
            if (fdIs_driverscore.equals("0")) {//该行程未评价过
                gopj_tv.setVisibility(View.VISIBLE);
                pjstatus_lay.setVisibility(View.VISIBLE);
                ispj_tv.setText(LanguageUtil.getResText(R.string.tv_nopj));
            } else {
                pjstatus_lay.setVisibility(View.GONE);
                gopj_tv.setVisibility(View.GONE);
            }
            double totaljJourney = Double.parseDouble(tripBean.getFdTripMileage());
            Log.e("price", "totaljJourney=" + totaljJourney);
            long fdTripStartTime = tripBean.getFdTripStartTime();
            long fdTripEndTime = tripBean.getFdTripEndTime();
            String carType = (String) SharePreferenceUtils.getParam(context, Const.CarType, "-1");
            double journeyPrice = 0;
            double totalMoney = 0;
            int startPrice = 0;
            int moneybymeter = 0;
            int journey = 0;
            int meter = 0;
            //参数说明详见登录页面
            long totalTime = (fdTripEndTime - fdTripStartTime) / 60000;//单位：分钟
            Log.e("price", "totalTime=" + totalTime);
            int minPrice = (int) SharePreferenceUtils.getParam(context, Const.moneybyminute, 0);
            long timePrice = totalTime * minPrice;

            if (carType.equals(Const.CarType_taxi)) {
                startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare, -1);
                moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter, -1);
                journey = (int) SharePreferenceUtils.getParam(context, Const.journey, -1);
                meter = (int) SharePreferenceUtils.getParam(context, Const.meter, -1);
            } else {
                startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare_moto, -1);
                moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter_moto, -1);
                journey = (int) SharePreferenceUtils.getParam(context, Const.journey_moto, -1);
                meter = (int) SharePreferenceUtils.getParam(context, Const.meter_moto, -1);
            }

            if (totaljJourney <= journey) {//路程小于等于起步价的里程
                journeyPrice = startPrice;
            } else {
                journeyPrice = startPrice + (totaljJourney - journey) * (moneybymeter / meter);
            }
            totalMoney = Double.parseDouble(orderBean.getFdOrderPrice());
            order_money.setText(new DecimalFormat("0.00").format(totalMoney));

            Log.e("tag", "carType=" + carType);
            if (carType.equals(Const.CarType_taxi)) {
                licheng_lay.setVisibility(View.VISIBLE);
                time_lay.setVisibility(View.VISIBLE);
                mileage_onetv.setText(LanguageUtil.getResText(R.string.tv_journeymoney) + new DecimalFormat("0.00").format(totaljJourney / 1000) + LanguageUtil.getResText(R.string.unit_km));
                mileage_twotv.setText(LanguageUtil.getResText(R.string.tv_timemoney) + new DecimalFormat("0").format(totalTime) + LanguageUtil.getResText(R.string.unit_min));
                mileage_moneyone.setText(new DecimalFormat("0.00").format(journeyPrice) + LanguageUtil.getResText(R.string.unit_money));
                mileage_moneytwo.setText(new DecimalFormat("0.00").format(timePrice) + LanguageUtil.getResText(R.string.unit_money));
            } else {
                licheng_lay.setVisibility(View.GONE);
                time_lay.setVisibility(View.GONE);
            }
        }
    }

    private void refreshUi2(String evaluate) {
        closePopwindow();
        if (!tag.equals("MyTripActivity")) {
            setResult(666);
            finish();
        } else {
            closePopwindow();
            ispj_tv.setText(LanguageUtil.getResText(R.string.tv_alreadypj));
            credit_lay2.setVisibility(View.VISIBLE);
            //设置信用值
            double fdDriverAvgSocre = Double.parseDouble(evaluate);
            double zhengshu = 0;
            double xiaoshu = 0;
            star_lay2.removeAllViews();
            if (fdDriverAvgSocre + "" != null) {
                if (String.valueOf(fdDriverAvgSocre).contains(".")) {
                    String[] strs = String.valueOf(fdDriverAvgSocre).split("\\.");
                    zhengshu = Double.parseDouble(strs[0]);
                    xiaoshu = Double.parseDouble(strs[1]);
                } else {
                    zhengshu = fdDriverAvgSocre;
                }
                for (int i = 1; i <= zhengshu; i++) {
                    ImageView imgv = new ImageView(context);
                    imgv.setImageResource(R.mipmap.yonghuduanyijiedan_strar);
                    star_lay2.addView(imgv);
                }
                if (String.valueOf(fdDriverAvgSocre).contains(".") && xiaoshu != 0) {
                    ImageView imgv = new ImageView(context);
                    imgv.setImageResource(R.mipmap.yonghuduanyijiedan_halfstrar);
                    star_lay2.addView(imgv);
                }
                credit_numtv2.setText(fdDriverAvgSocre + LanguageUtil.getResText(R.string.unit_minute));
            }
        }
    }
}
