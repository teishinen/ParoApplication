package com.example.paroapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.StaffDatabase.Staff;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    private ImageView imageView4;
    private TextView tvFinish;
    private TextView tvStart;
    private Staff staff;
    private Patient patient;
    private int assessmentPoints;
    private long startTime;
    private long endTime;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
    int currentTime = 0;
    final Handler mCalHandler = new Handler(Looper.getMainLooper());
    int loopTime = 0;
    final Runnable mTicker = new Runnable() {
        @Override
        public void run() {
            loopTime++;
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);
            mCalHandler.postAtTime(mTicker, next);
            currentTime += 1000;
            int hours = (currentTime) / (1000 * 60 * 60);


            int minutes = ((currentTime - (1000 * 60 * 60)*hours) / (1000 * 60));
            int seconds = (currentTime -(1000 * 60)*minutes-(1000 * 60 * 60)*hours) / 1000;
            String time = (hours < 10 ? ("0" + hours) : hours) + ":" + (minutes < 10 ? ("0" + minutes) : minutes) + ":"+ (seconds < 10 ? ("0" + seconds) : seconds);

            tvTime.setText(time);

        }
    };
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();
        staff = (Staff) getIntent().getSerializableExtra("staff");
        patient = (Patient) getIntent().getSerializableExtra("selectPatient");
        assessmentPoints = getIntent().getIntExtra("assessmentPoints", 0);

        /* 显示App icon左侧的back键 */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalHandler.removeCallbacks(mTicker);
                endTime=System.currentTimeMillis();
                Intent intent = new Intent(StartActivity.this, AddRecordActivity.class);
                intent.putExtra("staff",staff);
                intent.putExtra("selectPatient",patient);
                intent.putExtra("assessmentPoints",assessmentPoints);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);

                startActivity(intent);
                finish();
            }
        });
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime=System.currentTimeMillis();
                mCalHandler.post(mTicker);
            }
        });


    }


    private void initView() {
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvTime = (TextView) findViewById(R.id.tv_time);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}