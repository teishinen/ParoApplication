package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Gaze")
public class Gaze {

    @PrimaryKey(autoGenerate = true)
    private int GazeId;

    @ColumnInfo(name = "Gaze")            //give name
    private String Gaze;


    public Gaze(String Gaze) {
        this.Gaze = Gaze;
    }

    public int getGazeId() {
        return GazeId;
    }

    public void setGazeId(int gazeId) {
        this.GazeId = gazeId;
    }

    public String getGaze() {
        return Gaze;
    }

    public void setGaze(String gaze) {
        this.Gaze = gaze;
    }
}

