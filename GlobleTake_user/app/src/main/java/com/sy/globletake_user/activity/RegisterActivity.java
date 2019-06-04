package com.sy.globletake_user.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.CheckForAllUtils;
import com.sy.globletake_user.utils.DialogUtils;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.LogUtils;
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
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by sunnetdesign3 on 2017/2/14.
 */
public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.register_back)
    RelativeLayout register_back;
    @Bind(R.id.register_root)
    LinearLayout register_root;

    @Bind(R.id.register_nameedt)
    EditText register_nameedt;
    @Bind(R.id.register_phoneedt)
    EditText register_phoneedt;
    @Bind(R.id.changpwd_yzmedt)
    EditText changpwd_yzmedt;
    @Bind(R.id.register_emailedt)
    EditText register_emailedt;
    @Bind(R.id.register_pwd)
    EditText register_pwd;
    @Bind(R.id.register_pwdagain)
    EditText register_pwdagain;
    @Bind(R.id.queren_btn)
    Button queren_btn;
    @Bind(R.id.changpwd_yanzm)
    TextView yanzm_tv;
    @Bind(R.id.contry_tv)
    TextView contry_tv;

    private Context context;
    private View stateView;

    private Dialog dialog;

    private TimeCount time;
    protected static final int START_COUNT = 2;

    private String codeStr;

    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case START_COUNT:
                    time = new TimeCount(60000, 1000); // 构造CountDownTimer对象
                    time.start();
                    break;

                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        onlistener();
    }

    private void onlistener() {
        queren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_num = register_phoneedt.getText().toString();
                if (phone_num.isEmpty()){
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_inputphone));
                }else if (codeStr==null){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_beforgetyzm));
                }else if (!changpwd_yzmedt.getText().toString().equals(codeStr)){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_erroyzm));
                }else if (register_pwd.getText().toString().isEmpty()||register_pwdagain.getText().toString().isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.hint_inputpwd));
                }else if (!register_pwd.getText().toString().equals(register_pwdagain.getText().toString())){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_pwddifferent));
                }else {
                    if (!dialog.isShowing()){
                        dialog.show();
                    }
                    register(register_pwdagain.getText().toString());
                }
            }
        });
        yanzm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_num = register_phoneedt.getText().toString();
                if (phone_num.isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_inputphone));
                }else {//验证手机号码是否可用
//                    sendCode(phone_num);
                    SMSSDK.getVerificationCode(contry_tv.getText().toString(), phone_num, new OnSendMessageHandler() {
                        @Override
                        public boolean onSendMessage(String s, String s1) {
                            ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_sendsucess));
                            return false;
                        }
                    });
                    //计时开始
                    Message msg = Message.obtain();
                    msg.what = START_COUNT;
                    handler.sendMessage(msg);
                } /*else {
                    //提示手机号码错误
                    register_phoneedt.setError(LanguageUtil.getResText(R.string.toast_inputrightphone));
                }*/
            }
        });
        contry_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,CountryActivity.class);
                startActivityForResult(intent,310);
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,register_root);
        context = this;
        stateView = XuniKeyWord.initStateView(this);
        stateView.setBackgroundColor(getResources().getColor(R.color.diwen));
        dialog = DialogUtils.initDialog(context);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void register(String pwd) {
        RequestParams params = new RequestParams(Const.user_register);
        params.addBodyParameter("phone", register_phoneedt.getText().toString().trim());
        params.addBodyParameter("password", CheckForAllUtils.getMD5(pwd));
        params.addBodyParameter("userName", register_nameedt.getText().toString());//用户名
        params.addBodyParameter("pushCode", JPushInterface.getRegistrationID(this));//极光推送的token
        params.addBodyParameter("pushId", "1");//平台识别码，1：Android，0：ios
        params.addBodyParameter("email",register_emailedt.getText().toString());
        params.addBodyParameter("language", "zh");//语言版本；zh:中文，en:English

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject root = new JSONObject(result);
                    if (root.getString(Const.Code).equals(Const.reques_success)) {
                        Gson gs = new Gson();
                        ToastUtils.Toast_short(context, root.getString("message"));
//                        LoginBean loginBean = gs.fromJson(result,LoginBean.class);
                        Intent intent = new Intent(context,MainActivity.class);
                        startActivity(intent);
                        finish();
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

                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }
    /**
     * 发送验证码计时器
     *
     * @author wdp
     *
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发

            yanzm_tv.setText(LanguageUtil.getResText(R.string.tv_sendagain));
            yanzm_tv.setClickable(true);
            yanzm_tv.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            yanzm_tv.setClickable(false);
            yanzm_tv.setEnabled(false);
            yanzm_tv.setText(millisUntilFinished / 1000 + LanguageUtil.getResText(R.string.tv_second));
        }
    }
    private void sendCode(String phone_num){
        RequestParams params = new RequestParams(Const.getCode);
        params.addBodyParameter("phone",phone_num);
        params.addBodyParameter("type","1");
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject root = new JSONObject(result);
                    if (root.getString(Const.Code).equals(Const.reques_success)){
                        String result1 = root.getString("result");
                        if (result1!=null){
                            ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_sendsucess));
                            ToastUtils.Toast_short(context, result1);
                            LogUtils.Loge("code","code="+ result1);
                            codeStr = result1;
                        }

                    }else {
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_getyzmfail));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_getyzmfail));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==310&&resultCode==310&&data!=null){
            String[] countries = data.getStringArrayExtra("country");
            if (countries[1]!=null){
                contry_tv.setText("+"+countries[1]);
            }
        }
    }
}
