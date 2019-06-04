package com.sy.globletake_user.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;

import com.sy.globletake_user.R;

/**
 * Created by sunnetdesign3 on 2016/12/8.
 */
public class DialogUtils {


    /**
     * 设置添加屏幕的背景透明度
     * **/
    public static void backgroundAlpaha(Activity context, float bgAlpha) {
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().setAttributes(lp);
    }
    public static Dialog initDialog(Context context) {
        Dialog dialog = null;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.progress_dialog);
        }
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView msg = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("Loading...");
        return dialog;
    }
}
