package com.sy.globletake_user.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.CircleImageView;
import com.sy.globletake_user.utils.DialogUtils;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/2/20.
 */
public class UserInfoActivity extends AppCompatActivity  implements View.OnClickListener{

    @Bind(R.id.head_icon)
    ImageView mCircleImageView;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.sex_name)
    TextView sex_name;
    @Bind(R.id.phone_num)
    TextView phone_num;
    @Bind(R.id.user_email)
    TextView user_email;
    @Bind(R.id.exitlogin_btn)
    Button exitlogin_btn;
    @Bind(R.id.change_headicon_relay)
    RelativeLayout change_headicon_relay;
    @Bind(R.id.sex_relay)
    RelativeLayout sex_relay;
    @Bind(R.id.return_img)
    RelativeLayout return_img;
    @Bind(R.id.root_lay)
    RelativeLayout root_lay;
    @Bind(R.id.change_phonerelay)
    RelativeLayout change_phonerelay;

    private Context context;
    private Dialog dialog;
    private String userId;


    static final int PICTRUE = 100;
    static final int CAMERA = 200;
    private File mFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        context = this;
        userId = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        dialog = DialogUtils.initDialog(context);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.color666);
        setclickble();
        initData();
        EventBus.getDefault().register(this);
    }

    private void initData(){
        String userIcon_url = (String) SharePreferenceUtils.getParam(context, Const.User_HeadIcon, "");
        ImageOptions options = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(true)
                .setUseMemCache(true)
                .setFailureDrawableId(R.mipmap.tanchuang_touxiang)
                .build();
        if (!userIcon_url.isEmpty()){
           x.image().bind(mCircleImageView,Const.root_url+userIcon_url,options);
        }else {
            mCircleImageView.setImageResource(R.mipmap.tanchuang_touxiang);
        }

        String userName = (String) SharePreferenceUtils.getParam(context, Const.User_Name, "");
        user_name.setText(userName);
        String userSex = (String) SharePreferenceUtils.getParam(context, Const.fdPassengerSex, "");
        if (userSex.equals("1")){
            sex_name.setText(LanguageUtil.getResText(R.string.tv_male));
        }else if (userSex.equals("0")){
            sex_name.setText(LanguageUtil.getResText(R.string.tv_female));
        }
        String userPhone = (String) SharePreferenceUtils.getParam(context, Const.USER_PHONE, "");
        phone_num.setText(userPhone);
        String userEmail = (String) SharePreferenceUtils.getParam(context, Const.User_Email, "");
        user_email.setText(userEmail);
    }
    private void setclickble() {
        change_headicon_relay.setOnClickListener(this);
        sex_relay.setOnClickListener(this);
        return_img.setOnClickListener(this);
        root_lay.setOnClickListener(this);
        exitlogin_btn.setOnClickListener(this);
        change_phonerelay.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MainEvent(String eventStr){
        if (eventStr.equals("修改手机号")){
            String userPhone = (String) SharePreferenceUtils.getParam(context, Const.USER_PHONE, "");
            phone_num.setText(userPhone);
        }
    }

    private AlertDialog alertDialog;

    private void openDig() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.phone_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.Dialog);
        alertDialog = builder.create();
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        Window window = alertDialog.getWindow();
        window.setContentView(view);

        Button cancel_btn = (Button) window.findViewById(R.id.btn_cancel);
        Button confirm_btn = (Button) window.findViewById(R.id.btn_confirm);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();


                if (ContextCompat.checkSelfPermission(UserInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)//权限未授予
                {
                    ActivityCompat.requestPermissions(UserInfoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},240
                    );
                } else//已授予权限
                {
                    Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(picture, PICTRUE);
                }
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //相机
                alertDialog.dismiss();

                if (ContextCompat.checkSelfPermission(UserInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)//权限未授予
                {
                    ActivityCompat.requestPermissions(UserInfoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},250
                    );
                } else//已授予权限
                {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera, CAMERA);
                }
            }
        });

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_headicon_relay://修改头像
                openDig();
                break;
            case R.id.sex_relay://修改性别
                showPopwindow();
                //从底部显示
                popupWindow.showAtLocation(sex_relay, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.exitlogin_btn://退出登录
                exitLogin();
                break;
            case R.id.return_img://返回
                finish();
                break;
            case R.id.change_phonerelay://修改手机号
                Intent intent = new Intent(context,ChangeUserPhoneActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 显示popupWindow
     */
    private View contentView;
    private PopupWindow popupWindow;
    private void showPopwindow() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(context).inflate(
                R.layout.pop, null);
        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        RelativeLayout nan_relay = (RelativeLayout) contentView.findViewById(R.id.sex_nanrelay);
        RelativeLayout nv_relay = (RelativeLayout) contentView.findViewById(R.id.sex_nvrelay);
        nan_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeOtherInfo("","1","");
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        nv_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOtherInfo("","0","");
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
        RelativeLayout quxiao = (RelativeLayout) contentView.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTRUE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            mFile = new File(picturePath);

            Bitmap bitmap = getDiskBitmap(picturePath);
            mCircleImageView.setImageBitmap(bitmap);
            //上传头像

            if (!dialog.isShowing()) {
                dialog.show();
            }
            Log.e("mFile", "mFile=" + mFile);
            UpLoading(mFile);

        }
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && null != data) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(context, LanguageUtil.getResText(R.string.toast_sdcard), Toast.LENGTH_SHORT).show();
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            File file = new File("/sdcard/pintu/");
            file.mkdirs();
            String filename = file.getPath() + name;
            try {
                fout = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //显示图片
            mCircleImageView.setImageBitmap(bitmap);
            creat(filename);

        }
    }
    //创建文件
    public void creat(String path) {
        mFile = new File(path);
        UpLoading(mFile);
    }

    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        File file = new File(pathString);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(pathString);
        }

        return bitmap;
    }


    private void UpLoading(File file) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
        RequestParams entity = new RequestParams(Const.changeHead);
        String id = (String) SharePreferenceUtils.getParam(context, Const.UserId, "");
        entity.addBodyParameter("user_id", id);
        entity.addBodyParameter("file", file);
        LanguageUtil.addLanguage(entity);
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result","result="+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if ("1".equals(jsonObject.getString("code"))) {

                        Toast.makeText(UserInfoActivity.this, LanguageUtil.getResText(R.string.toast_xiugaicg), Toast.LENGTH_SHORT).show();
                        SharePreferenceUtils.setParam(UserInfoActivity.this, Const.User_HeadIcon, jsonObject.getString("result"));

                    } else {
                        Toast.makeText(UserInfoActivity.this, LanguageUtil.getResText(R.string.toast_xiugaifail), Toast.LENGTH_SHORT).show();
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
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void changeOtherInfo(String email, final String sex, String userName) {
        if (!dialog.isShowing()) {
            dialog.show();
        }
        RequestParams entity = new RequestParams(Const.changeOtherInfo);
        entity.addBodyParameter("type","1");//用户类型1：乘客；2：司机
        entity.addBodyParameter("user_id", userId);
        if (!sex.isEmpty()){
            entity.addBodyParameter("sex",sex);
        }
        if (!email.isEmpty()){
            entity.addBodyParameter("email",email);
        }
        if (!userName.isEmpty()){
            entity.addBodyParameter("userName",userName);
        }
        LanguageUtil.addLanguage(entity);
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("result","result="+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if ("1".equals(jsonObject.getString("code"))) {

                        Toast.makeText(UserInfoActivity.this, LanguageUtil.getResText(R.string.toast_xiugaicg), Toast.LENGTH_SHORT).show();
                        if (sex.equals("0")){
                            sex_name.setText(LanguageUtil.getResText(R.string.tv_female));
                        }else if (sex.equals("1")){
                            sex_name.setText(LanguageUtil.getResText(R.string.tv_male));
                        }
                        SharePreferenceUtils.setParam(context,Const.fdPassengerSex,sex);
                    } else {
                        Toast.makeText(UserInfoActivity.this, LanguageUtil.getResText(R.string.toast_xiugaifail), Toast.LENGTH_SHORT).show();
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
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
    private void clearUserInfo(){
        SharePreferenceUtils.setParam(context,Const.isLogin,false);
        SharePreferenceUtils.setParam(context,Const.User_Name,LanguageUtil.getResText(R.string.tv_notlogin));
        SharePreferenceUtils.setParam(context,Const.User_HeadIcon,"");

    }
    private void exitLogin(){
        RequestParams params = new RequestParams(Const.exitLogin);
        params.addBodyParameter("user_id",userId);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    if ("1".equals(jsonObject.getString("code"))) {

                        Toast.makeText(UserInfoActivity.this, LanguageUtil.getResText(R.string.toast_exitsuccess), Toast.LENGTH_SHORT).show();
                        clearUserInfo();
                        setResult(704);
                        finish();
                    } else {
                        Toast.makeText(UserInfoActivity.this, LanguageUtil.getResText(R.string.toast_exitfail), Toast.LENGTH_SHORT).show();
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

            }
        });
    }
}
