package com.example.paroapplication.StaffDatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.R;

import java.util.ArrayList;
import java.util.List;


public class StaffSelectAdapter extends ListAdapter<Staff, StaffSelectAdapter.MyViewHolder> {
    StaffViewModel staffViewModel;
    public static List<String> selectedStaffName;
    public static String[] buttonSelectedStaffName;

    public static List<String> getSelectedStaffName() {
        return selectedStaffName;
    }



    public StaffSelectAdapter() {
        super(new DiffUtil.ItemCallback<Staff>() {
            @Override
            public boolean areItemsTheSame(@NonNull Staff oldItem, @NonNull Staff newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Staff oldItem, @NonNull Staff newItem) {
                return (oldItem.getStaffId().equals(newItem.getStaffId())
                        && oldItem.getStaffName().equals(newItem.getStaffName())
                        && oldItem.getStaffAge().equals(newItem.getStaffAge())
                        && oldItem.getStaffSex().equals(newItem.getStaffSex()));
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.cell_select_staff, parent, false);
        staffViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(StaffViewModel.class);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Staff staff = getItem(position);
        holder.itemView.setTag(R.id.staff_for_view_holder02, staff);
        holder.textViewStaffID.setText(String.valueOf(staff.getStaffId()));
        holder.textViewStaffName.setText(String.valueOf(staff.getStaffName()));
        holder.textViewStaffAge.setText(String.valueOf(staff.getStaffAge()));
        holder.textViewStaffSex.setText(String.valueOf(staff.getStaffSex()));
        if (selectedStaffName == null){
            selectedStaffName = new ArrayList<>();
        }else {
            for (int i = 0; i < selectedStaffName.size(); i++ ){
                if(staff.getStaffName().equals(selectedStaffName.get(i))){
                    holder.checkBoxSelectStaff.setChecked(true);
                }
            }
        }
        //get checked staff data
        holder.checkBoxSelectStaff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedStaffName.add(holder.textViewStaffName.getText().toString());
                } else {
                    for (int i = 0; i < getSelectedStaffName().size(); i++) {
                        if (getSelectedStaffName().get(i).equals(holder.textViewStaffName.getText().toString())) {
                            selectedStaffName.remove(i);    //キャンセルした患者の名前を削除する
                            i--;
                        }
                    }
                }
                int size = selectedStaffName.size();
                buttonSelectedStaffName = selectedStaffName.toArray(new String[size]);
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxSelectStaff;
        TextView textViewStaffID, textViewStaffName, textViewStaffAge, textViewStaffSex;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxSelectStaff = itemView.findViewById(R.id.checkBoxInCellSelectStaff);
            textViewStaffID = itemView.findViewById(R.id.textViewStaffId1InCellSelectStaff);
            textViewStaffName = itemView.findViewById(R.id.textViewStaffName1InCellSelectStaff);
            textViewStaffAge = itemView.findViewById(R.id.textViewStaffAge1InCellSelectStaff);
            textViewStaffSex = itemView.findViewById(R.id.textViewStaffSex1InCellSelectStaff);
        }
    }
}
