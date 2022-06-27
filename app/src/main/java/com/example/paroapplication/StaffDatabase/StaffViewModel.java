package com.example.paroapplication.StaffDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paroapplication.PatientDatabase.Patient;

import java.util.List;

public class StaffViewModel extends AndroidViewModel{
    private final StaffRepository staffRepository;
    public StaffViewModel(@NonNull Application application) {
        super(application);
        staffRepository = new StaffRepository(application);
    }

    public LiveData<List<Staff>> getAllStaffsLive() {
        return staffRepository.getAllStaffsLive();
    }
    public LiveData<List<Staff>> findStaffsWithPattern(String pattern){
        return staffRepository.findStaffsWithPattern(pattern);
    }
    public LiveData<List<Staff>> getStaffByName(String staffName){
        return staffRepository.getStaffByName(staffName);
    }
    public LiveData<List<Staff>> getStaffById(String id){
        return staffRepository.getStaffById(id);
    }

    public void insertStaff(Staff... staff){
        staffRepository.insertStaffs(staff);
    }
    public void updateStaffs(Staff... staff){
        staffRepository.updateStaffs(staff);
    }
    public void deleteStaffs(Staff... staff){
        staffRepository.deleteStaffs(staff);
    }
    public void deleteAllStaffs(){
        staffRepository.deleteAllStaffs();
    }
}

