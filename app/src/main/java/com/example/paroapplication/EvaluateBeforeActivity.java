package com.example.paroapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.StaffDatabase.Staff;

public class EvaluateBeforeActivity extends AppCompatActivity {

    private Toolbar toolbarInPatientFragment;
    private ImageView imageView4;
    private TextView tvBack;
    private TextView next;
    private Staff staff;
    private Patient patient;
    private LinearLayout llReduce1;
    private LinearLayout llRecord2;
    private LinearLayout llZero;
    private LinearLayout llAddOne;
    private LinearLayout llTwo;
    private int assessmentPoints = 0;
    private TextView tvReduce1;
    private TextView tvReduce2;
    private TextView tvZero;
    private TextView tvAdd1;
    private TextView tvAdd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_evaluate_before);

        staff = (Staff) getIntent().getSerializableExtra("staff");
        patient = (Patient) getIntent().getSerializableExtra("selectPatient");
        initView();

        toolbarInPatientFragment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(view -> {
            Intent intent = new Intent(EvaluateBeforeActivity.this, StartActivity.class);
            intent.putExtra("selectPatient", patient);
            intent.putExtra("staff", staff);
            intent.putExtra("assessmentPoints", assessmentPoints);
            startActivity(intent);
            finish();
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llReduce1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentPoints = -1;
                setBg(tvReduce1);
            }
        });
        llRecord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentPoints = -2;
                setBg(tvReduce2);
            }
        });
        llZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentPoints = 0;
                setBg(tvZero);
            }
        });
        llAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentPoints = 1;
                setBg(tvAdd1);
            }
        });
        llTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assessmentPoints = 2;
                setBg(tvAdd2);
            }
        });
    }

    private void setBg(TextView view) {
        tvAdd1.setBackground(getResources().getDrawable(R.drawable.bg_evaluate_before));
        tvAdd2.setBackground(getResources().getDrawable(R.drawable.bg_evaluate_before));
        tvZero.setBackground(getResources().getDrawable(R.drawable.bg_evaluate_before));
        tvReduce1.setBackground(getResources().getDrawable(R.drawable.bg_evaluate_before));
        tvReduce2.setBackground(getResources().getDrawable(R.drawable.bg_evaluate_before));
        view.setBackground(getResources().getDrawable(R.drawable.bg_evaluate_before_select));

    }

    private void initView() {
        toolbarInPatientFragment = (Toolbar) findViewById(R.id.toolbarInPatientFragment);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        tvBack = (TextView) findViewById(R.id.tv_back);
        next = (TextView) findViewById(R.id.next);
        llReduce1 = (LinearLayout) findViewById(R.id.ll_reduce_1);
        llRecord2 = (LinearLayout) findViewById(R.id.ll_record_2);
        llZero = (LinearLayout) findViewById(R.id.ll_zero);
        llAddOne = (LinearLayout) findViewById(R.id.ll_add_one);
        llTwo = (LinearLayout) findViewById(R.id.ll_two);
        tvReduce1 = (TextView) findViewById(R.id.tv_reduce_1);
        tvReduce2 = (TextView) findViewById(R.id.tv_reduce_2);
        tvZero = (TextView) findViewById(R.id.tv_zero);
        tvAdd1 = (TextView) findViewById(R.id.tv_add_1);
        tvAdd2 = (TextView) findViewById(R.id.tv_add_2);
    }
}