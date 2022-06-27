package com.example.paroapplication.RecordButtonDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Expression")
public class Expression {

    @PrimaryKey(autoGenerate = true)
    private int expressionId;

    @ColumnInfo(name = "expression")            //give name
    private String expression;


    public Expression(String expression) {
        this.expression = expression;
    }

    public int getExpressionId() {
        return expressionId;
    }

    public void setExpressionId(int expressionId) {
        this.expressionId = expressionId;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

