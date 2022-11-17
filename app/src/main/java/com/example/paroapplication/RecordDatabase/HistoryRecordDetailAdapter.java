package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paroapplication.PatientActivity;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class HistoryRecordDetailAdapter extends ListAdapter<Patient, HistoryRecordDetailAdapter.MyViewHolder> {

    RecordViewModel recordViewModel;
    PatientViewModel patientViewModel;
    LiveData<List<Patient>> filteredPatients;
    private String time;

    public HistoryRecordDetailAdapter(RecordViewModel recordViewModel,PatientViewModel patientViewModel,String time) {
        super(new DiffUtil.ItemCallback<Patient>() {
            @Override
            public boolean areItemsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
                return oldItem.getPatientId() .equals( newItem.getPatientId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
                return (oldItem.getId()==(newItem.getId())
                        &&oldItem.getPatientId() .equals( newItem.getPatientId())
                        && oldItem.getPatientAge() .equals( newItem.getPatientAge())
                        && oldItem.getPatientSex() .equals(newItem.getPatientSex())
                        && oldItem.getPatientName().equals(newItem.getPatientName()));
            }
        });
        this.recordViewModel=recordViewModel;
        this.patientViewModel=patientViewModel;
        this.time=time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_history_record_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "LogConditional", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Patient patient = getItem(position);

        final String patientName = patient.getPatientName();
        if (patientName.length() >0){
            holder.textViewPatientName.setText(patientName);
        }
        holder.textViewPatientAge.setText(patient.getPatientAge());
        holder.textViewSex.setText(patient.getPatientSex());


        //----set click listener--
        //クリックすると，セラピーの過程が表示される
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.itemView.getContext();
                Intent intent=new Intent(context, PatientActivity.class);
                intent.putExtra("patient",patient);
                intent.putExtra("time",time);
                context.startActivity(intent);

            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPatientName, textViewPatientAge,textViewSex;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPatientName = itemView.findViewById(R.id.textViewPatientNameInCellHistoryRecordDetail);
            textViewPatientAge = itemView.findViewById(R.id.textViewPatientAgeInCellHistoryRecordDetail);
            textViewSex = itemView.findViewById(R.id.textViewSexInCellHistoryRecordDetail);
        }
    }
}
