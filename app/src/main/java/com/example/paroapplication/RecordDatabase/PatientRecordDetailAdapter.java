package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDetailActivity;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class PatientRecordDetailAdapter extends ListAdapter<Record, PatientRecordDetailAdapter.MyViewHolder> {


    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
    private Patient patient;
    public PatientRecordDetailAdapter(Patient patient) {

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
        this.patient = patient;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_patient_record_detail, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "LogConditional"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder_patient_detail, record);
        holder.textViewTime.setText(record.getTime());



        //----set click listener--
        //クリックすると，セラピーの過程が表示される
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), RecordDetailActivity.class);
                intent.putExtra("record",record);
                intent.putExtra("patient",patient);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTime, textViewItem,textViewNote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewTimeInCellRecordItem);
            textViewItem = itemView.findViewById(R.id.textViewItemInCellPatientRecordDetail);
            textViewNote = itemView.findViewById(R.id.textViewNoteInCellPatientRecordDetail);
        }
    }
}
