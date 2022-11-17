package com.example.paroapplication.PatientDatabase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paroapplication.PatientActivity;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PatientAdapter extends ListAdapter<Patient, PatientAdapter.MyViewHolder>{
    PatientViewModel patientViewModel;
    RecordViewModel recordViewModel;
    public static String selectedPatientId,selectedPatientName,selectedPatientAge,selectedPatientSex;
    double careHours;
    private  Context context;
    public PatientAdapter(Context context,PatientViewModel patientViewModel,RecordViewModel recordViewModel){
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
      this.context=context;
      this.patientViewModel=patientViewModel;
      this.recordViewModel=recordViewModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_patient,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemView);
        //----set delete button ----
        holder.buttonDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deletePatient);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Patient patientToDelete = (Patient) holder.itemView.getTag(R.id.patient_for_view_holder01);
                        patientViewModel.deletePatients(patientToDelete);        //Patientを削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Patient patient = (Patient) holder.itemView.getTag(R.id.patient_for_view_holder01);
                Intent intent=new Intent(context, PatientActivity.class);
                intent.putExtra("patient",patient);

                context.startActivity(intent);



            }
        });
        return holder;
    }

    @SuppressLint({"SetTextI18n", "LogConditional", "ResourceAsColor", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Patient patient = getItem(position);
        holder.itemView.setTag(R.id.patient_for_view_holder01, patient);
        holder.textViewPatientID.setText(String.valueOf(patient.getPatientId()));
        String patientName = patient.getPatientName();
        holder.textViewPatientName.setText(patientName);
        holder.textViewPatientAge.setText(String.valueOf(patient.getPatientAge()));
        String patientSex = patient.getPatientSex();
        holder.textViewPatientSex.setText(patientSex);
        String man = holder.itemView.getResources().getString(R.string.male);
        String color;
        //---- set image and text color ----
        if (patientSex.equals(man)){
            holder.imageViewPatient.setImageResource(R.drawable.ic_old_man);
            color = "#4c84b7";
        }else{
            holder.imageViewPatient.setImageResource(R.drawable.ic_old_woman);
            color = "#D38AAD";
        }
        holder.textViewPatientID1.setTextColor(Color.parseColor(color));
        holder.textViewPatientAge1.setTextColor(Color.parseColor(color));
        holder.textViewPatientSex1.setTextColor(Color.parseColor(color));
        holder.textViewPatientSymptom1.setTextColor(Color.parseColor(color));
        holder.textViewPatientWeeklyCareHours1.setTextColor(Color.parseColor(color));
        //---- set calculate time & get symptom ----
        try {
            careHours = (double) recordViewModel.getWeeklyCareHours(patientName)/(1000 * 60 *60);
            Log.d("TAG", "getCareHours: success" );
        } catch (ExecutionException | InterruptedException e) {
            Log.d("TAG", "getCareHours: fail" );
            e.printStackTrace();
        }


        holder.textViewPatientWeeklyCareHours.setText( String.format("%.2f", careHours) + "h");

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        Button buttonDeleteItem;
        TextView textViewPatientID, textViewPatientName, textViewPatientAge, textViewPatientSex, textViewPatientWeeklyCareHours, textViewProblemBehaviors;
        TextView textViewPatientID1, textViewPatientAge1, textViewPatientSex1, textViewPatientWeeklyCareHours1, textViewPatientSymptom1;
        ImageView imageViewPatient;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayoutCellPatient);
            textViewPatientID = itemView.findViewById(R.id.textViewPatientId1InCellPatient);
            textViewPatientName = itemView.findViewById(R.id.textViewPatientName1InCellPatient);
            textViewPatientAge = itemView.findViewById(R.id.textViewPatientAge1InCellPatient);
            textViewPatientSex = itemView.findViewById(R.id.textViewPatientSex1InCellPatient);
            textViewPatientWeeklyCareHours = itemView.findViewById(R.id.textViewPatientWeeklyCareHours1InCellPatient);
            textViewProblemBehaviors = itemView.findViewById(R.id.textViewPatientSymptom1InCellPatient);
            textViewPatientID1 = itemView.findViewById(R.id.textViewPatientIdInCellPatient);
            textViewPatientAge1 = itemView.findViewById(R.id.textViewPatientAgeInCellPatient);
            textViewPatientSex1 = itemView.findViewById(R.id.textViewPatientSexInCellPatient);
            textViewPatientWeeklyCareHours1 = itemView.findViewById(R.id.textViewPatientWeeklyTherapyHoursInCellPatient);
            textViewPatientSymptom1 = itemView.findViewById(R.id.textViewPatientPatientSymptomInCellPatient);
            buttonDeleteItem = itemView.findViewById(R.id.buttonDeleteInCellPatient);
            imageViewPatient = itemView.findViewById(R.id.imageViewInDialogPatient);
        }
    }
}
