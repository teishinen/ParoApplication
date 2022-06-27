package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Talk")
public class Talk {

    @PrimaryKey(autoGenerate = true)
    private int talkId;

    @ColumnInfo(name = "talk")            //give name
    private String talk;


    public Talk(String talk) {
        this.talk = talk;
    }

    public int getTalkId() {
        return talkId;
    }

    public void setTalkId(int talkId) {
        this.talkId = talkId;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }
}

