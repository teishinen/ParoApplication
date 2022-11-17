package com.example.paroapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.Action.ActionViewModel;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.adapter.ChartAdapter;
import com.example.paroapplication.bean.ChartShowBean;
import com.example.paroapplication.bean.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartActivity extends AppCompatActivity {

    private RecyclerView rvContent;
    private RecordViewModel recordViewModel;
    private Patient patient;
    private LiveData<List<Record>> filteredRecords;
    private LiveData<List<Action>> filteredStaff ;
    private ActionViewModel actionViewModel;
    private List<Record> allRecords=new ArrayList<>();
    private List<Action> allActionList=new ArrayList<>();
    private List<ChartShowBean> showBeans=new ArrayList<>();
    private ChartAdapter chartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        recordViewModel = new ViewModelProvider(ChartActivity.this, new ViewModelProvider.AndroidViewModelFactory(ChartActivity.this.getApplication())).get(RecordViewModel.class);
        actionViewModel = new ViewModelProvider(ChartActivity.this, new ViewModelProvider.AndroidViewModelFactory(ChartActivity.this.getApplication())).get(ActionViewModel.class);
        initView();
        chartAdapter=new ChartAdapter(ChartActivity.this);
        rvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvContent);
        rvContent.setAdapter(chartAdapter);

        initData();

    }

    private void initData() {
        patient= (Patient) getIntent().getSerializableExtra("patient");
        filteredRecords = recordViewModel.findRecordsByPatientName(String.valueOf(patient.getId()));
        filteredRecords.observe(this, new Observer<List<Record>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                filteredStaff=actionViewModel.getActionList(String.valueOf(patient.getId()));
                filteredStaff.observe(ChartActivity.this, new Observer<List<Action>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Action> actions) {
                        allActionList = actions;
                        sortData();
                    }
                });
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sortData() {
        Map<String, List<Record>> collect = allRecords.stream().collect(Collectors.groupingBy(Record::getTime));
        Map<String, List<Action>> acitonMap = allActionList.stream().collect(Collectors.groupingBy(Action::getTime));
        for (String key : collect.keySet()) {
             ChartShowBean chartShowBean=new ChartShowBean();
             List<Record> records = collect.get(key);
             if (null!=records&&records.size()>0){
                 chartShowBean.setRecords(records);
             }
            List<Action> actions = acitonMap.get(key);
            if (null!=actions&&actions.size()>0){
                chartShowBean.setMlist(actions);
            }
             chartShowBean.setTime(key);
             showBeans.add(chartShowBean);
        }
        chartAdapter.setData(showBeans);


    }

    private void initView() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
    }
}