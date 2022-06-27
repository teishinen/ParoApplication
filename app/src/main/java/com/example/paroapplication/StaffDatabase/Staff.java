package com.example.paroapplication.StaffDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Staff {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "staff_id")            //give name
    private String staffId;
    @ColumnInfo(name = "staff_name")            //give name
    private String staffName;
    @ColumnInfo(name = "staff_age")            //give name
    private String staffAge;
    @ColumnInfo(name = "staff_sex")            //give name
    private String staffSex;

    public Staff(String staffId, String staffName, String staffAge, String staffSex) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffAge = staffAge;
        this.staffSex = staffSex;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
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
