package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Condition")
public class Condition {

    @PrimaryKey(autoGenerate = true)
    private int conditionId;

    @ColumnInfo(name = "condition")            //give name
    private String condition;


    public Condition(String condition) {
        this.condition = condition;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

