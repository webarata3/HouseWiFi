package link.webarata3.dro.housewifi.activity;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.R;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {
    @Test
    public void test_clickRegisterButton_error() {
        ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.ssidEditText)).check(matches(hasErrorText("入力必須です")));
    }

    @Test
    public void test_clickRegisterbutton_intent() {
        ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.ssidEditText)).perform(replaceText("dummy_ssid"));
        onView(withId(R.id.registerButton)).perform(click());
    }
}
