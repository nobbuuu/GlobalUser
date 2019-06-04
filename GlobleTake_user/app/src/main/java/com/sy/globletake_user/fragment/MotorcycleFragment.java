package com.sy.globletake_user.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sy.globletake_user.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/1/20.
 */
public class MotorcycleFragment extends Fragment {

//    @Bind(R.id.main_mapview)
//    MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_taxi,null);
//        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
