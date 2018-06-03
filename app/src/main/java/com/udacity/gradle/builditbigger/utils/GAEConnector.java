package com.udacity.gradle.builditbigger.utils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class GAEConnector {
    private static MyApi backendApi;

    public static String getJokeFromApi() throws IOException {
        if (backendApi == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.0.7:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(abstractGoogleClientRequest -> abstractGoogleClientRequest.setDisableGZipContent(true));
            backendApi = builder.build();
        }

        return backendApi.sayJoke().execute().getJoke();
    }
}
