package link.webarata3.dro.housewifi.activity;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import link.webarata3.dro.housewifi.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {
    @Test
    public void test() {
        ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.ssidEditText)).check(matches(hasErrorText("入力必須です")));

//        Intent result = new Intent();
//
//        intending(toPackage("link.webarata3.dro.housewifi.acitivity")).respondWith();
//
//        intending(toPackage("package.containing.login.activity")).respondWith(result);
//
//        intended(allOf(
//                hasComponent(hasMyPackageName()),
//                hasComponent(hasClassName("link.webarata3.dro.housewifi.activity.RegisterActivity"))
//        ));
    }
}
