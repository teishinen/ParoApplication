package com.example.paroapplication.PatientDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Patient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "patient_id")            //give name
    private String patientId;
    @ColumnInfo(name = "patient_name")            //give name
    private String patientName;
    @ColumnInfo(name = "patient_age")            //give name
    private String patientAge;
    @ColumnInfo(name = "patient_sex")            //give name
    private String patientSex;

    public Patient(String patientId, String patientName, String patientAge, String patientSex) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientSex = patientSex;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }
}
