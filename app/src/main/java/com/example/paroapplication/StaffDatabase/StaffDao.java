package com.example.paroapplication.StaffDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface StaffDao {
    @Insert
    void insertStaffs(Staff... staff);

    @Update
    void updateStaffs(Staff... staff);

    @Delete
    void deleteStaffs(Staff... staff);

    @Query("DELETE FROM Staff")
    void deleteAllStaffs();

    @Query("SELECT * FROM Staff ORDER BY id DESC")
    LiveData<List<Staff>> getAllStaffsLive();


    @Query("SELECT * FROM Staff WHERE staff_name=:staffName and staff_password=:psw ORDER BY Id DESC")
    List<Staff> getStaffByName(String staffName,String psw);


}

