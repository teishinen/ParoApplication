package com.example.paroapplication.RecordDatabase;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.R;

import static com.example.paroapplication.Fragment.RecordFragment.*;

public class RecordAdapter extends ListAdapter<Record,RecordAdapter.MyViewHolder>{
    RecordViewModel recordViewModel;
    String note;
    public RecordAdapter(){
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
        final View itemView = layoutInflater.inflate(R.layout.cell_record,parent,false);
        recordViewModel = ViewModelProviders.of((FragmentActivity) itemView.getContext()).get(RecordViewModel.class);
        return new MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Record record = getItem(position);
        holder.itemView.setTag(R.id.record_for_view_holder01,record);
        holder.textViewRecordItem.setText(record.getItem());
        if (record.getItem().equals(holder.itemView.getContext().getString(R.string.staffIntervene))){
            holder.itemView.setBackgroundColor(Color.parseColor("#80FFF8B8"));
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#00000000"));
        }
        holder.textViewTime.setText(record.getTime());
        holder.textViewBehavior.setText(record.getNote());
        //---- set delete button ----
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Record recordToDelete = (Record) holder.itemView.getTag(R.id.record_for_view_holder01);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.deleteRecord);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Record record = (Record) holder.itemView.getTag(R.id.record_for_view_holder01);
                        if(!(String.valueOf(record.getNote()).equals(v.getContext().getString(R.string.start)))){
                            recordViewModel.deleteRecords(recordToDelete);        //Logを削除する
                        }//「開始」の㏒が削除できない
                        else{
                            TextView view = new TextView(v.getContext());
                            Toast toast = new Toast(v.getContext());
                            view.setText("            "+v.getContext().getString(R.string.warning01)+"            ");
                            view.setBackgroundResource(R.color.colorSearch);
                            view.setTextColor(v.getContext().getResources().getColor(R.color.white));
                            view.setTextSize(18);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.setView(view);
                            toast.show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                builder.show();
            }
        });
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Record record = (Record) holder.itemView.getTag(R.id.record_for_view_holder01);
                final EditText editTextRecordNote;
                final View view = View.inflate(holder.itemView.getContext(),R.layout.dialog_record, null);
                final Dialog recordDialog = new MyDialog(holder.itemView.getContext(), view, R.style.DialogTheme);
                TextView textViewTittle = view.findViewById(R.id.editRecordTittle);
                textViewTittle.setText(holder.itemView.getContext().getString(R.string.editRecord));
                editTextRecordNote = view.findViewById(R.id.editTextRecordNote);
                editTextRecordNote.setText(String.valueOf(record.getNote()));
                recordDialog.setCancelable(true);
                recordDialog.show();
                Button buttonOk,buttonCancel;
                buttonOk = view.findViewById(R.id.buttonOkEditRecord);
                buttonCancel = view.findViewById(R.id.buttonCancelEditRecord);
                buttonOk.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(View v) {
                        note = editTextRecordNote.getText().toString().trim();
                        Log.d("TAG", "note: " + note);
                        record.setNote(note);
                        recordViewModel.updateRecords(record);
                        notifyDataSetChanged();
                        recordDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager)holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordDialog.dismiss();
                        InputMethodManager imm = (InputMethodManager)holder.itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                });
            }
        });

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewRecordItem, textViewTime,textViewBehavior;
        Button buttonDelete,buttonEdit;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textViewRecordItem = itemView.findViewById(R.id.textViewRecordItem);
            textViewTime = itemView.findViewById(R.id.textViewTime01);
            textViewBehavior = itemView.findViewById(R.id.textViewLog);
            buttonDelete = itemView.findViewById(R.id.recordDeleteButton);
            buttonEdit = itemView.findViewById(R.id.recordEditButton);
        }
    }
}
