package com.example.paroapplication.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.paroapplication.R;
import com.example.paroapplication.RecordButtonDatabase.ConditionViewModel;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordAdapter;
import com.example.paroapplication.RecordDatabase.RecordViewModel;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ConfirmFragment extends Fragment {

    String patient,staff;
    Date date = new Date(System.currentTimeMillis());
    RadioGroup radioGroupPatientButton;
    RecordViewModel recordViewModel;
    ConditionViewModel conditionViewModel;
    RecyclerView recyclerViewRecord;
    RecordAdapter recordAdapter;
    List<Record> allRecords,allRecordsByStartId;
    LiveData<List<Record>> filteredRecords,filteredRecordsByStartId;
    TextView textViewStartTime,textViewFinishTime;
    public  static String startId;
    String[] patientList,staffList;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.confirm_fragment, container, false);
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        conditionViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ConditionViewModel.class);
        // TODO: Use the ViewModel

        //----recyclerViewRecord----
        recyclerViewRecord = requireView().findViewById(R.id.recyclerViewRecord02);
        LinearLayoutManager layoutRecord = new LinearLayoutManager(requireContext());
        layoutRecord.setStackFromEnd(true);
        layoutRecord.setReverseLayout(true);
        recyclerViewRecord.setLayoutManager(layoutRecord);
        recordAdapter = new RecordAdapter();
        recyclerViewRecord.setAdapter(recordAdapter);

        //----get finish time---
        date = new Date(System.currentTimeMillis());

        //----set Patient Button----
        LinearLayout linearLayoutPatientButton = requireActivity().findViewById(R.id.linearLayoutPatientButton02);
        LinearLayout linearLayout = new LinearLayout(requireContext());
        radioGroupPatientButton = new RadioGroup(requireContext());
        radioGroupPatientButton.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPatientButton.setOrientation(LinearLayout.HORIZONTAL);
        //get patient list & staff list
        try {
            int patientListLength = recordViewModel.findPatientRecordByStartId(startId).length;
            patientList = new String[patientListLength];
            patientList = recordViewModel.findPatientRecordByStartId(startId);
            int staffListLength = recordViewModel.findStaffRecordByStartId(startId).length;
            staffList = new String[staffListLength];
            staffList = recordViewModel.findStaffRecordByStartId(startId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        RadioButton allRadioButton = new RadioButton(requireContext());
        allRadioButton.setButtonDrawable(0);
        //  各患者Buttonを作る
        String all;
        all = getString(R.string.allPatient);   //「全員」ボタンの追加
        allRadioButton.setText(all);
        allRadioButton.setBackgroundResource(R.drawable.radio01);
        allRadioButton.setTextColor(requireContext().getResources().getColorStateList(R.drawable.text_color));
        allRadioButton.setPadding(30, 0, 30, 0);
        radioGroupPatientButton.addView(allRadioButton);
        //ボタンとボタンの間
        View allReit = new View(requireContext());
        allReit.setLayoutParams(new LinearLayout.LayoutParams(30, LinearLayout.LayoutParams.WRAP_CONTENT));
        allReit.setBackgroundColor(Color.TRANSPARENT);
        radioGroupPatientButton.addView(allReit);
        allRadioButton.setChecked(true);
        //患者Buttonを作る
        if (patientList.length !=0){
            for (int i = 1; i <= patientList.length; i++) {
                RadioButton radioButton = new RadioButton(requireContext());
                radioButton.setButtonDrawable(0);
                String patientName;
                patientName = patientList[i-1];
                radioButton.setText(patientName);
                radioButton.setBackgroundResource(R.drawable.radio01);
                radioButton.setTextColor(requireContext().getResources().getColorStateList(R.drawable.text_color));
                radioButton.setPadding(30, 0, 30, 0);
                radioGroupPatientButton.addView(radioButton);
                //ボタンとボタンの間
                View reit = new View(requireContext());
                reit.setLayoutParams(new LinearLayout.LayoutParams(30, LinearLayout.LayoutParams.WRAP_CONTENT));
                reit.setBackgroundColor(Color.TRANSPARENT);
                radioGroupPatientButton.addView(reit);

            }

        }
        //----set Helper Button----
        for (int i = 1; i <= staffList.length; i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setButtonDrawable(0);
            String HelperName;
            HelperName = staffList[i - 1];
            radioButton.setText(HelperName);
            radioButton.setBackgroundResource(R.drawable.radio02);
            radioButton.setTextColor(requireContext().getResources().getColorStateList(R.drawable.text_color));
            radioButton.setPadding(30, 0, 30, 0);
            radioGroupPatientButton.addView(radioButton);
            //ボタンとボタンの間
            View reit = new View(requireContext());
            reit.setLayoutParams(new LinearLayout.LayoutParams(30, LinearLayout.LayoutParams.WRAP_CONTENT));
            reit.setBackgroundColor(Color.TRANSPARENT);
            radioGroupPatientButton.addView(reit);
        }
        linearLayout.addView(radioGroupPatientButton);
        linearLayoutPatientButton.addView(linearLayout);
        radioGroupPatientButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton choice = requireActivity().findViewById(checkedId);                 //選択したボタンの内容を取る
                String selectedButton = choice.getText().toString().trim();
                Log.d("TAG", "[dataFragment]patientButton:"+ checkedId + " is checked");
                if (selectedButton.equals(getString(R.string.allPatient))){
                    filteredRecords = recordViewModel.findRecordsByStartId(startId);       //  介護記録の詳細を探す
                }else{
                    if(isHelper(selectedButton)){
                        staff = selectedButton;                            //選択した患者さんの名前を「staff」に出力
                        filteredRecords = recordViewModel.findRecordsDetailByStaff(startId, staff);       //  介護記録の詳細を探す
                    }else{
                        patient = selectedButton;                            //選択した患者さんの名前を「patient」に出力
                        filteredRecords = recordViewModel.findRecordsDetailByPatient(startId, patient);       //  介護記録の詳細を探す
                    }
                }

                filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Record> records) {
                        allRecords = records;
                        recordAdapter.submitList(records);
                        recordAdapter.notifyDataSetChanged();
                    }
                });
            }
            private boolean isHelper(String selectedButton) {
                for(int i = 1; i <= staffList.length; i++){
                    if(selectedButton.equals(staffList[i - 1])){
                        return true;
                    }
                }
                return false;
            }
        });

        //----Data change monitoring----
        filteredRecords = recordViewModel.findRecordsByStartId(startId);       //  介護記録の詳細を探す
        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                recordAdapter.submitList(records);
                if(!allRecords.isEmpty()){
                    String startTime = simpleDateFormatTime.format(allRecords.get(0).getStartTime());
                    String finishTime = simpleDateFormatTime.format(allRecords.get(0).getFinishTime());
                    textViewStartTime.setText(startTime);
                    textViewFinishTime.setText(finishTime);
                }
            }
        });


        //----set startTime & FinishTime----
        textViewStartTime = requireActivity().findViewById(R.id.textViewStartTimeInConfirmFragment);
        textViewFinishTime = requireActivity().findViewById(R.id.textViewFinishTimeInConfirmFragment);
        //開始時間の編集
        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextStartTime;
                final View view = View.inflate(requireContext(),R.layout.dialog_record, null);
                final Dialog recordDialog = new RecordFragment.MyDialog(requireContext(), view, R.style.DialogTheme);
                TextView textViewTittle = view.findViewById(R.id.editRecordTittle);
                textViewTittle.setText(requireContext().getString(R.string.editRecord));
                editTextStartTime = view.findViewById(R.id.editTextRecordNote);
                editTextStartTime.setText(textViewStartTime.getText());
                recordDialog.setCancelable(true);
                recordDialog.show();
                Button buttonOk,buttonCancel;
                buttonOk = view.findViewById(R.id.buttonOkEditRecord);
                buttonCancel = view.findViewById(R.id.buttonCancelEditRecord);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        filteredRecordsByStartId = recordViewModel.findRecordsByStartId(startId);
                        filteredRecordsByStartId.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                            @Override
                            public void onChanged(List<Record> records) {
                                allRecordsByStartId = records;
                                for (int i = 0;i<allRecordsByStartId.size();i++){
                                    Record record = allRecordsByStartId.get(i);
                                    Date time = new Date();
                                    try {
                                        time = simpleDateFormatTime.parse(String.valueOf(editTextStartTime.getText()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    assert time != null;
                                    record.setStartTime(time.getTime());
                                    recordViewModel.updateRecords(record);
                                }
                            }
                        });
                        recordDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });

            }
        });
        //終了時間の編集
        textViewFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextFinishTime;
                final View view = View.inflate(requireContext(),R.layout.dialog_record, null);
                final Dialog recordDialog = new RecordFragment.MyDialog(requireContext(), view, R.style.DialogTheme);
                TextView textViewTittle = view.findViewById(R.id.editRecordTittle);
                textViewTittle.setText(requireContext().getString(R.string.editRecord));
                editTextFinishTime = view.findViewById(R.id.editTextRecordNote);
                editTextFinishTime.setText(textViewFinishTime.getText());
                recordDialog.setCancelable(true);
                recordDialog.show();
                Button buttonOk,buttonCancel;
                buttonOk = view.findViewById(R.id.buttonOkEditRecord);
                buttonCancel = view.findViewById(R.id.buttonCancelEditRecord);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        filteredRecordsByStartId = recordViewModel.findRecordsByStartId(startId);
                        filteredRecordsByStartId.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                            @Override
                            public void onChanged(List<Record> records) {
                                allRecordsByStartId = records;
                                for (int i = 0;i < allRecordsByStartId.size();i++){
                                    Record record = allRecordsByStartId.get(i);
                                    Date time = new Date();
                                    try {
                                        time = simpleDateFormatTime.parse(String.valueOf(editTextFinishTime.getText()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    assert time != null;
                                    record.setFinishTime(time.getTime());
                                    recordViewModel.updateRecords(record);
                                }
                            }
                        });
                        recordDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager)requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });

            }
        });


        //----finish button----
        Button button;
        button = requireView().findViewById(R.id.buttonFinish2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordFragment.startTime = 0;
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_confirmFragment_to_homeFragment);

            }
        });

        //----delete item----
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) { //(拖动，滑动)
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Record recordToDelete = allRecords.get(viewHolder.getAdapterPosition());
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle(R.string.deleteRecord);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recordViewModel.deleteRecords(recordToDelete);        //介護記録を削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        }).attachToRecyclerView(recyclerViewRecord); //recyclerViewRecord
    }
}
