package com.example.paroapplication.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ActionShow implements Serializable {

    private String recordId;
         //give name
    private String actionName;

    private int type;
    private int count;
    private long time;
    public ActionShow(String actionName, int type){
        this.actionName=actionName;
        this.type=type;
    }


    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getType() {
        return type;
    }

    public String getActionName() {
        return actionName;
    }
}
