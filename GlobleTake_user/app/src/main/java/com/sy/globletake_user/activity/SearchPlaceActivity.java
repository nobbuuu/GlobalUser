package com.sy.globletake_user.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.sy.globletake_user.Bean.IconsBean;
import com.sy.globletake_user.Bean.MyAddressBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.adapter.EnterAdapter;
import com.sy.globletake_user.utils.DeviceUtil;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.MyListView;
import com.sy.globletake_user.utils.SeachHistoryData;
import com.sy.globletake_user.utils.SharePreferenceUtils;
import com.sy.globletake_user.utils.SqlHelper;
import com.sy.globletake_user.utils.ToastUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by sunnetdesign3 on 2017/2/14.
 */
public class SearchPlaceActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.placesearch_backrelay)
    RelativeLayout back_relay;
    @Bind(R.id.search_edt)
    EditText search_edt;
    @Bind(R.id.canting_lay)
    LinearLayout canting_lay;
    @Bind(R.id.zhusu_lay)
    LinearLayout zhusu_lay;
    @Bind(R.id.lvyou_lay)
    LinearLayout lvyou_lay;
    @Bind(R.id.zhengfu_lay)
    LinearLayout zhengfu_lay;
    @Bind(R.id.search_root)
    LinearLayout search_root;

    @Bind(R.id.search_tv)
    TextView search_tv;
    @Bind(R.id.histroy_lv)
    MyListView histroy_lv;
    @Bind(R.id.searchresult_lv)
    ListView searchresult_lv;
    @Bind(R.id.myaddress_lv1)
    MyListView myaddress_lv;
    @Bind(R.id.search_scrollview)
    ScrollView search_scrollview;
    @Bind(R.id.search_rsv)
    RecyclerView search_rsv;

    private Context context;
    private List<Address> addressList = new ArrayList<>();
    private List<IconsBean.ResultBean> iconList = new ArrayList<>();

    private EnterAdapter enterAdapter;

//    private GoogleApiClient mGoogleApiClient;
    private SearchAdapter searchAdapter;
    private AddressAdapter adddressAdapter;
    private HistroyAdapter histroyAdapter;
    private String TAG;
    private String type;
    private String userId;
    private SqlHelper mSqlHelper = new SqlHelper();//本地数据库
    private List<MyAddressBean.ResultBean> addressBean_list = new ArrayList<>();
    private List<SeachHistoryData> historyDatas = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placesearch);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this, search_root);
        initData();
        initEvent();
        setEvent();
    }

    private void initData() {
        context = this;
        /*mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .build();*/
        List<SeachHistoryData> query = mSqlHelper.query();
        Log.e("tag","query="+query.toString());
        if (query!=null){
            historyDatas.clear();
            Collections.reverse(query);//集合反转
            historyDatas.addAll(query);
        }

        histroyAdapter = new HistroyAdapter();
        histroy_lv.setAdapter(histroyAdapter);
        searchAdapter = new SearchAdapter();
        searchresult_lv.setAdapter(searchAdapter);
        adddressAdapter = new AddressAdapter();
        myaddress_lv.setAdapter(adddressAdapter);

        //icons图片适配器的初始化
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        search_rsv.setLayoutParams(layoutParams);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        search_rsv.setLayoutManager(manager);
        enterAdapter = new EnterAdapter(context,iconList);
        search_rsv.setAdapter(enterAdapter);
        //获取icon数据源
        getIcons();

        userId = (String) SharePreferenceUtils.getParam(this, Const.UserId,"-1");
        if (!userId.equals("-1")){
            getMyAddress(userId);
        }
        TAG = getIntent().getStringExtra("tag");
    }

    private void setEvent() {
        search_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    searchresult_lv.setVisibility(View.VISIBLE);
                    search_scrollview.setVisibility(View.GONE);
                } else {
                    searchresult_lv.setVisibility(View.GONE);
                    search_scrollview.setVisibility(View.VISIBLE);
                }
            }
        });
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //TODO:

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TODO:
                Log.e("afterTextChanged", "afterTextChanged=" + s);
                String eitStr = s + "";
                if (s != null) {
                    if (eitStr.isEmpty()){
                        searchresult_lv.setVisibility(View.GONE);
                        search_scrollview.setVisibility(View.VISIBLE);
                    }else {
                        searchresult_lv.setVisibility(View.VISIBLE);
                        search_scrollview.setVisibility(View.GONE);
//                        getAddress_list(context, eitStr.trim());
                    }
                }
            }
        });
        searchresult_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                String addressName = addressList.get(i).getFeatureName();
                double latitude = addressList.get(i).getLatitude();
                double longitude = addressList.get(i).getLongitude();
                String detailName = addressList.get(i).getAddressLine(0);
                Log.e("searchresult_lv","detailName="+detailName);
                intent.putExtra("addressName",addressName);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                if (TAG.equals("UsualAddressActivity")){
                    intent.putExtra("detailName",detailName);
                    setResult(111,intent);
                }else {

                    setResult(01,intent);
                }
                List<SeachHistoryData> query = mSqlHelper.query();
                if (query!=null){
                    for (int j = 0; j < query.size(); j++) {
                        if (latitude==query.get(j).getLatitude()&&longitude==query.get(j).getLongitude()){
                            mSqlHelper.deleteSomeone(query.get(j));
                        }
                    }
                }
                SeachHistoryData seachHistoryData = new SeachHistoryData();
                seachHistoryData.setAddressName(addressName);
                seachHistoryData.setDetailAddress(detailName);
                seachHistoryData.setLatitude(latitude);
                seachHistoryData.setLongitude(longitude);
                mSqlHelper.insert(seachHistoryData);
                finish();
            }
        });
        histroy_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                String addressName = historyDatas.get(i).getAddressName();
                intent.putExtra("addressName",addressName);
                intent.putExtra("latitude",historyDatas.get(i).getLatitude());
                intent.putExtra("longitude",historyDatas.get(i).getLongitude());
                setResult(01,intent);
                finish();
            }
        });
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = search_edt.getText().toString().trim();
                if (!address.isEmpty()){
                    getAddress_list(context,address);
                }else {
                    ToastUtils.Toast_short(context, LanguageUtil.getResText(R.string.toast_inputaddress));
                }
            }
        });
        back_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myaddress_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                String addressName = addressBean_list.get(i).getFdName();
                intent.putExtra("addressName",addressName);
                intent.putExtra("latitude",addressBean_list.get(i).getFdCoordinates());
                intent.putExtra("longitude",addressBean_list.get(i).getFdlongitudeCoordinates());
                setResult(01,intent);
                finish();
            }
        });
        enterAdapter.setOnItemClickListener(new EnterAdapter.OnItemClickListener() {
            @Override
            public Void click(View adapterView, int position) {

                Intent intent = new Intent(context,ClassfyActivity.class);
                intent.putExtra("classifyId",iconList.get(position).getFdId());
                if (LanguageUtil.isZh()){
                    intent.putExtra("titleName",iconList.get(position).getFdNameCn());
                }else {
                    intent.putExtra("titleName",iconList.get(position).getFdNameEn());
                }
                startActivityForResult(intent,317);
                return null;
            }
        });
    }

    private void initEvent() {
        search_edt.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_edt:
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class SearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return addressList.size();
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
                view = LayoutInflater.from(context).inflate(R.layout.searchadapter_item, null);
                viewHolder = new ViewHolder();
                viewHolder.search_address = (TextView) view.findViewById(R.id.search_address);
                viewHolder.search_address_detail = (TextView) view.findViewById(R.id.search_address_detail);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            //设置搜索到的地点
            String addressFeatrue = addressList.get(i).getFeatureName();
            if (addressFeatrue != null) {
                viewHolder.search_address.setText(addressFeatrue);
            }

            String detailadd = addressList.get(i).getAddressLine(0);
            if (detailadd != null) {
                viewHolder.search_address_detail.setText(detailadd);
            }
            return view;
        }

        private class ViewHolder {
            private TextView search_address;
            private TextView search_address_detail;
        }
    }

    /**
     * 根据地名返回一个有经纬度location,如果查询不到经纬度  则默认经纬度是0
     *
     * @param context
     * @param address
     * @return
     */
    public List<Address> getAddress_list(Context context, String address) {
        Log.e("tango", "Locale.getDefault()=" + Locale.getDefault());
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 5);
            Location location = new Location(address);
            location.setLatitude(addresses.get(0).getLatitude());
            location.setLongitude(addresses.get(0).getLongitude());
            if (addresses != null) {
                addressList.clear();
                addressList.addAll(addresses);
            }
            searchAdapter.notifyDataSetChanged();
            return addresses;
        } catch (Exception e) {
            ToastUtils.Toast_short1(LanguageUtil.getResText(R.string.toast_inputdetailaddress));
            e.printStackTrace();
            return null;
        }
    }

    //获取常用行程
    private void getMyAddress(String userId){
        RequestParams params = new RequestParams(Const.getMyAddress);
        params.addBodyParameter(Const.UserId,userId);
        params.addBodyParameter("pushCode", JPushInterface.getRegistrationID(context));//极光推送的token
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
                            Log.e("tag","data_list.size()="+data_list.size());
                            if (data_list!=null){
                                addressBean_list.clear();
                                addressBean_list.addAll(data_list);
                                adddressAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtils.Toast_short(context,LanguageUtil.getResText(R.string.toast_parsingexception));
                        }
                    }else {
                        ToastUtils.Toast_short(context,object.getString("message"));
                        ToastUtils.Toast_short1("请重新登录");
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
                view = LayoutInflater.from(context).inflate(R.layout.lvitem_changyong,null);
                addressHolder = new AddressHolder();
                addressHolder.addressName = (TextView) view.findViewById(R.id.search_jia_address);
                addressHolder.address_Detail = (TextView) view.findViewById(R.id.jia_detail);
                addressHolder.address_type = (TextView) view.findViewById(R.id.address_type);
                view.setTag(addressHolder);
            }else {
                addressHolder = (AddressHolder) view.getTag();
            }
            int fdType = addressBean_list.get(i).getFdType();
            if (String.valueOf(fdType).equals("1")){
                addressHolder.address_type.setText(LanguageUtil.getResText(R.string.tv_home));
            }else {
                addressHolder.address_type.setText(LanguageUtil.getResText(R.string.tv_company));
            }
            addressHolder.addressName.setText(addressBean_list.get(i).getFdName());
            addressHolder.address_Detail.setText(addressBean_list.get(i).getFdAddress());

            return view;
        }

        class AddressHolder{
            TextView addressName,address_Detail;
            private TextView address_type;
        }
    }


    private class  HistroyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return historyDatas.size();
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
            HistroyHolder holder = null;
            if (view==null){
                holder= new HistroyHolder();
                view = LayoutInflater.from(context).inflate(R.layout.lvitem_histroyaddress,null);
                holder.addressName_tv = (TextView) view.findViewById(R.id.address_name);
                holder.addressDetailName_tv = (TextView) view.findViewById(R.id.address_detailname);
                view.setTag(holder);
            }else {
                holder = (HistroyHolder) view.getTag();
            }
            String detailAddress = historyDatas.get(i).getDetailAddress();
            holder.addressName_tv.setText(historyDatas.get(i).getAddressName());
            holder.addressDetailName_tv.setText(detailAddress);
            return view;
        }
       class HistroyHolder{
            TextView addressName_tv,addressDetailName_tv;
        }
    }

    private void getIcons(){
        RequestParams params = new RequestParams(Const.getIcons);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                IconsBean iconBean = gson.fromJson(result,IconsBean.class);
                if (iconBean!=null){
                    if (iconBean.getCode().equals(Const.reques_success)){
                        List<IconsBean.ResultBean> icondata = iconBean.getResult();
                        if (icondata!=null){
                            iconList.clear();
                            iconList.addAll(icondata);
                            search_rsv.removeAllViews();
                            enterAdapter.notifyDataSetChanged();

                        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==317&&resultCode==317&&data!=null){
            double fdCoordinates = data.getDoubleExtra("latitude",0);
            double fdEndinates = data.getDoubleExtra("longitude",0);
            String fdAddress= data.getStringExtra("addressName");
            Intent intent = getIntent();
            intent.putExtra("addressName",fdAddress);
            intent.putExtra("latitude",fdCoordinates);
            intent.putExtra("longitude",fdEndinates);
            setResult(01,intent);
            finish();
        }
    }
}
