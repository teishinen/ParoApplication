package com.example.paroapplication.PatientDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Patient.class},version=2,exportSchema=false)
public abstract class PatientDatabase extends RoomDatabase{
        private static PatientDatabase INSTANCE;
        static synchronized PatientDatabase getDatabase(Context context){
                if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PatientDatabase.class,"patient_database")
                                .fallbackToDestructiveMigration()               //古いデータベースを削除し、新しいデータベースを作る
                                //.addMigrations(MIGRATION_1_2)                 //古いデータベースから新しいデータベースにデータを移行する
                                .build();
                }
                return INSTANCE;
        }
        public abstract PatientDao getPatientDao();

        /*static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE Record ADD COLUMN patient TEXT NOT NULL DEFAULT 0");
                }
        };*/
        }