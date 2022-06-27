package com.example.paroapplication.RecordButtonDatabase;

import static com.example.paroapplication.Fragment.RecordFragment.setSwitchButton;

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


public class InterveneAdapter extends ListAdapter<Intervene, InterveneAdapter.MyViewHolder> {
    RecordViewModel recordViewModel;
    InterveneViewModel interveneViewModel;

    public InterveneAdapter() {
        super(new DiffUtil.ItemCallback<Intervene>() {
            @Override
            public boolean areItemsTheSame(@NonNull Intervene oldItem, @NonNull Intervene newItem) {
                return oldItem.getInterveneId() == newItem.getInterveneId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Intervene oldItem, @NonNull Intervene newItem) {
                return oldItem.getIntervene().equals(newItem.getIntervene());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_record_button, parent, false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        interveneViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(InterveneViewModel.class);
        final MyViewHolder holder = new MyViewHolder(itemView);
        //----set button clickListener----
        holder.buttonIntervene.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "LogInterveneal"})
            @Override
            public void onClick(View v) {
                final Intervene intervene = (Intervene) holder.itemView.getTag(R.id.record_intervene_for_view_holder);
                String startId = RecordFragment.startId;
                long startTime = RecordFragment.startTime;
                String time = RecordFragment.time;
                String staff = RecordFragment.staff;
                String item = holder.itemView.getContext().getString(R.string.staffIntervene);
                String recordButton = intervene.getIntervene();
                try {
                    Record record = RecordViewModel.findStaffRecordByCoordinate(startId,time,staff,item);
                    if(record!=null){
                        record.setNote(recordButton);
                        recordViewModel.updateRecords(record);
                    }else{
                        record = new Record(startId,startTime,startTime, time, "",staff,item,recordButton);
                        recordViewModel.insertRecord(record);
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                RecordFragment.recordItemId = 7;
                setSwitchButton(RecordFragment.recordItemId);
            }
        });
        //----delete intervene button----
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intervene interveneToDelete = (Intervene) holder.itemView.getTag(R.id.record_intervene_for_view_holder);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deleteRecordButton);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        interveneViewModel.deleteIntervenes(interveneToDelete);        //患者行為ボタンを削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        //----edit the intervene button----
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intervene intervene = (Intervene) holder.itemView.getTag(R.id.record_intervene_for_view_holder);
                final EditText editTextIntervene;
                final Button buttonOk, buttonCancel;
                final View view = View.inflate(holder.itemView.getContext(), R.layout.dialog_edit_text01, null);
                final Dialog interveneDialog = new RecordFragment.MyDialog(holder.itemView.getContext(), view, R.style.DialogTheme);
                interveneDialog.setCancelable(true);
                interveneDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewDialogTittle01);
                textViewTittle.setText(holder.itemView.getContext().getString(R.string.editRecord));
                editTextIntervene = view.findViewById(R.id.editTextDialog);
                editTextIntervene.setText(String.valueOf(intervene.getIntervene()));
                buttonOk = view.findViewById(R.id.buttonEditOk);
                buttonCancel = view.findViewById(R.id.buttonEditCancel);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        String patientIntervene;
                        patientIntervene = editTextIntervene.getText().toString().trim();
                        intervene.setIntervene(patientIntervene);
                        notifyDataSetChanged();
                        interveneDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        interveneDialog.dismiss();       //close this dialog
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
        final Intervene intervene = getItem(position);
        holder.itemView.setTag(R.id.record_intervene_for_view_holder, intervene);
        holder.buttonIntervene.setText(intervene.getIntervene());
        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#D4D2ED"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#EDEDF8"));
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder.getAdapterPosition() % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#D4D2ED"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#EDEDF8"));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Button buttonIntervene, buttonDelete, buttonEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonIntervene = itemView.findViewById(R.id.buttonRecordButton);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteButton);
            buttonEdit = itemView.findViewById(R.id.buttonEditButton);
        }
    }

}
