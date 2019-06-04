package com.sy.globletake_user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.XuniKeyWord;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/8.
 */
public class WebAvtivity extends AppCompatActivity {

    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    @Bind(R.id.root_lay)
    RelativeLayout root_lay;
    @Bind(R.id.titlebar_centertv)
    TextView titlebar_centertv;
    @Bind(R.id.webview)
    WebView webview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.statecolor);
        String url = getIntent().getStringExtra("webUrl");
        String tag = getIntent().getStringExtra("tag");
        if (tag.equals("help")){
            titlebar_centertv.setText(LanguageUtil.getResText(R.string.title_helpcenter));
        }else {
            titlebar_centertv.setText(LanguageUtil.getResText(R.string.title_aboutus));
        }
        webview.loadUrl(url);
        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
