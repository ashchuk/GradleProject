package com.udacity.gradle.builditbigger.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashchuk.ashchuksjavalibrary.JokerClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.JokeSourcePreferences;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        root.findViewById(R.id.button_load_joke).setOnClickListener((view) ->{
            switch (JokeSourcePreferences.getJokeFetchType(getActivity())) {
                case JokeSourcePreferences.JAVA_LIBRARY_SOURCE:
                    ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
                    break;
                case JokeSourcePreferences.ANDROID_LIBRARY_SOURCE:
                    ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
                    break;
                case JokeSourcePreferences.GAE_SOURCE:
                    ((TextView) getActivity().findViewById(R.id.joke_tv)).setText(JokerClass.getGAEJoke());
                    break;
            }
        });

        AdView mAdView = root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }
}
