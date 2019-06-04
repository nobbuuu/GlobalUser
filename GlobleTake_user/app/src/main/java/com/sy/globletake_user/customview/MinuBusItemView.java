package com.sy.globletake_user.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sy.globletake_user.Bean.MinuBusBean;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.CallPhoneutil;
import com.sy.globletake_user.utils.LanguageUtil;
import com.sy.globletake_user.utils.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunnetdesign3 on 2017/3/6.
 */
public class MinuBusItemView extends LinearLayout {
    @Bind(R.id.startend_tv)
    TextView startend_tv;
    @Bind(R.id.luxian_tv)
    TextView luxian_tv;
    @Bind(R.id.minubus_itemlv)
    MyListView minubus_itemlv;
    private Context context;
    private  SmallItemAdapter adapter;
    private List<MinuBusBean.ResultBean.MinibusBean> smallList = new ArrayList<>();
    public MinuBusItemView(Context context) {
        super(context);
        initView(context);
    }

    public MinuBusItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){

        View view = inflate(context, R.layout.minubus_item,this);
        ButterKnife.bind(this,view);
        this.context = context;
        adapter = new SmallItemAdapter();
        minubus_itemlv.setAdapter(adapter);
    }

    public void setData(MinuBusBean.ResultBean dataBean, int position){

        if (dataBean!=null){
            String fdTitle = dataBean.getFdTitle();
            startend_tv.setText(fdTitle);
            luxian_tv.setText(LanguageUtil.getResText(R.string.tv_luxian)+(position+1));
            List<MinuBusBean.ResultBean.MinibusBean> minibus = dataBean.getMinibus();
            if (minibus!=null){
                smallList.clear();
                smallList.addAll(minibus);
                adapter.notifyDataSetChanged();
            }
        }
    }


    private class SmallItemAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return smallList.size();
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
            Holder holder = null;
            if (view==null){
                holder = new Holder();
                view = LayoutInflater.from(context).inflate(R.layout.minibus_lvsmallitem,null);
                holder.carnum_drivername = (TextView) view.findViewById(R.id.carnum_drivername);
                holder.driver_phonenum = (TextView) view.findViewById(R.id.driver_phonenum);
                holder.right_phone = (ImageView) view.findViewById(R.id.right_phone);
                view.setTag(holder);
            }else {
                holder = (Holder) view.getTag();
            }

            if (LanguageUtil.isZh()){
                holder.carnum_drivername.setText(smallList.get(i).getFdVehicleNumber()+"    "+smallList.get(i).getFdName());
                holder.driver_phonenum.setText(smallList.get(i).getFdNumber());
            }else {
                holder.carnum_drivername.setText(smallList.get(i).getFdVehicleNumber()+"    "+smallList.get(i).getFdEnName());
                holder.driver_phonenum.setText(smallList.get(i).getFdNumber());
            }

            holder.right_phone.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    CallPhoneutil.callPhone(context,smallList.get(i).getFdNumber());
                }
            });

            return view;
        }

        class Holder{
            TextView carnum_drivername,driver_phonenum;
            ImageView right_phone;
        }
    }
}
