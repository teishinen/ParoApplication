package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Remark")
public class Remark {

    @PrimaryKey(autoGenerate = true)
    private int remarkId;

    @ColumnInfo(name = "remark")            //give name
    private String remark;


    public Remark(String remark) {
        this.remark = remark;
    }

    public int getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(int remarkId) {
        this.remarkId = remarkId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

