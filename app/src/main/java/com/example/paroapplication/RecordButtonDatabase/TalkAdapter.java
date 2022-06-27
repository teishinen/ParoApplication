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


public class TalkAdapter extends ListAdapter<Talk, TalkAdapter.MyViewHolder> {
    RecordViewModel recordViewModel;
    TalkViewModel talkViewModel;

    public TalkAdapter() {
        super(new DiffUtil.ItemCallback<Talk>() {
            @Override
            public boolean areItemsTheSame(@NonNull Talk oldItem, @NonNull Talk newItem) {
                return oldItem.getTalkId() == newItem.getTalkId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Talk oldItem, @NonNull Talk newItem) {
                return oldItem.getTalk().equals(newItem.getTalk());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_record_button, parent, false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        talkViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(TalkViewModel.class);
        final MyViewHolder holder = new MyViewHolder(itemView);
        //----set button clickListener----
        holder.buttonTalk.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "LogTalkal"})
            @Override
            public void onClick(View v) {
                final Talk talk = (Talk) holder.itemView.getTag(R.id.record_talk_for_view_holder);
                String startId = RecordFragment.startId;
                long startTime = RecordFragment.startTime;
                String time = RecordFragment.time;
                String patient = RecordFragment.patient;
                String item = holder.itemView.getContext().getString(R.string.patientTalk);
                String recordButton = talk.getTalk();
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
                RecordFragment.recordItemId = 4;
                setSwitchButton(RecordFragment.recordItemId);
            }
        });
        //----delete talk button----
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Talk talkDelete = (Talk) holder.itemView.getTag(R.id.record_talk_for_view_holder);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deleteRecordButton);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        talkViewModel.deleteTalks(talkDelete);        //患者行為ボタンを削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        //----edit the talk button----
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Talk talk = (Talk) holder.itemView.getTag(R.id.record_talk_for_view_holder);
                final EditText editTextTalk;
                final Button buttonOk, buttonCancel;
                final View view = View.inflate(holder.itemView.getContext(), R.layout.dialog_edit_text01, null);
                final Dialog talkDialog = new RecordFragment.MyDialog(holder.itemView.getContext(), view, R.style.DialogTheme);
                talkDialog.setCancelable(true);
                talkDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewDialogTittle01);
                textViewTittle.setText(holder.itemView.getContext().getString(R.string.editRecord));
                editTextTalk = view.findViewById(R.id.editTextDialog);
                editTextTalk.setText(String.valueOf(talk.getTalk()));
                buttonOk = view.findViewById(R.id.buttonEditOk);
                buttonCancel = view.findViewById(R.id.buttonEditCancel);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        String patientTalk;
                        patientTalk = editTextTalk.getText().toString().trim();
                        talk.setTalk(patientTalk);
                        notifyDataSetChanged();
                        talkDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        talkDialog.dismiss();       //close this dialog
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
        final Talk talk = getItem(position);
        holder.itemView.setTag(R.id.record_talk_for_view_holder, talk);
        holder.buttonTalk.setText(talk.getTalk());
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
        public Button buttonTalk, buttonDelete, buttonEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonTalk = itemView.findViewById(R.id.buttonRecordButton);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteButton);
            buttonEdit = itemView.findViewById(R.id.buttonEditButton);
        }
    }

}
