package com.example.paroapplication.StaffDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Staff.class},version=2,exportSchema=false)
public abstract class StaffDatabase extends RoomDatabase{
        private static StaffDatabase INSTANCE;
        static synchronized StaffDatabase getDatabase(Context context){
                if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StaffDatabase.class,"staff_database")
                                .fallbackToDestructiveMigration()               //古いデータベースを削除し、新しいデータベースを作る
                                //.addMigrations(MIGRATION_1_2)                 //古いデータベースから新しいデータベースにデータを移行する
                                .build();
                }
                return INSTANCE;
        }
        public abstract StaffDao getStaffDao();

        /*static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE Record ADD COLUMN staff TEXT NOT NULL DEFAULT 0");
                }
        };*/
        }