package com.example.paroapplication.RecordButtonDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpressionViewModel extends AndroidViewModel{
    private final ExpressionRepository expressionRepository;

    public ExpressionViewModel(@NonNull Application application) {
        super(application);
        expressionRepository = new ExpressionRepository(application);
    }

    //get all
    public LiveData<List<Expression>> getAllExpressionsLive() {
        return expressionRepository.getAllExpressionsLive();
    }

    //insert
    public void insertExpression(Expression... expressions){
        expressionRepository.insertExpressions(expressions);
    }
    //update
    public void updateExpressions(){
        expressionRepository.updateExpressions();
    }
    //delete
    public void deleteExpressions(Expression... expressions){
        expressionRepository.deleteExpressions(expressions);
    }
    //delete all
    public void deleteAllExpressions(){
        expressionRepository.deleteAllExpressions();
    }
}
