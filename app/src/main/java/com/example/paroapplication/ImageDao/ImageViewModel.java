package com.example.paroapplication.ImageDao;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffRepository;

import java.util.List;

public class ImageViewModel extends AndroidViewModel{
    private final ImageRepository staffRepository;
    public ImageViewModel(@NonNull Application application) {
        super(application);
        staffRepository = new ImageRepository(application);
    }

    public LiveData<List<ImageBean>> getAllStaffsLive() {
        return staffRepository.getAllStaffsLive();
    }


    public List<ImageBean> getStaffByNameAndPsw(String staffName){
        return staffRepository.getStaffByName(staffName);
    }


    public void insertStaff(ImageBean... staff){
        staffRepository.insertStaffs(staff);
    }
    public void updateStaffs(ImageBean... staff){
        staffRepository.updateStaffs(staff);
    }
    public void deleteStaffs(ImageBean... staff){
        staffRepository.deleteStaffs(staff);
    }
    public void deleteAllStaffs(){
        staffRepository.deleteAllStaffs();
    }
}

