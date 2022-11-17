package com.example.paroapplication.RecordDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class Record  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int recordId;

    @ColumnInfo(name = "start_id_record")            //give name
    private String startId;
    @ColumnInfo(name = "start_time_record")            //give name
    private long startTime;
    @ColumnInfo(name = "finish_time_record")            //give name
    private long finishTime;
    @ColumnInfo(name = "time_record")            //give name
    private String time;
    @ColumnInfo(name = "patient_record")        //give name
    private  String patient;
    @ColumnInfo(name = "staff_record")        //give name
    private  String staff;


    @ColumnInfo(name = "beforePoint")        //give name
    private  int beforePoint;
    @ColumnInfo(name = "afterPoint")        //give name
    private  int afterPoint;

    public void setAfterPoint(int afterPoint) {
        this.afterPoint = afterPoint;
    }

    public int getAfterPoint() {
        return afterPoint;
    }


    public int getBeforePoint() {
        return beforePoint;
    }

    public void setBeforePoint(int beforePoint) {
        this.beforePoint = beforePoint;
    }
    @Ignore
    public Record(int beforePoint) {
        this.beforePoint = beforePoint;
    }

    public Record(String startId, long startTime, long finishTime, String time, String patient, String staff,  int beforePoint) {
        this.startId = startId;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.time = time;
        this.patient = patient;
        this.staff = staff;

        this.beforePoint=beforePoint;


    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }




}
