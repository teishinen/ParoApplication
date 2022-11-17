package com.example.paroapplication.Action;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Action implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "recordId")            //give name
    private int recordId;
    @ColumnInfo(name = "actionName")            //give name
    private String actionName;
    @ColumnInfo(name = "type")            //give name
    private int type;
    @ColumnInfo(name = "patientId")            //give name
    private String patientId;

    @ColumnInfo(name = "time")            //give name
    private String time;
    @ColumnInfo(name = "startTime")            //give name
    private long startTime;
    @ColumnInfo(name = "endTime")            //give name
    private long endTime;

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public Action(int recordId, String actionName, int type, String patientId, String time, long startTime, long endTime) {
        this.recordId = recordId;
        this.actionName = actionName;
        this.type = type;
        this.patientId=patientId;
        this.time=time;
        this.startTime=startTime;
        this.endTime=endTime;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRecordId() {
        return recordId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
