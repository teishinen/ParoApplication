package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Intervene")
public class Intervene {

    @PrimaryKey(autoGenerate = true)
    private int interveneId;

    @ColumnInfo(name = "intervene")            //give name
    private String intervene;


    public Intervene(String intervene) {
        this.intervene = intervene;
    }

    public int getInterveneId() {
        return interveneId;
    }

    public void setInterveneId(int interveneId) {
        this.interveneId = interveneId;
    }

    public String getIntervene() {
        return intervene;
    }

    public void setIntervene(String intervene) {
        this.intervene = intervene;
    }
}

