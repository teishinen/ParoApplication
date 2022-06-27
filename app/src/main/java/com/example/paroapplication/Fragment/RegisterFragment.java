package com.example.paroapplication.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffAdapter;
import com.example.paroapplication.StaffDatabase.StaffViewModel;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientAdapter;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RegisterFragment extends Fragment {

    PatientViewModel patientViewModel;
    StaffViewModel staffViewModel;
    RecyclerView recyclerView;
    PatientAdapter patientAdapter;
    StaffAdapter staffAdapter;
    List<Patient> allPatients;
    List<Staff> allStaffs;
    LiveData<List<Patient>> filteredPatients;
    LiveData<List<Staff>> filteredStaffs;
    FloatingActionButton floatingActionButtonAddPatient;
    String search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        patientViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PatientViewModel.class);
        staffViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(StaffViewModel.class);
        // TODO: Use the ViewModel

        //----recyclerViewRecord----
        recyclerView = requireView().findViewById(R.id.recyclerViewInRegister);
        LinearLayoutManager layoutPatient = new LinearLayoutManager(requireContext());
        layoutPatient.setStackFromEnd(true);
        layoutPatient.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutPatient);
        patientAdapter = new PatientAdapter();
        staffAdapter = new StaffAdapter();
        recyclerView.setAdapter(patientAdapter);

        //----Data change monitoring----
        patientViewModel.getAllPatientsLive().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Patient> patients) {
                allPatients = patients;
                recyclerView.smoothScrollBy(0, -200);
                patientAdapter.submitList(patients);
                patientAdapter.notifyDataSetChanged();
            }
        });

        staffViewModel.getAllStaffsLive().observe(getViewLifecycleOwner(), new Observer<List<Staff>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Staff> staff) {
                allStaffs = staff;
                recyclerView.smoothScrollBy(0, -200);
                staffAdapter.submitList(staff);
                staffAdapter.notifyDataSetChanged();
            }
        });

        //----set switch----
        @SuppressLint("UseSwitchCompatOrMaterialCode") final Switch switchStaff = requireActivity().findViewById(R.id.switchStaff);
        switchStaff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    recyclerView.setAdapter(staffAdapter);
                    switchStaff.setText(R.string.staff);
                }else{
                    recyclerView.setAdapter(patientAdapter);
                    switchStaff.setText(R.string.patient);
                }
            }
        });
        //----Select patient by search----
        SearchView searchView = requireActivity().findViewById(R.id.searchViewInDataFragment);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                if(switchStaff.isChecked()){
                    filteredStaffs = staffViewModel.findStaffsWithPattern(search);
                    filteredStaffs.observe(getViewLifecycleOwner(), new Observer<List<Staff>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<Staff> staffs) {
                            allStaffs = staffs;
                            recyclerView.smoothScrollBy(0, 200);
                            staffAdapter.submitList(staffs);
                            staffAdapter.notifyDataSetChanged();
                        }
                    });

                }else{
                    filteredPatients = patientViewModel.findPatientsWithPattern(search);
                    filteredPatients.observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<Patient> patients) {
                            allPatients = patients;
                            recyclerView.smoothScrollBy(0, 200);
                            patientAdapter.submitList(patients);
                            patientAdapter.notifyDataSetChanged();
                        }
                    });
                }

                return true;
            }
        });

        floatingActionButtonAddPatient = requireActivity().findViewById(R.id.floatingActionButtonAddPatient);
        floatingActionButtonAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = View.inflate(getContext(),R.layout.dialog_patient, null);
                final Dialog addPatientDialog = new MyDialog(getContext(), view, R.style.DialogTheme);
                addPatientDialog.setCancelable(true);
                addPatientDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewTittleInDialogPatient);
                final EditText editTextId,editTextName,editTextAge;
                final Spinner spinnerSex;
                editTextId = view.findViewById(R.id.editTextTextPersonPatientIdInDialogPatient);
                editTextName = view.findViewById(R.id.editTextTextPersonPatientNameInDialogPatient);
                editTextAge = view.findViewById(R.id.editTextTextPersonPatientAgeInDialogPatient);
                spinnerSex = view.findViewById(R.id.spinnerPatientSexInDialogPatient);
                int size;
                if (switchStaff.isChecked()){
                    size = allStaffs.size() + 1;                   //set default staff id
                    textViewTittle.setText(R.string.addStaffData);
                }else{
                    size = allPatients.size() + 1;                   //set default patient id
                    textViewTittle.setText(R.string.addPatientData);
                }
                @SuppressLint("DefaultLocale") String defaultId = String.format("%08d",size);
                editTextId.setText(defaultId);
                String[] spinnerItems ={getString(R.string.male),getString(R.string.female)};       //性別を選択する
                final String[] patientSex = new String[1];
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSex.setAdapter(adapter);
                spinnerSex.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        patientSex[0] = adapter.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        patientSex[0] = adapter.getItem(1);
                    }
                });
                Button buttonOk,buttonCancel;
                buttonOk = view.findViewById(R.id.buttonOkInDialogPatient);
                buttonCancel = view.findViewById(R.id.buttonCancelInDialogPatient);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        String id,name,age;
                        id = editTextId.getText().toString().trim();
                        name = editTextName.getText().toString().trim();
                        age = editTextAge.getText().toString().trim();
                        if (switchStaff.isChecked()){
                            Staff staff = new Staff(id,name,age, patientSex[0]);
                            staffViewModel.insertStaff(staff);
                            staffAdapter.notifyDataSetChanged();
                        }else{
                            Patient patient = new Patient(id,name,age, patientSex[0]);
                            patientViewModel.insertPatient(patient);
                            patientAdapter.notifyDataSetChanged();
                        }
                        addPatientDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addPatientDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
            }
        });
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
