package com.example.paroapplication.Action;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.paroapplication.ImageDao.ImageBean;
import com.example.paroapplication.RecordDatabase.Record;

import java.util.List;

@Dao        //Database access object
public interface ActionDao {
    @Insert
    void insertStaffs(Action... staff);

    @Update
    void updateStaffs(Action... staff);

    @Delete
    void deleteStaffs(Action... staff);


    @Query("SELECT * FROM Action WHERE patientId LIKE:patientName ")
    LiveData<List<Action>> findRecordsByPatientName(String patientName);

    @Query("SELECT * FROM Action WHERE recordId LIKE:recordId ")
    LiveData<List<Action>> findRecordsByPatientRecordId(String recordId);

    @Query("SELECT * FROM Action ")
    LiveData<List<Action>> getAll();
}

