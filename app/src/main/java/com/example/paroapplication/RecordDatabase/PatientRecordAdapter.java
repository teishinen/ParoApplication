package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDetailActivity;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class PatientRecordAdapter extends ListAdapter<Record, PatientRecordAdapter.MyViewHolder> {
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;

    LiveData<List<Record>> filteredRecords;
    List<Record> allRecords;
    PatientRecordDetailAdapter patientRecordDetailAdapter;
    public int careMin;
    double careHours;
    private Patient patient;
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");

    public PatientRecordAdapter(PatientViewModel patientViewModel, RecordViewModel recordViewModel, Patient patient) {
        super(new DiffUtil.ItemCallback<Record>() {
            @Override
            public boolean areItemsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
                return oldItem.getRecordId() == newItem.getRecordId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
                return (oldItem.getStartId().equals(newItem.getStartId())
                        &&oldItem.getStartTime() == newItem.getStartTime()
                        && oldItem.getFinishTime() == newItem.getFinishTime()
                        && oldItem.getTime() .equals(newItem.getTime())
                        && oldItem.getPatient().equals(newItem.getPatient())
                        && oldItem.getStaff().equals(newItem.getStaff())

                        );
            }
        });
        this.patientViewModel=patientViewModel;
        this.recordViewModel=recordViewModel;
        this.patient = patient;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_patient_record, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "LogConditional", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder_patient, record);
        holder.textViewDate.setText(simpleDateFormatDate.format(record.getStartTime()));
        holder.textViewTime.setText(simpleDateFormatTime.format(record.getStartTime()) + "~" + simpleDateFormatTime.format(record.getFinishTime()));
        //介護時間数を計算する
        careHours =(double) (record.getFinishTime()-record.getStartTime())/(1000*60*60);
        if (careHours < 0){
            careHours = 0;
        }
        holder.textViewCareHours.setText("(" + String.format("%.2f", careHours) + "h)");
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), RecordDetailActivity.class);
            intent.putExtra("record",record);
            intent.putExtra("patient",patient);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchDetail;
        TextView textViewDate, textViewTime, textViewCareHours, textViewNoData;
        RecyclerView recyclerViewPatientRecord;
        View view;
        ScrollView scrollView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            switchDetail = itemView.findViewById(R.id.switchDetail);
            textViewDate = itemView.findViewById(R.id.textViewDateInCellPatientRecord);
            textViewTime = itemView.findViewById(R.id.textViewTimeInCellPatientRecord);
            textViewCareHours = itemView.findViewById(R.id.textViewCareHoursInCellPatientRecord);
            textViewNoData = itemView.findViewById(R.id.textViewNoData);
            recyclerViewPatientRecord = itemView.findViewById(R.id.recyclerViewPatientRecord);
            view = itemView.findViewById(R.id.view7);
            scrollView = itemView.findViewById(R.id.scrollView01);
        }
    }
}
