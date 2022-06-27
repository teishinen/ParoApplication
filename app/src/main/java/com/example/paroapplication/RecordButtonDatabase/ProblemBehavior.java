package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProblemBehavior")
public class ProblemBehavior {

    @PrimaryKey(autoGenerate = true)
    private int problemBehaviorId;

    @ColumnInfo(name = "problemBehavior")            //give name
    private String problemBehavior;


    public ProblemBehavior(String problemBehavior) {
        this.problemBehavior = problemBehavior;
    }

    public int getProblemBehaviorId() {
        return problemBehaviorId;
    }

    public void setProblemBehaviorId(int problemBehaviorId) {
        this.problemBehaviorId = problemBehaviorId;
    }

    public String getProblemBehavior() {
        return problemBehavior;
    }

    public void setProblemBehavior(String problemBehavior) {
        this.problemBehavior = problemBehavior;
    }
}

