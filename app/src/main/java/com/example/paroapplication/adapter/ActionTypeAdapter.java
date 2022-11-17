package com.example.paroapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paroapplication.Action.Action;
import com.example.paroapplication.ImageDao.ImageBean;
import com.example.paroapplication.R;
import com.example.paroapplication.bean.ActionShow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActionTypeAdapter extends RecyclerView.Adapter<ActionTypeAdapter.RecyclerHolder> {
    private Context mContext;
    private List<ActionShow> dataList = new ArrayList<>();

    public ActionTypeAdapter(Context context, List<ActionShow> actionShows) {
        this.mContext = context;
        this.dataList.clear();
        this.dataList.addAll(actionShows);
    }

    public void setData(List<ActionShow> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        String actionName = dataList.get(position).getActionName();
        holder.tv_name.setText(actionName);
        int i = addColor(dataList.get(position).getType());
        holder.imageView.setBackgroundColor(i);
    }
    private int addColor(int type) {
        int color = 0;
        switch (type) {
            case 1:
                color = mContext.getResources().getColor(R.color.color1);
                break;
            case 2:
                color = mContext.getResources().getColor(R.color.color2);
                break;
            case 3:
                color = mContext.getResources().getColor(R.color.color3);
                break;
            case 4:
                color = mContext.getResources().getColor(R.color.color4);
                break;
            case 5:
                color = mContext.getResources().getColor(R.color.color5);
                break;
            case 6:
                color = mContext.getResources().getColor(R.color.color6);
                break;
            case 7:
                color = mContext.getResources().getColor(R.color.color7);
                break;
            case 8:
                color = mContext.getResources().getColor(R.color.color8);
                break;
            case 9:
                color = mContext.getResources().getColor(R.color.color9);
                break;
            case 10:
                color = mContext.getResources().getColor(R.color.color10);
                break;
            case 11:
                color = mContext.getResources().getColor(R.color.color11);
                break;
            case 12:
                color = mContext.getResources().getColor(R.color.color12);
                break;
            case 13:
                color = mContext.getResources().getColor(R.color.color13);
                break;
            case 14:
                color = mContext.getResources().getColor(R.color.color14);
                break;
            case 15:
                color = mContext.getResources().getColor(R.color.color15);
                break;
            case 16:
                color = mContext.getResources().getColor(R.color.color16);
                break;
            case 17:
                color = mContext.getResources().getColor(R.color.color17);
                break;
            case 18:
                color = mContext.getResources().getColor(R.color.color18);
                break;
            case 19:
                color = mContext.getResources().getColor(R.color.color19);
                break;
            case 20:
                color = mContext.getResources().getColor(R.color.color20);
                break;
            case 21:
                color = mContext.getResources().getColor(R.color.color21);
                break;
            case 22:
                color = mContext.getResources().getColor(R.color.color22);
                break;
            case 23:
                color = mContext.getResources().getColor(R.color.color23);
                break;
            case 24:
                color = mContext.getResources().getColor(R.color.color24);
                break;
        }

        return color;

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        View imageView;
        TextView tv_name;

        private RecyclerHolder(View itemView) {
            super(itemView);
            imageView = (View) itemView.findViewById(R.id.v_color);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}

