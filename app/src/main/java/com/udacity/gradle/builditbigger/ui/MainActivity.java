package com.udacity.gradle.builditbigger.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.utils.JokeSourcePreferences;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItemJavaLibrary = menu.findItem(R.id.action_java_library);
        MenuItem menuItemAndroidLibrary = menu.findItem(R.id.action_android_library);
        MenuItem menuItemGoogleAppEngine = menu.findItem(R.id.action_google_app_engine);

        menuItemJavaLibrary.setChecked(false);
        menuItemAndroidLibrary.setChecked(false);
        menuItemGoogleAppEngine.setChecked(false);

        switch (JokeSourcePreferences.getJokeFetchType(this)) {
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        item.setChecked(true);
        switch (itemId) {
            case R.id.action_java_library:
                JokeSourcePreferences.saveJokeFetchType(this, JokeSourcePreferences.JAVA_LIBRARY_SOURCE);
                break;
            case R.id.action_android_library:
                JokeSourcePreferences.saveJokeFetchType(this, JokeSourcePreferences.ANDROID_LIBRARY_SOURCE);
                break;
            case R.id.action_google_app_engine:
                JokeSourcePreferences.saveJokeFetchType(this, JokeSourcePreferences.GAE_SOURCE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
