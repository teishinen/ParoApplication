package com.example.paroapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.paroapplication.RecordButtonDatabase.Expression;
import com.example.paroapplication.RecordButtonDatabase.Gaze;
import com.example.paroapplication.RecordButtonDatabase.Interaction;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffViewModel;
import com.example.paroapplication.PatientDatabase.Patient;
import com.example.paroapplication.PatientDatabase.PatientViewModel;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordButtonDatabase.Condition;
import com.example.paroapplication.RecordButtonDatabase.ConditionViewModel;
import com.example.paroapplication.RecordButtonDatabase.ExpressionViewModel;
import com.example.paroapplication.RecordButtonDatabase.Intervene;
import com.example.paroapplication.RecordButtonDatabase.InterveneViewModel;
import com.example.paroapplication.RecordButtonDatabase.GazeViewModel;
import com.example.paroapplication.RecordButtonDatabase.InteractionViewModel;
import com.example.paroapplication.RecordButtonDatabase.ProblemBehavior;
import com.example.paroapplication.RecordButtonDatabase.ProblemBehaviorViewModel;
import com.example.paroapplication.RecordButtonDatabase.Remark;
import com.example.paroapplication.RecordButtonDatabase.RemarkViewModel;
import com.example.paroapplication.RecordButtonDatabase.Talk;
import com.example.paroapplication.RecordButtonDatabase.TalkViewModel;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    PatientViewModel patientViewModel;
    String recordPatientCondition,patientFacialExpression,patientLineOfSight, patientTalk,patientParoContact,patientProblemBehavior,staffRemark,staffIntervene;
    String patientName,patientId,patientAge,patientSex;
    String helperName,helperId,helperAge,helperSex;
    SharedPreferences preferences;
    RecordViewModel recordViewModel;
    ConditionViewModel conditionViewModel;
    ExpressionViewModel expressionViewModel;
    GazeViewModel gazeViewModel;
    TalkViewModel talkViewModel;
    InteractionViewModel interactionViewModel;
    ProblemBehaviorViewModel problemBehaviorViewModel;
    RemarkViewModel remarkViewModel;
    InterveneViewModel interveneViewModel;
    Patient patient;
    Staff staff;
    StaffViewModel staffViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(RecordViewModel.class);
        conditionViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(ConditionViewModel.class);
        expressionViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(ExpressionViewModel.class);
        gazeViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(GazeViewModel.class);
        talkViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(TalkViewModel.class);
        interactionViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(InteractionViewModel.class);
        problemBehaviorViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(ProblemBehaviorViewModel.class);
        remarkViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(RemarkViewModel.class);
        interveneViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(InterveneViewModel.class);
        patientViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(PatientViewModel.class);
        staffViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(StaffViewModel.class);
        // TODO: Use the ViewModel

        //Initializing
        preferences = getSharedPreferences("count", MODE_PRIVATE);
        int count = preferences.getInt("count", 0);
        if (count == 0) {
            resetRecordData();
            resetButtonData();
            resetPatientData();
            resetHelperData();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("count", ++count);
        editor.apply();

        // bottom menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        //getPermission
        onPermission();

        //Action Bar
        NavController controller = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,controller);
        Objects.requireNonNull(getSupportActionBar()).hide();
        // delete shadow
        if(Build.VERSION.SDK_INT>=21){
            Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        //back button
        NavController controller = Navigation.findNavController(this,R.id.fragment);
        return controller.navigateUp();
        //return super.onSupportNavigateUp();
    }

    //reset record data
    void resetRecordData(){
        recordViewModel.deleteAllRecords();
    }

    //reset button data
    void resetButtonData() {
        conditionViewModel.deleteAllConditions();
        expressionViewModel.deleteAllExpressions();
        gazeViewModel.deleteAllGazes();
        talkViewModel.deleteAllTalks();
        interactionViewModel.deleteAllInteractions();
        problemBehaviorViewModel.deleteAllProblemBehaviors();
        remarkViewModel.deleteAllRemarks();
        interveneViewModel.deleteAllIntervenes();
        //----set default condition----
        recordPatientCondition = getString(R.string.condition04);
        Condition condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        recordPatientCondition = getString(R.string.condition03);
        condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        recordPatientCondition = getString(R.string.condition02);
        condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        recordPatientCondition = getString(R.string.condition01);
        condition = new Condition(recordPatientCondition);
        conditionViewModel.insertCondition(condition);
        //----set default facialExpression----
        patientFacialExpression = getString(R.string.expression01);
        Expression expression = new Expression(patientFacialExpression);
        expressionViewModel.insertExpression(expression);
        patientFacialExpression = getString(R.string.facialExpression02);
        expression = new Expression(patientFacialExpression);
        expressionViewModel.insertExpression(expression);
        patientFacialExpression = getString(R.string.facialExpression03);
        expression = new Expression(patientFacialExpression);
        expressionViewModel.insertExpression(expression);
        patientFacialExpression = getString(R.string.facialExpression04);
        expression = new Expression(patientFacialExpression);
        expressionViewModel.insertExpression(expression);
        patientFacialExpression = getString(R.string.facialExpression05);
        expression = new Expression(patientFacialExpression);
        expressionViewModel.insertExpression(expression);
        //----set default lineOfSight----
        patientLineOfSight = getString(R.string.talkTo01);
        Gaze gaze = new Gaze(patientLineOfSight);
        gazeViewModel.insertGaze(gaze);
        patientLineOfSight = getString(R.string.talkTo02);
        gaze = new Gaze(patientLineOfSight);
        gazeViewModel.insertGaze(gaze);
        patientLineOfSight = getString(R.string.talkTo03);
        gaze = new Gaze(patientLineOfSight);
        gazeViewModel.insertGaze(gaze);
        patientLineOfSight = getString(R.string.lineOfSight01);
        gaze = new Gaze(patientLineOfSight);
        gazeViewModel.insertGaze(gaze);
        patientLineOfSight = getString(R.string.lineOfSight02);
        gaze = new Gaze(patientLineOfSight);
        gazeViewModel.insertGaze(gaze);
        //----set default talkTo----
        patientTalk = getString(R.string.talkTo01);
        Talk talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        patientTalk = getString(R.string.talkTo02);
        talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        patientTalk = getString(R.string.talkTo03);
        talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        patientTalk = getString(R.string.talkTo04);
        talk = new Talk(patientTalk);
        talkViewModel.insertTalk(talk);
        //----set default paroContact----
        patientParoContact = getString(R.string.interaction01);
        Interaction interaction = new Interaction(patientParoContact);
        interactionViewModel.insertInteraction(interaction);
        patientParoContact = getString(R.string.paroContact02);
        interaction = new Interaction(patientParoContact);
        interactionViewModel.insertInteraction(interaction);
        patientParoContact = getString(R.string.paroContact03);
        interaction = new Interaction(patientParoContact);
        interactionViewModel.insertInteraction(interaction);
        patientParoContact = getString(R.string.paroContact04);
        interaction = new Interaction(patientParoContact);
        interactionViewModel.insertInteraction(interaction);
        //----set default problemBehavior----
        patientProblemBehavior = getString(R.string.problemBehavior01);
        ProblemBehavior problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior02);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior03);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior04);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        patientProblemBehavior = getString(R.string.problemBehavior05);
        problemBehavior = new ProblemBehavior(patientProblemBehavior);
        problemBehaviorViewModel.insertProblemBehavior(problemBehavior);
        //----set default remark----
        staffRemark = getString(R.string.remark01);
        Remark remark = new Remark(staffRemark);
        remarkViewModel.insertRemark(remark);
        staffRemark = getString(R.string.remark02);
        remark = new Remark(staffRemark);
        remarkViewModel.insertRemark(remark);
        //----set default intervene----
        staffIntervene = getString(R.string.intervene04);
        Intervene intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);
        staffIntervene = getString(R.string.intervene03);
        intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);
        staffIntervene = getString(R.string.intervene02);
        intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);
        staffIntervene = getString(R.string.intervene01);
        intervene = new Intervene(staffIntervene);
        interveneViewModel.insertIntervene(intervene);

    }

    private void resetHelperData(){
        staffViewModel.deleteAllStaffs();
        helperName = "介護者" + "01";
        helperId = "00000001";
        helperAge = "25";
        helperSex = getString(R.string.male);;
        staff = new Staff(helperId,helperName,helperAge,helperSex);
        staffViewModel.insertStaff(staff);
        helperName = "介護者" + "02";
        helperId = "00000002";
        helperAge = "22";
        helperSex = getString(R.string.female);;
        staff = new Staff(helperId,helperName,helperAge,helperSex);
        staffViewModel.insertStaff(staff);
    }

    private void resetPatientData() {
        patientViewModel.deleteAllPatients();

        patientName = getString(R.string.patient01);
        patientId = "00000001";
        patientAge = "65";
        patientSex = getString(R.string.male);
        patient = new Patient(patientId,patientName,patientAge,patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient02);
        patientId = "00000002";
        patientAge = "73";
        patientSex = getString(R.string.male);
        patient = new Patient(patientId,patientName,patientAge,patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient03);
        patientId = "00000003";
        patientAge = "80";
        patientSex = getString(R.string.male);
        patient = new Patient(patientId,patientName,patientAge,patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient04);
        patientId = "00000004";
        patientAge = "72";
        patientSex = getString(R.string.female);
        patient = new Patient(patientId,patientName,patientAge,patientSex);
        patientViewModel.insertPatient(patient);
        patientName = getString(R.string.patient05);
        patientId = "00000005";
        patientAge = "83";
        patientSex = getString(R.string.female);
        patient = new Patient(patientId,patientName,patientAge,patientSex);
        patientViewModel.insertPatient(patient);
    }

    public void onPermission()
    {
// Get permission
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) !=
                PackageManager.PERMISSION_GRANTED)
        {
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
