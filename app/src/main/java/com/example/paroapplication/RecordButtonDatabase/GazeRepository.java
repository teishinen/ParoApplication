package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GazeRepository {
    private final LiveData<List<Gaze>> allGazesLive;
    private final GazeDao gazeDao;

    public GazeRepository(Context context) {
        GazeDatabase gazeDatabase = GazeDatabase.getDatabase(context.getApplicationContext());
        gazeDao = gazeDatabase.getGazeDao();
        allGazesLive = gazeDao.getAllGazeLive();
    }

    //insert
    void insertGazes(Gaze... gazes) {
        new InsertAsyncTask(gazeDao).execute(gazes);
    }
    //update
    void updateGazes(Gaze... gazes) {
        new UpdateAsyncTask(gazeDao).execute(gazes);
    }
    //delete
    void deleteGazes(Gaze... gazes) {
        new DeleteAsyncTask(gazeDao).execute(gazes);
    }
    //delete all
    void deleteAllGazes() {
        new DeleteAllAsyncTask(gazeDao).execute();
    }

    //get all
    public LiveData<List<Gaze>> getAllGazesLive() {
        return allGazesLive;
    }


    static class InsertAsyncTask extends AsyncTask<Gaze, Void, Void> {
        private final GazeDao gazeDao;

        public InsertAsyncTask(GazeDao gazeDao) {
            this.gazeDao = gazeDao;
        }

        @Override
        protected Void doInBackground(Gaze... gazes) {
            gazeDao.insertGaze(gazes);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Gaze, Void, Void> {
        private final GazeDao gazeDao;

        public UpdateAsyncTask(GazeDao gazeDao) {
            this.gazeDao = gazeDao;
        }

        @Override
        protected Void doInBackground(Gaze... gazes) {
            gazeDao.updateGaze(gazes);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Gaze, Void, Void> {
        private final GazeDao gazeDao;

        public DeleteAsyncTask(GazeDao gazeDao) {
            this.gazeDao = gazeDao;
        }

        @Override
        protected Void doInBackground(Gaze... gazes) {
            gazeDao.deleteGazes(gazes);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final GazeDao gazeDao;

        public DeleteAllAsyncTask(GazeDao gazeDao) {
            this.gazeDao = gazeDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            gazeDao.deleteAllGazes();
            return null;
        }
    }
}
