package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TalkRepository {
    private final LiveData<List<Talk>> allTalksLive;
    private final TalkDao talkDao;

    public TalkRepository(Context context) {
        TalkDatabase talkDatabase = TalkDatabase.getDatabase(context.getApplicationContext());
        talkDao = talkDatabase.getTalkDao();
        allTalksLive = talkDao.getAllTalksLive();
    }

    //insert
    void insertTalks(Talk... talks) {
        new InsertAsyncTask(talkDao).execute(talks);
    }
    //update
    void updateTalks(Talk... talks) {
        new UpdateAsyncTask(talkDao).execute(talks);
    }
    //delete
    void deleteTalks(Talk... talks) {
        new DeleteAsyncTask(talkDao).execute(talks);
    }
    //delete all
    void deleteAllTalks() {
        new DeleteAllAsyncTask(talkDao).execute();
    }

    //get all
    public LiveData<List<Talk>> getAllTalksLive() {
        return allTalksLive;
    }


    static class InsertAsyncTask extends AsyncTask<Talk, Void, Void> {
        private final TalkDao talkDao;

        public InsertAsyncTask(TalkDao talkDao) {
            this.talkDao = talkDao;
        }

        @Override
        protected Void doInBackground(Talk... talks) {
            talkDao.insertTalks(talks);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Talk, Void, Void> {
        private final TalkDao talkDao;

        public UpdateAsyncTask(TalkDao talkDao) {
            this.talkDao = talkDao;
        }

        @Override
        protected Void doInBackground(Talk... talks) {
            talkDao.updateTalks(talks);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Talk, Void, Void> {
        private final TalkDao talkDao;

        public DeleteAsyncTask(TalkDao talkDao) {
            this.talkDao = talkDao;
        }

        @Override
        protected Void doInBackground(Talk... talks) {
            talkDao.deleteTalks(talks);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final TalkDao talkDao;

        public DeleteAllAsyncTask(TalkDao talkDao) {
            this.talkDao = talkDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            talkDao.deleteAllTalks();
            return null;
        }
    }
}
