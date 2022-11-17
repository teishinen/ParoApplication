package com.example.paroapplication.PatientDatabase;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PatientViewModel extends AndroidViewModel{
    private final PatientRepository patientRepository;
    public PatientViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
    }

    public LiveData<List<Patient>> getAllPatientsLive() {
        return patientRepository.getAllPatientsLive();

    } public List<Patient> getAllPatient() {
        return patientRepository.getAllPatient();
    }
    public LiveData<List<Patient>>findPatientsWithPattern(String pattern){
        return patientRepository.findPatientsWithPattern(pattern);
    }
    public LiveData<List<Patient>>getPatientByName(String patientName){
        return patientRepository.getPatientByName(patientName);
    }
    public LiveData<List<Patient>> getPatientById(String id){
        return patientRepository.getPatientById(id);
    }


    public void insertPatient(Patient... patients){
        patientRepository.insertPatients(patients);
    }
    public void updatePatients(Patient... patients){
        patientRepository.updatePatients(patients);
    }
    public void deletePatients(Patient... patients){
        patientRepository.deletePatients(patients);
    }
    public void deleteAllPatients(){
        patientRepository.deleteAllPatients();
    }
}

