package com.example.paroapplication.StaffDatabase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.concurrent.ExecutionException;

public class StaffAdapter extends ListAdapter<Staff, StaffAdapter.MyViewHolder>{
    StaffViewModel staffViewModel;
    RecordViewModel recordViewModel;
    public static String selectedStaffId, selectedStaffName, selectedStaffAge, selectedStaffSex;
    double careHours;
    public StaffAdapter(){
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
        final View itemView = layoutInflater.inflate(R.layout.cell_staff,parent,false);
        staffViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(StaffViewModel.class);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        final MyViewHolder holder = new MyViewHolder(itemView);
        //----set delete button ----
        holder.buttonDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deleteStaff);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Staff staffToDelete = (Staff) holder.itemView.getTag(R.id.staff_for_view_holder01);
                        staffViewModel.deleteStaffs(staffToDelete);        //Staffを削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Staff staff = (Staff) holder.itemView.getTag(R.id.staff_for_view_holder01);
                selectedStaffId = staff.getStaffId();
                selectedStaffName = staff.getStaffName();
                selectedStaffAge = staff.getStaffAge();
                selectedStaffSex = staff.getStaffSex();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_dataFragment_to_staffFragment);
            }
        });
        return holder;
    }

    @SuppressLint({"SetTextI18n", "LogConditional", "ResourceAsColor", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Staff staff = getItem(position);
        holder.itemView.setTag(R.id.staff_for_view_holder01, staff);
        holder.textViewStaffID.setText(String.valueOf(staff.getStaffId()));
        String staffName = staff.getStaffName();
        holder.textViewStaffName.setText(staffName);
        holder.textViewStaffAge.setText(String.valueOf(staff.getStaffAge()));
        String staffSex = staff.getStaffSex();
        holder.textViewStaffSex.setText(staffSex);
        String man = holder.itemView.getResources().getString(R.string.male);

        //---- set image and text color ----
        String color;
        if (staffSex.equals(man)){
            holder.imageViewStaff.setImageResource(R.drawable.ic_staff01);
            color = "#4c84b7";
        }else{
            holder.imageViewStaff.setImageResource(R.drawable.ic_staff02);
            color = "#D38AAD";
        }
        holder.textViewStaffID1.setTextColor(Color.parseColor(color));
        holder.textViewStaffAge1.setTextColor(Color.parseColor(color));
        holder.textViewStaffSex1.setTextColor(Color.parseColor(color));
        holder.textViewStaffSymptom1.setTextColor(Color.parseColor(color));
        holder.textViewStaffWeeklyCareHours1.setTextColor(Color.parseColor(color));
        //---- set calculate time & get symptom ----
        try {
            careHours = (double) recordViewModel.getWeeklyCareHours(staffName)/(1000 * 60 *60);
            Log.d("TAG", "getCareHours: success" );
        } catch (ExecutionException | InterruptedException e) {
            Log.d("TAG", "getCareHours: fail" );
            e.printStackTrace();
        }

//        try {
//            String allSymptom = recordViewModel.getProblemBehaviorsByPatientName(staffName);
//            holder.textViewStaffSymptom.setText(allSymptom);
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }

        holder.textViewStaffWeeklyCareHours.setText( String.format("%.2f", careHours) + "h");

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        Button buttonDeleteItem;
        TextView textViewStaffID, textViewStaffName, textViewStaffAge, textViewStaffSex, textViewStaffWeeklyCareHours, textViewStaffSymptom;
        TextView textViewStaffID1, textViewStaffAge1, textViewStaffSex1, textViewStaffWeeklyCareHours1, textViewStaffSymptom1;
        ImageView imageViewStaff;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayoutCellStaff);
            textViewStaffID = itemView.findViewById(R.id.textViewStaffId1InCellStaff);
            textViewStaffName = itemView.findViewById(R.id.textViewStaffName1InCellStaff);
            textViewStaffAge = itemView.findViewById(R.id.textViewStaffAge1InCellStaff);
            textViewStaffSex = itemView.findViewById(R.id.textViewStaffSex1InCellStaff);
            textViewStaffWeeklyCareHours = itemView.findViewById(R.id.textViewStaffWeeklyCareHours1InCellStaff);
            textViewStaffSymptom = itemView.findViewById(R.id.textViewStaffSymptom1InCellStaff);
            textViewStaffID1 = itemView.findViewById(R.id.textViewStaffIdInCellStaff);
            textViewStaffAge1 = itemView.findViewById(R.id.textViewStaffAgeInCellStaff);
            textViewStaffSex1 = itemView.findViewById(R.id.textViewStaffSexInCellStaff);
            textViewStaffWeeklyCareHours1 = itemView.findViewById(R.id.textViewStaffWeeklyTherapyHoursInCellStaff);
            textViewStaffSymptom1 = itemView.findViewById(R.id.textViewStaffSymptomInCellStaff);
            buttonDeleteItem = itemView.findViewById(R.id.buttonDeleteInCellStaff);
            imageViewStaff = itemView.findViewById(R.id.imageViewInCellStaff);
        }
    }
}
