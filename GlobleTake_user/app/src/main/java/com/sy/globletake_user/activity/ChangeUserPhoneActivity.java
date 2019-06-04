package com.sy.globletake_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
 * Created by sunnetdesign3 on 2017/3/8.
 */
public class ChangeUserPhoneActivity extends AppCompatActivity {

    @Bind(R.id.currentphone_tv)
    TextView currentphone_tv;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    @Bind(R.id.changephone_btn)
    Button changephone_btn;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changephone);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.color666);
        String phoneNumber = (String) SharePreferenceUtils.getParam(this, Const.USER_PHONE, "");
        currentphone_tv.setText(LanguageUtil.getResText(R.string.content_currentphone) + phoneNumber);
        changephone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeUserPhoneActivity.this,ChangeUserPhoneSecond.class);
                startActivity(intent);
                finish();
            }
        });
        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
