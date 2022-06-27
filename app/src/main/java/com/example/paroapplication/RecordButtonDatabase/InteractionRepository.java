package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InteractionRepository {
    private final LiveData<List<Interaction>> allInteractionsLive;
    private final InteractionDao interactionDao;

    public InteractionRepository(Context context) {
        InteractionDatabase interactionDatabase = InteractionDatabase.getDatabase(context.getApplicationContext());
        interactionDao = interactionDatabase.getInteractionDao();
        allInteractionsLive = interactionDao.getAllInteractionsLive();
    }

    //insert
    void insertInteractions(Interaction... interactions) {
        new InsertAsyncTask(interactionDao).execute(interactions);
    }
    //update
    void updateInteractions(Interaction... interactions) {
        new UpdateAsyncTask(interactionDao).execute(interactions);
    }
    //delete
    void deleteInteractions(Interaction... interactions) {
        new DeleteAsyncTask(interactionDao).execute(interactions);
    }
    //delete all
    void deleteAllInteractions() {
        new DeleteAllAsyncTask(interactionDao).execute();
    }

    //get all
    public LiveData<List<Interaction>> getAllInteractionsLive() {
        return allInteractionsLive;
    }


    static class InsertAsyncTask extends AsyncTask<Interaction, Void, Void> {
        private final InteractionDao interactionDao;

        public InsertAsyncTask(InteractionDao interactionDao) {
            this.interactionDao = interactionDao;
        }

        @Override
        protected Void doInBackground(Interaction... interactions) {
            interactionDao.insertInteractions(interactions);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Interaction, Void, Void> {
        private final InteractionDao interactionDao;

        public UpdateAsyncTask(InteractionDao interactionDao) {
            this.interactionDao = interactionDao;
        }

        @Override
        protected Void doInBackground(Interaction... interactions) {
            interactionDao.updateInteractions(interactions);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Interaction, Void, Void> {
        private final InteractionDao interactionDao;

        public DeleteAsyncTask(InteractionDao interactionDao) {
            this.interactionDao = interactionDao;
        }

        @Override
        protected Void doInBackground(Interaction... interactions) {
            interactionDao.deleteInteractions(interactions);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final InteractionDao interactionDao;

        public DeleteAllAsyncTask(InteractionDao interactionDao) {
            this.interactionDao = interactionDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            interactionDao.deleteAllInteractions();
            return null;
        }
    }
}
