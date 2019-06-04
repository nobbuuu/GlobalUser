package com.sy.globletake_user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.sy.globletake_user.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class MyActivity extends AppCompatActivity {
    @Bind(R.id.edt_log)
    EditText edt_log;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.axiba);
        ButterKnife.bind(this);
        String logmsg = getIntent().getStringExtra("logmsg");
        edt_log.setText(logmsg);
    }
}
