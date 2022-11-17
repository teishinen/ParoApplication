package com.example.paroapplication.ImageDao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

public class ImageRepository {
    private final LiveData<List<ImageBean>> allStaffsLive;
    private final ImageDao staffDao;

    public ImageRepository(Context context) {
        ImageDatabase staffDatabase = ImageDatabase.getDatabase(context.getApplicationContext());
        staffDao = staffDatabase.getStaffDao();
        allStaffsLive = staffDao.getAllStaffsLive();
    }

    void insertStaffs(ImageBean... staff) {
        new InsertAsyncTask(staffDao).execute(staff);
    }
    void updateStaffs(ImageBean... staff) {
        new UpdateAsyncTask(staffDao).execute(staff);
    }
    void deleteStaffs(ImageBean... staff) { new DeleteAsyncTask(staffDao).execute(staff); }
    void deleteAllStaffs() {
        new DeleteAllAsyncTask(staffDao).execute();
    }

    public LiveData<List<ImageBean>> getAllStaffsLive() {
        return allStaffsLive;
    }

    public List<ImageBean> getStaffByName(String StaffName){
        return staffDao.getStaffByName( StaffName );
    }


    static class InsertAsyncTask extends AsyncTask<ImageBean, Void, Void> {
        private final ImageDao staffDao;

        public InsertAsyncTask(ImageDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(ImageBean... staff) {
            staffDao.insertStaffs(staff);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<ImageBean, Void, Void> {
        private final ImageDao staffDao;

        public UpdateAsyncTask(ImageDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(ImageBean... staff) {
            staffDao.updateStaffs(staff);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<ImageBean, Void, Void> {
        private final ImageDao staffDao;

        public DeleteAsyncTask(ImageDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(ImageBean... staff) {
            staffDao.deleteStaffs(staff);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ImageDao staffDao;

        public DeleteAllAsyncTask(ImageDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            staffDao.deleteAllStaffs();
            return null;
        }
    }
}
