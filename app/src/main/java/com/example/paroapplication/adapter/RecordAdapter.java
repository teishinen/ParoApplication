package com.example.paroapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.Record;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecyclerHolder> {
    private  Record record;

    private Context mContext;
    private List<Action> dataList = new ArrayList<>();



    public RecordAdapter(ArrayList<Action> mlist, Record record,Context context) {
        this.dataList.addAll(mlist);
        this.record=record;
        this.mContext=context;

    }



    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.id_rv_item_layout, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        Action action = dataList.get(position);
        holder.tvTime.setText(record.getTime());
        holder.tvAction.setText(action.getActionName());
        holder.tvFraction.setText(""+record.getBeforePoint()+"->"+record.getAfterPoint());
        holder.tvDifference.setText(""+(record.getAfterPoint()-record.getBeforePoint()));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvAction;
        TextView tvFraction;
        TextView tvDifference;

        private RecyclerHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvAction = (TextView) itemView.findViewById(R.id.tv_action);
            tvFraction = (TextView) itemView.findViewById(R.id.tv_fraction);
            tvDifference = (TextView) itemView.findViewById(R.id.tv_difference);
        }
    }
}

