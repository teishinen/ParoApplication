package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordButtonDatabase.ConditionViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;


public class PatientRecordAdapter extends ListAdapter<Record, PatientRecordAdapter.MyViewHolder> {
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    ConditionViewModel conditionViewModel;
    LiveData<List<Record>> filteredRecords;
    List<Record> allRecords;
    PatientRecordDetailAdapter patientRecordDetailAdapter;
    public int careMin;
    double careHours;
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");

    public PatientRecordAdapter() {
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
                        && oldItem.getItem().equals(newItem.getItem())
                        && oldItem.getNote().equals(newItem.getNote()));
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_patient_record, parent, false);
        patientViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(PatientViewModel.class);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
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
        holder.switchDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.view.setVisibility(View.VISIBLE);
                    holder.scrollView.setVisibility(View.VISIBLE);
                    holder.textViewNoData.setVisibility(View.VISIBLE);
                    //患者の名前とstartIdを取る
                    String startId = record.getStartId();
                    String patientName = record.getPatient();
                    //recyclerViewを設置する
                    LinearLayoutManager layoutRecord = new LinearLayoutManager(holder.itemView.getContext());
                    layoutRecord.setStackFromEnd(true);
                    layoutRecord.setReverseLayout(true);
                    holder.recyclerViewPatientRecord.setLayoutManager(layoutRecord);
                    patientRecordDetailAdapter = new PatientRecordDetailAdapter();
                    holder.recyclerViewPatientRecord.setAdapter(patientRecordDetailAdapter);
                    filteredRecords = recordViewModel.findRecordsDetailByPatient(startId, patientName);       //  介護記録の詳細を探す
                    filteredRecords.observe((LifecycleOwner) holder.itemView.getContext(), new Observer<List<Record>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<Record> records) {
                            allRecords = records;
                            holder.recyclerViewPatientRecord.smoothScrollBy(0, 200);
                            patientRecordDetailAdapter.submitList(records);
                            patientRecordDetailAdapter.notifyDataSetChanged();
                            if(allRecords.size()==0){
                                holder.scrollView.setVisibility(View.GONE);
                                holder.textViewNoData.setVisibility(View.VISIBLE);
                            }else{
                                holder.scrollView.setVisibility(View.VISIBLE);
                                holder.textViewNoData.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    holder.view.setVisibility(View.GONE);
                    holder.scrollView.setVisibility(View.GONE);
                    holder.textViewNoData.setVisibility(View.GONE);
                }
            }
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
