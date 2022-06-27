package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface InterveneDao {
    @Insert
    void insertIntervenes(Intervene... intervenes);

    @Update
    void updateIntervenes(Intervene... intervenes);

    @Delete
    void deleteIntervenes(Intervene... intervenes);

    @Query("DELETE FROM intervene")
    void deleteAllIntervenes();

    @Query("SELECT * FROM intervene ORDER BY interveneId DESC")
    LiveData<List<Intervene>> getAllIntervenesLive();

}
