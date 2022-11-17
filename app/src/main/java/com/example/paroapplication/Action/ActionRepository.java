package com.example.paroapplication.Action;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.paroapplication.RecordDatabase.Record;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffDao;
import com.example.paroapplication.StaffDatabase.StaffDatabase;

import java.util.List;

public class ActionRepository {

    private final ActionDao staffDao;

    public ActionRepository(Context context) {
        ActionDatabase staffDatabase = ActionDatabase.getDatabase(context.getApplicationContext());
        staffDao = staffDatabase.getStaffDao();

    }

    void insertStaffs(Action... staff) {
        new InsertAsyncTask(staffDao).execute(staff);
    }
    void updateStaffs(Action... staff) {
        new UpdateAsyncTask(staffDao).execute(staff);
    }

    void deleteStaffs(Action... staff) { new DeleteAsyncTask(staffDao).execute(staff); }


    public LiveData<List<Action>> findRecordsByPatientName(String patientName){
        return staffDao.findRecordsByPatientName("%" + patientName + "%");
    }

    public LiveData<List<Action>> findRecordsByPatientRecordId(String patientName){
        return staffDao.findRecordsByPatientRecordId("%" + patientName + "%");
    }
    public LiveData<List<Action>> getAll(){
        return staffDao.getAll();
    }
    static class InsertAsyncTask extends AsyncTask<Action, Void, Void> {
        private final ActionDao staffDao;

        public InsertAsyncTask(ActionDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Action... staff) {
            staffDao.insertStaffs(staff);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Action, Void, Void> {
        private final ActionDao staffDao;

        public UpdateAsyncTask(ActionDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Action... staff) {
            staffDao.updateStaffs(staff);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Action, Void, Void> {
        private final ActionDao staffDao;

        public DeleteAsyncTask(ActionDao staffDao) {
            this.staffDao = staffDao;
        }

        @Override
        protected Void doInBackground(Action... staff) {
            staffDao.deleteStaffs(staff);
            return null;
        }
    }


}
