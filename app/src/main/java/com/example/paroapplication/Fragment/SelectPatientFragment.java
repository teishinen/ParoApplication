package com.example.paroapplication.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.paroapplication.EvaluateBeforeActivity;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientSelectAdapter;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.bean.PatientSelect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectPatientFragment extends Fragment {
    RecordViewModel recordViewModel;
    PatientViewModel patientViewModel;
    RecyclerView recyclerViewSelectedPatient;
    PatientSelectAdapter patientSelectAdapter;

    LiveData<List<Patient>> filteredPatients;
    String search;
    private Button buttonContinue;
    private ArrayList<PatientSelect> mlist=new ArrayList<>();
    private Staff staff;
    public SelectPatientFragment(Staff staff) {
        this.staff=staff;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_patient_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PatientViewModel.class);

        //----recyclerViewRecord----
        recyclerViewSelectedPatient = requireView().findViewById(R.id.recyclerViewSelectPatient);
        LinearLayoutManager layoutPatient = new LinearLayoutManager(requireContext());

        recyclerViewSelectedPatient.setLayoutManager(layoutPatient);
        patientSelectAdapter = new PatientSelectAdapter();
        recyclerViewSelectedPatient.setAdapter(patientSelectAdapter);
        patientSelectAdapter.setOnSelect(new PatientSelectAdapter.onSelect() {
            @Override
            public void onSelectListener(boolean patientSelect, int pos) {
                for (int i = 0; i <mlist.size() ; i++) {
                    mlist.get(i).setSelect(false);
                }
                    mlist.get(pos).setSelect(patientSelect);
                    patientSelectAdapter.setMlist(mlist);



            }
        });


        //----Data change monitoring----
        patientViewModel.getAllPatientsLive().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Patient> patients) {
                mlist.clear();

                for (int i = 0; i <patients.size() ; i++) {
                    mlist.add(new PatientSelect(patients.get(i),false));
                }
                Collections.reverse(mlist);
                patientSelectAdapter.setMlist(mlist);

            }
        });

        //----Select patient by search----
        SearchView searchView = requireActivity().findViewById(R.id.searchViewInSelectPatientFragment);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                filteredPatients = patientViewModel.findPatientsWithPattern(search);
                filteredPatients.observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Patient> patients) {
                        mlist.clear();
                        recyclerViewSelectedPatient.smoothScrollBy(0, 200);
                        for (int i = 0; i <patients.size() ; i++) {
                            mlist.add(new PatientSelect(patients.get(i),false));
                        }
                        patientSelectAdapter.setMlist(mlist);

                    }
                });

                return true;
            }
        });

        buttonContinue = requireView().findViewById(R.id.buttonContinueInSelectPatientFragment);
        buttonContinue.setOnClickListener(v -> {
            ArrayList<PatientSelect>  patientSelects=new ArrayList<>();
            for (int i = 0; i <mlist.size() ; i++) {
                if (mlist.get(i).isSelect()){
                    patientSelects.add(mlist.get(i));
                }
            }
            if (patientSelects.size()>0){
                Intent intent=new Intent(getActivity(),EvaluateBeforeActivity.class);
                intent.putExtra("selectPatient",patientSelects.get(0).getPatient());
                intent.putExtra("staff",staff);
                startActivity(intent);

            }

        });

    }

}
