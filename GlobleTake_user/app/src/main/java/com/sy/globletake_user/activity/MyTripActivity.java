package com.sy.globletake_user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.MyTripBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.LogUtils;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.TimeUtils;
import com.sy.globletake_user.utils.ToastUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/3.
 */
public class MyTripActivity extends AppCompatActivity {

    @Bind(R.id.titlebar_centertv)
    TextView titlebar_centertv;
    @Bind(R.id.mymessage_lv)
    ListView mymessage_lv;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.back_relay)
    RelativeLayout back_relay;
    private Context context;
    private MyTripAdapter myTripAdapter;
    private List<MyTripBean.ResultBean> dataList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);
        ButterKnife.bind(this);
        context = this;
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.statecolor);
        titlebar_centertv.setText(LanguageUtil.getResText(R.string.tv_mytrip));
        myTripAdapter = new MyTripAdapter();
        mymessage_lv.setAdapter(myTripAdapter);
        getTripData();
        setEvent();
    }

    private void setEvent(){
        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mymessage_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("tag","order_id="+dataList.get(i).getOrder().getFdId());
                Log.e("tag","trip_id="+dataList.get(i).getTrip().getFdId());
                Intent intent = new Intent(MyTripActivity.this,EndOrderActivity.class);
                intent.putExtra("tag","MyTripActivity");
                intent.putExtra("order_id",dataList.get(i).getOrder().getFdId());
                intent.putExtra("trip_id",dataList.get(i).getTrip().getFdId());
                startActivityForResult(intent,520);
            }
        });
    }

    private void getTripData(){
        String userId = (String) SharePreferenceUtils.getParam(MyTripActivity.this, Const.UserId,"");
        RequestParams params = new RequestParams(Const.getMyTrip);
        params.addBodyParameter("user_id",userId);
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject objece = new JSONObject(result);
                    if (objece.getString(Const.Code).equals(Const.reques_success)){
                        Gson gson = new Gson();
                        MyTripBean messagBean = gson.fromJson(result,MyTripBean.class);
                        if (messagBean!=null){
                            List<MyTripBean.ResultBean> resultBeanList = messagBean.getResult();
                            if (resultBeanList!=null){
                                dataList.clear();
                                dataList.addAll(resultBeanList);
                                LogUtils.Loge("tag","dataList.size()="+dataList.size());
                                myTripAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_parsingexception));
                        }
                    }else {
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_dataexception));
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

    class MyTripAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapteritem_mytrip, viewGroup, false);
                viewHolder.trip_time = (TextView) view.findViewById(R.id.trip_time);
                viewHolder.trip_status = (TextView) view.findViewById(R.id.trip_status);
                viewHolder.start_place = (TextView) view.findViewById(R.id.start_place);
                viewHolder.end_place = (TextView) view.findViewById(R.id.end_place);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.trip_time.setText(TimeUtils.getStrTime(String.valueOf(dataList.get(i).getTrip().getFdTripCreateTime())));
            viewHolder.start_place.setText(dataList.get(i).getTrip().getFdTripStartLocation());
            viewHolder.end_place.setText(dataList.get(i).getTrip().getFdTripEndLocation());
            int fdStatus = Integer.parseInt(dataList.get(i).getTrip().getFdStatus());
            if (fdStatus==0){
                viewHolder.trip_status.setText(LanguageUtil.getResText(R.string.tv_canceled));
            }else if (fdStatus==1){
                viewHolder.trip_status.setText(LanguageUtil.getResText(R.string.tv_waitjiedan));
            }
            else if (fdStatus==2){
                viewHolder.trip_status.setText(LanguageUtil.getResText(R.string.tv_receivedorder));
            }
            else if (fdStatus==3){
                viewHolder.trip_status.setText(LanguageUtil.getResText(R.string.tv_waittakecar));
            }else if (fdStatus==4){
                viewHolder.trip_status.setText(LanguageUtil.getResText(R.string.tv_taked));
            }else if (fdStatus==5){
                viewHolder.trip_status.setText(LanguageUtil.getResText(R.string.tv_completed));
            }
            return view;
        }
        class ViewHolder {
            private TextView trip_time, trip_status, start_place,end_place;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==520&&resultCode==520){
            getTripData();
        }
    }
}
