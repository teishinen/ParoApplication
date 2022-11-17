package com.example.paroapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientSelectAdapter;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.List;

public class SelectPatientActivity extends AppCompatActivity {
    RecordViewModel recordViewModel;
    PatientViewModel patientViewModel;
    RecyclerView recyclerViewSelectedPatient;
    PatientSelectAdapter patientSelectAdapter;
    List<Patient> allPatients;
    LiveData<List<Patient>> filteredPatients;
    String search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);
        recordViewModel = new ViewModelProvider(SelectPatientActivity.this, new ViewModelProvider.AndroidViewModelFactory(SelectPatientActivity.this.getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(SelectPatientActivity.this, new ViewModelProvider.AndroidViewModelFactory(SelectPatientActivity.this.getApplication())).get(PatientViewModel.class);

        //----recyclerViewRecord----
        recyclerViewSelectedPatient =findViewById(R.id.recyclerViewSelectPatient);
        LinearLayoutManager layoutPatient = new LinearLayoutManager(SelectPatientActivity.this);
        layoutPatient.setStackFromEnd(true);
        layoutPatient.setReverseLayout(true);
        recyclerViewSelectedPatient.setLayoutManager(layoutPatient);
        patientSelectAdapter = new PatientSelectAdapter();
        recyclerViewSelectedPatient.setAdapter(patientSelectAdapter);


        //----Data change monitoring----
        patientViewModel.getAllPatientsLive().observe(this, new Observer<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Patient> patients) {
                allPatients = patients;
                recyclerViewSelectedPatient.smoothScrollBy(0, -200);
//                patientSelectAdapter.submitList(patients);
                patientSelectAdapter.notifyDataSetChanged();
            }
        });

        //----Select patient by search----
        SearchView searchView =findViewById(R.id.searchViewInSelectPatientFragment);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                filteredPatients = patientViewModel.findPatientsWithPattern(search);
                filteredPatients.observe(SelectPatientActivity.this, new Observer<List<Patient>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Patient> patients) {
                        allPatients = patients;
                        recyclerViewSelectedPatient.smoothScrollBy(0, 200);
//                        patientSelectAdapter.submitList(patients);
                        patientSelectAdapter.notifyDataSetChanged();
                    }
                });

                return true;
            }
        });

        //start button
        Button buttonContinue, buttonCancel;
        buttonContinue = findViewById(R.id.buttonContinueInSelectPatientFragment);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonCancel =findViewById(R.id.buttonCancelInSelectPatientFragment);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
