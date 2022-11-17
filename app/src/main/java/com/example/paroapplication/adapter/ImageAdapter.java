package com.example.paroapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paroapplication.GlideEngine;
import com.example.paroapplication.ImageDao.ImageBean;
import com.example.paroapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.RecyclerHolder> {
    private Context mContext;
    private List<ImageBean> dataList = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ImageBean> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_img, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        Glide.with(mContext)
                .load(dataList.get(position).getImgPath())
                .into(holder.imageView);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        holder.tv_time.setText(simpleDateFormat2.format(new Date(dataList.get(position).getTime())));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_time;

        private RecyclerHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}

