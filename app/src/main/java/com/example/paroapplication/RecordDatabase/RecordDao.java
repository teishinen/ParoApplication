package com.example.paroapplication.RecordDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao        //Database access object
public interface RecordDao {
    @Insert
    void insertRecords(Record... records);//...複数のrecord

    @Update
    void updateRecords(Record... records);

    @Delete
    void deleteRecords(Record... records);

    @Query("DELETE FROM record")
    void deleteAllRecords();

    @Query("DELETE FROM record WHERE start_id_record LIKE:startId")
    void deleteRecordsByStartId(String startId);

    @Query("SELECT * FROM record ORDER BY recordId DESC")
    LiveData<List<Record>> getAllRecordsLive();

    @Query("SELECT * FROM record WHERE start_time_record LIKE:startTime ORDER BY recordId DESC")
    LiveData<List<Record>> findRecordsByStartTime(long startTime);

    @Query("SELECT * FROM record WHERE patient_record LIKE:patientName GROUP BY start_time_record")
    LiveData<List<Record>> findRecordsByPatientName(String patientName);

    @Query("SELECT * FROM record WHERE patient_record LIKE:patientName and time_record =:time  GROUP BY start_time_record")
    LiveData<List<Record>> findRecordsByPatientNameAndTime(String patientName,String time);

    @Query("SELECT * FROM record WHERE staff_record LIKE:staffName GROUP BY start_time_record")
    LiveData<List<Record>> findRecordsByStaffName(String staffName);

    @Query("SELECT * FROM record WHERE start_id_record LIKE:startId and patient_record LIKE:patientName ORDER BY recordId DESC")
    LiveData<List<Record>> findRecordsDetailByPatient(String startId, String patientName);

    @Query("SELECT * FROM record WHERE start_id_record LIKE:startId and staff_record LIKE:staffName ORDER BY recordId DESC")
    LiveData<List<Record>> findRecordsDetailByStaff(String startId, String staffName);

    @Query("SELECT * FROM record WHERE start_id_record LIKE:startId ORDER BY recordId DESC")
    LiveData<List<Record>> findRecordsByStartId(String startId);

    @Query("SELECT * FROM record GROUP BY start_time_record")
    LiveData<List<Record>> findHistoryRecord();

    @Query("SELECT * FROM record where time_record=:time GROUP BY start_time_record")
    LiveData<List<Record>> findHistoryRecordBytime(String time);

    @Query("SELECT * FROM record WHERE strftime('%Y',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:year GROUP BY start_id_record")
    LiveData<List<Record>> findHistoryRecordByYear(String year);

    @Query("SELECT * FROM record WHERE strftime('%Y',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:year AND strftime('%m',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:month GROUP BY start_id_record")
    LiveData<List<Record>> findHistoryRecordByYearAndMonth(String year,String month);

    @Query("SELECT * FROM record WHERE strftime('%Y',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:year AND strftime('%m',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:month AND strftime('%d',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:day GROUP BY start_id_record")
    LiveData<List<Record>> findHistoryRecordByYearAndMonthAndDay(String year,String month,String day);

    @Query("SELECT DISTINCT strftime('%Y',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) FROM record ORDER BY start_time_record DESC")
    String[] findHistoryRecordYear();

    @Query("SELECT DISTINCT strftime('%m',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) FROM record WHERE strftime('%Y',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:year ORDER BY start_time_record DESC")
    String[] findHistoryRecordMonth(String year);

    @Query("SELECT DISTINCT strftime('%d',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) FROM record WHERE strftime('%Y',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:year AND strftime('%m',datetime(start_time_record / 1000, 'unixepoch', 'localtime')) LIKE:month ORDER BY start_time_record DESC")
    String[] findHistoryRecordDay(String year,String month);

    @Query("SELECT patient_record FROM record WHERE patient_record != '' AND start_id_record LIKE:startId  GROUP BY patient_record")
    String[] findPatientRecordByStartId(String startId);

    @Query("SELECT staff_record FROM record WHERE staff_record != '' AND start_id_record LIKE:startId GROUP BY staff_record ")
    String[] findStaffRecordByStartId(String startId);

    @Query("SELECT * FROM record WHERE start_id_record LIKE:startId ")
    LiveData<List<Record>> findHistoryDetail(String startId);

    @Query("SELECT SUM(DISTINCT finish_time_record- start_time_record) FROM record WHERE patient_record LIKE:patientName AND start_time_record >=(strftime('%S','now','localtime') - (7*24*60*60*1000))")
    long getWeeklyCareHours(String patientName);






}
