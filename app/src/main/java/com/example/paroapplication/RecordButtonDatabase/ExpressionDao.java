package com.example.paroapplication.RecordButtonDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao        //Database access object
public interface ExpressionDao {
    @Insert
    void insertExpressions(Expression... expressions);

    @Update
    void updateExpressions(Expression... expressions);

    @Delete
    void deleteExpressions(Expression... expressions);

    @Query("DELETE FROM Expression")
    void deleteAllExpressions();

    @Query("SELECT * FROM Expression ORDER BY expressionId DESC")
    LiveData<List<Expression>> getAllExpressionsLive();

}
