package com.example.paroapplication.RecordButtonDatabase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Fragment.RecordFragment;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.RecordDatabase.RecordViewModel;

import java.util.concurrent.ExecutionException;


public class RemarkAdapter extends ListAdapter<Remark, RemarkAdapter.MyViewHolder> {
    RecordViewModel recordViewModel;
    RemarkViewModel remarkViewModel;

    public RemarkAdapter() {
        super(new DiffUtil.ItemCallback<Remark>() {
            @Override
            public boolean areItemsTheSame(@NonNull Remark oldItem, @NonNull Remark newItem) {
                return oldItem.getRemarkId() == newItem.getRemarkId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Remark oldItem, @NonNull Remark newItem) {
                return oldItem.getRemark().equals(newItem.getRemark());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_record_button, parent, false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        remarkViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RemarkViewModel.class);
        final MyViewHolder holder = new MyViewHolder(itemView);
        //----set button clickListener----
        holder.buttonRemark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "LogRemarkal"})
            @Override
            public void onClick(View v) {
                final Remark remark = (Remark) holder.itemView.getTag(R.id.record_remark_for_view_holder);
                String startId = RecordFragment.startId;
                long startTime = RecordFragment.startTime;
                String time = RecordFragment.time;
                String patient = RecordFragment.patient;
                String item = holder.itemView.getContext().getString(R.string.remark);
                String recordButton = remark.getRemark();
                try {
                    Record record = RecordViewModel.findPatientRecordByCoordinate(startId,time,patient,item);
                    if(record!=null){
                        record.setNote(recordButton);
                        recordViewModel.updateRecords(record);
                    }else{
                        record = new Record(startId,startTime,startTime, time, patient,"",item,recordButton);
                        recordViewModel.insertRecord(record);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //----delete remark button----
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Remark remarkToDelete = (Remark) holder.itemView.getTag(R.id.record_remark_for_view_holder);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deleteRecordButton);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remarkViewModel.deleteRemarks(remarkToDelete);        //患者行為ボタンを削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        //----edit the remark button----
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Remark remark = (Remark) holder.itemView.getTag(R.id.record_remark_for_view_holder);
                final EditText editTextRemark;
                final Button buttonOk, buttonCancel;
                final View view = View.inflate(holder.itemView.getContext(), R.layout.dialog_edit_text01, null);
                final Dialog remarkDialog = new RecordFragment.MyDialog(holder.itemView.getContext(), view, R.style.DialogTheme);
                remarkDialog.setCancelable(true);
                remarkDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewDialogTittle01);
                textViewTittle.setText(holder.itemView.getContext().getString(R.string.editRecord));
                editTextRemark = view.findViewById(R.id.editTextDialog);
                editTextRemark.setText(String.valueOf(remark.getRemark()));
                buttonOk = view.findViewById(R.id.buttonEditOk);
                buttonCancel = view.findViewById(R.id.buttonEditCancel);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String patientRemark;
                        patientRemark = editTextRemark.getText().toString().trim();
                        remark.setRemark(patientRemark);
                        notifyDataSetChanged();
                        remarkDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remarkDialog.dismiss();       //close this dialog
                        InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);      //close the keyboard
                    }
                });
            }
        });
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Remark remark = getItem(position);
        holder.itemView.setTag(R.id.record_remark_for_view_holder, remark);
        holder.buttonRemark.setText(remark.getRemark());
        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#D2E0ED"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#EDF2F8"));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Button buttonRemark, buttonDelete, buttonEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonRemark = itemView.findViewById(R.id.buttonRecordButton);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteButton);
            buttonEdit = itemView.findViewById(R.id.buttonEditButton);
        }
    }

}
