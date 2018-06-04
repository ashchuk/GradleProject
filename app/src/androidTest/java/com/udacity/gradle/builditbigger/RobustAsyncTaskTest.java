package com.udacity.gradle.builditbigger;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.fragments.MainActivityFragment;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import javax.security.auth.callback.Callback;

import static junit.framework.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RobustAsyncTaskTest {
    private static final String ERROR_MESSAGE = "Error. Try again";

    public RobustAsyncTaskTest(){
    }

    @Test
    public void testDoInBackground() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);
        String joke = new MainActivityFragment.RobustGetJokeAsyncTask(signal).execute().get();
        signal.await();
        assertFalse("Error: Fetched Joke = " + joke, TextUtils.equals(joke,ERROR_MESSAGE));
    }
}