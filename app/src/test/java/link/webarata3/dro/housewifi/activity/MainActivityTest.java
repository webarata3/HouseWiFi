package link.webarata3.dro.housewifi.activity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.R;

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

    @Before
    public void setUp() {
        mainActivity = mainActivityRule.getActivity();
    }

    @Test
    public void test_clickFab() {
        onView(ViewMatchers.withId(R.id.addFab)).perform(click());

        intended(allOf(
                hasComponent(hasMyPackageName()),
                hasComponent(hasClassName("link.webarata3.dro.housewifi.activity.RegisterActivity"))
        ));
    }
}
