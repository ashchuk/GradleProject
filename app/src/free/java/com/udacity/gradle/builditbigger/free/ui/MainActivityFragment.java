package com.udacity.gradle.builditbigger.free.ui;

import android.app.Activity;
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

import com.ashchuk.ashchuksjavalibrary.JokerClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.JokeSourcePreferences;

public class MainActivityFragment extends Fragment {
    private static final String ARG_JOKE_TEXT = "JOKE_TEXT";

    private InterstitialAd mInterstitialAd;

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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ((TextView) rootView.findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
        rootView.findViewById(R.id.button_load_joke).setOnClickListener((view) ->{
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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        Activity activity = this.getActivity();

        AdView mAdView = activity.findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

        if (savedInstanceState != null) {
            String currJoke = savedInstanceState.getString(ARG_JOKE_TEXT);
            ((TextView) activity.findViewById(R.id.joke_tv)).setText(currJoke);
        } else {
            ((TextView) activity.findViewById(R.id.joke_tv)).setText(JokerClass.getJoke());
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItemJavaLibrary = menu.findItem(R.id.action_java_library);
        MenuItem menuItemAndroidLibrary = menu.findItem(R.id.action_android_library);
        MenuItem menuItemGoogleAppEngine = menu.findItem(R.id.action_google_app_engine);

        menuItemJavaLibrary.setChecked(false);
        menuItemAndroidLibrary.setChecked(false);
        menuItemGoogleAppEngine.setChecked(false);

        switch (JokeSourcePreferences.getJokeFetchType(getActivity())) {
            case JokeSourcePreferences.JAVA_LIBRARY_SOURCE:
                menuItemJavaLibrary.setChecked(true);
                break;
            case JokeSourcePreferences.ANDROID_LIBRARY_SOURCE:
                menuItemAndroidLibrary.setChecked(true);
                break;
            case JokeSourcePreferences.GAE_SOURCE:
                menuItemGoogleAppEngine.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        item.setChecked(true);
        switch (itemId) {
            case R.id.action_java_library:
                JokeSourcePreferences.saveJokeFetchType(getActivity(), JokeSourcePreferences.JAVA_LIBRARY_SOURCE);
                break;
            case R.id.action_android_library:
                JokeSourcePreferences.saveJokeFetchType(getActivity(), JokeSourcePreferences.ANDROID_LIBRARY_SOURCE);
                break;
            case R.id.action_google_app_engine:
                JokeSourcePreferences.saveJokeFetchType(getActivity(), JokeSourcePreferences.GAE_SOURCE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String currJoke = ((TextView) getActivity().findViewById(R.id.joke_tv)).getText().toString();
        outState.putString(ARG_JOKE_TEXT, currJoke);
    }
}