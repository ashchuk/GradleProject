package com.udacity.gradle.builditbigger.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashchuk.ashchuksandroidlibrary.JokerLibActivity;
import com.ashchuk.ashchuksjavalibrary.JokerClass;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.JokeSourcePreferences;

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
        rootView.findViewById(R.id.button_load_joke).setOnClickListener((view) ->{
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
                    ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(JokerClass.getGAEJoke());
                    break;
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        Activity activity = this.getActivity();

        if (savedInstanceState != null) {
            String currJoke = savedInstanceState.getString(ARG_JOKE_TEXT);
            ((TextView) activity.findViewById(R.id.joke_tv)).setText(currJoke);
        } else {
            ((TextView) activity.findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String currJoke = ((TextView) getActivity().findViewById(R.id.joke_tv)).getText().toString();
        outState.putString(ARG_JOKE_TEXT, currJoke);
    }
}
