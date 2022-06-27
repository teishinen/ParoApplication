package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface ProblemBehaviorDao {
    @Insert
    void insertProblemBehaviors(ProblemBehavior... problemBehaviors);

    @Update
    void updateProblemBehaviors(ProblemBehavior... problemBehaviors);

    @Delete
    void deleteProblemBehaviors(ProblemBehavior... problemBehaviors);

    @Query("DELETE FROM problemBehavior")
    void deleteAllProblemBehaviors();

    @Query("SELECT * FROM problemBehavior ORDER BY problemBehaviorId DESC")
    LiveData<List<ProblemBehavior>> getAllProblemBehaviorsLive();

}
