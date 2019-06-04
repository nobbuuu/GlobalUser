package com.sy.globletake_user.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by sunnetdesign3 on 2017/3/4.
 */
public class CallPhoneutil {
    public static void callPhone(Context context,String phoneNumber){
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
        }else {
            ToastUtils.Toast_short(context,"该用户未提供手机号");
        }
    }
}
