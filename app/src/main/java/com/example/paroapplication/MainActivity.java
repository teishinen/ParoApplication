package com.example.paroapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.paroapplication.Fragment.AccountFragment;
import com.example.paroapplication.Fragment.HomeFragment;
import com.example.paroapplication.Fragment.RegisterFragment;
import com.example.paroapplication.Fragment.SelectPatientFragment;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.StaffDatabase.Staff;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout flContent;
    private LinearLayout llBottom;
    private LinearLayout llHome;
    private LinearLayout llRegister;

    private LinearLayout llAccount;
    private LinearLayout llRecord;
    private HomeFragment homeFragment;
    private RegisterFragment registerFragment;
    private SelectPatientFragment selectPatientFragment;

    private AccountFragment accountFragment;
    private FragmentTransaction transaction;
    PatientViewModel patientViewModel;
    String recordPatientCondition, patientFacialExpression, patientLineOfSight, patientTalk, patientParoContact, patientProblemBehavior, staffRemark, staffIntervene;
    String patientName, patientId, patientAge, patientSex;
    String helperName, helperId, helperAge, helperSex;
    SharedPreferences preferences;
    RecordViewModel recordViewModel;

    Patient patient;
    Staff staff;
    private ArrayList<Patient> mlist = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (mlist.size() == 0) {
                    resetPatientData();
                }

            }
        }
    };
    private ImageView ivHome;
    private ImageView ivUser;
    private ImageView ivRecord;
    private ImageView ivPersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
        staff = (Staff) getIntent().getSerializableExtra("staff");
        recordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(RecordViewModel.class);
        patientViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(PatientViewModel.class);


        new Thread(() -> {
            mlist = (ArrayList<Patient>) patientViewModel.getAllPatient();
            if (mlist.size() == 0) {
                handler.sendEmptyMessage(100);
            }
        }).start();
        //----Data change monitoring----
        patientViewModel.getAllPatientsLive().observe(this, new Observer<List<Patient>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Patient> patients) {
                if (patients.size() == 0) {

                }
            }
        });

        onPermission();
        initData();
        initListener();
        setSelectBg(ivHome,R.drawable.ic_baseline_home_24_select);

    }

    private void setSelectBg(ImageView imageView, int selectbg) {
        ivHome.setImageResource(R.drawable.ic_baseline_home_24);
        ivUser.setImageResource(R.drawable.ic_baseline_supervised_user_circle_24_unselect);
        ivRecord.setImageResource(R.drawable.ic_baseline_drive_file_rename_outline_24);
        ivPersion.setImageResource(R.drawable.ic_baseline_person_24_unselect);
        imageView.setImageResource(selectbg);


    }

    private void initData() {
        homeFragment = new HomeFragment(staff);
        registerFragment = new RegisterFragment();
        selectPatientFragment = new SelectPatientFragment(staff);

        accountFragment = new AccountFragment(staff);
        selectFragment(1);
        homeFragment.setStartTheTherapy(new HomeFragment.StartTheTherapy() {
            @Override
            public void start() {
                llRecord.performClick();
            }
        });
    }

    private void initListener() {
        llHome.setOnClickListener(this);
        llRegister.setOnClickListener(this);
        llRecord.setOnClickListener(this);
        llAccount.setOnClickListener(this);
    }

    private void selectFragment(int i) {
        Fragment nowFragment = null;
        switch (i) {
            case 1:
                nowFragment = homeFragment;
                setSelectBg(ivHome,R.drawable.ic_baseline_home_24_select);
                break;
            case 2:
                nowFragment = registerFragment;
                setSelectBg(ivUser,R.drawable.ic_baseline_supervised_user_circle_24_select);
                break;
            case 3:
                nowFragment = selectPatientFragment;
                setSelectBg(ivRecord,R.drawable.ic_baseline_drive_file_rename_outline_24_select);
                break;
            case 4:
                setSelectBg(ivPersion,R.drawable.ic_baseline_person_24_select);
                nowFragment = accountFragment;
                break;
            case 5:

                break;
            default:
                break;
        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content, nowFragment);
        transaction.commit();
    }

    private void initView() {
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        llHome = (LinearLayout) findViewById(R.id.ll_home);
        llRegister = (LinearLayout) findViewById(R.id.ll_register);
        llAccount = (LinearLayout) findViewById(R.id.ll_account);
        llRecord = (LinearLayout) findViewById(R.id.ll_record);
        ivHome = (ImageView) findViewById(R.id.iv_home);
        ivUser = (ImageView) findViewById(R.id.iv_user);
        ivRecord = (ImageView) findViewById(R.id.iv_record);
        ivPersion = (ImageView) findViewById(R.id.iv_persion);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                selectFragment(1);
                break;
            case R.id.ll_register:
                selectFragment(2);
                break;
            case R.id.ll_record:
                selectFragment(3);
                break;

            case R.id.ll_account:
                selectFragment(4);
                break;

        }
    }


    private void resetPatientData() {
        patientViewModel.deleteAllPatients();
        patientName = getString(R.string.patient01);
        patientId = "00000001";
        patientAge = "65";
        patientSex = getString(R.string.male);
        patient = new Patient(patientId, patientName, patientAge, patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient02);
        patientId = "00000002";
        patientAge = "73";
        patientSex = getString(R.string.male);
        patient = new Patient(patientId, patientName, patientAge, patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient03);
        patientId = "00000003";
        patientAge = "80";
        patientSex = getString(R.string.male);
        patient = new Patient(patientId, patientName, patientAge, patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient04);
        patientId = "00000004";
        patientAge = "72";
        patientSex = getString(R.string.female);
        patient = new Patient(patientId, patientName, patientAge, patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient05);
        patientId = "00000005";
        patientAge = "83";
        patientSex = getString(R.string.female);
        patient = new Patient(patientId, patientName, patientAge, patientSex);
        patientViewModel.insertPatient(patient);
    }

    public void onPermission() {
// Get permission
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]
                            {
                                    Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.BLUETOOTH,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.CALL_PHONE
                            },
                    0
            );
        }
    }
}