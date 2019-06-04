package com.sy.globletake_user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/13.
 */
public class FareRuleActivity extends AppCompatActivity {
    @Bind(R.id.qibujia_tv)
    TextView qibujia_tv;
    @Bind(R.id.meterprice_tv)
    TextView meterprice_tv;
    @Bind(R.id.minuteprice_tv)
    TextView minuteprice_tv;
    @Bind(R.id.jia_tv)
    TextView jia_tv;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jijiaguize);
        ButterKnife.bind(this);
        context = this;
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.statecolor);
        int startPrice = 0;
        int moneybymeter = 0;
        int journey = 0;
        int meter = 0;
        int moneybyminute = 0;
       String carType = (String) SharePreferenceUtils.getParam(context,Const.CarType,"-1");
        if (carType.equals(Const.CarType_taxi)){
            startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare, -1);
            moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter, -1);
            journey = (int) SharePreferenceUtils.getParam(context, Const.journey, -1);
            meter = (int) SharePreferenceUtils.getParam(context, Const.meter, -1);
            moneybyminute = (int) SharePreferenceUtils.getParam(context, Const.moneybyminute, -1);
            minuteprice_tv.setVisibility(View.VISIBLE);
            jia_tv.setVisibility(View.VISIBLE);
        }else {
            startPrice = (int) SharePreferenceUtils.getParam(context, Const.startingfare_moto, -1);
            moneybymeter = (int) SharePreferenceUtils.getParam(context, Const.moneybymeter_moto, -1);
            journey = (int) SharePreferenceUtils.getParam(context, Const.journey_moto, -1);
            meter = (int) SharePreferenceUtils.getParam(context, Const.meter_moto, -1);
            minuteprice_tv.setVisibility(View.GONE);
            jia_tv.setVisibility(View.GONE);
        }
        qibujia_tv.setText(startPrice+ LanguageUtil.getResText(R.string.unit_money)+"/"+journey+LanguageUtil.getResText(R.string.mqibu));
        meterprice_tv.setText(moneybymeter+LanguageUtil.getResText(R.string.unit_money)+"/"+meter+LanguageUtil.getResText(R.string.unit_meter));
        minuteprice_tv.setText(moneybyminute+LanguageUtil.getResText(R.string.unit_money)+"/"+LanguageUtil.getResText(R.string.unit_minutes));

        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
