package com.example.paroapplication.PatientDatabase;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PatientRepository {
    private final LiveData<List<Patient>> allPatientsLive;
    private final PatientDao patientDao;

    public PatientRepository(Context context) {
        PatientDatabase patientDatabase = PatientDatabase.getDatabase(context.getApplicationContext());
        patientDao = patientDatabase.getPatientDao();
        allPatientsLive = patientDao.getAllPatientsLive();
    }

    void insertPatients(Patient... patients) {
        new InsertAsyncTask(patientDao).execute(patients);
    }
    void updatePatients(Patient... patients) {
        new UpdateAsyncTask(patientDao).execute(patients);
    }
    void deletePatients(Patient... patients) { new DeleteAsyncTask(patientDao).execute(patients); }
    void deleteAllPatients() {
        new DeleteAllAsyncTask(patientDao).execute();
    }

    public LiveData<List<Patient>> getAllPatientsLive() {
        return allPatientsLive;
    }
    public LiveData<List<Patient>> findPatientsWithPattern(String pattern){
        return patientDao.findPatientsWithPattern("%" + pattern + "%");
    }
    public LiveData<List<Patient>> getPatientByName(String patientName){
        return patientDao.getPatientByName("%" + patientName + "%");
    }
    public LiveData<List<Patient>> getPatientById(String id){
        return patientDao.getPatientById("%" + id + "%");
    }

    public List<Patient> getAllPatient(){
        return patientDao.getAllPatient();
    }

    static class InsertAsyncTask extends AsyncTask<Patient, Void, Void> {
        private final PatientDao patientDao;

        public InsertAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.insertPatients(patients);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Patient, Void, Void> {
        private final PatientDao patientDao;

        public UpdateAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.updatePatients(patients);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Patient, Void, Void> {
        private final PatientDao patientDao;

        public DeleteAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.deletePatients(patients);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final PatientDao patientDao;

        public DeleteAllAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            patientDao.deleteAllPatients();
            return null;
        }
    }
}
