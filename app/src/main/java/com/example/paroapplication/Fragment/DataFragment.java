package com.example.paroapplication.Fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.HistoryRecordAdapter;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataFragment extends Fragment {
    RecordViewModel recordViewModel;
    LiveData<List<Record>> filteredRecords;
    List<Record> allRecords;
    HistoryRecordAdapter historyRecordAdapter;
    Spinner spinnerYear,spinnerMonth,spinnerDay;
    RecyclerView recyclerViewRecord;
    Date date = new Date(System.currentTimeMillis());
    String[] historyYear,historyMonth,historyDay;
    String selectedYear,selectedMonth,selectedDay;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.data_fragment, container, false);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        // TODO: Use the ViewModel

        spinnerYear = requireActivity().findViewById(R.id.spinnerRecordYearsInDataFragment);
        spinnerMonth = requireActivity().findViewById(R.id.spinnerRecordMonthInDataFragment);
        spinnerDay = requireActivity().findViewById(R.id.spinnerRecordDayInDataFragment);
        //----set recycler view----
        recyclerViewRecord = requireView().findViewById(R.id.recyclerViewRecordInDataFragment);
        LinearLayoutManager layoutRecord = new LinearLayoutManager(requireContext());
        layoutRecord.setStackFromEnd(true);
        layoutRecord.setReverseLayout(true);
        recyclerViewRecord.setLayoutManager(layoutRecord);
        historyRecordAdapter = new HistoryRecordAdapter();
        recyclerViewRecord.setAdapter(historyRecordAdapter);

        //----Data change monitoring----
        filteredRecords = recordViewModel.findHistoryRecord();
        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                historyRecordAdapter.submitList(records);
                historyRecordAdapter.notifyDataSetChanged();
            }
        });

        try {
            historyYear = new String[recordViewModel.findHistoryRecordYear().length + 1];
            historyYear[0] = "all";
            System.arraycopy(recordViewModel.findHistoryRecordYear(),0,historyYear,1,recordViewModel.findHistoryRecordYear().length);
            setSpinnerYear(historyYear);
            Log.d("TAG", "spinnerYear: " + Arrays.toString(recordViewModel.findHistoryRecordYear()));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }




        setMenu();
    }

    private void setSpinnerYear(String[] historyYear) throws ExecutionException, InterruptedException {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, historyYear);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = adapter.getItem(position);
                Log.d("TAG", "spinnerYear: selectedItem" + selectedYear);
                if(position == 0){
                    filteredRecords = recordViewModel.findHistoryRecord();
                }else {
                    try {
                        filteredRecords = recordViewModel.findHistoryRecordByYear(selectedYear);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Record> records) {
                        allRecords = records;
                        historyRecordAdapter.submitList(records);
                        historyRecordAdapter.notifyDataSetChanged();
                    }
                });
                //set month spinner

                try {
                    historyMonth = new String[recordViewModel.findHistoryRecordMonth(selectedYear).length + 1];
                    historyMonth[0] = "all";
                    System.arraycopy(recordViewModel.findHistoryRecordMonth(selectedYear),0,historyMonth,1,recordViewModel.findHistoryRecordMonth(selectedYear).length);
                    setSpinnerMonth(historyMonth);
                    Log.d("TAG", "spinnerMonth: " + Arrays.toString(recordViewModel.findHistoryRecordMonth(selectedYear)));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedYear = adapter.getItem(0);
            }
        });


    }

    private void setSpinnerMonth(String[] historyMonth) throws ExecutionException, InterruptedException {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, historyMonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = adapter.getItem(position);
                if(position == 0){
                    if(selectedYear.equals("all")){
                        filteredRecords = recordViewModel.findHistoryRecord();
                    }else{
                        try {
                            filteredRecords = recordViewModel.findHistoryRecordByYear(selectedYear);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }else {
                    try {
                        filteredRecords = recordViewModel.findHistoryRecordByYearAndMonth(selectedYear,selectedMonth);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Record> records) {
                        allRecords = records;
                        historyRecordAdapter.submitList(records);
                        historyRecordAdapter.notifyDataSetChanged();
                    }
                });
                //set day spinner

                try {
                    historyDay = new String[recordViewModel.findHistoryRecordDay(selectedYear,selectedMonth).length + 1];
                    historyDay[0] = "all";
                    System.arraycopy(recordViewModel.findHistoryRecordDay(selectedYear,selectedMonth),0,historyDay,1,recordViewModel.findHistoryRecordDay(selectedYear,selectedMonth).length);
                    setSpinnerDay(historyDay);
                    Log.d("TAG", "spinnerMonth: " + Arrays.toString(recordViewModel.findHistoryRecordDay(selectedYear,selectedMonth)));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMonth = adapter.getItem(0);
            }
        });

    }

    private void setSpinnerDay(String[] historyDay) throws ExecutionException, InterruptedException {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, historyDay);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = adapter.getItem(position);
                if(position == 0){
                    if(selectedMonth.equals("all")){
                        if(selectedYear.equals("all")){
                            filteredRecords = recordViewModel.findHistoryRecord();
                        }else{
                            try {
                                filteredRecords = recordViewModel.findHistoryRecordByYear(selectedYear);
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }else{
                        try {
                            filteredRecords = recordViewModel.findHistoryRecordByYearAndMonth(selectedYear,selectedMonth);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    try {
                        filteredRecords = recordViewModel.findHistoryRecordByYearAndMonthAndDay(selectedYear,selectedMonth,selectedDay);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Record> records) {
                        allRecords = records;
                        historyRecordAdapter.submitList(records);
                        historyRecordAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDay = adapter.getItem(0);
            }
        });

    }

    private void setMenu() {
        //----set menu----
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbarInDataFragment);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deleteAllData: {
                        Log.d("TAG", "deleteAllData:isChecked ");
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle(getString(R.string.deleteAllData));
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recordViewModel.deleteAllRecords();
                            }
                        });
                        builder.setNegativeButton("no", null);
                        builder.show();
                        break;
                    }
                    case R.id.exportData: {
                        Log.d("TAG", "sendData:isChecked ");
                        String fileName = "test_table(" + simpleDateFormatDate.format(date) + ").csv";
                        exportToCsv(fileName);
                    }
                }
                return false;
            }
        });
    }

    //----data output----
    void exportToCsv(String fileName) {
        Log.d("TAG", "exportToCsv:start ");
        int rowCount = allRecords.size();    //行
        FileWriter fw;
        BufferedWriter bfw;
        File dir = Environment.getExternalStorageDirectory();
        File saveFile = new File(dir, fileName);
        try {
            fw = new FileWriter(saveFile);
            bfw = new BufferedWriter(fw);
            if (rowCount > 0) {
                //一行目
                bfw.write(requireActivity().getString(R.string.startTime) + ',' + requireActivity().getString(R.string.finishTime) + ',' + requireActivity().getString(R.string.time) + ',' + requireActivity().getString(R.string.patient) + ',' + requireActivity().getString(R.string.staff) + ',' + requireActivity().getString(R.string.item) + ',' + requireActivity().getString(R.string.note));
                bfw.newLine();
                for (int i = 0; i < rowCount; i++) {
                    Log.d("TAG", "Exporting: " + i);
                    Record record = allRecords.get(i);
                    bfw.write(simpleDateFormatTime.format(record.getStartTime()) + ',' + simpleDateFormatTime.format(record.getFinishTime()) + "," + record.getTime() + "," + record.getPatient() + "," + record.getStaff() + "," + record.getItem() + "," + record.getNote());
                    bfw.newLine();
                }
                showToast("データを出力しました！");
            }
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
            showToast("データ出力が失敗しました！");
            Log.d("TAG", "BufferedWriter:error ");
        }
    }

    @SuppressLint("SetTextI18n")
    void showToast(String warning) {
        TextView view = new TextView(getContext());
        Toast toast = new Toast(getContext());
        view.setText("            " + warning + "            ");
        view.setBackgroundResource(R.color.colorSearch);
        view.setTextColor(requireContext().getResources().getColor(R.color.white));
        view.setTextSize(18);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

}


