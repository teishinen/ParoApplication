package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Interaction.class},version=1,exportSchema=false)
public abstract class InteractionDatabase extends RoomDatabase {
    private static InteractionDatabase INSTANCE;
    static synchronized InteractionDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), InteractionDatabase.class,"record_interaction_database")
                    .fallbackToDestructiveMigration()               //古いデータベースを削除し、新しいデータベースを作る
                    //.addMigrations(MIGRATION_1_2)                 //古いデータベースから新しいデータベースにデータを移行する
                    .build();
        }
        return INSTANCE;
    }

    public abstract InteractionDao getInteractionDao();

        /*static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE Record ADD COLUMN patient TEXT NOT NULL DEFAULT 0");
                }
        };*/
}
