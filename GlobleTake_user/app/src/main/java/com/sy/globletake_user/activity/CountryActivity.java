package com.sy.globletake_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.LogUtils;
import com.sy.globletake_user.utils.XuniKeyWord;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.gui.CountryListView;
import cn.smssdk.gui.GroupListView;

/**
 * Created by sunnetdesign3 on 2017/3/10.
 */
public class CountryActivity extends AppCompatActivity {

    @Bind(R.id.country_lv)
    CountryListView listView;
    @Bind(R.id.root_lay)
    LinearLayout root_lay;
    @Bind(R.id.return_img)
    RelativeLayout return_img;
    private String id,code,name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        ButterKnife.bind(this);
        XuniKeyWord.setShiPei(this,root_lay);
        XuniKeyWord.setStatusBarBgColor(this,R.color.color666);
        listView.setOnItemClickListener(new GroupListView.OnItemClickListener() {
            @Override
            public void onItemClick(GroupListView groupListView, View view, int group, int position) {
                if(position >= 0){
                    String[] country = listView.getCountry(group, position);
                    id = country[2];
                    code = country[1];
                    name = country[0];
                    LogUtils.Loge("tag","id="+id);//国家ID
                    LogUtils.Loge("tag","code="+code);//国家code eg:+86
                    LogUtils.Loge("tag","name="+name);//国家名称
                    Intent intent = getIntent();
                    intent.putExtra("country",country);
                    setResult(310,intent);
                    finish();

                }
            }
        });

        return_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
