package com.example.paroapplication.Fragment;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.paroapplication.RecordButtonDatabase.Gaze;
import com.example.paroapplication.RecordButtonDatabase.Interaction;
import com.example.paroapplication.RecordButtonDatabase.Talk;
import com.example.paroapplication.StaffDatabase.StaffSelectAdapter;
import com.example.paroapplication.PatientDatabase.PatientSelectAdapter;
import com.example.paroapplication.RecordButtonDatabase.Condition;
import com.example.paroapplication.RecordButtonDatabase.ConditionAdapter;
import com.example.paroapplication.RecordButtonDatabase.ConditionViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordButtonDatabase.Expression;
import com.example.paroapplication.RecordButtonDatabase.ExpressionAdapter;
import com.example.paroapplication.RecordButtonDatabase.ExpressionViewModel;
import com.example.paroapplication.RecordButtonDatabase.Intervene;
import com.example.paroapplication.RecordButtonDatabase.InterveneAdapter;
import com.example.paroapplication.RecordButtonDatabase.InterveneViewModel;
import com.example.paroapplication.RecordButtonDatabase.GazeAdapter;
import com.example.paroapplication.RecordButtonDatabase.GazeViewModel;
import com.example.paroapplication.RecordButtonDatabase.InteractionAdapter;
import com.example.paroapplication.RecordButtonDatabase.InteractionViewModel;
import com.example.paroapplication.RecordButtonDatabase.ProblemBehavior;
import com.example.paroapplication.RecordButtonDatabase.ProblemBehaviorAdapter;
import com.example.paroapplication.RecordButtonDatabase.ProblemBehaviorViewModel;
import com.example.paroapplication.RecordButtonDatabase.Remark;
import com.example.paroapplication.RecordButtonDatabase.RemarkAdapter;
import com.example.paroapplication.RecordButtonDatabase.RemarkViewModel;
import com.example.paroapplication.RecordButtonDatabase.TalkAdapter;
import com.example.paroapplication.RecordButtonDatabase.TalkViewModel;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.RecordDatabase.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.VIBRATOR_SERVICE;
import static com.example.paroapplication.StaffDatabase.StaffSelectAdapter.selectedStaffName;
import static com.example.paroapplication.PatientDatabase.PatientSelectAdapter.selectedPatientName;


public class RecordFragment extends Fragment {
    private static int REQUEST_CODE = 1;
    public static Date date;
    public static String patient, staff, startId, time;
    public static long startTime = 0, finishTime;
    public static int recordItemId = 0;
    public String[] item;
    public TextView[][] textView;
    String voiceNote;
    String recordPatientCondition, patientExpression, patientGaze, patientTalk, patientInteraction, patientProblemBehavior, staffRemark, staffIntervene;
    long timeItem = 0, currentTime = 0;
    int selectedTime;
    static ConstraintLayout constraintLayout00, constraintLayout01, constraintLayout02, constraintLayout03, constraintLayout04, constraintLayout05, constraintLayout06, constraintLayout07;
    String[] spinnerItems;
    Spinner spinnerTime;
    TextView textViewTime;
    Button buttonAddRecord, buttonFinish, buttonVoice, buttonLeft, buttonRight;
    RadioGroup radioGroupPatientButton;
    RecordViewModel recordViewModel;
    ConditionViewModel conditionViewModel;
    ExpressionViewModel expressionViewModel;
    GazeViewModel gazeViewModel;
    TalkViewModel talkViewModel;
    InteractionViewModel interactionViewModel;
    ProblemBehaviorViewModel problemBehaviorViewModel;
    RemarkViewModel remarkViewModel;
    InterveneViewModel interveneViewModel;
    public static RecyclerView recyclerViewButton00, recyclerViewButton01, recyclerViewButton02, recyclerViewButton03, recyclerViewButton04, recyclerViewButton05, recyclerViewButton06, recyclerViewButton07;
    ConditionAdapter conditionAdapter = new ConditionAdapter();
    ExpressionAdapter expressionAdapter = new ExpressionAdapter();
    GazeAdapter gazeAdapter = new GazeAdapter();
    TalkAdapter talkAdapter = new TalkAdapter();
    InteractionAdapter interactionAdapter = new InteractionAdapter();
    ProblemBehaviorAdapter problemBehaviorAdapter = new ProblemBehaviorAdapter();
    RemarkAdapter remarkAdapter = new RemarkAdapter();
    InterveneAdapter interveneAdapter = new InterveneAdapter();
    ArrayAdapter<String> timeAdapter;
    List<Condition> allConditions;
    List<Expression> allExpressions;
    List<Gaze> allGazes;
    List<Talk> allTalks;
    List<Interaction> allInteractions;
    List<ProblemBehavior> allProblemBehaviors;
    List<Remark> allRemarks;
    List<Record> allRecords, allRecords1;
    List<Intervene> allIntervenes;
    LiveData<List<Record>> filteredRecords, filteredRecords1;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "StaticFieldLeak"})
    public static Switch switch00, switch01, switch02, switch03, switch04, switch05, switch06, switch07;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"ResourceAsColor", "ResourceType", "UseCompatLoadingForColorStateLists"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        conditionViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ConditionViewModel.class);
        expressionViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ExpressionViewModel.class);
        gazeViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(GazeViewModel.class);
        talkViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(TalkViewModel.class);
        interactionViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(InteractionViewModel.class);
        problemBehaviorViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ProblemBehaviorViewModel.class);
        remarkViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RemarkViewModel.class);
        interveneViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(InterveneViewModel.class);
        // TODO: Use the ViewModel

        //----set start Data----
        setStartData();
        setRecyclerViewButton();    //記録ボタンの設置
        setDataMonitor();


//        ----move item----
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton00.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allConditions, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton00);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton01.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allExpressions, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton01);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton02.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allGazes, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton02);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton03.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allTalks, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }

        }).attachToRecyclerView(recyclerViewButton03);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton04.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allInteractions, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton04);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton05.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allProblemBehaviors, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton05);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton06.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allRemarks, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton06);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                Objects.requireNonNull(recyclerViewButton07.getAdapter()).notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                Collections.swap(allIntervenes, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        }).attachToRecyclerView(recyclerViewButton07);

        //----add record button----
        buttonAddRecord = requireActivity().findViewById(R.id.buttonAddRecord);
        buttonAddRecord.setOnClickListener(listener);
        //----voice button----
        buttonVoice = requireActivity().findViewById(R.id.buttonVoice01);
        buttonVoice.setOnClickListener(listener);
        //----finish button----
        buttonFinish = requireActivity().findViewById(R.id.buttonFinish1);
        buttonFinish.setOnClickListener(listener);
        //----Time button----
        buttonLeft = requireActivity().findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(listener);
        buttonRight = requireActivity().findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(listener);
        //----switch----
        switch00.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 0;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton00.setVisibility(View.GONE);
                }
            }
        });
        switch01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 1;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton01.setVisibility(View.GONE);
                }
            }
        });
        switch02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 2;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton02.setVisibility(View.GONE);
                }
            }
        });
        switch03.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 3;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton03.setVisibility(View.GONE);
                }
            }
        });
        switch04.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 4;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton04.setVisibility(View.GONE);
                }
            }
        });
        switch05.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 5;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton05.setVisibility(View.GONE);
                }
            }
        });
        switch06.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 6;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton06.setVisibility(View.GONE);
                }
            }
        });
        switch07.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recordItemId = 7;
                    setSwitchButton(recordItemId);
                } else {
                    recyclerViewButton07.setVisibility(View.GONE);
                }
            }
        });

    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"})
    private void setPatientButton() {
        //----set Patient Button----
        LinearLayout linearLayoutPatientButton = requireActivity().findViewById(R.id.linearLayoutPatientButton01);
        LinearLayout linearLayout = new LinearLayout(requireContext());
        radioGroupPatientButton = new RadioGroup(requireContext());
        radioGroupPatientButton.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPatientButton.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < selectedPatientName.size(); i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setButtonDrawable(0);
            String patientName;
            patientName = PatientSelectAdapter.buttonSelectedPatientName[i];
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
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
        patient = PatientSelectAdapter.buttonSelectedPatientName[0];
        //----set Helper Button----
        for (int i = 0; i < selectedStaffName.size(); i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setButtonDrawable(0);
            String HelperName;
            HelperName = StaffSelectAdapter.buttonSelectedStaffName[i];
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
                if (isHelper(selectedButton)) {
                    staff = selectedButton;                            //選択した患者さんの名前を「staff」に出力
                    filteredRecords = recordViewModel.findRecordsDetailByStaff(startId, staff);       //  介護記録の詳細を探す
                    filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                        @Override
                        public void onChanged(List<Record> records) {
                            allRecords = records;
                        }
                    });
                    setSwitchButton(7);
                } else {
                    patient = selectedButton;                            //選択した患者さんの名前を「patient」に出力
                    filteredRecords = recordViewModel.findRecordsDetailByPatient(startId, patient);       //  介護記録の詳細を探す
                    filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                        @Override
                        public void onChanged(List<Record> records) {
                            allRecords = records;
                        }
                    });
                    setSwitchButton(0);
                }
                SetRecordTextView();
            }

            private boolean isHelper(String selectedButton) {
                for (int i = 0; i < selectedStaffName.size(); i++) {
                    if (selectedButton.equals(StaffSelectAdapter.buttonSelectedStaffName[i])) {
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public static void setSwitchButton(int recordItemId) {
        recyclerViewButton00.setVisibility(View.GONE);
        recyclerViewButton01.setVisibility(View.GONE);
        recyclerViewButton02.setVisibility(View.GONE);
        recyclerViewButton03.setVisibility(View.GONE);
        recyclerViewButton04.setVisibility(View.GONE);
        recyclerViewButton05.setVisibility(View.GONE);
        recyclerViewButton06.setVisibility(View.GONE);
        recyclerViewButton07.setVisibility(View.GONE);
        switch (recordItemId) {
            case 0: {
                setRecordModel(false);
                recyclerViewButton00.setVisibility(View.VISIBLE);
                switch01.setChecked(false);
                switch02.setChecked(false);
                switch03.setChecked(false);
                switch04.setChecked(false);
                switch05.setChecked(false);
                switch06.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 1: {
                setRecordModel(false);
                recyclerViewButton01.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch02.setChecked(false);
                switch03.setChecked(false);
                switch04.setChecked(false);
                switch05.setChecked(false);
                switch06.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 2: {
                setRecordModel(false);
                recyclerViewButton02.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch01.setChecked(false);
                switch03.setChecked(false);
                switch04.setChecked(false);
                switch05.setChecked(false);
                switch06.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 3: {
                setRecordModel(false);
                recyclerViewButton03.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch01.setChecked(false);
                switch02.setChecked(false);
                switch04.setChecked(false);
                switch05.setChecked(false);
                switch06.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 4: {
                setRecordModel(false);
                recyclerViewButton04.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch01.setChecked(false);
                switch02.setChecked(false);
                switch03.setChecked(false);
                switch05.setChecked(false);
                switch06.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 5: {
                setRecordModel(false);
                recyclerViewButton05.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch01.setChecked(false);
                switch02.setChecked(false);
                switch03.setChecked(false);
                switch04.setChecked(false);
                switch06.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 6: {
                setRecordModel(false);
                recyclerViewButton06.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch01.setChecked(false);
                switch02.setChecked(false);
                switch03.setChecked(false);
                switch04.setChecked(false);
                switch05.setChecked(false);
                switch07.setChecked(false);
                break;
            }
            case 7: {
                setRecordModel(true);
                recyclerViewButton07.setVisibility(View.VISIBLE);
                switch00.setChecked(false);
                switch01.setChecked(false);
                switch02.setChecked(false);
                switch03.setChecked(false);
                switch04.setChecked(false);
                switch05.setChecked(false);
                switch06.setChecked(false);
            }
        }


    }

    private static void setRecordModel(boolean i) {
        if (i) {
            constraintLayout00.setVisibility(View.GONE);
            constraintLayout01.setVisibility(View.GONE);
            constraintLayout02.setVisibility(View.GONE);
            constraintLayout03.setVisibility(View.GONE);
            constraintLayout04.setVisibility(View.GONE);
            constraintLayout05.setVisibility(View.GONE);
            constraintLayout06.setVisibility(View.GONE);
            constraintLayout07.setVisibility(View.VISIBLE);
        } else {
            constraintLayout00.setVisibility(View.VISIBLE);
            constraintLayout01.setVisibility(View.VISIBLE);
            constraintLayout02.setVisibility(View.VISIBLE);
            constraintLayout03.setVisibility(View.VISIBLE);
            constraintLayout04.setVisibility(View.VISIBLE);
            constraintLayout05.setVisibility(View.VISIBLE);
            constraintLayout06.setVisibility(View.VISIBLE);
            constraintLayout07.setVisibility(View.GONE);
        }
    }

    private void setDataMonitor() {
        //----Data change monitoring----
        filteredRecords1 = recordViewModel.findRecordsByStartTime(startTime);
        filteredRecords1.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @SuppressLint("LogConditional")
            @Override
            public void onChanged(List<Record> records) {
                allRecords1 = records;
                SetRecordTextView();
            }
        });
        conditionViewModel.getAllConditionsLive().observe(getViewLifecycleOwner(), new Observer<List<Condition>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Condition> conditions) {
                allConditions = conditions;
                recyclerViewButton00.smoothScrollBy(0, -200);
                conditionAdapter.submitList(conditions);
                conditionAdapter.notifyDataSetChanged();
                recyclerViewButton00.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton00.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                ConditionAdapter.MyViewHolder holder = (ConditionAdapter.MyViewHolder) recyclerViewButton00.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        expressionViewModel.getAllExpressionsLive().observe(getViewLifecycleOwner(), new Observer<List<Expression>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Expression> expressions) {
                allExpressions = expressions;
                recyclerViewButton01.smoothScrollBy(0, -200);
                expressionAdapter.submitList(expressions);
                expressionAdapter.notifyDataSetChanged();
                recyclerViewButton01.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton01.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                ExpressionAdapter.MyViewHolder holder = (ExpressionAdapter.MyViewHolder) recyclerViewButton01.findViewHolderForAdapterPosition(i);
                                assert holder != null;
                                if(i % 2 == 0){
                                    holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                }else{
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                }
                            }
                        }
                    }
                });
            }
        });
        gazeViewModel.getAllGazesLive().observe(getViewLifecycleOwner(), new Observer<List<Gaze>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Gaze> gazes) {
                allGazes = gazes;
                recyclerViewButton02.smoothScrollBy(0, -200);
                gazeAdapter.submitList(gazes);
                gazeAdapter.notifyDataSetChanged();
                recyclerViewButton02.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton02.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                GazeAdapter.MyViewHolder holder = (GazeAdapter.MyViewHolder) recyclerViewButton02.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        talkViewModel.getAllTalksLive().observe(getViewLifecycleOwner(), new Observer<List<Talk>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Talk> talks) {
                allTalks = talks;
                recyclerViewButton03.smoothScrollBy(0, -200);
                talkAdapter.submitList(talks);
                talkAdapter.notifyDataSetChanged();
                recyclerViewButton03.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton03.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                TalkAdapter.MyViewHolder holder = (TalkAdapter.MyViewHolder) recyclerViewButton03.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        interactionViewModel.getAllInteractionsLive().observe(getViewLifecycleOwner(), new Observer<List<Interaction>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Interaction> interactions) {
                allInteractions = interactions;
                recyclerViewButton04.smoothScrollBy(0, -200);
                interactionAdapter.submitList(interactions);
                interactionAdapter.notifyDataSetChanged();
                recyclerViewButton04.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton04.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                InteractionAdapter.MyViewHolder holder = (InteractionAdapter.MyViewHolder) recyclerViewButton04.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        problemBehaviorViewModel.getAllProblemBehaviorsLive().observe(getViewLifecycleOwner(), new Observer<List<ProblemBehavior>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ProblemBehavior> problemBehaviors) {
                allProblemBehaviors = problemBehaviors;
                recyclerViewButton05.smoothScrollBy(0, -200);
                problemBehaviorAdapter.submitList(problemBehaviors);
                problemBehaviorAdapter.notifyDataSetChanged();
                recyclerViewButton05.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton05.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                ProblemBehaviorAdapter.MyViewHolder holder = (ProblemBehaviorAdapter.MyViewHolder) recyclerViewButton05.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        remarkViewModel.getAllRemarksLive().observe(getViewLifecycleOwner(), new Observer<List<Remark>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Remark> remarks) {
                allRemarks = remarks;
                recyclerViewButton06.smoothScrollBy(0, -200);
                remarkAdapter.submitList(remarks);
                remarkAdapter.notifyDataSetChanged();
                recyclerViewButton06.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton06.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                RemarkAdapter.MyViewHolder holder = (RemarkAdapter.MyViewHolder) recyclerViewButton06.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
        interveneViewModel.getAllIntervenesLive().observe(getViewLifecycleOwner(), new Observer<List<Intervene>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Intervene> intervenes) {
                allIntervenes = intervenes;
                recyclerViewButton07.smoothScrollBy(0, -200);
                interveneAdapter.submitList(intervenes);
                interveneAdapter.notifyDataSetChanged();
                recyclerViewButton07.setItemAnimator(new DefaultItemAnimator() {
                    @Override
                    public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                        super.onAnimationFinished(viewHolder);
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewButton07.getLayoutManager();
                        if (linearLayoutManager != null) {
                            int firstPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                            for (int i = firstPosition; i <= lastPosition; i++) {
                                InterveneAdapter.MyViewHolder holder = (InterveneAdapter.MyViewHolder) recyclerViewButton07.findViewHolderForAdapterPosition(i);
                                if (holder != null) {
                                    holder.itemView.setBackgroundColor(Color.parseColor("#D4D2ED"));
                                    if (i % 2 == 0) {
                                        holder.itemView.setBackgroundColor(Color.parseColor("#EDEDF8"));
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    private void setRecyclerViewButton() {
        //----recyclerViewRecord----
        recyclerViewButton00 = requireView().findViewById(R.id.recyclerViewRecordButton01);
        LinearLayoutManager layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton00.setLayoutManager(layoutRecordButton);
        conditionAdapter = new ConditionAdapter();
        recyclerViewButton00.setAdapter(conditionAdapter);
        recyclerViewButton00.setVisibility(View.VISIBLE);

        recyclerViewButton01 = requireView().findViewById(R.id.recyclerViewRecordButton02);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton01.setLayoutManager(layoutRecordButton);
        expressionAdapter = new ExpressionAdapter();
        recyclerViewButton01.setAdapter(expressionAdapter);
        recyclerViewButton01.setVisibility(View.GONE);

        recyclerViewButton02 = requireView().findViewById(R.id.recyclerViewRecordButton03);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton02.setLayoutManager(layoutRecordButton);
        gazeAdapter = new GazeAdapter();
        recyclerViewButton02.setAdapter(gazeAdapter);
        recyclerViewButton02.setVisibility(View.GONE);

        recyclerViewButton03 = requireView().findViewById(R.id.recyclerViewRecordButton04);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton03.setLayoutManager(layoutRecordButton);
        talkAdapter = new TalkAdapter();
        recyclerViewButton03.setAdapter(talkAdapter);
        recyclerViewButton03.setVisibility(View.GONE);

        recyclerViewButton04 = requireView().findViewById(R.id.recyclerViewRecordButton05);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton04.setLayoutManager(layoutRecordButton);
        interactionAdapter = new InteractionAdapter();
        recyclerViewButton04.setAdapter(interactionAdapter);
        recyclerViewButton04.setVisibility(View.GONE);

        recyclerViewButton05 = requireView().findViewById(R.id.recyclerViewRecordButton06);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton05.setLayoutManager(layoutRecordButton);
        problemBehaviorAdapter = new ProblemBehaviorAdapter();
        recyclerViewButton05.setAdapter(problemBehaviorAdapter);
        recyclerViewButton05.setVisibility(View.GONE);

        recyclerViewButton06 = requireView().findViewById(R.id.recyclerViewRecordButton07);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton06.setLayoutManager(layoutRecordButton);
        remarkAdapter = new RemarkAdapter();
        recyclerViewButton06.setAdapter(remarkAdapter);
        recyclerViewButton06.setVisibility(View.GONE);

        recyclerViewButton07 = requireView().findViewById(R.id.recyclerViewRecordButton08);
        layoutRecordButton = new LinearLayoutManager(requireContext());
        recyclerViewButton07.setLayoutManager(layoutRecordButton);
        interveneAdapter = new InterveneAdapter();
        recyclerViewButton07.setAdapter(interveneAdapter);
        recyclerViewButton07.setVisibility(View.GONE);
    }

    private void setMenu() {
        //----set menu----
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbarInRecord01);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.recordManual:
                        break;
                    case R.id.recordDeleteAll: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle(getString(R.string.deleteAllData));
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    recordViewModel.deleteRecordsByStartId(startId);
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        builder.setNegativeButton("no", null);
                        builder.show();
                        break;
                    }
                    case R.id.recordButtonReset: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle(getString(R.string.deleteAllData));
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetButtonData();
                            }
                        });
                        builder.setNegativeButton("no", null);
                        builder.show();
                    }
                }
                return false;
            }
        });

    }

    @SuppressLint("LogConditional")
    void SetRecordTextView() {
        try {
            TextView textViewCondition = requireActivity().findViewById(R.id.textViewCondition);
            TextView textViewFacialExpression = requireActivity().findViewById(R.id.textViewFacialExpression);
            TextView textViewLineOfSight = requireActivity().findViewById(R.id.textViewLineOfSight);
            TextView textViewTalkTo = requireActivity().findViewById(R.id.textViewTalkTo);
            TextView textViewParoContact = requireActivity().findViewById(R.id.textViewParoContact);
            TextView textViewProblemBehavior = requireActivity().findViewById(R.id.textViewProblemBehavior);
            TextView textViewRemark = requireActivity().findViewById(R.id.textViewRemark);
            TextView textViewIntervene = requireActivity().findViewById(R.id.textViewIntervene);
            String note = recordViewModel.getPatientRecordTextView(startId, patient, item[0], time);
            textViewCondition.setText(note);
            note = recordViewModel.getPatientRecordTextView(startId, patient, item[1], time);
            textViewFacialExpression.setText(note);
            note = recordViewModel.getPatientRecordTextView(startId, patient, item[2], time);
            textViewLineOfSight.setText(note);
            note = recordViewModel.getPatientRecordTextView(startId, patient, item[3], time);
            textViewTalkTo.setText(note);
            note = recordViewModel.getPatientRecordTextView(startId, patient, item[4], time);
            textViewParoContact.setText(note);
            note = recordViewModel.getPatientRecordTextView(startId, patient, item[5], time);
            textViewProblemBehavior.setText(note);
            note = recordViewModel.getPatientRecordTextView(startId, patient, item[6], time);
            textViewRemark.setText(note);
            note = recordViewModel.getStaffRecordTextView(startId, staff, item[7], time);
            textViewIntervene.setText(note);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setStartData() {
        if (startTime == 0) {
            simpleDateFormatTime.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            setMenu();
            setPatientButton();
            mCalHandler.post(mTicker);
            //set switch
            switch00 = requireActivity().findViewById(R.id.switchRecordItem01);
            switch01 = requireActivity().findViewById(R.id.switchRecordItem02);
            switch02 = requireActivity().findViewById(R.id.switchRecordItem03);
            switch03 = requireActivity().findViewById(R.id.switchRecordItem04);
            switch04 = requireActivity().findViewById(R.id.switchRecordItem05);
            switch05 = requireActivity().findViewById(R.id.switchRecordItem06);
            switch06 = requireActivity().findViewById(R.id.switchRecordItem07);
            switch07 = requireActivity().findViewById(R.id.switchRecordItem08);
            switch00.setChecked(true);
            //set constraintLayout
            constraintLayout00 = requireActivity().findViewById(R.id.constraintlayoutRecord00);
            constraintLayout01 = requireActivity().findViewById(R.id.constraintlayoutRecord01);
            constraintLayout02 = requireActivity().findViewById(R.id.constraintlayoutRecord02);
            constraintLayout03 = requireActivity().findViewById(R.id.constraintlayoutRecord03);
            constraintLayout04 = requireActivity().findViewById(R.id.constraintlayoutRecord04);
            constraintLayout05 = requireActivity().findViewById(R.id.constraintlayoutRecord05);
            constraintLayout06 = requireActivity().findViewById(R.id.constraintlayoutRecord06);
            constraintLayout07 = requireActivity().findViewById(R.id.constraintlayoutRecord07);
            //set spinner
            spinnerTime = requireActivity().findViewById(R.id.spinnerTime);
            spinnerTime.setGravity(Gravity.CENTER);
            spinnerItems = new String[]{simpleDateFormatTime.format(timeItem)};
            timeAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item1, spinnerItems);
            timeAdapter.setDropDownViewResource(R.layout.dropdown_style1);
            spinnerTime.setAdapter(timeAdapter);
            spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    time = timeAdapter.getItem(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    time = timeAdapter.getItem(0);
                }
            });

            //set start time
            startTime = getCurrentTime();
            startId = String.valueOf(startTime);
            item = new String[]{requireContext().getString(R.string.patientCondition), requireContext().getString(R.string.patientExpression), requireContext().getString(R.string.patientGaze), requireContext().getString(R.string.patientTalk), requireContext().getString(R.string.interaction), requireContext().getString(R.string.patientProblemBehavior), requireContext().getString(R.string.remark), requireContext().getString(R.string.staffIntervene)};
            patient = selectedPatientName.get(0);
            textViewTime = requireActivity().findViewById(R.id.textViewTime);
            textViewTime.setText(simpleDateFormatTime.format(currentTime));
        }
    }

    //----set button ClickListener----
    View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint({"ResourceAsColor", "NonConstantResourceId", "LogConditional"})
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //----voice button---
                case R.id.buttonVoice01: {
                    REQUEST_CODE = 1;
                    doSpeechRecognition();
                    return;
                }
                //----finish button----
                case R.id.buttonFinish1: {
                    finishTime = getCurrentTime();
                    Log.d("TAG", "finishTime = " + finishTime);
                    Log.d("TAG", "allRecords1.size() = " + allRecords1.size());
                    for (int i = 0; i < allRecords1.size(); i++) {
                        Record record = allRecords1.get(i);
                        record.setFinishTime(finishTime);
                        recordViewModel.updateRecords(record);
                        ConfirmFragment.startId = startId;
                        selectedPatientName.clear();
                        selectedStaffName.clear();
                        Log.d("TAG", "allRecords(" + i + ").finishTime = " + record.getFinishTime() + " startTime =" + record.getStartTime() + "   recordId : " + record.getRecordId());
                    }
                    mCalHandler.removeCallbacksAndMessages(null);
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_recordFragment_to_confirmFragment);
                    return;
                }
                //---- time button ----
                case R.id.buttonLeft: {
                    Log.d("TAG", "onMenuItemClick: " + selectedTime);
                    if (selectedTime != spinnerItems.length - 1) {
                        spinnerTime.setSelection(selectedTime + 1);          //前の時間を選ぶ
                        selectedTime++;
                    }
                    break;
                }
                case R.id.buttonRight: {
                    Log.d("TAG", "onMenuItemClick: " + selectedTime);
                    if (selectedTime != 0) {
                        spinnerTime.setSelection(selectedTime - 1);          //次の時間を選ぶ
                        selectedTime--;
                    }
                    break;
                }
                //----add record button----
                case R.id.buttonAddRecord: {
                    final EditText editTextAddRecord;
                    TextView textViewTittle;
                    @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.dialog_edit_text01, null);
                    final Dialog AddDialog = new MyDialog(requireContext(), view, R.style.DialogTheme);
                    AddDialog.setCancelable(true);
                    AddDialog.show();
                    textViewTittle = view.findViewById(R.id.textViewDialogTittle01);
                    textViewTittle.setText(getString(R.string.addRecordButtonTips));
                    editTextAddRecord = view.findViewById(R.id.editTextDialog);
                    final Button buttonOk, buttonCancel;
                    buttonOk = view.findViewById(R.id.buttonEditOk);
                    buttonCancel = view.findViewById(R.id.buttonEditCancel);
                    String buttonCondition = editTextAddRecord.getText().toString().trim();      //入力した内容を取る
                    buttonOk.setEnabled(!buttonCondition.isEmpty());
                    //---set OkButton Enabled
                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                            String addRecord = editTextAddRecord.getText().toString().trim();
                            buttonOk.setEnabled(!addRecord.isEmpty());
                        }

                        @Override
                        public void afterTextChanged(Editable sequence) {

                        }
                    };
                    editTextAddRecord.addTextChangedListener(textWatcher);

                    //----set button----
                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String note = editTextAddRecord.getText().toString().trim();      //入力した患者行為をget
                            Record record;
                            try {
                                if(recordItemId == 7){
                                    record = RecordViewModel.findStaffRecordByCoordinate(startId,time,staff,item[recordItemId]);
                                }else{
                                    record = RecordViewModel.findPatientRecordByCoordinate(startId,time,patient,item[recordItemId]);
                                }
                                if(record!=null){
                                    record.setNote(note);
                                    recordViewModel.updateRecords(record);
                                }else{
                                    if (recordItemId == 7){
                                        record = new Record(startId,startTime,startTime, time, "",staff,item[recordItemId],note);
                                    }else{
                                        record = new Record(startId,startTime,startTime, time, patient,"",item[recordItemId],note);
                                    }
                                    recordViewModel.insertRecord(record);
                                }
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            SetRecordTextView();
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            String notice = "記録ボタンとして追加しますか？";
                            builder.setTitle(notice);
                            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //記録ボタンを追加
                                    switch (recordItemId) {
                                        case 0: {
                                            Condition condition = new Condition(note);           //Conditionを追加する
                                            conditionViewModel.insertCondition(condition);
                                            break;
                                        }
                                        case 1: {
                                            Expression expression = new Expression(note);           //facialExpressionを追加する
                                            expressionViewModel.insertExpression(expression);
                                            break;
                                        }
                                        case 2: {
                                            Gaze gaze = new Gaze(note);           //LineOfSightを追加する
                                            gazeViewModel.insertGaze(gaze);
                                            break;
                                        }
                                        case 3: {
                                            Talk talk = new Talk(note);           //TalkToを追加する
                                            talkViewModel.insertTalk(talk);
                                            break;
                                        }
                                        case 4: {
                                            Interaction interaction = new Interaction(note);           //ParoContactを追加する
                                            interactionViewModel.insertInteraction(interaction);
                                            break;
                                        }
                                        case 5: {
                                            ProblemBehavior problemBehavior = new ProblemBehavior(note);           //ProblemBehaviorを追加する
                                            problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
                                            break;
                                        }
                                        case 6: {
                                            Remark remark = new Remark(note);           //Remarkを追加する
                                            remarkViewModel.insertRemark(remark);
                                        }
                                        case 7: {
                                            Intervene intervene = new Intervene(note);           //Remarkを追加する
                                            interveneViewModel.insertIntervene(intervene);
                                        }
                                    }
                                }
                            });
                            builder.setNegativeButton("no", null);
                            builder.show();
                            AddDialog.dismiss();       //close this dialog
                            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert imm != null;
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);      //close the keyboard
                        }
                    });
                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddDialog.dismiss();       //close this dialog
                            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert imm != null;
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);      //close the keyboard
                        }
                    });
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_fragment, container, false);
    }

    void doSpeechRecognition() {
        //音声認識用のインテントを作成
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //認識する言語を指定
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage());
        //認識する候補数の指定
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
        //音声認識時に表示する案内を設定
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "話してください");
        //音声認識を開始
        startActivityForResult(intent, REQUEST_CODE);
        Log.d("TAG", "voiceRecognition: done");
    }

    @SuppressLint({"LogConditional", "NonConstantResourceId"})
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: resultCode = " + resultCode + " requestCode = " + requestCode);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //data から音声認識の結果を取り出す（リスト形式で）
            Log.d("TAG", "onActivityResult: data から音声認識の結果を取り出す（リスト形式で）");
            assert data != null;
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //認識結果が一つ以上ある場合はテキストビューに結果を表示する
            assert result != null;
            if (result.size() > 0) {
                //一番最初にある認識結果を表示する
                voiceNote = result.get(0);
                final EditText editText;
                TextView textViewTittle;
                @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.dialog_edit_text01, null);
                final Dialog AddDialog = new MyDialog(requireContext(), view, R.style.DialogTheme);
                AddDialog.setCancelable(true);
                AddDialog.show();
                textViewTittle = view.findViewById(R.id.textViewDialogTittle01);
                textViewTittle.setText(getString(R.string.addRecordButtonTips));
                editText = view.findViewById(R.id.editTextDialog);
                editText.setText(voiceNote);
                final Button buttonOk, buttonCancel;
                buttonOk = view.findViewById(R.id.buttonEditOk);
                buttonCancel = view.findViewById(R.id.buttonEditCancel);
                String buttonCondition = editText.getText().toString().trim();      //入力した内容を取る
                buttonOk.setEnabled(!buttonCondition.isEmpty());
                //---set OkButton Enabled
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                        String buttonCondition = editText.getText().toString().trim();
                        buttonOk.setEnabled(!buttonCondition.isEmpty());
                    }
                    @Override
                    public void afterTextChanged(Editable sequence) {
                    }
                };
                editText.addTextChangedListener(textWatcher);
                //----set button----
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //音声入力で記録する
                        Record record;
                        try {
                            if(recordItemId == 7){
                                record = RecordViewModel.findStaffRecordByCoordinate(startId,time,staff,item[recordItemId]);
                            }else{
                                record = RecordViewModel.findPatientRecordByCoordinate(startId,time,patient,item[recordItemId]);
                            }
                            if(record!=null){
                                record.setNote(voiceNote);
                                recordViewModel.updateRecords(record);
                                Log.d("TAG", "音声入力(更新)： " + voiceNote + "to" + item[recordItemId]);
                            }else{
                                if (recordItemId == 7){
                                    record = new Record(startId,startTime,startTime, time, "",staff,item[recordItemId],voiceNote);
                                }else{
                                    record = new Record(startId,startTime,startTime, time, patient,"",item[recordItemId],voiceNote);
                                }
                                recordViewModel.insertRecord(record);
                                Log.d("TAG", "音声入力(新規)： " + voiceNote + "to" + item[recordItemId]);
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        SetRecordTextView();
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        String notice = "記録ボタンとして追加しますか？";
                        builder.setTitle(notice);
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //記録ボタンを追加
                                switch (recordItemId) {
                                    case 0: {
                                        Condition condition = new Condition(voiceNote);           //Conditionを追加する
                                        conditionViewModel.insertCondition(condition);
                                        break;
                                    }
                                    case 1: {
                                        Expression expression = new Expression(voiceNote);           //facialExpressionを追加する
                                        expressionViewModel.insertExpression(expression);
                                        break;
                                    }
                                    case 2: {
                                        Gaze gaze = new Gaze(voiceNote);           //LineOfSightを追加する
                                        gazeViewModel.insertGaze(gaze);
                                        break;
                                    }
                                    case 3: {
                                        Talk talk = new Talk(voiceNote);           //TalkToを追加する
                                        talkViewModel.insertTalk(talk);
                                        break;
                                    }
                                    case 4: {
                                        Interaction interaction = new Interaction(voiceNote);           //ParoContactを追加する
                                        interactionViewModel.insertInteraction(interaction);
                                        break;
                                    }
                                    case 5: {
                                        ProblemBehavior problemBehavior = new ProblemBehavior(voiceNote);           //ProblemBehaviorを追加する
                                        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
                                        break;
                                    }
                                    case 6: {
                                        Remark remark = new Remark(voiceNote);           //Remarkを追加する
                                        remarkViewModel.insertRemark(remark);
                                    }
                                    case 7: {
                                        Intervene intervene = new Intervene(voiceNote);           //Remarkを追加する
                                        interveneViewModel.insertIntervene(intervene);
                                    }
                                }
                            }
                        });
                        builder.setNegativeButton("no", null);
                        builder.show();
                        AddDialog.dismiss();       //close this dialog
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddDialog.dismiss();       //close this dialog
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);      //close the keyboard
                    }
                });
                Log.d("TAG", "onActivityResult: 一番最初にある認識結果を表示する");
            } else {
                //何らかの原因で音声認識に失敗した場合はエラーメッセージを表示
                String warning = "音声の認識に失敗しました…";
                showToast(warning);
                Log.d("TAG", "onActivityResult: 何らかの原因で音声認識に失敗した場合はエラーメッセージを表示");
            }
        } else {
            Log.d("TAG", "onActivityResult: 04");
        }

    }

    @SuppressLint("SetTextI18n")
    void showToast(String warning) {
        TextView view = new TextView(getContext());
        Toast toast = new Toast(getContext());
        view.setText("        " + warning + "        ");
        view.setBackgroundResource(R.color.colorSearch);
        view.setTextColor(requireContext().getResources().getColor(R.color.white));
        view.setTextSize(18);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }


    public static long getCurrentTime() {
        date = new Date(System.currentTimeMillis());
        return (date.getTime());
    }

    public static class MyDialog extends Dialog {
        public MyDialog(Context context, View layout, int style) {
            super(context, style);
            setContentView(layout);
            Window window = getWindow();
            assert window != null;
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
    }

    //reset button data
    void resetButtonData() {
        conditionViewModel.deleteAllConditions();
        expressionViewModel.deleteAllExpressions();
        gazeViewModel.deleteAllGazes();
        talkViewModel.deleteAllTalks();
        interactionViewModel.deleteAllInteractions();
        problemBehaviorViewModel.deleteAllProblemBehaviors();
        remarkViewModel.deleteAllRemarks();
        interveneViewModel.deleteAllIntervenes();
        //----set default condition----
        recordPatientCondition = getString(R.string.condition04);
        Condition condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        recordPatientCondition = getString(R.string.condition03);
        condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        recordPatientCondition = getString(R.string.condition02);
        condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        recordPatientCondition = getString(R.string.condition01);
        condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        //----set default facialExpression----
        patientExpression = getString(R.string.expression01);
        Expression expression = new Expression(patientExpression);
        expressionViewModel.insertExpression(expression);
        patientExpression = getString(R.string.facialExpression02);
        expression = new Expression(patientExpression);
        expressionViewModel.insertExpression(expression);
        patientExpression = getString(R.string.facialExpression03);
        expression = new Expression(patientExpression);
        expressionViewModel.insertExpression(expression);
        patientExpression = getString(R.string.facialExpression04);
        expression = new Expression(patientExpression);
        expressionViewModel.insertExpression(expression);
        //----set default lineOfSight----
        patientGaze = getString(R.string.talkTo01);
        Gaze gaze = new Gaze(patientGaze);
        gazeViewModel.insertGaze(gaze);
        patientGaze = getString(R.string.talkTo02);
        gaze = new Gaze(patientGaze);
        gazeViewModel.insertGaze(gaze);
        patientGaze = getString(R.string.talkTo03);
        gaze = new Gaze(patientGaze);
        gazeViewModel.insertGaze(gaze);
        patientGaze = getString(R.string.lineOfSight01);
        gaze = new Gaze(patientGaze);
        gazeViewModel.insertGaze(gaze);
        patientGaze = getString(R.string.lineOfSight02);
        gaze = new Gaze(patientGaze);
        gazeViewModel.insertGaze(gaze);
        //----set default talkTo----
        patientTalk = getString(R.string.talkTo01);
        Talk talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        patientTalk = getString(R.string.talkTo02);
        talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        patientTalk = getString(R.string.talkTo03);
        talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        patientTalk = getString(R.string.talkTo04);
        talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        //----set default paroContact----
        patientInteraction = getString(R.string.interaction01);
        Interaction interaction = new Interaction(patientInteraction);
        interactionViewModel.insertInteraction(interaction);
        patientInteraction = getString(R.string.paroContact02);
        interaction = new Interaction(patientInteraction);
        interactionViewModel.insertInteraction(interaction);
        patientInteraction = getString(R.string.paroContact03);
        interaction = new Interaction(patientInteraction);
        interactionViewModel.insertInteraction(interaction);
        //----set default problemBehavior----
        patientProblemBehavior = getString(R.string.problemBehavior01);
        ProblemBehavior problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior02);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior03);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior04);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior05);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        //----set default remark----
        staffRemark = getString(R.string.remark01);
        Remark remark = new Remark(staffRemark);
        remarkViewModel.insertRemark(remark);
        staffRemark = getString(R.string.remark02);
        remark = new Remark(staffRemark);
        remarkViewModel.insertRemark(remark);
        //----set default intervene----
        staffIntervene = getString(R.string.intervene04);
        Intervene intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);
        staffIntervene = getString(R.string.intervene03);
        intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);
        staffIntervene = getString(R.string.intervene02);
        intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);
        staffIntervene = getString(R.string.intervene01);
        intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);

    }

    final Handler mCalHandler = new Handler(Looper.getMainLooper());
    int loopTime = 0;
    final Runnable mTicker = new Runnable() {
        public void run() {
            loopTime++;
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);
            mCalHandler.postAtTime(mTicker, next);
            textViewTime.setText(simpleDateFormatTime.format(currentTime += 1000));
            if (loopTime % 60 == 0) {
                final int length = spinnerItems.length;
                String[] addSpinnerItem = new String[length + 1];
                String newItem = simpleDateFormatTime.format(timeItem += 60 * 1000);
                addSpinnerItem[0] = newItem;
                System.arraycopy(spinnerItems, 0, addSpinnerItem, 1, length);
                spinnerItems = new String[length + 1];
                System.arraycopy(addSpinnerItem, 0, spinnerItems, 0, length + 1);
                timeAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item1, spinnerItems);
                timeAdapter.setDropDownViewResource(R.layout.dropdown_style1);
                spinnerTime.setAdapter(timeAdapter);
                spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        time = timeAdapter.getItem(position);
                        selectedTime = position;
                        SetRecordTextView();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        time = timeAdapter.getItem(length);
                        selectedTime = length - 1;
                        SetRecordTextView();

                    }
                });
                spinnerTime.setSelection(1);
                SetRecordTextView();
                newItem = simpleDateFormatTime.format(timeItem - 60 * 1000);
                showToast(newItem + "の内容を記入してください！");
                Vibrator vibrator = (Vibrator) requireActivity().getSystemService(VIBRATOR_SERVICE);
                long[] patter = {100, 100, 100, 100};
                vibrator.vibrate(patter, -1);
            }
        }
    };
}

