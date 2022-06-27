package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ExpressionRepository {
    private final LiveData<List<Expression>> allExpressionsLive;
    private final ExpressionDao expressionDao;

    public ExpressionRepository(Context context) {
        ExpressionDatabase expressionDatabase = ExpressionDatabase.getDatabase(context.getApplicationContext());
        expressionDao = expressionDatabase.getExpressionDao();
        allExpressionsLive = expressionDao.getAllExpressionsLive();
    }

    //insert
    void insertExpressions(Expression... expressions) {
        new InsertAsyncTask(expressionDao).execute(expressions);
    }
    //update
    void updateExpressions(Expression... expressions) {
        new UpdateAsyncTask(expressionDao).execute(expressions);
    }
    //delete
    void deleteExpressions(Expression... expressions) {
        new DeleteAsyncTask(expressionDao).execute(expressions);
    }
    //delete all
    void deleteAllExpressions() {
        new DeleteAllAsyncTask(expressionDao).execute();
    }

    //get all
    public LiveData<List<Expression>> getAllExpressionsLive() {
        return allExpressionsLive;
    }


    static class InsertAsyncTask extends AsyncTask<Expression, Void, Void> {
        private final ExpressionDao expressionDao;

        public InsertAsyncTask(ExpressionDao expressionDao) {
            this.expressionDao = expressionDao;
        }

        @Override
        protected Void doInBackground(Expression... expressions) {
            expressionDao.insertExpressions(expressions);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Expression, Void, Void> {
        private final ExpressionDao expressionDao;

        public UpdateAsyncTask(ExpressionDao expressionDao) {
            this.expressionDao = expressionDao;
        }

        @Override
        protected Void doInBackground(Expression... expressions) {
            expressionDao.updateExpressions(expressions);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Expression, Void, Void> {
        private final ExpressionDao expressionDao;

        public DeleteAsyncTask(ExpressionDao expressionDao) {
            this.expressionDao = expressionDao;
        }

        @Override
        protected Void doInBackground(Expression... expressions) {
            expressionDao.deleteExpressions(expressions);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ExpressionDao expressionDao;

        public DeleteAllAsyncTask(ExpressionDao expressionDao) {
            this.expressionDao = expressionDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            expressionDao.deleteAllExpressions();
            return null;
        }
    }
}
