package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InteractionViewModel extends AndroidViewModel{
    private final InteractionRepository interactionRepository;

    public InteractionViewModel(@NonNull Application application) {
        super(application);
        interactionRepository = new InteractionRepository(application);
    }

    //get all
    public LiveData<List<Interaction>> getAllInteractionsLive() {
        return interactionRepository.getAllInteractionsLive();
    }

    //insert
    public void insertInteraction(Interaction... interactions){
        interactionRepository.insertInteractions(interactions);
    }
    //update
    public void updateInteractions(){
        interactionRepository.updateInteractions();
    }
    //delete
    public void deleteInteractions(Interaction... interactions){
        interactionRepository.deleteInteractions(interactions);
    }
    //delete all
    public void deleteAllInteractions(){
        interactionRepository.deleteAllInteractions();
    }
}
