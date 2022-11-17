package com.example.paroapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.R;
import com.example.paroapplication.bean.ActionShow;

import java.util.ArrayList;
import java.util.List;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.RecyclerHolder> {
    private Context mContext;
    private List<ActionShow> dataList = new ArrayList<>();

    public ActionAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ActionShow> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener {
        void onItemClick(int pos);
        void onDelete(int pos);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.id_rv_item_action, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textView.setText(dataList.get(position).getActionName());
        Drawable color = null;
        switch (dataList.get(position).getType()) {
            case 1:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_1);
                break;
            case 2:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_2);
                break;
            case 3:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_3);
                break;
            case 4:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_4);
                break;
            case 5:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_5);
                break;
            case 6:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_6);
                break;
            case 7:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_7);
                break;
            case 8:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_8);
                break;
            case 9:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_9);
                break;
            case 10:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_10);
                break;
            case 11:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_11);
                break;
            case 12:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_12);

                break;
            case 13:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_13);
                break;
            case 14:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_14);
                break;
            case 15:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_15);
                break;
            case 16:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_16);
                break;
            case 17:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_17);
                break;
            case 18:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_18);
                break;
            case 19:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_19);
                break;
            case 20:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_20);
                break;

            case 21:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_21);
                break;
            case 22:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_22);
                break;
            case 23:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_23);
                break;
            case 24:
                color=mContext.getResources().getDrawable(R.drawable.ic_baseline_circle_24);
                break;

            default:
                break;



        }
        if (null!=color){
            holder.imageView.setImageDrawable(color);
        }
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=onItemClickListener){
                    onItemClickListener.onItemClick(position);

                }
            }
        });
       holder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               new AlertDialog.Builder(mContext).
                       setTitle(R.string.is_delete).
                       setMessage(R.string.sure_delete).

                       setPositiveButton(R.string.no_delete, new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               // TODO Auto-generated method stub
                               if (null!=onItemClickListener){
                                   onItemClickListener.onDelete(position);
                               }
                               dialog.dismiss();
                           }
                       }).
                       setNegativeButton(R.string.yes_delete, new DialogInterface.OnClickListener() {

                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                           }
                       }).
                       create().show();

               return true;
           }
       });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayout llItem;

        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_type_name);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}

