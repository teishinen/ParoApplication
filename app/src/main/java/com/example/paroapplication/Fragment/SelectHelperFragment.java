package com.example.paroapplication.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffSelectAdapter;
import com.example.paroapplication.StaffDatabase.StaffViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.List;

public class SelectHelperFragment extends Fragment {
    RecordViewModel recordViewModel;
    StaffViewModel staffViewModel;
    RecyclerView recyclerViewSelectedHelper;
    StaffSelectAdapter staffSelectAdapter;
    List<Staff> allStaffs;
    LiveData<List<Staff>> filteredHelpers;
    String search;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_helper_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        staffViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(StaffViewModel.class);

        //----recyclerViewRecord----
        recyclerViewSelectedHelper = requireView().findViewById(R.id.recyclerViewSelectHelper);
        LinearLayoutManager layoutHelper = new LinearLayoutManager(requireContext());
        layoutHelper.setStackFromEnd(true);
        layoutHelper.setReverseLayout(true);
        recyclerViewSelectedHelper.setLayoutManager(layoutHelper);
        staffSelectAdapter = new StaffSelectAdapter();
        recyclerViewSelectedHelper.setAdapter(staffSelectAdapter);


        //----Data change monitoring----
        staffViewModel.getAllStaffsLive().observe(getViewLifecycleOwner(), new Observer<List<Staff>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Staff> staff) {
                allStaffs = staff;
                recyclerViewSelectedHelper.smoothScrollBy(0, -200);
                staffSelectAdapter.submitList(staff);
                staffSelectAdapter.notifyDataSetChanged();
            }
        });

        //----Select patient by search----
        SearchView searchView = requireActivity().findViewById(R.id.searchViewInSelectHelperFragment);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                filteredHelpers = staffViewModel.findStaffsWithPattern(search);
                filteredHelpers.observe(getViewLifecycleOwner(), new Observer<List<Staff>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Staff> staff) {
                        allStaffs = staff;
                        recyclerViewSelectedHelper.smoothScrollBy(0, 200);
                        staffSelectAdapter.submitList(staff);
                        staffSelectAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });

        //start button
        Button buttonStart, buttonBack;
        buttonStart = requireView().findViewById(R.id.buttonStartInSelectHelperFragment);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaffSelectAdapter.getSelectedStaffName().size() > 0) {
                    NavController controller = Navigation.findNavController(v);
                    controller.navigate(R.id.action_selectHelperFragment_to_recordFragment);
                } else {
                    Toast.makeText(requireContext(), "介護者を選んでください！", Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonBack = requireActivity().findViewById(R.id.buttonBackInSelectHelperFragment);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_selectHelperFragment_to_selectPatientFragment);
            }
        });

    }

}
