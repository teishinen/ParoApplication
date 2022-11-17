package com.example.paroapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.bean.ActionShow;
import com.example.paroapplication.bean.ChartShowBean;
import com.example.paroapplication.view.MyMarkerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.RecyclerHolder> {
    private Context mContext;
    private List<ChartShowBean> dataList = new ArrayList<>();

    public ChartAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ChartShowBean> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chart, parent, false);
        return new RecyclerHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        ChartShowBean chartShowBean = dataList.get(position);
        holder.tvTime.setText(chartShowBean.getTime());
        List<Action> mlist = chartShowBean.getMlist();
        Map<String, List<Action>> acitonMap = mlist.stream().collect(Collectors.groupingBy(Action::getActionName));
        List<ActionShow> actionShows = new ArrayList<>();
        for (String key : acitonMap.keySet()) {
            List<Action> actions = acitonMap.get(key);
            if (actions.size() > 0) {
                ActionShow actionShow = new ActionShow(key, actions.get(0).getType());
                actionShow.setCount(actions.size());
                long time = 0;
                for (int i = 0; i < actions.size(); i++) {
                    time = time + actions.get(i).getEndTime() - actions.get(i).getStartTime();
                }
                actionShow.setTime(time);
                actionShows.add(actionShow);
            }
        }
        initChartHcTime(holder.hcTime, actionShows);
        initChartHcAccount(holder.hcAccount, actionShows);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.llBefore.setLayoutManager(linearLayoutManager);
        holder.llBefore.setAdapter(new ChartChangeAdapter(mContext,chartShowBean.getRecords()));

        holder.rvTypeAccount.setAdapter(new ActionTypeAdapter(mContext, actionShows));
        holder.rvTypeAccount.setLayoutManager(new GridLayoutManager(mContext, 3));

    }




    private void initChartHcAccount(BarChart hcAccount, List<ActionShow> actionShows) {
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        hcAccount.setMaxVisibleValueCount(60);
        hcAccount.getDescription().setEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        hcAccount.setPinchZoom(false);

        hcAccount.setDrawBarShadow(false);
        hcAccount.setDrawGridBackground(false);

        XAxis xAxis = hcAccount.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        hcAccount.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        hcAccount.animateY(1500);

        hcAccount.getLegend().setEnabled(false);
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<Integer> color = new ArrayList<>();
        for (int i = 0; i < actionShows.size(); i++) {
            values.add(new BarEntry(i, actionShows.get(i).getCount()));

            color.add(addColor(actionShows.get(i).getType()));

        }

        BarDataSet set1;

        if (hcAccount.getData() != null &&
                hcAccount.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) hcAccount.getData().getDataSetByIndex(0);
            set1.setValues(values);
            hcAccount.getData().notifyDataChanged();
            hcAccount.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");

            set1.setColors(color);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            hcAccount.setData(data);
            hcAccount.setFitBars(true);
        }

        hcAccount.invalidate();
    }

    private void initChartHcTime(BarChart hcTime, List<ActionShow> actionShows) {
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        hcTime.setMaxVisibleValueCount(60);
        hcTime.getDescription().setEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        hcTime.setPinchZoom(false);

        hcTime.setDrawBarShadow(false);
        hcTime.setDrawGridBackground(false);

        XAxis xAxis = hcTime.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        hcTime.getAxisLeft().setDrawGridLines(false);

        // add a nice and smooth animation
        hcTime.animateY(1500);

        hcTime.getLegend().setEnabled(false);
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<Integer> color = new ArrayList<>();
        for (int i = 0; i < actionShows.size(); i++) {
            values.add(new BarEntry(i, actionShows.get(i).getTime() / 1000));

            color.add(addColor(actionShows.get(i).getType()));

        }

        BarDataSet set1;

        if (hcTime.getData() != null &&
                hcTime.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) hcTime.getData().getDataSetByIndex(0);
            set1.setValues(values);
            hcTime.getData().notifyDataChanged();
            hcTime.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");

            set1.setColors(color);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            hcTime.setData(data);
            hcTime.setFitBars(true);
        }

        hcTime.invalidate();
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
        BarChart hcTime;
        BarChart hcAccount;
        RecyclerView llBefore;
        RecyclerView rvType;
        RecyclerView rvTypeAccount;
        TextView tvTime;


        private RecyclerHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            hcTime = (BarChart) itemView.findViewById(R.id.hc_time);
            rvType = (RecyclerView) itemView.findViewById(R.id.rv_type);
            rvTypeAccount = (RecyclerView) itemView.findViewById(R.id.rv_type_account);
            hcAccount = (BarChart) itemView.findViewById(R.id.hc_account);
            llBefore = (RecyclerView) itemView.findViewById(R.id.ll_before);


        }
    }
}

