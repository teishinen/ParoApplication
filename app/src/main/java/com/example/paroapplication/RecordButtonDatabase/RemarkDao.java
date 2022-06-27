package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface RemarkDao {
    @Insert
    void insertRemarks(Remark... remarks);

    @Update
    void updateRemarks(Remark... remarks);

    @Delete
    void deleteRemarks(Remark... remarks);

    @Query("DELETE FROM remark")
    void deleteAllRemarks();

    @Query("SELECT * FROM remark ORDER BY remarkId DESC")
    LiveData<List<Remark>> getAllRemarksLive();

}
