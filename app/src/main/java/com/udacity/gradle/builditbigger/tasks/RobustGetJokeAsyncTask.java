package com.udacity.gradle.builditbigger.tasks;

import android.os.AsyncTask;

import java.util.concurrent.CountDownLatch;

import static com.udacity.gradle.builditbigger.utils.GAEConnector.getJokeFromApi;

public class RobustGetJokeAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String ERROR_MESSAGE = "Error. Try again";
    private CountDownLatch countDownLatch;

    public RobustGetJokeAsyncTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }

    protected String doInBackground(Void... voids) {
        try {
            return getJokeFromApi();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_MESSAGE;
        }
        finally {
            countDownLatch.countDown();
        }
    }
}