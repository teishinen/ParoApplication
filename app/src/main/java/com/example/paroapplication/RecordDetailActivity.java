package com.example.paroapplication;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.Action.ActionViewModel;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.adapter.RecordAdapter;
import com.example.paroapplication.bean.PatientSelect;

import java.util.ArrayList;
import java.util.List;

public class RecordDetailActivity extends AppCompatActivity {

    private ImageView ivBack;
    private RecyclerView rvContent;
    private Patient patient;
    private Record record;
    private ArrayList<Action> mlist = new ArrayList<>();
    private ActionViewModel actionViewModel;
    private ImageView ivImg;
    private TextView tvId;
    private TextView tvAge;
    private TextView tvDuration;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_record_detail);
        initView();
        actionViewModel = new ViewModelProvider(RecordDetailActivity.this, new ViewModelProvider.AndroidViewModelFactory(RecordDetailActivity.this.getApplication())).get(ActionViewModel.class);
        patient = (Patient) getIntent().getSerializableExtra("patient");
        record = (Record) getIntent().getSerializableExtra("record");
        ivBack.setOnClickListener(view -> {
            finish();
        });
        String patientSex = patient.getPatientSex();

        String man = getResources().getString(R.string.male);
        String color;

        if (patientSex.equals(man)){
            ivImg.setImageResource(R.drawable.ic_old_man);
            color = "#4c84b7";
        }else{
            ivImg.setImageResource(R.drawable.ic_old_woman);
            color = "#D38AAD";
        }
        tvId.setTextColor(Color.parseColor(color));
        tvAge.setTextColor(Color.parseColor(color));
        tvId.setText(patient.getPatientId());
        tvAge.setText(patient.getPatientAge());
        tvTime.setText(record.getTime());
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        tvDuration.setText(""+(record.getAfterPoint()-record.getBeforePoint()));
        actionViewModel.findRecordsByPatientRecordId(String.valueOf(record.getRecordId())).observe(this, actions -> {
            mlist.addAll(actions);
            RecordAdapter recordAdapter=new RecordAdapter(mlist,record,RecordDetailActivity.this);
            rvContent.setAdapter(recordAdapter);
        });


    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        ivImg = (ImageView) findViewById(R.id.iv_img);
        tvId = (TextView) findViewById(R.id.tv_id);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        tvTime = (TextView) findViewById(R.id.tv_time);
    }
}