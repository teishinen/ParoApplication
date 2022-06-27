package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GazeViewModel extends AndroidViewModel{
    private final GazeRepository gazeRepository;

    public GazeViewModel(@NonNull Application application) {
        super(application);
        gazeRepository = new GazeRepository(application);
    }

    //get all
    public LiveData<List<Gaze>> getAllGazesLive() {
        return gazeRepository.getAllGazesLive();
    }

    //insert
    public void insertGaze(Gaze... gazes){
        gazeRepository.insertGazes(gazes);
    }
    //update
    public void updateGazes(){
        gazeRepository.updateGazes();
    }
    //delete
    public void deleteGazes(Gaze... gazes){
        gazeRepository.deleteGazes(gazes);
    }
    //delete all
    public void deleteAllGazes(){
        gazeRepository.deleteAllGazes();
    }
}
