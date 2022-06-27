package com.example.paroapplication.RecordDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecordRepository {
    private final LiveData<List<Record>> allRecordsLive;
    private final RecordDao recordDao;

    public RecordRepository(Context context) {
        RecordDatabase recordDatabase = RecordDatabase.getDatabase(context.getApplicationContext());
        recordDao = recordDatabase.getRecordDao();
        allRecordsLive = recordDao.getAllRecordsLive();
    }

    void insertRecords(Record... records) {
        new InsertAsyncTask(recordDao).execute(records);
    }
    void updateRecords(Record... records) {
        new UpdateAsyncTask(recordDao).execute(records);
    }
    void deleteRecords(Record... records) { new DeleteAsyncTask(recordDao).execute(records); }
    void deleteAllRecords() {
        new DeleteAllAsyncTask(recordDao).execute();
    }
    void deleteRecordsByStartId(String startId) throws ExecutionException, InterruptedException {
        new DeleteRecordsByStartId(recordDao).execute(startId).get();
    }
    long getWeeklyCareHours(String patientName) throws ExecutionException, InterruptedException {
        return new GetWeeklyCareHours(recordDao).execute(patientName).get();
    }
    String getProblemBehaviorsByPatientName(String patientName,String item) throws ExecutionException, InterruptedException {
        return new GetProblemBehaviorsByPatientName(recordDao).execute(patientName,item).get();
    }
    String getPatientRecordTextView(String startId, String patientName, String item, String time) throws ExecutionException, InterruptedException {
        return new GetPatientRecordTextView(recordDao).execute(startId,patientName,item,time).get();
    }
    String getStaffRecordTextView(String startId, String staffName, String item, String time) throws ExecutionException, InterruptedException {
        return new GetStaffRecordTextView(recordDao).execute(startId,staffName,item,time).get();
    }
    Record findPatientRecordByCoordinate(String startId,String time, String patientName, String item ) throws ExecutionException, InterruptedException {
        return new FindPatientRecordByCoordinate(recordDao).execute(startId,time,patientName,item).get();
    }
    Record findStaffRecordByCoordinate(String startId,String time, String staffName, String item ) throws ExecutionException, InterruptedException {
        return new FindStaffRecordByCoordinate(recordDao).execute(startId,time,staffName,item).get();
    }
    String[] findHistoryRecordYear( ) throws ExecutionException, InterruptedException {
        return new FindHistoryRecordYear(recordDao).execute().get();
    }
    String[] findHistoryRecordMonth(String year) throws ExecutionException, InterruptedException {
        return new FindHistoryRecordMonth(recordDao).execute(year).get();
    }
    String[] findHistoryRecordDay(String year,String month) throws ExecutionException, InterruptedException {
        return new FindHistoryRecordDay(recordDao).execute(year,month).get();
    }
    String[] findPatientRecordByStartId(String startId) throws ExecutionException, InterruptedException {
        return new FindPatientRecordByStartId(recordDao).execute(startId).get();
    }
    String[] findStaffRecordByStartId(String startId) throws ExecutionException, InterruptedException {
        return new FindStaffRecordByStartId(recordDao).execute(startId).get();
    }

    public LiveData<List<Record>> getAllRecordsLive() {
        return allRecordsLive;
    }
    public LiveData<List<Record>> findRecordsByStartTime(long startTime){
        return recordDao.findRecordsByStartTime(startTime);
    }
    public LiveData<List<Record>> findRecordsByPatientName(String patientName){
        return recordDao.findRecordsByPatientName("%" + patientName + "%");
    }
    public LiveData<List<Record>> findRecordsByStaffName(String staffName){
        return recordDao.findRecordsByStaffName("%" + staffName + "%");
    }
    public LiveData<List<Record>> findRecordsDetailByPatient(String startId, String patientName){
        return recordDao.findRecordsDetailByPatient("%" + startId +"%","%" + patientName +"%");
    }
    public LiveData<List<Record>> findRecordsDetailByStaff(String startId, String staffName){
        return recordDao.findRecordsDetailByStaff("%" + startId +"%","%" + staffName +"%");
    }
    public LiveData<List<Record>> findRecordsByStartId(String startId){
        return recordDao.findRecordsByStartId("%" + startId +"%");
    }
    public LiveData<List<Record>> findHistoryRecord() {
        return recordDao.findHistoryRecord();
    }
    public LiveData<List<Record>> findHistoryRecordByYear(String year){
        return recordDao.findHistoryRecordByYear("%" + year +"%");
    }
    public LiveData<List<Record>> findHistoryRecordByYearAndMonth(String year,String month){
        return recordDao.findHistoryRecordByYearAndMonth("%" + year +"%","%" + month +"%");
    }
    public LiveData<List<Record>> findHistoryRecordByYearAndMonthAndDay(String year,String month,String day){
        return recordDao.findHistoryRecordByYearAndMonthAndDay("%" + year +"%","%" + month +"%","%" + day +"%");
    }
    public LiveData<List<Record>> findHistoryDetail(String startId) {
        return recordDao.findHistoryDetail(startId);
    }


    static class InsertAsyncTask extends AsyncTask<Record, Void, Void> {
        private final RecordDao recordDao;
        public InsertAsyncTask(RecordDao recordDao) {
            this.recordDao = recordDao;
        }
        @Override
        protected Void doInBackground(Record... records) {
            recordDao.insertRecords(records);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Record, Void, Void> {
        private final RecordDao recordDao;
        public UpdateAsyncTask(RecordDao recordDao) {
            this.recordDao = recordDao;
        }
        @Override
        protected Void doInBackground(Record... records) {
            recordDao.updateRecords(records);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Record, Void, Void> {
        private final RecordDao recordDao;
        public DeleteAsyncTask(RecordDao recordDao) {
            this.recordDao = recordDao;
        }
        @Override
        protected Void doInBackground(Record... records) {
            recordDao.deleteRecords(records);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final RecordDao recordDao;
        public DeleteAllAsyncTask(RecordDao recordDao) {
            this.recordDao = recordDao;
        }
        @Override
        protected Void doInBackground(Void... Voids) {
            recordDao.deleteAllRecords();
            return null;
        }
    }

    static class DeleteRecordsByStartId extends AsyncTask<String, Void, Void> {
        private final RecordDao recordDao;
        public DeleteRecordsByStartId(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected Void doInBackground(String ... strings) {
            recordDao.deleteRecordsByStartId(strings[0]);
            return null;
        }
    }

    static class GetWeeklyCareHours extends AsyncTask<String, Void, Long> {
        private final RecordDao recordDao;
        public GetWeeklyCareHours(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected Long doInBackground(String ... strings) {
            return recordDao.getWeeklyCareHours(strings[0]);
        }
    }

    static class GetProblemBehaviorsByPatientName extends AsyncTask<String, Void, String> {
        private final RecordDao recordDao;
        public GetProblemBehaviorsByPatientName(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected String doInBackground(String ... strings) {
            return recordDao.getProblemBehaviorsByPatientName(strings[0],strings[1]);
        }
    }

    static class GetPatientRecordTextView extends AsyncTask<String, Void, String> {
        private final RecordDao recordDao;
        public GetPatientRecordTextView(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected String doInBackground(String ... strings) {
            return recordDao.getPatientRecordTextView(strings[0],strings[1],strings[2],strings[3]);
        }
    }

    static class GetStaffRecordTextView extends AsyncTask<String, Void, String> {
        private final RecordDao recordDao;
        public GetStaffRecordTextView(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected String doInBackground(String ... strings) {
            return recordDao.getStaffRecordTextView(strings[0],strings[1],strings[2],strings[3]);
        }
    }

    static class FindPatientRecordByCoordinate extends AsyncTask<String, Void, Record> {
        private final RecordDao recordDao;
        public FindPatientRecordByCoordinate(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected Record doInBackground(String ... strings) {
            return recordDao.findPatientRecordByCoordinate(strings[0],strings[1],strings[2],strings[3]);
        }
    }

    static class FindStaffRecordByCoordinate extends AsyncTask<String, Void, Record> {
        private final RecordDao recordDao;
        public FindStaffRecordByCoordinate(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected Record doInBackground(String ... strings) {
            return recordDao.findStaffRecordByCoordinate(strings[0],strings[1],strings[2],strings[3]);
        }
    }

    static class FindHistoryRecordYear extends AsyncTask<Void, Void, String[]> {
        private final RecordDao recordDao;
        public FindHistoryRecordYear(RecordDao recordDao) {
            this.recordDao = recordDao;
        }


        @Override
        protected String[] doInBackground(Void... voids) {
            return recordDao.findHistoryRecordYear();
        }
    }

    static class FindHistoryRecordMonth extends AsyncTask<String, Void, String[]> {
        private final RecordDao recordDao;
        public FindHistoryRecordMonth(RecordDao recordDao) {
            this.recordDao = recordDao;
        }


        @Override
        protected String[] doInBackground(String... strings) {
            return recordDao.findHistoryRecordMonth(strings[0]);
        }
    }

    static class FindHistoryRecordDay extends AsyncTask<String, Void, String[]> {
        private final RecordDao recordDao;
        public FindHistoryRecordDay(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected String[] doInBackground(String... strings) {
            return recordDao.findHistoryRecordDay(strings[0],strings[1]);
        }
    }

    static class FindPatientRecordByStartId extends AsyncTask<String, Void, String[]> {
        private final RecordDao recordDao;
        public FindPatientRecordByStartId(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected String[] doInBackground(String... strings) {
            return recordDao.findPatientRecordByStartId(strings[0]);
        }
    }

    static class FindStaffRecordByStartId extends AsyncTask<String, Void, String[]> {
        private final RecordDao recordDao;
        public FindStaffRecordByStartId(RecordDao recordDao) {
            this.recordDao = recordDao;
        }

        @Override
        protected String[] doInBackground(String... strings) {
            return recordDao.findStaffRecordByStartId(strings[0]);
        }
    }
}
