package com.udacity.gradle.builditbigger.tasks;

import android.os.AsyncTask;

import java.io.IOException;

import static com.udacity.gradle.builditbigger.utils.GAEConnector.getJokeFromApi;

public class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String ERROR_MESSAGE = "Error. Try again";

    protected String doInBackground(Void... voids) {
        try {
            return getJokeFromApi();
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_MESSAGE;
        }
    }
}
