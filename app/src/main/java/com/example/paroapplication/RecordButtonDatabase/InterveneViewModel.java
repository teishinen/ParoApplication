package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InterveneViewModel extends AndroidViewModel{
    private final InterveneRepository interveneRepository;

    public InterveneViewModel(@NonNull Application application) {
        super(application);
        interveneRepository = new InterveneRepository(application);
    }

    //get all
    public LiveData<List<Intervene>> getAllIntervenesLive() {
        return interveneRepository.getAllIntervenesLive();
    }

    //insert
    public void insertIntervene(Intervene... intervenes){
        interveneRepository.insertIntervenes(intervenes);
    }
    //update
    public void updateIntervenes(){
        interveneRepository.updateIntervenes();
    }
    //delete
    public void deleteIntervenes(Intervene... intervenes){
        interveneRepository.deleteIntervenes(intervenes);
    }
    //delete all
    public void deleteAllIntervenes(){
        interveneRepository.deleteAllIntervenes();
    }
}
