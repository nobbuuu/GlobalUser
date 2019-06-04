package com.sy.globletake_user.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.MinuBusBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.customview.MinuBusItemView;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.ToastUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/1/20.
 */
public class MinibusFragment extends Fragment {

    private LinearLayout keche_lay;

    private View mView;
    private List<MinuBusBean.ResultBean> data_list = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  121:
                    for (int i = 0; i < data_list.size(); i++) {
                        MinuBusBean.ResultBean dataBean = data_list.get(i);
                        MinuBusItemView itemView = new MinuBusItemView(getActivity());
                        itemView.setData(dataBean,i);
                        keche_lay.addView(itemView);
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_minubus,null);
        ButterKnife.bind(this,mView);
        keche_lay = (LinearLayout) mView.findViewById(R.id.keche_lay);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDta();
    }

    private void getDta(){
        RequestParams params = new RequestParams(Const.getMianBaoCar);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MinuBusBean kecheBean = gson.fromJson(result,MinuBusBean.class);
                if (kecheBean!=null){
                    if (kecheBean.getCode().equals(Const.reques_success)){
                        List<MinuBusBean.ResultBean> keche_data = kecheBean.getResult();
                        if (keche_data!=null){
                            data_list.clear();
                            data_list.addAll(keche_data);
                            Message message = new Message();
                            message.what = 121;
                            mHandler.sendMessage(message);

                        }
                    }else {
                        ToastUtils.Toast_short(getActivity(), LanguageUtil.getResText(R.string.toast_dataexception));
                    }
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
