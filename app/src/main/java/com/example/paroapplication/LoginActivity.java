package com.example.paroapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private StaffViewModel staffViewModel;
    private List<Staff> staffByNameAndPsw;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (null != staffByNameAndPsw && staffByNameAndPsw.size() > 0) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("staff", staffByNameAndPsw.get(0));
                    startActivity(intent);
                }

            }
        }
    };
    private ImageView imageViewParo;
    private ImageView imageViewTittle;
    private EditText editTextName;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonSignIn;
    private Button button3;
    private Button button4;
    private Button button5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        staffViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(StaffViewModel.class);
        buttonSignIn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(editTextName.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString())) {
                Toast.makeText(LoginActivity.this, "Please fill in the registration information", Toast.LENGTH_SHORT).show();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        staffByNameAndPsw = staffViewModel.getStaffByNameAndPsw(editTextName.getText().toString(), editTextPassword.getText().toString());
                        if (staffByNameAndPsw.size() > 0) {
                            handler.sendEmptyMessage(100);
                        }
                    }
                }).start();


            }

        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView() {


        imageViewParo = (ImageView) findViewById(R.id.imageViewParo);
        imageViewTittle = (ImageView) findViewById(R.id.imageViewTittle);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
    }
}