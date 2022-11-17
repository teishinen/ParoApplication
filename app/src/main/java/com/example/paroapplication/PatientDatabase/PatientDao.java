package com.example.paroapplication.PatientDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao        //Database access object
public interface PatientDao {
    @Insert
    void insertPatients(Patient... patients);

    @Update
    void updatePatients(Patient... patients);

    @Delete
    void deletePatients(Patient... patients);

    @Query("DELETE FROM patient")
    void deleteAllPatients();

    @Query("SELECT * FROM patient ORDER BY id DESC")
    LiveData<List<Patient>> getAllPatientsLive();

    @Query("SELECT * FROM patient WHERE patient_id LIKE:pattern OR patient_name LIKE:pattern OR patient_age LIKE:pattern OR patient_sex LIKE:pattern ORDER BY Id DESC")
    LiveData<List<Patient>> findPatientsWithPattern(String pattern);

    @Query("SELECT * FROM patient WHERE patient_name LIKE:patientName ORDER BY Id DESC")
    LiveData<List<Patient>> getPatientByName(String patientName);

    @Query("SELECT * FROM patient WHERE patient_id LIKE:id")
    LiveData<List<Patient>> getPatientById(String id);

    @Query("SELECT * FROM patient ORDER BY id DESC ")
    List<Patient> getAllPatient();
}

