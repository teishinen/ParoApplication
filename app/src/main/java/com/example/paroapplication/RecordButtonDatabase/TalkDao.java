package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface TalkDao {
    @Insert
    void insertTalks(Talk... talks);

    @Update
    void updateTalks(Talk... talks);

    @Delete
    void deleteTalks(Talk... talks);

    @Query("DELETE FROM Talk")
    void deleteAllTalks();

    @Query("SELECT * FROM Talk ORDER BY talkId DESC")
    LiveData<List<Talk>> getAllTalksLive();

}
