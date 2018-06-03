package com.udacity.gradle.builditbigger.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashchuk.ashchuksandroidlibrary.JokerLibActivity;
import com.ashchuk.ashchuksjavalibrary.JokerClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.JokeSourcePreferences;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static com.udacity.gradle.builditbigger.utils.GAEConnector.getJokeFromApi;

public class MainActivityFragment extends Fragment {
    private static final String ARG_JOKE_TEXT = "JOKE_TEXT";
    private static final int ACTIVITY_REQUEST_CODE = 123;

    public static MainActivityFragment newInstance() {
        Bundle args = new Bundle();
        MainActivityFragment fragment = new MainActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
        ((TextView) rootView.findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
        rootView.findViewById(R.id.button_load_joke).setOnClickListener((view) -> {
            switch (JokeSourcePreferences.getJokeFetchType(getActivity())) {
                case JokeSourcePreferences.JAVA_LIBRARY_SOURCE:
                    ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
                    break;
                case JokeSourcePreferences.ANDROID_LIBRARY_SOURCE:
                    Intent intentStartJokeLibActivity = new Intent(getActivity(), JokerLibActivity.class);
                    intentStartJokeLibActivity.putExtra(JokerLibActivity.ARG_JOKE_RECEIVED, JokerClass.getJoke());
                    getActivity().startActivityForResult(intentStartJokeLibActivity, ACTIVITY_REQUEST_CODE);
                    break;
                case JokeSourcePreferences.GAE_SOURCE:
                    GetJokeAsyncTask task = new GetJokeAsyncTask();
                    try {
                        String GAEjoke = task.execute().get();
                        ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(GAEjoke);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        AdView mAdView = rootView.findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String currJoke = savedInstanceState.getString(ARG_JOKE_TEXT);
            ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(currJoke);
        } else {
            ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String currJoke = ((TextView) getActivity().findViewById(R.id.joke_tv)).getText().toString();
        outState.putString(ARG_JOKE_TEXT, currJoke);
    }

    public static class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... voids) {
            try {
                return getJokeFromApi();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error. Try again";
            }
        }
    }
}
