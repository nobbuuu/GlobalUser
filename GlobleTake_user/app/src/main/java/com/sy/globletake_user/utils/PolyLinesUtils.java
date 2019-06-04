package com.sy.globletake_user.utils;


import com.sy.globletake_user.Other.Const;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by sunnetdesign3 on 2017/2/15.
 */
public class PolyLinesUtils {

    private   void getLuxianJson(String startStr,String endStr,String mode){
        RequestParams params = new RequestParams(Const.polylines_url);
        params.addBodyParameter("origin",startStr);
        params.addBodyParameter("destination",endStr);
        params.addBodyParameter("sensor","false");
        params.addBodyParameter("mode",mode);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
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
