package com.sy.globletake_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.sy.globletake_user.Bean.IconsBean;
import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.BitmapUtil;
import com.sy.globletake_user.utils.LanguageUtil;

import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class EnterAdapter extends RecyclerView.Adapter<EnterAdapter.EnterHolder> {
    private LayoutInflater inflater;
    private List<IconsBean.ResultBean> specilList;
    private Context context;

    private OnItemClickListener listener;


    public interface OnItemClickListener{
        Void click(View adapterView,int position);
    }
    public EnterAdapter(Context context, List<IconsBean.ResultBean> list) {
        this.specilList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

        //获取系统服务
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public EnterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.adapter_enter,null);
        return new EnterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EnterHolder holder, int position) {
        x.image().bind(holder.imageView, Const.root_url+specilList.get(position).getFddUrl(), BitmapUtil.getCircleBitmapOption());
        if (LanguageUtil.isZh()){
            holder.type_tv.setText(specilList.get(position).getFdNameCn());
        }else {
            holder.type_tv.setText(specilList.get(position).getFdNameEn());
        }
    }

    @Override
    public int getItemCount() {
        return specilList==null?0:specilList.size();
    }

    class  EnterHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView type_tv;
        public EnterHolder(View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.icon_imgv);
            type_tv = (TextView) itemView.findViewById(R.id.type_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.click(view,getPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
