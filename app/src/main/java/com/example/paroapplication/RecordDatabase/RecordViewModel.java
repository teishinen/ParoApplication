package com.example.paroapplication.RecordDatabase;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecordViewModel extends AndroidViewModel{
    private static RecordRepository recordRepository;
    public RecordViewModel(@NonNull Application application) {
        super(application);
        recordRepository = new RecordRepository(application);
    }

    public LiveData<List<Record>> getAllRecordsLive() {
        return recordRepository.getAllRecordsLive();
    }
    public LiveData<List<Record>> findRecordsByStartTime(long startTime){
        return recordRepository.findRecordsByStartTime(startTime);
    }
    public LiveData<List<Record>> findRecordsByPatientName(String patientName){
        return recordRepository.findRecordsByPatientName(patientName);
    }
    public LiveData<List<Record>> findRecordsByPatientNameAndTime(String patientName,String time){
        return recordRepository.findRecordsByPatientName(patientName,time);
    }
    public LiveData<List<Record>> findRecordsByStaffName(String staffName){
        return recordRepository.findRecordsByStaffName(staffName);
    }
    public LiveData<List<Record>> findRecordsDetailByPatient(String startID, String patientName){
        return recordRepository.findRecordsDetailByPatient(startID,patientName);
    }
    public LiveData<List<Record>> findRecordsDetailByStaff(String startID, String staffName){
        return recordRepository.findRecordsDetailByStaff(startID,staffName);
    }
    public LiveData<List<Record>> findRecordsByStartId(String startID){
        return recordRepository.findRecordsByStartId(startID);
    }
    public LiveData<List<Record>> findHistoryRecord() {
        return recordRepository.findHistoryRecord();
    }
    public LiveData<List<Record>> findHistoryRecordByTime(String time) {
        return recordRepository.findHistoryRecordBytime(time);
    }
    public LiveData<List<Record>> findHistoryRecordByYear(String year) throws ExecutionException, InterruptedException {
        return recordRepository.findHistoryRecordByYear(year);
    }
    public LiveData<List<Record>> findHistoryRecordByYearAndMonth(String year,String month) throws ExecutionException, InterruptedException {
        return recordRepository.findHistoryRecordByYearAndMonth(year,month);
    }
    public LiveData<List<Record>> findHistoryRecordByYearAndMonthAndDay(String year,String month,String day) throws ExecutionException, InterruptedException {
        return recordRepository.findHistoryRecordByYearAndMonthAndDay(year,month,day);
    }
    public String[] findHistoryRecordYear() throws ExecutionException, InterruptedException {
        return recordRepository.findHistoryRecordYear();
    }
    public String[] findHistoryRecordMonth(String year) throws ExecutionException, InterruptedException {
        return recordRepository.findHistoryRecordMonth(year);
    }
    public String[] findHistoryRecordDay(String year,String month) throws ExecutionException, InterruptedException {
        return recordRepository.findHistoryRecordDay(year,month);
    }
    public String[] findPatientRecordByStartId(String startId) throws ExecutionException, InterruptedException {
        return recordRepository.findPatientRecordByStartId(startId);
    }
    public String[] findStaffRecordByStartId(String startId) throws ExecutionException, InterruptedException {
        return recordRepository.findStaffRecordByStartId(startId);
    }
    public LiveData<List<Record>> findHistoryDetail(String startID) {
        return recordRepository.findHistoryDetail(startID);
    }


    public long getWeeklyCareHours(String patientName) throws ExecutionException, InterruptedException {
        return recordRepository.getWeeklyCareHours(patientName);
    }





    public void insertRecord(Record... records){
        recordRepository.insertRecords(records);
    }
    public void updateRecords(Record... records){
        recordRepository.updateRecords(records);
    }
    public void deleteRecords(Record... records){
        recordRepository.deleteRecords(records);
    }
    public void deleteAllRecords(){
        recordRepository.deleteAllRecords();
    }
    public void deleteRecordsByStartId(String startId) throws ExecutionException, InterruptedException {
        recordRepository.deleteRecordsByStartId(startId);
    }
}

