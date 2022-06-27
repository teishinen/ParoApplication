package com.example.paroapplication.Fragment;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.StaffRecordAdapter;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffAge;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffId;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffName;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffSex;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffAge;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffId;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffName;
import static com.example.paroapplication.StaffDatabase.StaffAdapter.selectedStaffSex;

public class StaffFragment extends Fragment {
    StaffViewModel staffViewModel;
    RecordViewModel recordViewModel;
    RecyclerView recyclerViewStaffRecord;
    StaffRecordAdapter staffRecordAdapter;
    List<Record> allRecords;
    List<Staff> allStaffs;
    LiveData<List<Record>> filteredRecords;
    LiveData<List<Staff>> filteredStaffs;
    TextView textViewStaffName,textViewStaffId,textViewStaffAge,textViewStaffSex,textViewWeeklyCareHours,textViewStaffSymptom;
    public double careHours;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.staff_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(RecordViewModel.class);
        staffViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(StaffViewModel.class);
        // TODO: Use the ViewModel

        Toolbar mToolBar = requireActivity().findViewById(R.id.toolbarInStaffFragment);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_staffFragment_to_dataFragment);
            }
        });

        Button editStaff = requireActivity().findViewById(R.id.buttonEditInStaffFragment);
        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = View.inflate(getContext(), R.layout.dialog_patient, null);
                final Dialog editStaffDialog = new RegisterFragment.MyDialog(getContext(), view, R.style.DialogTheme);
                editStaffDialog.setCancelable(true);
                editStaffDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewTittleInDialogPatient);
                textViewTittle.setText(R.string.addStaffData);
                final EditText editTextId, editTextName, editTextAge;
                final Spinner spinnerSex;
                editTextId = view.findViewById(R.id.editTextTextPersonPatientIdInDialogPatient);
                editTextName = view.findViewById(R.id.editTextTextPersonPatientNameInDialogPatient);
                editTextAge = view.findViewById(R.id.editTextTextPersonPatientAgeInDialogPatient);
                spinnerSex = view.findViewById(R.id.spinnerPatientSexInDialogPatient);
                editTextId.setText(textViewStaffId.getText());
                editTextId.setEnabled(false);
                editTextName.setText(textViewStaffName.getText());
                editTextAge.setText(textViewStaffAge.getText());
                String[] spinnerItems = {getString(R.string.male), getString(R.string.female)};       //性別を選択する
                final String[] staffSex = new String[1];
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSex.setAdapter(adapter);
                staffSex[0] = textViewStaffSex.getText().toString();
                if(staffSex[0].equals(getString(R.string.female))){
                    spinnerSex.setSelection(1);
                }else{
                    spinnerSex.setSelection(0);
                }
                spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        staffSex[0] = adapter.getItem(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        staffSex[0] = adapter.getItem(1);
                    }
                });
                Button buttonOk, buttonCancel;
                buttonOk = view.findViewById(R.id.buttonOkInDialogPatient);
                buttonCancel = view.findViewById(R.id.buttonCancelInDialogPatient);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        Staff staff = allStaffs.get(0);
                        staff.setStaffId(editTextId.getText().toString());
                        staff.setStaffName(editTextName.getText().toString());
                        staff.setStaffAge(editTextAge.getText().toString());
                        staff.setStaffSex(staffSex[0]);
                        staffViewModel.updateStaffs(staff);
                        editStaffDialog.dismiss();
                        //画面をリフレッシュする
                        setTextView(editTextId.getText().toString(),editTextName.getText().toString(),editTextAge.getText().toString(),staffSex[0]);
                        //記録データの患者氏名を直す
                        for(int i =0; i< allRecords.size();i++){
                            Record record = allRecords.get(i);
                            record.setStaff(editTextName.getText().toString());
                            recordViewModel.updateRecords(record);
                        }
                        filteredRecords = recordViewModel.findRecordsByStaffName(textViewStaffName.getText().toString());
                        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onChanged(List<Record> records) {
                                allRecords = records;
                                recyclerViewStaffRecord.smoothScrollBy(0, 200);
                                staffRecordAdapter.submitList(records);
                                staffRecordAdapter.notifyDataSetChanged();
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
                        editStaffDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
            }
        });


        
        textViewStaffName = requireActivity().findViewById(R.id.textViewStaffNameInStaffFragment);
        textViewStaffId = requireActivity().findViewById(R.id.textViewStaffIdInStaffFragment);
        textViewStaffAge = requireActivity().findViewById(R.id.textViewStaffAgeInStaffFragment);
        textViewStaffSex = requireActivity().findViewById(R.id.textViewStaffSexInStaffFragment);
        textViewWeeklyCareHours = requireActivity().findViewById(R.id.textViewWeeklyCareHoursInStaffFragment);
        textViewStaffSymptom = requireActivity().findViewById(R.id.textViewStaffSymptomInStaffFragment);
        recyclerViewStaffRecord = requireActivity().findViewById(R.id.redyclerViewInStaffFragment);
        LinearLayoutManager layoutStaffRecord = new LinearLayoutManager(requireContext());
        layoutStaffRecord.setStackFromEnd(true);
        layoutStaffRecord.setReverseLayout(true);
        recyclerViewStaffRecord.setLayoutManager(layoutStaffRecord);
        staffRecordAdapter = new StaffRecordAdapter();
        recyclerViewStaffRecord.setAdapter(staffRecordAdapter);

        //----Data change monitoring----
        filteredRecords = recordViewModel.findRecordsByStaffName(selectedStaffName);
        filteredRecords.observe(getViewLifecycleOwner(), new Observer<List<Record>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Record> records) {
                allRecords = records;
                recyclerViewStaffRecord.smoothScrollBy(0, 200);
                staffRecordAdapter.submitList(records);
                staffRecordAdapter.notifyDataSetChanged();
            }
        });
        filteredStaffs = staffViewModel.getStaffById(selectedStaffId);
        filteredStaffs.observe(getViewLifecycleOwner(), new Observer<List<Staff>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Staff> staffs) {
                allStaffs = staffs;
            }
        });

        //----get careHours----
        try {
            careHours = (double) recordViewModel.getWeeklyCareHours(selectedStaffName)/(1000 * 60 *60);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //----get symptom----
//        try {
//            String allSymptom = recordViewModel.getProblemBehaviorsByStaffName(selectedStaffName,);
//            textViewStaffSymptom.setText(allSymptom);
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
        setTextView(selectedStaffId,selectedStaffName,selectedStaffAge,selectedStaffSex);
    }
    @SuppressLint({"DefaultLocale", "SetTextI18n", "ResourceAsColor"})
    private void setTextView(String id,String name,String age,String sex) {
        textViewStaffName.setText(name);
        textViewStaffId.setText(id);
        textViewStaffAge.setText(age);
        textViewStaffSex.setText(sex);
        textViewWeeklyCareHours.setText(String.format("%.2f", careHours) + "h");
        TextView textViewId,textViewName,textViewAge,textViewSex,textViewProblemBehavior;
        textViewId = requireActivity().findViewById(R.id.textView16InCellStaffFragment);
        textViewName = requireActivity().findViewById(R.id.textView20InCellStaffFragment);
        textViewAge = requireActivity().findViewById(R.id.textView4InCellStaffFragment);
        textViewSex = requireActivity().findViewById(R.id.textViewInCellStaffFragment);
        textViewProblemBehavior = requireActivity().findViewById(R.id.textView3InCellStaffFragment);
        ImageView imageView = requireActivity().findViewById(R.id.imageView3InCellStaffFragment);
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