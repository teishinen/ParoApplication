package com.example.paroapplication.StaffDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.paroapplication.StaffDatabase.Staff;

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

    @Query("SELECT * FROM Staff WHERE staff_Id LIKE:pattern OR staff_name LIKE:pattern OR staff_age LIKE:pattern OR staff_sex LIKE:pattern ORDER BY Id DESC")
    LiveData<List<Staff>> findStaffsWithPattern(String pattern);

    @Query("SELECT * FROM Staff WHERE staff_name LIKE:staffName ORDER BY Id DESC")
    LiveData<List<Staff>> getStaffByName(String staffName);

    @Query("SELECT * FROM staff WHERE staff_id LIKE:id")
    LiveData<List<Staff>> getStaffById(String id);
}

