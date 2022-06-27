package com.example.paroapplication.RecordButtonDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProblemBehaviorRepository {
    private final LiveData<List<ProblemBehavior>> allProblemBehaviorsLive;
    private final ProblemBehaviorDao problemBehaviorDao;

    public ProblemBehaviorRepository(Context context) {
        ProblemBehaviorDatabase problemBehaviorDatabase = ProblemBehaviorDatabase.getDatabase(context.getApplicationContext());
        problemBehaviorDao = problemBehaviorDatabase.getProblemBehaviorDao();
        allProblemBehaviorsLive = problemBehaviorDao.getAllProblemBehaviorsLive();
    }

    //insert
    void insertProblemBehaviors(ProblemBehavior... problemBehaviors) {
        new InsertAsyncTask(problemBehaviorDao).execute(problemBehaviors);
    }
    //update
    void updateProblemBehaviors(ProblemBehavior... problemBehaviors) {
        new UpdateAsyncTask(problemBehaviorDao).execute(problemBehaviors);
    }
    //delete
    void deleteProblemBehaviors(ProblemBehavior... problemBehaviors) {
        new DeleteAsyncTask(problemBehaviorDao).execute(problemBehaviors);
    }
    //delete all
    void deleteAllProblemBehaviors() {
        new DeleteAllAsyncTask(problemBehaviorDao).execute();
    }

    //get all
    public LiveData<List<ProblemBehavior>> getAllProblemBehaviorsLive() {
        return allProblemBehaviorsLive;
    }


    static class InsertAsyncTask extends AsyncTask<ProblemBehavior, Void, Void> {
        private final ProblemBehaviorDao problemBehaviorDao;

        public InsertAsyncTask(ProblemBehaviorDao problemBehaviorDao) {
            this.problemBehaviorDao = problemBehaviorDao;
        }

        @Override
        protected Void doInBackground(ProblemBehavior... problemBehaviors) {
            problemBehaviorDao.insertProblemBehaviors(problemBehaviors);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<ProblemBehavior, Void, Void> {
        private final ProblemBehaviorDao problemBehaviorDao;

        public UpdateAsyncTask(ProblemBehaviorDao problemBehaviorDao) {
            this.problemBehaviorDao = problemBehaviorDao;
        }

        @Override
        protected Void doInBackground(ProblemBehavior... problemBehaviors) {
            problemBehaviorDao.updateProblemBehaviors(problemBehaviors);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<ProblemBehavior, Void, Void> {
        private final ProblemBehaviorDao problemBehaviorDao;

        public DeleteAsyncTask(ProblemBehaviorDao problemBehaviorDao) {
            this.problemBehaviorDao = problemBehaviorDao;
        }

        @Override
        protected Void doInBackground(ProblemBehavior... problemBehaviors) {
            problemBehaviorDao.deleteProblemBehaviors(problemBehaviors);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final ProblemBehaviorDao problemBehaviorDao;

        public DeleteAllAsyncTask(ProblemBehaviorDao problemBehaviorDao) {
            this.problemBehaviorDao = problemBehaviorDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            problemBehaviorDao.deleteAllProblemBehaviors();
            return null;
        }
    }
}
