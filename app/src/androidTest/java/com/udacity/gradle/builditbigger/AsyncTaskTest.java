package com.udacity.gradle.builditbigger;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.fragments.MainActivityFragment;
import com.udacity.gradle.builditbigger.tasks.GetJokeAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AsyncTaskTest {
    private static final String ERROR_MESSAGE = "Error. Try again";

    public AsyncTaskTest(){
    }

    @Test
    public void testDoInBackground() throws Exception {
        String joke = new GetJokeAsyncTask().execute().get();
        Thread.sleep(10000);
        assertFalse("Error: Fetched Joke = " + joke, TextUtils.equals(joke,ERROR_MESSAGE));
    }
}