package com.example.paroapplication.bean;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.RecordDatabase.Record;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.List;

public class ChartShowBean {
    private String time;
    private List<Action> mlist=new ArrayList<>();
    private List<Record> records=new ArrayList<>();

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setMlist(List<Action> mlist) {
        this.mlist = mlist;
    }

    public List<Action> getMlist() {
        return mlist;
    }
}
