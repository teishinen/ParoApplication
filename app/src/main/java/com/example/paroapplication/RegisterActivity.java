package com.example.paroapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffViewModel;

public class RegisterActivity extends AppCompatActivity {


    private TextView textView7;
    private ImageView imageView;
    private EditText editTextTextPersonName;
    private EditText editTextTextPassword;
    private EditText editTextTextAge;
    private Button button15;
    private RadioGroup rgSex;
    private String sex;
    private StaffViewModel staffViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sex=getResources().getString(R.string.female);
        staffViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(StaffViewModel.class);
        initView();
        initListener();
    }

    private void initListener() {

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextTextPersonName.getText().toString()) || TextUtils.isEmpty(editTextTextAge.getText().toString()) || TextUtils.isEmpty(editTextTextPassword.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Please complete your personal information", Toast.LENGTH_SHORT).show();
                } else {
                    staffViewModel.insertStaff(new Staff(editTextTextPersonName.getText().toString(),editTextTextPassword.getText().toString(),editTextTextAge.getText().toString(),sex));
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();



                }
            }
        });
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rb_female){
                    sex=getResources().getString(R.string.female);
                }
                if (i==R.id.rb_male){
                    sex=getResources().getString(R.string.male);
                }
            }
        });
    }

    private void initView() {
        textView7 = (TextView) findViewById(R.id.textView7);
        imageView = (ImageView) findViewById(R.id.imageView);
        editTextTextPersonName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        editTextTextAge = (EditText) findViewById(R.id.editTextTextAge);
        button15 = (Button) findViewById(R.id.button15);
        rgSex = (RadioGroup) findViewById(R.id.rg_sex);
    }
}