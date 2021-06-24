package ru.geekbrains.githubclient;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setup() {
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void mainActivityNotNull() {
        scenario.onActivity(TestCase::assertNotNull);
    }

    @Test
    public void mainActivityResumed() {
        assertEquals(Lifecycle.State.RESUMED, scenario.getState());
    }

    @After
    public void close() {
        scenario.close();
    }
}
