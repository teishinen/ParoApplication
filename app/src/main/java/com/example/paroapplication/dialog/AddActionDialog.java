package com.example.paroapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.paroapplication.R;

public class AddActionDialog extends Dialog {
    private EditText etAction;
    private Button btnCancel;
    private Button btAdd;
    private Context context;

    public AddActionDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        this.context=context;
    }
    private  AddAction action;

    public void setAction(AddAction action) {
        this.action = action;
    }

    public  interface   AddAction{
        void  addAction(String actionName);
     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (WindowManager.LayoutParams.MATCH_PARENT*0.8);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(R.layout.add_action_dialog);
        initView();
        btAdd.setOnClickListener(view -> {
            if (null!=action){
                if (!TextUtils.isEmpty(etAction.getText().toString())){
                    action.addAction(etAction.getText().toString());

                }else {
                    Toast.makeText(context,"Please enter the action name",Toast.LENGTH_SHORT).show();
                }
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    private void initView() {
        etAction = (EditText) findViewById(R.id.et_action);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btAdd = (Button) findViewById(R.id.bt_add);
    }
}
