package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProblemBehaviorViewModel extends AndroidViewModel{
    private final ProblemBehaviorRepository problemBehaviorRepository;

    public ProblemBehaviorViewModel(@NonNull Application application) {
        super(application);
        problemBehaviorRepository = new ProblemBehaviorRepository(application);
    }

    //get all
    public LiveData<List<ProblemBehavior>> getAllProblemBehaviorsLive() {
        return problemBehaviorRepository.getAllProblemBehaviorsLive();
    }

    //insert
    public void insertProblemBehavior(ProblemBehavior... problemBehaviors){
        problemBehaviorRepository.insertProblemBehaviors(problemBehaviors);
    }
    //update
    public void updateProblemBehaviors(){
        problemBehaviorRepository.updateProblemBehaviors();
    }
    //delete
    public void deleteProblemBehaviors(ProblemBehavior... problemBehaviors){
        problemBehaviorRepository.deleteProblemBehaviors(problemBehaviors);
    }
    //delete all
    public void deleteAllProblemBehaviors(){
        problemBehaviorRepository.deleteAllProblemBehaviors();
    }
}
