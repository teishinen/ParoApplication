package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TalkViewModel extends AndroidViewModel{
    private final TalkRepository talkRepository;

    public TalkViewModel(@NonNull Application application) {
        super(application);
        talkRepository = new TalkRepository(application);
    }

    //get all
    public LiveData<List<Talk>> getAllTalksLive() {
        return talkRepository.getAllTalksLive();
    }

    //insert
    public void insertTalk(Talk... talks){
        talkRepository.insertTalks(talks);
    }
    //update
    public void updateTalks(){
        talkRepository.updateTalks();
    }
    //delete
    public void deleteTalks(Talk... talks){
        talkRepository.deleteTalks(talks);
    }
    //delete all
    public void deleteAllTalks(){
        talkRepository.deleteAllTalks();
    }
}
