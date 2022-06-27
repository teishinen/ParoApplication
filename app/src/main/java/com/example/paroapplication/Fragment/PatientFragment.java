package com.example.paroapplication.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.appcompat.widget.Toolbar;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.PatientRecordAdapter;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.StaffDatabase.Staff;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientAge;
import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientId;
import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientName;
import static com.example.paroapplication.PatientDatabase.PatientAdapter.selectedPatientSex;

public class PatientFragment extends Fragment {
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    RecyclerView recyclerViewPatientRecord;
    PatientRecordAdapter patientRecordAdapter;
    List<Record> allRecords;
    List<Patient> allPatients;
    LiveData<List<Record>> filteredRecords;
    LiveData<List<Patient>> filteredPatients;
    TextView textViewPatientName, textViewPatientId, textViewPatientAge, textViewPatientSex, textViewWeeklyCareHours, textViewPatientSymptom;
    public double careHours;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.patient_fragment, container, false);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PatientViewModel.class);
        // TODO: Use the ViewModel


        Toolbar mToolBar = requireActivity().findViewById(R.id.toolbarInPatientFragment);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_patientFragment_to_dataFragment);
            }
        });

        Button editPatient = requireActivity().findViewById(R.id.buttonEditInPatientFragment);
        editPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = View.inflate(getContext(), R.layout.dialog_patient, null);
                final Dialog editPatientDialog = new RegisterFragment.MyDialog(getContext(), view, R.style.DialogTheme);
                editPatientDialog.setCancelable(true);
                editPatientDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewTittleInDialogPatient);
                textViewTittle.setText(R.string.addPatientData);
                final EditText editTextId, editTextName, editTextAge;
                final Spinner spinnerSex;
                editTextId = view.findViewById(R.id.editTextTextPersonPatientIdInDialogPatient);
                editTextName = view.findViewById(R.id.editTextTextPersonPatientNameInDialogPatient);
                editTextAge = view.findViewById(R.id.editTextTextPersonPatientAgeInDialogPatient);
                spinnerSex = view.findViewById(R.id.spinnerPatientSexInDialogPatient);
                editTextId.setText(textViewPatientId.getText());
                editTextId.setEnabled(false);
                editTextName.setText(textViewPatientName.getText());
                editTextAge.setText(textViewPatientAge.getText());
                String[] spinnerItems = {getString(R.string.male), getString(R.string.female)};       //性別を選択する
                final String[] patientSex = new String[1];
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSex.setAdapter(adapter);
                patientSex[0] = textViewPatientSex.getText().toString();
                if(patientSex[0].equals(getString(R.string.female))){
                    spinnerSex.setSelection(1);
                }else{
                    spinnerSex.setSelection(0);
                }
                spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        patientSex[0] = adapter.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        patientSex[0] = adapter.getItem(1);
                    }
                });
                Button buttonOk, buttonCancel;
                buttonOk = view.findViewById(R.id.buttonOkInDialogPatient);
                buttonCancel = view.findViewById(R.id.buttonCancelInDialogPatient);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        Patient patient = allPatients.get(0);
                        patient.setPatientId(editTextId.getText().toString());
                        patient.setPatientName(editTextName.getText().toString());
                        patient.setPatientAge(editTextAge.getText().toString());
                        patient.setPatientSex(patientSex[0]);
                        patientViewModel.updatePatients(patient);
                        editPatientDialog.dismiss();
                        //画面をリフレッシュする
                        setTextView(editTextId.getText().toString(),editTextName.getText().toString(),editTextAge.getText().toString(),patientSex[0]);
                        //記録データの患者氏名を直す
                        for(int i =0; i< allRecords.size();i++){
                            Record record = allRecords.get(i);
                            record.setPatient(editTextName.getText().toString());
                            recordViewModel.updateRecords(record);
                        }
                        filteredRecords = recordViewModel.findRecordsByPatientName(textViewPatientName.getText().toString());
                        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onChanged(List<Record> records) {
                                allRecords = records;
                                recyclerViewPatientRecord.smoothScrollBy(0, 200);
                                patientRecordAdapter.submitList(records);
                                patientRecordAdapter.notifyDataSetChanged();
                            }
                        });
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editPatientDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
            }
        });

        textViewPatientName = requireActivity().findViewById(R.id.textViewPatientNameInPatientFragment);
        textViewPatientId = requireActivity().findViewById(R.id.textViewPatientIdInPatientFragment);
        textViewPatientAge = requireActivity().findViewById(R.id.textViewPatientAgeInPatientFragment);
        textViewPatientSex = requireActivity().findViewById(R.id.textViewPatientSexInPatientFragment);
        textViewWeeklyCareHours = requireActivity().findViewById(R.id.textViewWeeklyCareHoursInPatientFragment);
        textViewPatientSymptom = requireActivity().findViewById(R.id.textViewPatientSymptomInPatientFragment);
        recyclerViewPatientRecord = requireActivity().findViewById(R.id.redyclerViewInPatientFragment);
        LinearLayoutManager layoutPatientRecord = new LinearLayoutManager(requireContext());
        layoutPatientRecord.setStackFromEnd(true);
        layoutPatientRecord.setReverseLayout(true);
        recyclerViewPatientRecord.setLayoutManager(layoutPatientRecord);
        patientRecordAdapter = new PatientRecordAdapter();
        recyclerViewPatientRecord.setAdapter(patientRecordAdapter);

        //----Data change monitoring----
        filteredRecords = recordViewModel.findRecordsByPatientName(selectedPatientName);
        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                recyclerViewPatientRecord.smoothScrollBy(0, 200);
                patientRecordAdapter.submitList(records);
                patientRecordAdapter.notifyDataSetChanged();
            }
        });
        filteredPatients = patientViewModel.getPatientById(selectedPatientId);
        filteredPatients.observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Patient> patients) {
                allPatients = patients;
            }
        });
        //----get careHours----
        try {
            careHours = (double) recordViewModel.getWeeklyCareHours(selectedPatientName) / (1000 * 60 * 60);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //----get symptom----
        try {
            String item = requireActivity().getString(R.string.patientProblemBehavior);
            String problemBehaviors = recordViewModel.getProblemBehaviorsByPatientName(selectedPatientName, item);
            textViewPatientSymptom.setText(problemBehaviors);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        setTextView(selectedPatientId,selectedPatientName,selectedPatientAge,selectedPatientSex);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n", "ResourceAsColor"})
    private void setTextView(String id,String name,String age,String sex) {
        textViewPatientName.setText(name);
        textViewPatientId.setText(id);
        textViewPatientAge.setText(age);
        textViewPatientSex.setText(sex);
        textViewWeeklyCareHours.setText(String.format("%.2f", careHours) + "h");
        TextView textViewId,textViewName,textViewAge,textViewSex,textViewProblemBehavior;
        textViewId = requireActivity().findViewById(R.id.textView16);
        textViewName = requireActivity().findViewById(R.id.textView20);
        textViewAge = requireActivity().findViewById(R.id.textView4);
        textViewSex = requireActivity().findViewById(R.id.textView);
        textViewProblemBehavior = requireActivity().findViewById(R.id.textView3);
        ImageView imageView = requireActivity().findViewById(R.id.imageView3);
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
}