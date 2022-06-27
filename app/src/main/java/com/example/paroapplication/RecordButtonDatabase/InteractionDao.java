package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface InteractionDao {
    @Insert
    void insertInteractions(Interaction... interactions);

    @Update
    void updateInteractions(Interaction... interactions);

    @Delete
    void deleteInteractions(Interaction... interactions);

    @Query("DELETE FROM Interaction")
    void deleteAllInteractions();

    @Query("SELECT * FROM Interaction ORDER BY interactionId DESC")
    LiveData<List<Interaction>> getAllInteractionsLive();

}
