package com.sy.globletake_user.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

/**
 * Created by sunnetdesign3 on 2017/2/10.
 */
public class ChangPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.changpwd_rootlay)
    LinearLayout changpwd_rootlay;
    @Bind(R.id.changpwd_yanzm)
    TextView yanzm_tv;
    @Bind(R.id.changpwd_phoneedt)
    EditText changpwd_phoneedt;
    @Bind(R.id.changpwd_pwdedt)
    EditText changpwd_pwdedt;
    @Bind(R.id.pwd_one)
    EditText pwd_one;
    @Bind(R.id.pwd_again)
    EditText pwd_again;
    @Bind(R.id.queren_btn)
    Button queren_btn;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;

    private View stateView;
    private Context context;
    private Dialog dialog;

    private TimeCount time;
    protected static final int START_COUNT = 2;

    private String codeStr;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
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
        setContentView(R.layout.activity_changpwd);
        ButterKnife.bind(this);
        context = this;
        XuniKeyWord.setShiPei(this,changpwd_rootlay);

        stateView = XuniKeyWord.initStateView(this);
        stateView.setBackgroundResource(R.color.diwen);

        String user_phone = getIntent().getStringExtra("phone_num");
        if (user_phone!=null&&!user_phone.isEmpty()){
            changpwd_phoneedt.setText(user_phone);
        }
        yanzm_tv.setOnClickListener(this);
        queren_btn.setOnClickListener(this);
        dialog = DialogUtils.initDialog(this);
        back_relay.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    //重置密码的请求
    private void releasePWD(String mobileNum,String pwd){
        RequestParams params = new RequestParams(Const.resetPWD);
        params.addBodyParameter("phone",mobileNum);
        params.addBodyParameter("password", CheckForAllUtils.getMD5(pwd));//加密过的密码
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject root = new JSONObject(result);
                    if (root.getString(Const.Code).equals(Const.reques_success)){
                        ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_pwdresetsucess));
                        SystemClock.sleep(1000);
//                        setResult();
                        finish();
                    }else {
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_pwdresetfail));
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
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        String phone_num = changpwd_phoneedt.getText().toString();
        switch (view.getId()){
            case  R.id.changpwd_yanzm://获取验证码
                if (phone_num.isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_inputphone));
                }else if (CheckForAllUtils.isMobileNO(phone_num)) {//验证手机号码是否可用
                    sendCode(phone_num);
                    //计时开始
                    Message msg = Message.obtain();
                    msg.what = START_COUNT;
                    handler.sendMessage(msg);
                } else {
                    //提示手机号码错误
                    changpwd_phoneedt.setError(LanguageUtil.getResText(R.string.toast_inputrightphone));
                }

                break;
            case R.id.queren_btn:

                if (phone_num.isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_inputphone));
                }else if (codeStr==null){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_beforgetyzm));
                }else if (!changpwd_pwdedt.getText().toString().equals(codeStr)){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_erroyzm));
                }else if (pwd_one.getText().toString().isEmpty()||pwd_again.getText().toString().isEmpty()){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.hint_inputpwd));
                }else if (!pwd_one.getText().toString().equals(pwd_again.getText().toString())){
                    ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_pwddifferent));
                }else {
                    if (!dialog.isShowing()){
                        dialog.show();
                    }
                    releasePWD(phone_num,pwd_again.getText().toString());
                }
                break;
            case R.id.back_relay:
                finish();
                break;
        }
    }

    private void sendCode(String phone_num){
        RequestParams params = new RequestParams(Const.getCode);
        params.addBodyParameter("phone",phone_num);
        params.addBodyParameter("type","2");
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
}
