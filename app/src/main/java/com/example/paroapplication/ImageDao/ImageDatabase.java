package com.example.paroapplication.ImageDao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffDao;

//singleton
@Database(entities = {ImageBean.class},version=2,exportSchema=false)
public abstract class ImageDatabase extends RoomDatabase{
        private static ImageDatabase INSTANCE;
        static synchronized ImageDatabase getDatabase(Context context){
                if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ImageDatabase.class,"image_database")
                                .fallbackToDestructiveMigration()               //古いデータベースを削除し、新しいデータベースを作る
                                //.addMigrations(MIGRATION_1_2)                 //古いデータベースから新しいデータベースにデータを移行する
                                .build();
                }
                return INSTANCE;
        }
        public abstract ImageDao getStaffDao();

        /*static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE Record ADD COLUMN staff TEXT NOT NULL DEFAULT 0");
                }
        };*/
        }