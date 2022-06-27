package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface GazeDao {
    @Insert
    void insertGaze(Gaze... gazes);

    @Update
    void updateGaze(Gaze... gazes);

    @Delete
    void deleteGazes(Gaze... gazes);

    @Query("DELETE FROM Gaze")
    void deleteAllGazes();

    @Query("SELECT * FROM Gaze ORDER BY GazeId DESC")
    LiveData<List<Gaze>> getAllGazeLive();

}
