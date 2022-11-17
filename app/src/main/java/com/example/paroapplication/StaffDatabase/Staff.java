package com.example.paroapplication.StaffDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Staff implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "staff_name")            //give name
    private String staffName;
    @ColumnInfo(name = "staff_age")            //give name
    private String staffAge;
    @ColumnInfo(name = "staff_sex")            //give name
    private String staffSex;
    @ColumnInfo(name = "staff_password")            //give name
    private String staffPassword;
    public Staff( String staffName, String staffPassword,String staffAge, String staffSex) {
        this.staffName = staffName;
        this.staffAge = staffAge;
        this.staffSex = staffSex;
        this.staffPassword=staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffAge() {
        return staffAge;
    }

    public void setStaffAge(String staffAge) {
        this.staffAge = staffAge;
    }

    public String getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(String staffSex) {
        this.staffSex = staffSex;
    }
}
