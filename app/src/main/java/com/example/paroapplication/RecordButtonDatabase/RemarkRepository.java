package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RemarkRepository {
    private final LiveData<List<Remark>> allRemarksLive;
    private final RemarkDao remarkDao;

    public RemarkRepository(Context context) {
        RemarkDatabase remarkDatabase = RemarkDatabase.getDatabase(context.getApplicationContext());
        remarkDao = remarkDatabase.getRemarkDao();
        allRemarksLive = remarkDao.getAllRemarksLive();
    }

    //insert
    void insertRemarks(Remark... remarks) {
        new InsertAsyncTask(remarkDao).execute(remarks);
    }
    //update
    void updateRemarks(Remark... remarks) {
        new UpdateAsyncTask(remarkDao).execute(remarks);
    }
    //delete
    void deleteRemarks(Remark... remarks) {
        new DeleteAsyncTask(remarkDao).execute(remarks);
    }
    //delete all
    void deleteAllRemarks() {
        new DeleteAllAsyncTask(remarkDao).execute();
    }

    //get all
    public LiveData<List<Remark>> getAllRemarksLive() {
        return allRemarksLive;
    }


    static class InsertAsyncTask extends AsyncTask<Remark, Void, Void> {
        private final RemarkDao remarkDao;

        public InsertAsyncTask(RemarkDao remarkDao) {
            this.remarkDao = remarkDao;
        }

        @Override
        protected Void doInBackground(Remark... remarks) {
            remarkDao.insertRemarks(remarks);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Remark, Void, Void> {
        private final RemarkDao remarkDao;

        public UpdateAsyncTask(RemarkDao remarkDao) {
            this.remarkDao = remarkDao;
        }

        @Override
        protected Void doInBackground(Remark... remarks) {
            remarkDao.updateRemarks(remarks);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Remark, Void, Void> {
        private final RemarkDao remarkDao;

        public DeleteAsyncTask(RemarkDao remarkDao) {
            this.remarkDao = remarkDao;
        }

        @Override
        protected Void doInBackground(Remark... remarks) {
            remarkDao.deleteRemarks(remarks);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final RemarkDao remarkDao;

        public DeleteAllAsyncTask(RemarkDao remarkDao) {
            this.remarkDao = remarkDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            remarkDao.deleteAllRemarks();
            return null;
        }
    }
}
