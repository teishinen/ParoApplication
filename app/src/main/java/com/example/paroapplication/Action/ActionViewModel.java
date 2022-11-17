package com.example.paroapplication.Action;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;

public class ActionViewModel extends AndroidViewModel{
    private final ActionRepository staffRepository;
    public ActionViewModel(@NonNull Application application) {
        super(application);
        staffRepository = new ActionRepository(application);
    }

    public LiveData<List<Action>> getActionList(String patientId) {
        return staffRepository.findRecordsByPatientName(patientId);
    }
    public LiveData<List<Action>> findRecordsByPatientRecordId(String patientId) {
        return staffRepository.findRecordsByPatientRecordId(patientId);
    }
    public LiveData<List<Action>> getAll() {
        return staffRepository.getAll();
    }



    public void insertStaff(Action... staff){
        staffRepository.insertStaffs(staff);
    }
    public void updateStaffs(Action... staff){
        staffRepository.updateStaffs(staff);
    }
    public void deleteStaffs(Action... staff){
        staffRepository.deleteStaffs(staff);
    }

}

