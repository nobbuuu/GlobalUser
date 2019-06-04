package com.sy.globletake_user.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sy.globletake_user.Bean.CoachBean;
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
public class CoachItemView extends LinearLayout {
    @Bind(R.id.company_name)
    TextView company_name;
    @Bind(R.id.bohao_tv)
    TextView bohao_tv;
    @Bind(R.id.coach_smalllv)
    MyListView coach_smalllv;
    private View itemView;
    private Context context;

    private SmallItemAdapter smallItemAdapter;
    private List<CoachBean.ResultBean.DateBean> smallList = new ArrayList<>();
    public CoachItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CoachItemView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context){
        itemView = inflate(context, R.layout.coach_item,this);
        ButterKnife.bind(this,itemView);
        this.context = context;
        smallItemAdapter = new SmallItemAdapter();
        coach_smalllv.setAdapter(smallItemAdapter);
    }

    public void setData(final CoachBean.ResultBean dataBean, int position){
        if (dataBean!=null){
            if (dataBean.getFdName()!=null){
                company_name.setText(dataBean.getFdName());
            }

            List<CoachBean.ResultBean.DateBean> datas = dataBean.getDate();
            if (datas!=null){
                smallList.clear();
                smallList.addAll(datas);
                smallItemAdapter.notifyDataSetChanged();
            }
        }

        bohao_tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CallPhoneutil.callPhone(context,dataBean.getFdPhone());
            }
        });
    }

    private class SmallItemAdapter extends BaseAdapter {

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
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder = null;
            if (view==null){
                holder = new Holder();
                view = LayoutInflater.from(context).inflate(R.layout.coachsmalllv_item,null);
                holder.startplace_tv = (TextView) view.findViewById(R.id.startplace_tv);
                holder.endplace_tv = (TextView) view.findViewById(R.id.endplace_tv);
                holder.mianbao_tv = (TextView) view.findViewById(R.id.mianbao_tv);
                holder.day_tv = (TextView) view.findViewById(R.id.day_tv);
                view.setTag(holder);
            }else {
                holder = (Holder) view.getTag();
            }

            if (LanguageUtil.isZh()){
                holder.startplace_tv.setText(smallList.get(i).getImformationType().getFdName());
                holder.endplace_tv.setText(smallList.get(i).getImformationType().getFdEndName());
                holder.day_tv.setText(smallList.get(i).getInformation().getFdOperatingday());
                holder.mianbao_tv.setText(smallList.get(i).getInformation().getFdContent());
            }else {
                holder.startplace_tv.setText(smallList.get(i).getImformationType().getFdEnName());
                holder.endplace_tv.setText(smallList.get(i).getImformationType().getFdEnEndName());
                holder.day_tv.setText(smallList.get(i).getInformation().getFdEnOperatingday());
                holder.mianbao_tv.setText(smallList.get(i).getInformation().getFdEnContent());
            }
            return view;
        }

        class Holder{
            TextView startplace_tv,endplace_tv,mianbao_tv,day_tv;
        }
    }
}
