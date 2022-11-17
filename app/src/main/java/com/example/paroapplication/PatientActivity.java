package com.example.paroapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.paroapplication.Fragment.RegisterFragment;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.RecordDatabase.PatientRecordAdapter;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientAge;
import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientId;
import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientName;
import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientSex;

public class PatientActivity extends AppCompatActivity {
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    RecyclerView recyclerViewPatientRecord;
    PatientRecordAdapter patientRecordAdapter;
    List<Record> allRecords;

    LiveData<List<Record>> filteredRecords;

    TextView textViewPatientName, textViewPatientId, textViewPatientAge, textViewPatientSex, textViewWeeklyCareHours, textViewPatientSymptom;
    public double careHours;
    private Patient patient;
    private String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_patient);
        patient= (Patient) getIntent().getSerializableExtra("patient");
        time=getIntent().getStringExtra("time");
        recordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(PatientViewModel.class);



        Toolbar mToolBar = findViewById(R.id.toolbarInPatientFragment);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        Button editPatient = findViewById(R.id.buttonEditInPatientFragment);
        editPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientActivity.this,ChartActivity.class);
                intent.putExtra("patient",patient);
                startActivity(intent);

            }

        });

        textViewPatientName = findViewById(R.id.textViewPatientNameInPatientFragment);
        textViewPatientId = findViewById(R.id.textViewPatientIdInPatientFragment);
        textViewPatientAge = findViewById(R.id.textViewPatientAgeInPatientFragment);
        textViewPatientSex = findViewById(R.id.textViewPatientSexInPatientFragment);
        textViewWeeklyCareHours = findViewById(R.id.textViewWeeklyCareHoursInPatientFragment);
        textViewPatientSymptom = findViewById(R.id.textViewPatientSymptomInPatientFragment);
        recyclerViewPatientRecord = findViewById(R.id.redyclerViewInPatientFragment);
        LinearLayoutManager layoutPatientRecord = new LinearLayoutManager(this);
        layoutPatientRecord.setStackFromEnd(true);
        layoutPatientRecord.setReverseLayout(true);
        recyclerViewPatientRecord.setLayoutManager(layoutPatientRecord);
        patientRecordAdapter = new PatientRecordAdapter(patientViewModel,recordViewModel,patient);
        recyclerViewPatientRecord.setAdapter(patientRecordAdapter);

        //----Data change monitoring----
        if (TextUtils.isEmpty(time)){
            filteredRecords = recordViewModel.findRecordsByPatientName(String.valueOf(patient.getId()));
        }else {
            filteredRecords = recordViewModel.findRecordsByPatientNameAndTime(String.valueOf(patient.getId()),time);
        }
        filteredRecords.observe(this, new Observer<List<Record>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                recyclerViewPatientRecord.smoothScrollBy(0, 200);
                patientRecordAdapter.submitList(records);
                patientRecordAdapter.notifyDataSetChanged();
            }
        });

        try {
            careHours = (double) recordViewModel.getWeeklyCareHours(String.valueOf(patient.getId())) / (1000 * 60 * 60);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        setTextView();
    }


    private void setTextView() {
        String name=patient.getPatientName();
        String id=patient.getPatientId();
        String age=patient.getPatientAge();
        String sex=patient.getPatientSex();

        textViewPatientName.setText(name);
        textViewPatientId.setText(id);
        textViewPatientAge.setText(age);
        textViewPatientSex.setText(sex);
        textViewWeeklyCareHours.setText(String.format("%.2f", careHours) + "h");
        TextView textViewId,textViewName,textViewAge,textViewSex,textViewProblemBehavior;
        textViewId = findViewById(R.id.textView16);
        textViewName = findViewById(R.id.textView20);
        textViewAge = findViewById(R.id.textView4);
        textViewSex = findViewById(R.id.textView);
        textViewProblemBehavior = findViewById(R.id.textView3);
        ImageView imageView = findViewById(R.id.imageView3);
        if(sex.equals(getString(R.string.female))){
            textViewName.setTextColor(Color.parseColor("#D38AAD"));
            textViewAge.setTextColor(Color.parseColor("#D38AAD"));
            textViewSex.setTextColor(Color.parseColor("#D38AAD"));
            textViewId.setTextColor(Color.parseColor("#D38AAD"));
            textViewProblemBehavior.setTextColor(Color.parseColor("#D38AAD"));
            imageView.setImageResource(R.drawable.ic_old_woman);
        }else {
            textViewName.setTextColor(Color.parseColor("#4c84b7"));
            textViewAge.setTextColor(Color.parseColor("#4c84b7"));
            textViewSex.setTextColor(Color.parseColor("#4c84b7"));
            textViewId.setTextColor(Color.parseColor("#4c84b7"));
            textViewProblemBehavior.setTextColor(Color.parseColor("#4c84b7"));
            imageView.setImageResource(R.drawable.ic_old_man);
        }
    }


    }
