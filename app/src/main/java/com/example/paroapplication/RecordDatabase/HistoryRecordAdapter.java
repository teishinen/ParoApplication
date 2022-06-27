package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.util.Log;
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

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;


public class HistoryRecordAdapter extends ListAdapter<Record, HistoryRecordAdapter.MyViewHolder> {
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    LiveData<List<Record>> filteredRecords;
    List<Record> allRecords;
    HistoryRecordDetailAdapter historyRecordDetailAdapter;
    public double careHours;
    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")  SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");


    public HistoryRecordAdapter() {
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


    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_history_record, parent, false);
        patientViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(PatientViewModel.class);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "LogConditional", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder_history, record);
        holder.textViewDate.setText(simpleDateFormatDate.format(record.getStartTime()));
        holder.textViewTime.setText(simpleDateFormatTime.format(record.getStartTime()) + "~" + simpleDateFormatTime.format(record.getFinishTime()));
        Log.d("TAG", "Records(" + position + ").finishTime = " + record.getFinishTime() + "recordId : " + record.getRecordId());
        //介護時間数を計算する
        careHours =(double) (record.getFinishTime()-record.getStartTime())/(1000*60*60);
        if (careHours < 0){
            careHours = 0;
        }
        holder.textViewCareHours.setText("(" + String.format("%.2f", careHours) + "h)");

        holder.switchHistoryDetail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.view.setVisibility(View.VISIBLE);
                    holder.scrollView.setVisibility(View.VISIBLE);
                    //recyclerViewを設置する
                    LinearLayoutManager layoutRecord = new LinearLayoutManager(holder.itemView.getContext());
                    layoutRecord.setStackFromEnd(true);
                    layoutRecord.setReverseLayout(true);
                    holder.recyclerView.setLayoutManager(layoutRecord);
                    historyRecordDetailAdapter = new HistoryRecordDetailAdapter();
                    holder.recyclerView.setAdapter(historyRecordDetailAdapter);
                    String startId = record.getStartId();
                    filteredRecords = recordViewModel.findHistoryDetail(startId);       //  介護記録の詳細を探す
                    filteredRecords.observe((LifecycleOwner) holder.itemView.getContext(), new Observer<List<Record>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<Record> records) {
                            allRecords = records;
                            holder.recyclerView.smoothScrollBy(0, 200);
                            historyRecordDetailAdapter.submitList(records);
                            historyRecordDetailAdapter.notifyDataSetChanged();
                            if(allRecords.size()==0){
                                Log.d("TAG", "onChanged: " + "size = 0" );
                            }else{
                                Log.d("TAG", "onChanged: " + "size != 0" );
                            }
                        }
                    });
                    historyRecordDetailAdapter.notifyDataSetChanged();
                }else{
                    holder.view.setVisibility(View.GONE);
                    holder.scrollView.setVisibility(View.GONE);
                }
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        TextView textViewDate, textViewTime, textViewCareHours;
        RecyclerView recyclerView;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchHistoryDetail;
        View view;
        ScrollView scrollView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDateInCellHistoryRecord);
            textViewTime = itemView.findViewById(R.id.textViewTimeInCellHistroyRecord);
            textViewCareHours = itemView.findViewById(R.id.textViewTherapyHoursInCellHistoryRecord);
            recyclerView = itemView.findViewById(R.id.RecyclerViewInCellHistoryRecord);
            switchHistoryDetail = itemView.findViewById(R.id.switchHistoryDetail);
            view = itemView.findViewById(R.id.view15);
            scrollView = itemView.findViewById(R.id.scrollView4);
        }
    }
}
