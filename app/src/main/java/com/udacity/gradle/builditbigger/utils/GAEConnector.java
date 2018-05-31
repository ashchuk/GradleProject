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
                    .setRootUrl("http://127.0.0.1:8080/")
                    .setGoogleClientRequestInitializer(abstractGoogleClientRequest -> abstractGoogleClientRequest.setDisableGZipContent(true));
            backendApi = builder.build();
        }

        String joke = backendApi.sayJoke().execute().getJoke();
        return backendApi.sayHi("Hi").execute().getData();
    }
}
