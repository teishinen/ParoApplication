package com.example.paroapplication.ImageDao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface ImageDao {
    @Insert
    void insertStaffs(ImageBean... staff);

    @Update
    void updateStaffs(ImageBean... staff);

    @Delete
    void deleteStaffs(ImageBean... staff);

    @Query("DELETE FROM ImageBean")
    void deleteAllStaffs();

    @Query("SELECT * FROM ImageBean ORDER BY id DESC")
    LiveData<List<ImageBean>> getAllStaffsLive();


    @Query("SELECT * FROM ImageBean WHERE staff_name=:staffName  ORDER BY Id DESC")
    List<ImageBean> getStaffByName(String staffName);


}

