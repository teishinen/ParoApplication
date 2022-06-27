package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Fragment.ConfirmFragment;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class HistoryRecordDetailAdapter extends ListAdapter<Record, HistoryRecordDetailAdapter.MyViewHolder> {

    RecordViewModel recordViewModel;
    PatientViewModel patientViewModel;
    LiveData<List<Patient>> filteredPatients;

    public HistoryRecordDetailAdapter() {
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
        final View itemView = layoutInflater.inflate(R.layout.cell_history_record_detail, parent, false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        patientViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(PatientViewModel.class);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "LogConditional", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder_history_detail, record);
        final String patientName = record.getPatient();
        if (patientName.length() >0){
            holder.textViewPatientName.setText(patientName);
        }else{
            holder.textViewPatientName.setText(record.getStaff());
            holder.textViewPatientName.setTextColor(R.color.orange);
        }
        filteredPatients = patientViewModel.getPatientByName(patientName);
        filteredPatients.observe((LifecycleOwner) holder.itemView.getContext(), new Observer<List<Patient>>() {
            @Override
            public void onChanged(List<Patient> patients) {
                Patient patient = patients.get(0);
                holder.textViewPatientAge.setText(patient.getPatientAge());
                holder.textViewSex.setText(patient.getPatientSex());
            }
        });

        //----set click listener--
        //クリックすると，セラピーの過程が表示される
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmFragment.startId = record.getStartId();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.confirmFragment);
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
