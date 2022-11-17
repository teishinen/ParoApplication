package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.paroapplication.R;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RecordCalendarAdapter extends ListAdapter<Record, RecordCalendarAdapter.MyViewHolder>{

    @SuppressLint("SimpleDateFormat")  SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");
    @SuppressLint("SimpleDateFormat")  SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd");
    @SuppressLint("SimpleDateFormat")  SimpleDateFormat simpleDateFormatWeek = new SimpleDateFormat("E");
    @SuppressLint("SimpleDateFormat")  SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
    public RecordCalendarAdapter(){
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

                        );
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_calendar,parent,false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder_calendar,record);
        String month = simpleDateFormatMonth.format(record.getStartTime());
        holder.textViewMonth.setText(month);
        String day = simpleDateFormatDate.format(record.getStartTime());
        holder.textViewDay.setText(day);
        holder.textViewWeekDay.setText(simpleDateFormatWeek.format(record.getStartTime()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DataFragment.textViewTime.setText(simpleDateFormatTime.format(record.getStartTime()) + " ~ " + simpleDateFormatTime.format(record.getFinishTime()));

            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewMonth,textViewDay, textViewWeekDay;
        View viewSelect;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textViewMonth = itemView.findViewById(R.id.textViewMonthInCellCalendar);
            textViewDay = itemView.findViewById(R.id.textViewDayInCellCalendar);
            textViewWeekDay = itemView.findViewById(R.id.textViewWeekInCellCalendar);
            viewSelect = itemView.findViewById(R.id.viewSelect);
            checkBox = itemView.findViewById(R.id.checkBoxInCellCalendar);
        }
    }
}
