package com.sy.globletake_user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.ToastUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/8.
 */
public class TousuActivity extends AppCompatActivity {
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.send_msgtv)
    TextView send_msgtv;
    @Bind(R.id.tousu_contentedt)
    EditText tousu_contentedt;

    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tousu);
        ButterKnife.bind(this);
        context = this;

        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.color666);
        final String userId = (String) SharePreferenceUtils.getParam(this, Const.UserId,"");

        send_msgtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = tousu_contentedt.getText().toString().trim();
                if (content.isEmpty()){
                    ToastUtils.Toast_short(TousuActivity.this, LanguageUtil.getResText(R.string.toast_tousucontent));
                }else {
                    sendTousuMsg(userId,content);
                }
            }
        });

        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void sendTousuMsg(String user_id,String msg){
        RequestParams params = new RequestParams(Const.tousu);
        params.addBodyParameter("user_id",user_id);
        params.addBodyParameter("content",msg);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)){
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_toususuccess));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_serverexception));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
