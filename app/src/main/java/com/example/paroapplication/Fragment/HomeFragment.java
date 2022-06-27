package com.example.paroapplication.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.HistoryRecordAdapter;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.List;


public class HomeFragment extends Fragment {
    Button buttonStartTheTherapy;
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    RecyclerView recyclerView;
    HistoryRecordAdapter historyRecordAdapter;
    List<Record> allRecords;
    LiveData<List<Record>> filteredRecords;

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

        // TODO: Use the ViewModel
        //set textView
        TextView textView;
        textView = requireActivity().findViewById(R.id.textView5);
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);        //下線を設置する
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
//                controller.navigate(R.id.action_dataFragment_to_staffFragment);
            }
        });
        //set the button
        buttonStartTheTherapy = requireActivity().findViewById(R.id.buttonStartTheTherapy);
        buttonStartTheTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_selectPatientFragment);
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
        historyRecordAdapter = new HistoryRecordAdapter();
        recyclerView.setAdapter(historyRecordAdapter);

        //----Data change monitoring----
        filteredRecords = recordViewModel.findHistoryRecord();
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

    }
}
