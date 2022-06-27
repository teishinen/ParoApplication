package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ConditionViewModel extends AndroidViewModel{
    private final ConditionRepository conditionRepository;

    public ConditionViewModel(@NonNull Application application) {
        super(application);
        conditionRepository = new ConditionRepository(application);
    }

    //get all
    public LiveData<List<Condition>> getAllConditionsLive() {
        return conditionRepository.getAllConditionsLive();
    }

    //insert
    public void insertCondition(Condition... conditions){
        conditionRepository.insertConditions(conditions);
    }
    //update
    public void updateConditions(){
        conditionRepository.updateConditions();
    }
    //delete
    public void deleteConditions(Condition... conditions){
        conditionRepository.deleteConditions(conditions);
    }
    //delete all
    public void deleteAllConditions(){
        conditionRepository.deleteAllConditions();
    }
}
