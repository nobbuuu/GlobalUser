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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.origamilabs.library.views.StaggeredGridView;
import com.sy.globletake_user.Bean.ClassifyBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.customview.OvalImageView;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.XuniKeyWord;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/17.
 */
public class ClassfyActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.classify_gv)
    StaggeredGridView classify_gv;
    @Bind(R.id.backrelay)
    RelativeLayout backrelay;
    @Bind(R.id.titlebar_centertv)
    TextView titlebar_centertv;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;

    private Context context;
    private ClassifyGvAdpter gvAdapter;
    private List<ClassifyBean.ResultBean> classifyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);
        context = this;
        XuniKeyWord.setShiPei(this, root_lay);
        XuniKeyWord.setStatusBarBgColor(this, R.color.statecolor);
        String classifyId = getIntent().getStringExtra("classifyId");
        String titleName = getIntent().getStringExtra("titleName");

        if (titleName != null) {
            titlebar_centertv.setText(titleName);
        }
        gvAdapter = new ClassifyGvAdpter();
        classify_gv.setItemMargin(20);
//        classify_gv.setHorizontalSpacing(20);
        classify_gv.setAdapter(gvAdapter);
        getClassfylist(classifyId);
        backrelay.setOnClickListener(this);
        classify_gv.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {
            @Override
            public void onItemClick(StaggeredGridView parent, View view, int i, long id) {
                double fdCoordinates = classifyList.get(i).getFdCoordinates();
                double fdEndinates = classifyList.get(i).getFdEndinates();
                String fdAddressCn = classifyList.get(i).getFdAddressCn();
                String fdAddressEn = classifyList.get(i).getFdAddressEn();
                Intent intent = getIntent();
                if (LanguageUtil.isZh()){
                    intent.putExtra("addressName",fdAddressCn);
                }else {
                    intent.putExtra("addressName",fdAddressEn);
                }
                intent.putExtra("latitude",fdCoordinates);
                intent.putExtra("longitude",fdEndinates);
                Log.e("gg","latitude="+fdCoordinates);
                Log.e("gg","longitude="+fdEndinates);
                setResult(317,intent);
                finish();
            }
        });
        /*classify_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double fdCoordinates = classifyList.get(i).getFdCoordinates();
                double fdEndinates = classifyList.get(i).getFdEndinates();
                String fdAddressCn = classifyList.get(i).getFdAddressCn();
                String fdAddressEn = classifyList.get(i).getFdAddressEn();
                Intent intent = getIntent();
                if (LanguageUtil.isZh()){
                    intent.putExtra("addressName",fdAddressCn);
                }else {
                    intent.putExtra("addressName",fdAddressEn);
                }
                intent.putExtra("latitude",fdCoordinates);
                intent.putExtra("longitude",fdEndinates);
                setResult(317,intent);
                finish();
            }
        });*/
    }

    private void getClassfylist(String id) {
        RequestParams params = new RequestParams(Const.getClassfylist);
        params.addBodyParameter("hot_address_classid", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                ClassifyBean classifyBean = gson.fromJson(result,ClassifyBean.class);
                if (classifyBean!=null){
                    if (classifyBean.getCode().equals(Const.reques_success)){
                        List<ClassifyBean.ResultBean> classifyData = classifyBean.getResult();
                        if (classifyData!=null){
                            classifyList.clear();
                            classifyList.addAll(classifyData);
                            gvAdapter.notifyDataSetChanged();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backrelay:
                finish();
                break;
        }
    }

    private class ClassifyGvAdpter extends BaseAdapter {

        @Override
        public int getCount() {
            return classifyList.size();
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

            ViewHolder holder = null;
            if (view==null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gvitm_lvplace, viewGroup, false);
                holder = new ViewHolder();
                holder.big_img = (OvalImageView) view.findViewById(R.id.big_img);
                holder.placename_tv = (TextView) view.findViewById(R.id.placename_tv);
                holder.phone_tv = (TextView) view.findViewById(R.id.phone_tv);
                holder.people_name = (TextView) view.findViewById(R.id.people_name);
                holder.people_lay = (LinearLayout) view.findViewById(R.id.people_lay);
                view.setTag(holder);
            }else {
                holder = (ViewHolder) view.getTag();
            }
            if (classifyList.get(i).getFdUrl()!=null){
                x.image().bind(holder.big_img,Const.root_url+classifyList.get(i).getFdUrl());
            }

            if (LanguageUtil.isZh()){
                if (classifyList.get(i).getFdAddressCn()!=null){
                    holder.placename_tv.setText(classifyList.get(i).getFdAddressCn());
                }

                if (classifyList.get(i).getFdUserName()!=null){
                    holder.people_lay.setVisibility(View.VISIBLE);
                    holder.people_name.setText(classifyList.get(i).getFdUserName());
                }else {
                    holder.people_lay.setVisibility(View.GONE);
                }
            }else {
                if (classifyList.get(i).getFdAddressEn()!=null){
                    holder.placename_tv.setText(classifyList.get(i).getFdAddressEn());
                }
                if (classifyList.get(i).getFdUserName()!=null){
                    holder.people_lay.setVisibility(View.VISIBLE);
                    holder.people_name.setText(classifyList.get(i).getFdUserNameEn());
                }else {
                    holder.people_lay.setVisibility(View.GONE);
                }
            }
            if (classifyList.get(i).getFdPhone()!=null){
                holder.phone_tv.setText(classifyList.get(i).getFdPhone());
            }

            return view;
        }

        private class ViewHolder {
            TextView people_name,phone_tv,placename_tv;
            OvalImageView big_img;
            LinearLayout people_lay;
        }
    }
}
