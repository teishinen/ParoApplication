package com.example.paroapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.Action.ActionViewModel;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.adapter.ActionAdapter;
import com.example.paroapplication.bean.ActionShow;
import com.example.paroapplication.dialog.AddActionDialog;
import com.example.paroapplication.dialog.SelectColorDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddRecordActivity extends AppCompatActivity {
    private ImageView imageView4;
    private TextView tvName;
    private RecyclerView rvRecord;
    private LinearLayout llAdd;
    private ArrayList<ActionShow> mList = new ArrayList<>();
    private ActionAdapter adapter;
    private TextView tvBack;
    private TextView tvFinish;
    private Staff staff;
    private long startTime;
    private long endTime;
    private Patient patient;
    private int beforePoint;
    private RecordViewModel recordViewModel;
    private PatientViewModel patientViewModel;

    private ActionViewModel actionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recoird);
        getSupportActionBar().hide();
        staff= (Staff) getIntent().getSerializableExtra("staff");
        patient= (Patient) getIntent().getSerializableExtra("selectPatient");
        beforePoint= getIntent().getIntExtra("assessmentPoints",0);
        startTime= getIntent().getLongExtra("startTime",0);
        endTime= getIntent().getLongExtra("endTime",0);
        recordViewModel = new ViewModelProvider(AddRecordActivity.this, new ViewModelProvider.AndroidViewModelFactory(AddRecordActivity.this.getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(AddRecordActivity.this, new ViewModelProvider.AndroidViewModelFactory(AddRecordActivity.this.getApplication())).get(PatientViewModel.class);
        actionViewModel = new ViewModelProvider(AddRecordActivity.this, new ViewModelProvider.AndroidViewModelFactory(AddRecordActivity.this.getApplication())).get(ActionViewModel.class);
        initView();
        initListener();
        initData();


    }

    private void initData() {
        mList.add(new ActionShow(" パロを撫でる ", 1));
        mList.add(new ActionShow("パロを抱きしめる ", 2));
        mList.add(new ActionShow("パロを遠さける ", 3));
        mList.add(new ActionShow("パロをじっと見つめる ", 4));
        mList.add(new ActionShow("パロを叩く", 5));
        mList.add(new ActionShow(" 笑顔が見られる ", 6));

        adapter = new ActionAdapter(AddRecordActivity.this);
        adapter.setOnItemClickListener(new ActionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                SelectColorDialog selectColorDialog = new SelectColorDialog(AddRecordActivity.this, mList.get(pos), pos);
                selectColorDialog.setColor((color, pos1) -> {
                    mList.get(pos1).setType(color.getType());
                    adapter.setData(mList);

                });
                selectColorDialog.show();

            }

            @Override
            public void onDelete(int pos) {
                mList.remove(pos);
                adapter.setData(mList);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(AddRecordActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        rvRecord.setLayoutManager(layoutManager);
        rvRecord.setAdapter(adapter);
        adapter.setData(mList);
    }

    private void initListener() {
        llAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActionDialog addActionDialog = new AddActionDialog(AddRecordActivity.this);
                addActionDialog.setAction(actionName -> {
                    mList.add(new ActionShow(actionName, 1));
                    adapter.setData(mList);
                });
                addActionDialog.show();
            }
        });
        tvFinish.setOnClickListener(view -> {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
            Record record=new Record(String.valueOf(startTime),startTime,endTime,simpleDateFormatDate.format(new Date(startTime)),String.valueOf(patient.getId()),String.valueOf(staff.getId()),beforePoint);
            recordViewModel.insertRecord(record);
            recordViewModel.findHistoryDetail(String.valueOf(startTime)).observe((LifecycleOwner)this, new Observer<List<Record>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(List<Record> records) {



                    if(records.size()==1){
                        Record record = records.get(0);
//                        patient.setPoint(patient.getPoint()+point);
////                        patientViewModel.updatePatients(patient);
                        for (int i = 0; i <mList.size() ; i++) {
                            Action action=new Action(record.getRecordId(),mList.get(i).getActionName(),mList.get(i).getType(),patient.getPatientId(),simpleDateFormatDate.format(new Date(startTime)),startTime,endTime);
                            actionViewModel.insertStaff(action);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(AddRecordActivity.this,EvaluateAfterActivity.class);
                                intent.putExtra("record",record);
                                startActivity(intent);

                                finish();
                            }
                        });

                    }
                }
            });
        });



    }

    private void initView() {
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        tvName = (TextView) findViewById(R.id.tv_name);
        rvRecord = (RecyclerView) findViewById(R.id.rv_record);
        llAdd = (LinearLayout) findViewById(R.id.ll_add);
        tvBack = (TextView) findViewById(R.id.tv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
    }
}