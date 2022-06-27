package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InterveneRepository {
    private final LiveData<List<Intervene>> allIntervenesLive;
    private final InterveneDao interveneDao;

    public InterveneRepository(Context context) {
        InterveneDatabase interveneDatabase = InterveneDatabase.getDatabase(context.getApplicationContext());
        interveneDao = interveneDatabase.getInterveneDao();
        allIntervenesLive = interveneDao.getAllIntervenesLive();
    }

    //insert
    void insertIntervenes(Intervene... intervenes) {
        new InsertAsyncTask(interveneDao).execute(intervenes);
    }
    //update
    void updateIntervenes(Intervene... intervenes) {
        new UpdateAsyncTask(interveneDao).execute(intervenes);
    }
    //delete
    void deleteIntervenes(Intervene... intervenes) {
        new DeleteAsyncTask(interveneDao).execute(intervenes);
    }
    //delete all
    void deleteAllIntervenes() {
        new DeleteAllAsyncTask(interveneDao).execute();
    }

    //get all
    public LiveData<List<Intervene>> getAllIntervenesLive() {
        return allIntervenesLive;
    }


    static class InsertAsyncTask extends AsyncTask<Intervene, Void, Void> {
        private final InterveneDao interveneDao;

        public InsertAsyncTask(InterveneDao interveneDao) {
            this.interveneDao = interveneDao;
        }

        @Override
        protected Void doInBackground(Intervene... intervenes) {
            interveneDao.insertIntervenes(intervenes);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Intervene, Void, Void> {
        private final InterveneDao interveneDao;

        public UpdateAsyncTask(InterveneDao interveneDao) {
            this.interveneDao = interveneDao;
        }

        @Override
        protected Void doInBackground(Intervene... intervenes) {
            interveneDao.updateIntervenes(intervenes);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Intervene, Void, Void> {
        private final InterveneDao interveneDao;

        public DeleteAsyncTask(InterveneDao interveneDao) {
            this.interveneDao = interveneDao;
        }

        @Override
        protected Void doInBackground(Intervene... intervenes) {
            interveneDao.deleteIntervenes(intervenes);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final InterveneDao interveneDao;

        public DeleteAllAsyncTask(InterveneDao interveneDao) {
            this.interveneDao = interveneDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            interveneDao.deleteAllIntervenes();
            return null;
        }
    }
}
