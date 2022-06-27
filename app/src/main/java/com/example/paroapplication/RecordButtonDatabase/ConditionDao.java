package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface ConditionDao {
    @Insert
    void insertConditions(Condition... conditions);

    @Update
    void updateConditions(Condition... conditions);

    @Delete
    void deleteConditions(Condition... conditions);

    @Query("DELETE FROM condition")
    void deleteAllConditions();

    @Query("SELECT * FROM condition ORDER BY conditionId DESC")
    LiveData<List<Condition>> getAllConditionsLive();

}
