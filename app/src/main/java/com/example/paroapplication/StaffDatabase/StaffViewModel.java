package com.example.paroapplication.StaffDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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


    public List<Staff> getStaffByNameAndPsw(String staffName,String psw){
        return staffRepository.getStaffByName(staffName,psw);
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

