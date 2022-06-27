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


public class InteractionAdapter extends ListAdapter<Interaction, InteractionAdapter.MyViewHolder> {
    RecordViewModel recordViewModel;
    InteractionViewModel interactionViewModel;

    public InteractionAdapter() {
        super(new DiffUtil.ItemCallback<Interaction>() {
            @Override
            public boolean areItemsTheSame(@NonNull Interaction oldItem, @NonNull Interaction newItem) {
                return oldItem.getInteractionId() == newItem.getInteractionId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Interaction oldItem, @NonNull Interaction newItem) {
                return oldItem.getInteraction().equals(newItem.getInteraction());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_record_button, parent, false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        interactionViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(InteractionViewModel.class);
        final MyViewHolder holder = new MyViewHolder(itemView);
        //----set button clickListener----
        holder.buttonInteraction.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "LogInteractional"})
            @Override
            public void onClick(View v) {
                final Interaction interaction = (Interaction) holder.itemView.getTag(R.id.record_Interaction_for_view_holder);
                String startId = RecordFragment.startId;
                long startTime = RecordFragment.startTime;
                String time = RecordFragment.time;
                String patient = RecordFragment.patient;
                String item = holder.itemView.getContext().getString(R.string.interaction);
                String recordButton = interaction.getInteraction();
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
                RecordFragment.recordItemId = 5;
                setSwitchButton(RecordFragment.recordItemId);
            }
        });
        //----delete Interaction button----
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Interaction interactionToDelete = (Interaction) holder.itemView.getTag(R.id.record_Interaction_for_view_holder);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deleteRecordButton);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        interactionViewModel.deleteInteractions(interactionToDelete);        //患者行為ボタンを削除する
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        //----edit the Interaction button----
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Interaction interaction = (Interaction) holder.itemView.getTag(R.id.record_Interaction_for_view_holder);
                final EditText editTextInteraction;
                final Button buttonOk, buttonCancel;
                final View view = View.inflate(holder.itemView.getContext(), R.layout.dialog_edit_text01, null);
                final Dialog interactionDialog = new RecordFragment.MyDialog(holder.itemView.getContext(), view, R.style.DialogTheme);
                interactionDialog.setCancelable(true);
                interactionDialog.show();
                TextView textViewTittle = view.findViewById(R.id.textViewDialogTittle01);
                textViewTittle.setText(holder.itemView.getContext().getString(R.string.editRecord));
                editTextInteraction = view.findViewById(R.id.editTextDialog);
                editTextInteraction.setText(String.valueOf(interaction.getInteraction()));
                buttonOk = view.findViewById(R.id.buttonEditOk);
                buttonCancel = view.findViewById(R.id.buttonEditCancel);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        String patientInteraction;
                        patientInteraction = editTextInteraction.getText().toString().trim();
                        interaction.setInteraction(patientInteraction);
                        notifyDataSetChanged();
                        interactionDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager) holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        interactionDialog.dismiss();       //close this dialog
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
        final Interaction interaction = getItem(position);
        holder.itemView.setTag(R.id.record_Interaction_for_view_holder, interaction);
        holder.buttonInteraction.setText(interaction.getInteraction());
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
        public Button buttonInteraction, buttonDelete, buttonEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonInteraction = itemView.findViewById(R.id.buttonRecordButton);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteButton);
            buttonEdit = itemView.findViewById(R.id.buttonEditButton);
        }
    }

}
