package com.sy.globletake_user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.globletake_user.Bean.MyAddressBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by sunnetdesign3 on 2017/3/1.
 */
public class UsualAddressActivity extends AppCompatActivity {

    @Bind(R.id.endorder_backrelay)
    RelativeLayout endorder_backrelay;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.myaddress_lv)
    ListView myaddress_lv;

    private Context context;
    private AddressAdapter adddressAdapter;
    private String type;
    private String userId;
    private List<MyAddressBean.ResultBean> addressBean_list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changyong_address);
        ButterKnife.bind(this);
        context = this;
        XuniKeyWord.setShiPei(this,root_lay);
        adddressAdapter = new AddressAdapter();
        myaddress_lv.setAdapter(adddressAdapter);

        userId = (String) SharePreferenceUtils.getParam(this,Const.UserId,"-1");
        if (!userId.equals("-1")){
            getMyAddress(userId);
        }
        setEvent();
    }

    private void getMyAddress(String userId){
        RequestParams params = new RequestParams(Const.getMyAddress);
        params.addBodyParameter(Const.UserId,userId);
        params.addBodyParameter("pushCode", JPushInterface.getRegistrationID(this));//极光推送的token
        params.addBodyParameter("pushId", "1");//平台识别码，1：Android，0：ios
        LanguageUtil.addLanguage(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)){
                        Gson gson = new Gson();
                        MyAddressBean myAddressBean = gson.fromJson(result,MyAddressBean.class);
                        if (myAddressBean!=null){
                            List<MyAddressBean.ResultBean> data_list = myAddressBean.getResult();
                            if (data_list!=null){
                                addressBean_list.clear();
                                addressBean_list.addAll(data_list);
                                adddressAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_parsingexception));
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

    private class AddressAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return addressBean_list.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            AddressHolder addressHolder = null;
            if (view==null){
                view = LayoutInflater.from(context).inflate(R.layout.item_myaddress,null);
                addressHolder = new AddressHolder();
                addressHolder.home_tv = (TextView) view.findViewById(R.id.myaddress_tv1);
                addressHolder.company_tv = (TextView) view.findViewById(R.id.myaddress_tv2);
                addressHolder.setaddress_relay = (RelativeLayout) view.findViewById(R.id.myaddress_nextrelay_home);
                addressHolder.address_icon = (ImageView) view.findViewById(R.id.address_icon);
                addressHolder.address_type = (TextView) view.findViewById(R.id.address_type);
                view.setTag(addressHolder);
            }else {
                addressHolder = (AddressHolder) view.getTag();
            }
            int fdType = addressBean_list.get(i).getFdType();
            if (String.valueOf(fdType).equals("1")){
                addressHolder.address_icon.setImageResource(R.mipmap.changyongdizhi_home);
                addressHolder.address_type.setText(LanguageUtil.getResText(R.string.tv_homesimple));
            }else {
                addressHolder.address_icon.setImageResource(R.mipmap.changyongdizhi_office);
                addressHolder.address_type.setText(LanguageUtil.getResText(R.string.tv_companysimple));
            }
            addressHolder.home_tv.setText(addressBean_list.get(i).getFdName());
            addressHolder.company_tv.setText(addressBean_list.get(i).getFdAddress());

            addressHolder.setaddress_relay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //去设置具体的地址
                    type = addressBean_list.get(i).getFdType()+"";
                    Intent intent = new Intent(UsualAddressActivity.this,SearchPlaceActivity.class);
                    intent.putExtra("tag","UsualAddressActivity");
                    startActivityForResult(intent,111);
                }
            });
            return view;
        }

        class AddressHolder{
            TextView home_tv,company_tv;
            RelativeLayout setaddress_relay;
            private ImageView address_icon;
            private TextView address_type;
        }
    }

    private void setEvent() {
        endorder_backrelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==111&&resultCode==111&&data!=null){
            //先添加地址到服务器
            String addressName = data.getStringExtra("addressName");
            String detailName = data.getStringExtra("detailName");
            double latitude = data.getDoubleExtra("latitude",0);
            double longitude = data.getDoubleExtra("longitude",0);
            addMyAddress(String.valueOf(latitude),String.valueOf(longitude),addressName,detailName);

        }
    }

    private void addMyAddress(String Pointw,String pointj,String adressname,String adressInfo){
        RequestParams params = new RequestParams(Const.addMyAddress);
        params.addBodyParameter("pointw",Pointw);
        params.addBodyParameter("pointj",pointj);
        params.addBodyParameter("type",type);
        params.addBodyParameter("adressname",adressname);
        params.addBodyParameter("adressInfo",adressInfo);
        params.addBodyParameter(Const.UserId,userId );
        params.addBodyParameter(Const.Language,Const.Chinese);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString(Const.Code).equals(Const.reques_success)){
                        getMyAddress(userId);
                    }else {
                        ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_dataexception));
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
