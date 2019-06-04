package com.sy.globletake_user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.PriceRuleBean;
import com.sy.globletake_user.Bean.UserBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.CheckForAllUtils;
import com.sy.globletake_user.utils.DialogUtils;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.LogUtils;
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
import cn.jpush.android.api.JPushInterface;

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.phone_edt)
    EditText phone_edt;
    @Bind(R.id.pwd_edt)
    EditText pwd_edt;
    @Bind(R.id.login_btn)
    Button login_btn;
    @Bind(R.id.forgetpwd_tv)
    TextView forgetpwd_tv;
    @Bind(R.id.register_tv)
    TextView register_tv;
    @Bind(R.id.login_rootlay)
    LinearLayout login_rootlay;

    private Context context;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,login_rootlay);
        context = this;
        dialog = DialogUtils.initDialog(this);
        setdate();

        setListener();
    }

    private void setListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (phone_edt.getText().toString().isEmpty()) {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.hint_inputphone));
                } else if (pwd_edt.getText().toString().isEmpty()) {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.hint_inputpwd));
                } else {

                    String MD5password = CheckForAllUtils.getMD5(pwd_edt.getText().toString());
                    String phonenum = phone_edt.getText().toString();
                    /*
                    *
                    *缓存用户信息
                    * */
                    SharePreferenceUtils.setParam(context, Const.USER_PHONE,phonenum);
                    SharePreferenceUtils.setParam(context,Const.USER_PWD,pwd_edt.getText().toString());
                    LogUtils.Loge("login", phonenum);
                    LogUtils.Loge("login", MD5password);
                    login(MD5password);
                }
            }
        });

        forgetpwd_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_num = phone_edt.getText().toString();
                Intent intent = new Intent(context,ChangPasswordActivity.class);
                if (!phone_num.isEmpty()){
                    intent.putExtra("phone_num",phone_num);
                }else {
                    intent.putExtra("phone_num","");
                }
                startActivity(intent);
            }
        });
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //初始化
    private void setdate(){
        String phone = (String) SharePreferenceUtils.getParam(context,Const.USER_PHONE,"");
        String pwd = (String) SharePreferenceUtils.getParam(context,Const.USER_PWD,"");

        if (!phone.isEmpty()){
            phone_edt.setText(phone);
        }
        if (!pwd.isEmpty()){
            pwd_edt.setText(pwd);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void login(String pwd) {
        if (!dialog.isShowing()){
            dialog.show();
        }
        RequestParams params = new RequestParams(Const.user_login);
        params.addBodyParameter("phone", phone_edt.getText().toString().trim());
//        params.addBodyParameter("password", pwd.trim());
        params.addBodyParameter("password", "e10adc3949ba59abbe56e057f20f883e");
        params.addBodyParameter("pushCode", JPushInterface.getRegistrationID(this));//极光推送的token
        params.addBodyParameter("pushId", "1");//平台识别码，1：Android，0：ios
        params.addBodyParameter("type", "1");//用户类型；1：乘客，2：司机
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject root = new JSONObject(result);
                    if (root.getString(Const.Code).equals(Const.reques_success)) {
                        Gson gs = new Gson();
                        ToastUtils.Toast_short(context, root.getString("message"));
                        UserBean userBean = gs.fromJson(result,UserBean.class);
                        UserBean.ResultBean resultBean = userBean.getResult();
                        if (resultBean!=null){
                            if (resultBean.getFdId()!=null){
                                SharePreferenceUtils.setParam(context,Const.UserId,resultBean.getFdId());
                            }
                            if (resultBean.getFdIconUrl()!=null){
                                SharePreferenceUtils.setParam(context,Const.User_HeadIcon,resultBean.getFdIconUrl());
                            }
                            if (resultBean.getFdUserName()!=null){
                                savaUserInfo(Const.User_Name,resultBean.getFdUserName());
                            }else {
                                savaUserInfo(Const.User_Name,"");
                            }
                            if (resultBean.getFdNickName()!=null){
                                savaUserInfo(Const.User_NickName,resultBean.getFdNickName());
                            }else {
                                savaUserInfo(Const.User_NickName,"");
                            }
                            savaUserInfo(Const.User_Stauts,resultBean.getFdStauts());
                            savaUserInfo(Const.fdPassengerCode,resultBean.getFdPassengerCode());
                            if (resultBean.getFdPassengerSex()!=null){
                                savaUserInfo(Const.fdPassengerSex,resultBean.getFdPassengerSex());
                            }else {
                                savaUserInfo(Const.fdPassengerSex,"");
                            }
                            savaUserInfo(Const.fdPassowrd,resultBean.getFdPassowrd());
                            if (resultBean.getFdPhone()!=null){
                                savaUserInfo(Const.USER_PHONE,resultBean.getFdPhone());
                            }else {
                                savaUserInfo(Const.USER_PHONE,"");
                            }
                            if (resultBean.getFdEmail()!=null){

                                savaUserInfo(Const.User_Email,resultBean.getFdEmail());
                            }else {
                                savaUserInfo(Const.User_Email,"");
                            }
                            savaUserInfo(Const.isLogin,true);
                            getPriceRule(Const.taxiPriceRule);
                            getKefuPhone(Const.getWaiterPhonenumber);
                            //获取客服电话
//                            getPhoneNumber();
                        }
                    } else {
                        ToastUtils.Toast_short(context, root.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_serverexception));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void savaUserInfo(String key,Object info){
        if (info!=null){
            SharePreferenceUtils.setParam(context,key,info);
        }
    }

    private void getPriceRule(String url){
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PriceRuleBean ruleBean = gson.fromJson(result,PriceRuleBean.class);
                if (ruleBean!=null){
                    if (ruleBean.getCode().equals(Const.reques_success)){
                        PriceRuleBean.ResultBean priceBean = ruleBean.getResult();

                        savaPriceRule(priceBean.getJourney(),Const.journey);
                        savaPriceRule(priceBean.getMin(),Const.meter);
                        savaPriceRule(priceBean.getMoneybymin(),Const.moneybymeter);
                        savaPriceRule(priceBean.getMoneybytime(),Const.moneybyminute);
                        int startingfare = priceBean.getStartingfare();
                        Log.e("startfare","startingfare="+startingfare);
                        savaPriceRule(startingfare,Const.startingfare);

//                        Intent intent = new Intent(context,MainActivity.class);
//                        startActivity(intent);
                        setResult(369);
                        finish();
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

    private void getKefuPhone(String url){
        RequestParams params = new RequestParams(url);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)){
                        String phoneNumber = object.getString("result");
                        savaUserInfo(Const.waiterPhone,phoneNumber);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
