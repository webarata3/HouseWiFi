package link.webarata3.dro.housewifi.activity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.R;
import link.webarata3.dro.housewifi.helper.DatabaseHelper;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> mainActivityRule = new IntentsTestRule<>(MainActivity.class);
    private MainActivity mainActivity;
    private Context context;

    @Before
    public void setUp() {
        mainActivity = mainActivityRule.getActivity();
        context = ApplicationProvider.getApplicationContext();
    }

    @After
    public void tearDwon() {
        DatabaseHelper.getInstance(context).close();
    }

    @Test
    public void test_clickFab() {
        onView(ViewMatchers.withId(R.id.addFab)).perform(click());

        intended(allOf(
                hasComponent(hasMyPackageName()),
                hasComponent(hasClassName("link.webarata3.dro.housewifi.activity.RegisterActivity"))
        ));
    }

    @Test
    public void test_activityResult() {
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(
                        MainActivity.REGISTER_SSID_CODE, new Intent(mainActivity, RegisterActivity.class));
        intending(toPackage("link.webarata3.dro.housewifi.activity")).respondWith(result);
    }
}
