package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Interaction")
public class Interaction {

    @PrimaryKey(autoGenerate = true)
    private int interactionId;

    @ColumnInfo(name = "Interaction")            //give name
    private String interaction;


    public Interaction(String interaction) {
        this.interaction = interaction;
    }

    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(int interactionId) {
        this.interactionId = interactionId;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }
}

