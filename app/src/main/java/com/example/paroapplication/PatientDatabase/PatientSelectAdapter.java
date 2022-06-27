package com.example.paroapplication.PatientDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.R;

import java.util.ArrayList;
import java.util.List;


public class PatientSelectAdapter extends ListAdapter<Patient, PatientSelectAdapter.MyViewHolder> {
    PatientViewModel patientViewModel;
    public static List<String> selectedPatientName;
    public static String[] buttonSelectedPatientName;

    public static List<String> getSelectedPatientName() {
        return selectedPatientName;
    }



    public PatientSelectAdapter() {
        super(new DiffUtil.ItemCallback<Patient>() {
            @Override
            public boolean areItemsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
                return (oldItem.getPatientId().equals(newItem.getPatientId())
                        && oldItem.getPatientName().equals(newItem.getPatientName())
                        && oldItem.getPatientAge().equals(newItem.getPatientAge())
                        && oldItem.getPatientSex().equals(newItem.getPatientSex()));
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_select_patient, parent, false);
        patientViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(PatientViewModel.class);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Patient patient = getItem(position);
        holder.itemView.setTag(R.id.patient_for_view_holder02, patient);
        holder.textViewPatientID.setText(String.valueOf(patient.getPatientId()));
        holder.textViewPatientName.setText(String.valueOf(patient.getPatientName()));
        holder.textViewPatientAge.setText(String.valueOf(patient.getPatientAge()));
        holder.textViewPatientSex.setText(String.valueOf(patient.getPatientSex()));
        if (selectedPatientName == null){
            selectedPatientName = new ArrayList<>();
        }else{
            for (int i = 0; i < selectedPatientName.size(); i++ ){
                if(patient.getPatientName().equals(selectedPatientName.get(i))){
                    holder.checkBoxSelectPatient.setChecked(true);
                }
            }
        }
        //get checked patient data
        holder.checkBoxSelectPatient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedPatientName.add(holder.textViewPatientName.getText().toString());
                } else {
                    for (int i = 0; i < getSelectedPatientName().size(); i++) {
                        if (getSelectedPatientName().get(i).equals(holder.textViewPatientName.getText().toString())) {
                            selectedPatientName.remove(i);    //キャンセルした患者の名前を削除する
                            i--;
                        }
                    }
                }
                int size = selectedPatientName.size();
                buttonSelectedPatientName = selectedPatientName.toArray(new String[size]);
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxSelectPatient;
        TextView textViewPatientID, textViewPatientName, textViewPatientAge, textViewPatientSex;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxSelectPatient = itemView.findViewById(R.id.checkBoxInCellSelectPatient);
            textViewPatientID = itemView.findViewById(R.id.textViewPatientId1InCellSelectPatient);
            textViewPatientName = itemView.findViewById(R.id.textViewPatientName1InCellSelectPatient);
            textViewPatientAge = itemView.findViewById(R.id.textViewPatientAge1InCellSelectPatient);
            textViewPatientSex = itemView.findViewById(R.id.textViewPatientSex1InCellSelectPatient);
        }
    }
}
