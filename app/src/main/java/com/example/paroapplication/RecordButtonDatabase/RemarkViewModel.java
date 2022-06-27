package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RemarkViewModel extends AndroidViewModel{
    private final RemarkRepository remarkRepository;

    public RemarkViewModel(@NonNull Application application) {
        super(application);
        remarkRepository = new RemarkRepository(application);
    }

    //get all
    public LiveData<List<Remark>> getAllRemarksLive() {
        return remarkRepository.getAllRemarksLive();
    }

    //insert
    public void insertRemark(Remark... remarks){
        remarkRepository.insertRemarks(remarks);
    }
    //update
    public void updateRemarks(){
        remarkRepository.updateRemarks();
    }
    //delete
    public void deleteRemarks(Remark... remarks){
        remarkRepository.deleteRemarks(remarks);
    }
    //delete all
    public void deleteAllRemarks(){
        remarkRepository.deleteAllRemarks();
    }
}
