package com.example.paroapplication.RecordDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Record.class},version=2,exportSchema=false)
public abstract class RecordDatabase extends RoomDatabase{
        private static RecordDatabase INSTANCE;
        static synchronized RecordDatabase getDatabase(Context context){
                if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecordDatabase.class,"record_database")
                                .fallbackToDestructiveMigration()               //古いデータベースを削除し、新しいデータベースを作る
                                //.addMigrations(MIGRATION_1_2)                 //古いデータベースから新しいデータベースにデータを移行する
                                .build();
                }
                return INSTANCE;
        }
        public abstract RecordDao getRecordDao();

        /*static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE record ADD COLUMN patient TEXT NOT NULL DEFAULT 0");
                }
        };*/
}