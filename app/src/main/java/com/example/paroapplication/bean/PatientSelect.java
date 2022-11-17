package com.example.paroapplication.bean;

import com.example.paroapplication.PatientDatabase.Patient;

public class PatientSelect {
    private Patient patient;
    private boolean isSelect;

    public PatientSelect(Patient patient, boolean isSelect) {
        this.patient = patient;
        this.isSelect = isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
