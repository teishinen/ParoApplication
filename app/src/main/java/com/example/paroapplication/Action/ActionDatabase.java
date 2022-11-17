package com.example.paroapplication.Action;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.StaffDatabase.StaffDao;


@Database(entities = {Action.class},version=2,exportSchema=false)
public abstract class ActionDatabase extends RoomDatabase{
        private static ActionDatabase INSTANCE;
        static synchronized ActionDatabase getDatabase(Context context){
                if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ActionDatabase.class,"action_database")
                                .fallbackToDestructiveMigration()               //古いデータベースを削除し、新しいデータベースを作る
                                //.addMigrations(MIGRATION_1_2)                 //古いデータベースから新しいデータベースにデータを移行する
                                .build();
                }
                return INSTANCE;
        }
        public abstract ActionDao getStaffDao();

        /*static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE Record ADD COLUMN staff TEXT NOT NULL DEFAULT 0");
                }
        };*/
        }