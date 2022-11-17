package com.example.paroapplication.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.HistoryRecordAdapter;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.StaffDatabase.Staff;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment {
    Button buttonStartTheTherapy;
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    RecyclerView recyclerView;
    HistoryRecordAdapter historyRecordAdapter;
    List<Record> allRecords;
    LiveData<List<Record>> filteredRecords;
    private Staff staff;
    private String time;

    public HomeFragment(Staff staff) {
        this.staff=staff;
    }
    public  interface  StartTheTherapy{
        void  start();
    }
    private  StartTheTherapy startTheTherapy;

    public void setStartTheTherapy(StartTheTherapy startTheTherapy) {
        this.startTheTherapy = startTheTherapy;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PatientViewModel.class);

        TextView textView;
        textView = requireActivity().findViewById(R.id.textView5);
        textView.setText(staff.getStaffName());

        //set the button
        buttonStartTheTherapy = requireActivity().findViewById(R.id.buttonStartTheTherapy);
        buttonStartTheTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTheTherapy!=null){
                    startTheTherapy.start();
                }

            }
        });

        //set recycler view
        recyclerView = requireActivity().findViewById(R.id.recyclerViewInHome);
        LinearLayoutManager layoutPatientRecord = new LinearLayoutManager(requireContext()){
            @Override
            public  boolean canScrollVertically(){
                return false;
            }
        };
        layoutPatientRecord.setStackFromEnd(true);
        layoutPatientRecord.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutPatientRecord);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        historyRecordAdapter = new HistoryRecordAdapter(patientViewModel,recordViewModel,simpleDateFormat.format(new Date()));
        recyclerView.setAdapter(historyRecordAdapter);

        //----Data change monitoring----
        filteredRecords = recordViewModel.findHistoryRecordByTime(simpleDateFormat.format(new Date()));
        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                recyclerView.smoothScrollBy(0, 200);
                historyRecordAdapter.submitList(records);
                historyRecordAdapter.notifyDataSetChanged();
            }
        });
        CalendarView calendarView = requireActivity().findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            String moth="";
            if (month+1<10){
                moth="0"+(month+1);
            }else {
                moth=String.valueOf(month+1);
            }
            String time;
            if (dayOfMonth>10){
                 time=year+"-"+moth+"-"+dayOfMonth;
            }else {
                time=year+"-"+moth+"-0"+dayOfMonth;
            }
            time=time;
            historyRecordAdapter.setTime(time);
            filteredRecords = recordViewModel.findHistoryRecordByTime(time);
            filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onChanged(List<Record> records) {
                    allRecords = records;
                    recyclerView.smoothScrollBy(0, 200);
                    historyRecordAdapter.submitList(records);
                    historyRecordAdapter.notifyDataSetChanged();
                }
            });
        });
    }
}
