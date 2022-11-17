package com.example.paroapplication.PatientDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.paroapplication.R;
import com.example.paroapplication.bean.PatientSelect;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class PatientSelectAdapter extends RecyclerView.Adapter<PatientSelectAdapter.MyViewHolder> {


   private ArrayList<PatientSelect> mlist=new ArrayList<>();
    public interface  onSelect{
        void onSelectListener(boolean  isSelect,int pos);
    }
    private  onSelect onSelect;

    public void setOnSelect(PatientSelectAdapter.onSelect onSelect) {
        this.onSelect = onSelect;
    }

    public void setMlist(ArrayList<PatientSelect> mlist) {
        this.mlist = mlist;

       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_select_patient, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PatientSelect patientSelect = mlist.get(position);
        Patient patient=patientSelect.getPatient();
        holder.itemView.setTag(R.id.patient_for_view_holder02, patient);
        holder.textViewPatientID.setText(String.valueOf(patient.getPatientId()));
        holder.textViewPatientName.setText(String.valueOf(patient.getPatientName()));
        holder.textViewPatientAge.setText(String.valueOf(patient.getPatientAge()));
        holder.textViewPatientSex.setText(String.valueOf(patient.getPatientSex()));
         if (patientSelect.isSelect()){

             holder.checkBoxSelectPatient.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
         }else {
             holder.checkBoxSelectPatient.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);

         }
        holder.constraintLayoutCellPatient.setOnClickListener(view -> {
            if (onSelect!=null){
                   if (patientSelect.isSelect()){


                       onSelect.onSelectListener(false,position);
                   }else {
                       onSelect.onSelectListener(true,position);

                   }



            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView checkBoxSelectPatient;
        TextView textViewPatientID, textViewPatientName, textViewPatientAge, textViewPatientSex;
        View constraintLayoutCellPatient;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            constraintLayoutCellPatient = itemView.findViewById(R.id.constraintLayoutCellPatient);
            checkBoxSelectPatient = itemView.findViewById(R.id.checkBoxInCellSelectPatient);
            textViewPatientID = itemView.findViewById(R.id.textViewPatientId1InCellSelectPatient);
            textViewPatientName = itemView.findViewById(R.id.textViewPatientName1InCellSelectPatient);
            textViewPatientAge = itemView.findViewById(R.id.textViewPatientAge1InCellSelectPatient);
            textViewPatientSex = itemView.findViewById(R.id.textViewPatientSex1InCellSelectPatient);
        }
    }
}
