package com.sy.globletake_user.customview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sy.globletake_user.Bean.OrderInfoBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.BitmapUtil;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.ToastUtils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by sunnetdesign3 on 2017/2/22.
 */
public class TakerPhoneItemView extends LinearLayout {

    private ImageView taker_headicon, taker_phone;
    private TextView taker_startplace, taker_endplace, willcome_time, driver_score;
    private LinearLayout credit_lay,star_lay;
    private String phoneNumber;
    private Context context;
    public TakerPhoneItemView(Context context) {
        super(context);
        initView(context);
        setListener(context);
    }


    public TakerPhoneItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        setListener(context);
    }

    private void initView(Context context) {

        inflate(context, R.layout.smallitem_peoplelay, this);
        this.context = context;
        taker_headicon = (ImageView) findViewById(R.id.taker_headicon);
        taker_phone = (ImageView) findViewById(R.id.taker_phone);
        taker_startplace = (TextView) findViewById(R.id.taker_startplace);
        taker_endplace = (TextView) findViewById(R.id.taker_endplace);
        willcome_time = (TextView) findViewById(R.id.willcome_time);
        driver_score = (TextView) findViewById(R.id.driver_score);
        credit_lay = (LinearLayout) findViewById(R.id.credit_lay);
        star_lay = (LinearLayout) findViewById(R.id.star_lay);
    }

    public void setData(OrderInfoBean.ResultBean resultBean) {

        OrderInfoBean.ResultBean.DriverBean driverBean = resultBean.getDriver();
        OrderInfoBean.ResultBean.OrderBean orderBean = resultBean.getOrder();
        OrderInfoBean.ResultBean.TripBean tripBean = resultBean.getTrip();
        OrderInfoBean.ResultBean.VehicleBean vehicleBean = resultBean.getVehicle();
        x.image().bind(taker_headicon, Const.root_url + driverBean.getFdIconUrl(), BitmapUtil.getCircleBitmapOption());
        String fdUserName = driverBean.getFdUserName();
        //柬埔寨的用户姓名规则尚不明了，要截取姓氏
        if (fdUserName != null) {
            if (LanguageUtil.isZh()){
                taker_startplace.setText(fdUserName.substring(0, 1) + LanguageUtil.getResText(R.string.tv_mdriver) + vehicleBean.getFdVehicleNumber()+")");
            }else {
                taker_startplace.setText( LanguageUtil.getResText(R.string.tv_mdriver)+fdUserName.substring(0, 1)+"(" + vehicleBean.getFdVehicleNumber()+")");
            }
        } else {
            if (LanguageUtil.isZh()){
                taker_startplace.setText("The driver" + vehicleBean.getFdVehicleNumber() + "）");
            }else {
                taker_startplace.setText("师傅" + vehicleBean.getFdVehicleNumber() + "）");
            }
            ToastUtils.Toast_short1(LanguageUtil.getResText(R.string.tv_noname));
        }
        taker_endplace.setText(vehicleBean.getFdVehiclecolor() + "   " + vehicleBean.getFdVehicleName());
        phoneNumber = driverBean.getFdPhone();
        double fdDriverAvgSocre = driverBean.getFdDriverAvgSocre();
        double zhengshu = 0;
        double xiaoshu = 0;
        star_lay.removeAllViews();
        if (fdDriverAvgSocre + "" != null) {
            if (String.valueOf(fdDriverAvgSocre).contains(".")){
                String[] strs = String.valueOf(fdDriverAvgSocre).split("\\.");
                zhengshu = Double.parseDouble(strs[0]);
                xiaoshu = Double.parseDouble(strs[1]);
            }else {
                zhengshu = fdDriverAvgSocre;
            }
            if (zhengshu!=0){
                for (int i = 1; i <= zhengshu ; i++) {
                    ImageView imgv = new ImageView(context);
                    imgv.setImageResource(R.mipmap.yonghuduanyijiedan_strar);
                    star_lay.addView(imgv);
                }
                if (String.valueOf(fdDriverAvgSocre).contains(".")&&xiaoshu!=0){
                    ImageView imgv = new ImageView(context);
                    imgv.setImageResource(R.mipmap.yonghuduanyijiedan_halfstrar);
                    star_lay.addView(imgv);
                }
            }else {
                for (int i = 1; i <=5 ; i++) {
                    ImageView imgv = new ImageView(context);
                    imgv.setImageResource(R.mipmap.stargray);
                    star_lay.addView(imgv);
                }
            }
            driver_score.setText(fdDriverAvgSocre + LanguageUtil.getResText(R.string.unit_minute));
        }
    }

    private void setListener(final Context context) {
        taker_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phoneNumber != null) {
                    Intent intent = new Intent(
                            Intent.ACTION_CALL, Uri
                            .parse("tel:" + phoneNumber));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                } else {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.tv_nophone));
                }
            }
        });
    }
}
