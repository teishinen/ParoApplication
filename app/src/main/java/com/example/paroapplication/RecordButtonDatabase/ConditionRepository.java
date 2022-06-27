package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ConditionRepository {
    private final LiveData<List<Condition>> allConditionsLive;
    private final ConditionDao conditionDao;

    public ConditionRepository(Context context) {
        ConditionDatabase conditionDatabase = ConditionDatabase.getDatabase(context.getApplicationContext());
        conditionDao = conditionDatabase.getConditionDao();
        allConditionsLive = conditionDao.getAllConditionsLive();
    }

    //insert
    void insertConditions(Condition... conditions) {
        new InsertAsyncTask(conditionDao).execute(conditions);
    }
    //update
    void updateConditions(Condition... conditions) {
        new UpdateAsyncTask(conditionDao).execute(conditions);
    }
    //delete
    void deleteConditions(Condition... conditions) {
        new DeleteAsyncTask(conditionDao).execute(conditions);
    }
    //delete all
    void deleteAllConditions() {
        new DeleteAllAsyncTask(conditionDao).execute();
    }

    //get all
    public LiveData<List<Condition>> getAllConditionsLive() {
        return allConditionsLive;
    }


    static class InsertAsyncTask extends AsyncTask<Condition, Void, Void> {
        private final ConditionDao conditionDao;

        public InsertAsyncTask(ConditionDao conditionDao) {
            this.conditionDao = conditionDao;
        }

        @Override
        protected Void doInBackground(Condition... conditions) {
            conditionDao.insertConditions(conditions);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Condition, Void, Void> {
        private final ConditionDao conditionDao;

        public UpdateAsyncTask(ConditionDao conditionDao) {
            this.conditionDao = conditionDao;
        }

        @Override
        protected Void doInBackground(Condition... conditions) {
            conditionDao.updateConditions(conditions);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Condition, Void, Void> {
        private final ConditionDao conditionDao;

        public DeleteAsyncTask(ConditionDao conditionDao) {
            this.conditionDao = conditionDao;
        }

        @Override
        protected Void doInBackground(Condition... conditions) {
            conditionDao.deleteConditions(conditions);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ConditionDao conditionDao;

        public DeleteAllAsyncTask(ConditionDao conditionDao) {
            this.conditionDao = conditionDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            conditionDao.deleteAllConditions();
            return null;
        }
    }
}
