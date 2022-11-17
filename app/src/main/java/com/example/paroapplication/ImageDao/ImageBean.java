package com.example.paroapplication.ImageDao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ImageBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "staff_name")            //give name
    private String staffName;

    @ColumnInfo(name = "img_path")            //give name
    private String imgPath;
    @ColumnInfo(name = "time")            //give name
    private long time;

    public ImageBean(String staffName, String imgPath,long time) {
        this.staffName = staffName;
        this.imgPath = imgPath;
        this.time=time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
