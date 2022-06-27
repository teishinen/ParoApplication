package com.example.paroapplication.Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientSelectAdapter;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.List;

public class SelectPatientFragment extends Fragment {
    RecordViewModel recordViewModel;
    PatientViewModel patientViewModel;
    RecyclerView recyclerViewSelectedPatient;
    PatientSelectAdapter patientSelectAdapter;
    List<Patient> allPatients;
    LiveData<List<Patient>> filteredPatients;
    String search;

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
        layoutPatient.setStackFromEnd(true);
        layoutPatient.setReverseLayout(true);
        recyclerViewSelectedPatient.setLayoutManager(layoutPatient);
        patientSelectAdapter = new PatientSelectAdapter();
        recyclerViewSelectedPatient.setAdapter(patientSelectAdapter);


        //----Data change monitoring----
        patientViewModel.getAllPatientsLive().observe(getViewLifecycleOwner(), new Observer<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Patient> patients) {
                allPatients = patients;
                recyclerViewSelectedPatient.smoothScrollBy(0, -200);
                patientSelectAdapter.submitList(patients);
                patientSelectAdapter.notifyDataSetChanged();
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
                        allPatients = patients;
                        recyclerViewSelectedPatient.smoothScrollBy(0, 200);
                        patientSelectAdapter.submitList(patients);
                        patientSelectAdapter.notifyDataSetChanged();
                    }
                });

                return true;
            }
        });

        //start button
        Button buttonContinue, buttonCancel;
        buttonContinue = requireView().findViewById(R.id.buttonContinueInSelectPatientFragment);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PatientSelectAdapter.getSelectedPatientName().size() > 0) {
                    //患者を選択していないとき
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_selectPatientFragment_to_selectHelperFragment);
                } else {
                    Toast.makeText(requireContext(), "患者を選んでください！", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonCancel = requireActivity().findViewById(R.id.buttonCancelInSelectPatientFragment);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_selectPatientFragment_to_homeFragment);
            }
        });

    }

}
