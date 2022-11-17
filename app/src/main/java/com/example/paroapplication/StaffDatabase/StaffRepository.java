package com.example.paroapplication.StaffDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StaffRepository {
    private final LiveData<List<Staff>> allStaffsLive;
    private final StaffDao staffDao;

    public StaffRepository(Context context) {
        StaffDatabase staffDatabase = StaffDatabase.getDatabase(context.getApplicationContext());
        staffDao = staffDatabase.getStaffDao();
        allStaffsLive = staffDao.getAllStaffsLive();
    }

    void insertStaffs(Staff... staff) {
        new InsertAsyncTask(staffDao).execute(staff);
    }
    void updateStaffs(Staff... staff) {
        new UpdateAsyncTask(staffDao).execute(staff);
    }
    void deleteStaffs(Staff... staff) { new DeleteAsyncTask(staffDao).execute(staff); }
    void deleteAllStaffs() {
        new DeleteAllAsyncTask(staffDao).execute();
    }

    public LiveData<List<Staff>> getAllStaffsLive() {
        return allStaffsLive;
    }

    public List<Staff> getStaffByName(String StaffName,String psw){
        return staffDao.getStaffByName( StaffName,psw );
    }


    static class InsertAsyncTask extends AsyncTask<Staff, Void, Void> {
        private final StaffDao staffDao;

        public InsertAsyncTask(StaffDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Staff... staff) {
            staffDao.insertStaffs(staff);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Staff, Void, Void> {
        private final StaffDao staffDao;

        public UpdateAsyncTask(StaffDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Staff... staff) {
            staffDao.updateStaffs(staff);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Staff, Void, Void> {
        private final StaffDao staffDao;

        public DeleteAsyncTask(StaffDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Staff... staff) {
            staffDao.deleteStaffs(staff);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final StaffDao staffDao;

        public DeleteAllAsyncTask(StaffDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            staffDao.deleteAllStaffs();
            return null;
        }
    }
}
