package com.example.paroapplication.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.bean.ActionShow;
import com.example.paroapplication.view.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ChartChangeAdapter extends RecyclerView.Adapter<ChartChangeAdapter.RecyclerHolder> {
    private Context mContext;
    private List<Record> dataList = new ArrayList<>();

    public ChartChangeAdapter(Context context, List<Record> records) {
        this.mContext = context;
        dataList.addAll(records);
    }


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);

        void onDelete(int pos);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.id_rv_item_chart, parent, false);
        return new RecyclerHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {

        initChartHcLine(holder.lineChart, dataList.get(position));
        int count=position+1;
        holder.textView.setText(count+"回目");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initChartHcLine(LineChart llBefore, Record records) {

        {

            // background color
            llBefore.setBackgroundColor(Color.WHITE);

            // disable description text
            llBefore.getDescription().setEnabled(false);

            // enable touch gestures
            llBefore.setTouchEnabled(true);

            // set listeners
            llBefore.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {

                }

                @Override
                public void onNothingSelected() {

                }
            });
            llBefore.setDrawGridBackground(false);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(llBefore);
            llBefore.setMarker(mv);

            // enable scaling and dragging
            llBefore.setDragEnabled(true);
            llBefore.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            llBefore.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = llBefore.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0, records.getBeforePoint()));

        entries.add(new Entry(1, records.getAfterPoint()));

        Entry max = entries.stream().max(Comparator.comparing(Entry::getY)).get();
        Entry min = entries.stream().min(Comparator.comparing(Entry::getY)).get();

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = llBefore.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            llBefore.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(max.getY() + 5);
            yAxis.setAxisMinimum(min.getY() - 5);

        }


        // draw points over time
        llBefore.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = llBefore.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
        setData(entries, llBefore);

        llBefore.invalidate();
    }

    private void setData(ArrayList<Entry> entries, LineChart llBefore) {


        LineDataSet set1;

        if (llBefore.getData() != null &&
                llBefore.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) llBefore.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            set1.notifyDataSetChanged();
            llBefore.getData().notifyDataChanged();
            llBefore.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(entries, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return llBefore.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            llBefore.setData(data);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LineChart lineChart;


        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_count);
            lineChart = (LineChart) itemView.findViewById(R.id.ll_before);

        }
    }
}

