package com.example.paroapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paroapplication.ImageDao.ImageBean;
import com.example.paroapplication.R;
import com.example.paroapplication.bean.Color;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageColorAdapter extends RecyclerView.Adapter<ImageColorAdapter.RecyclerHolder> {
    private Context mContext;
    private List<Color> dataList = new ArrayList<>();

    public ImageColorAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Color> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }
    private  SelectColor selectColor;
    public  interface  SelectColor{
        void SelectColor(Color color,int position);
    }

    public void setSelectColor(SelectColor selectColor) {
        this.selectColor = selectColor;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_img_color, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(mContext)
                .load(dataList.get(position).getColor())
                .into(holder.imageView);
        if (dataList.get(position).isSelect){
            holder.imageView.setBackgroundResource(R.drawable.ic_baseline_circle_26);
        }else {
            holder.imageView.setBackgroundResource(R.color.white);

        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (null!=selectColor){
                  selectColor.SelectColor(dataList.get(position),position);
              }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);

        }
    }
}

