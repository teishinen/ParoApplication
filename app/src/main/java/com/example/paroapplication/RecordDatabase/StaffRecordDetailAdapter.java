package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.R;

import java.text.SimpleDateFormat;


public class StaffRecordDetailAdapter extends ListAdapter<Record, StaffRecordDetailAdapter.MyViewHolder> {

    RecordViewModel recordViewModel;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
    public StaffRecordDetailAdapter() {
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
        final View itemView = layoutInflater.inflate(R.layout.cell_staff_record_detail, parent, false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "LogConditional"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder_staff_detail, record);
        holder.textViewTime.setText(record.getTime());
        holder.textViewItem.setText(record.getItem());
        holder.textViewNote.setText(record.getNote());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTime, textViewItem,textViewNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewTimeInCellStaffRecordDetail);
            textViewItem = itemView.findViewById(R.id.textViewItemInCellStaffRecordDetail);
            textViewNote = itemView.findViewById(R.id.textViewNoteInCellStaffRecordDetail);
        }
    }
}
