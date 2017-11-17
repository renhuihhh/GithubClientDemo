package open.hui.ren.githubclientdemo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import open.hui.ren.githubclientdemo.login.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author renhui
 * @date 16-11-11
 * @desc open.hui.ren.githubclientdemo
 */

@RunWith(AndroidJUnit4.class)
public class LoginSceneSuite {
    @Rule
    public  ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(
        LoginActivity.class);
    private boolean                         isTestable   = true;

    @Before
    public void prepare(){
        isTestable = activityRule.getActivity().getPresenter().whetherNeedLog();
    }

    @Test
    public void login() {
        if(!isTestable){
            return;
        }
        onView(withId(R.id.username)).check(matches(withText("")))
                                     .perform(typeText("renhuihhh"));
        onView(withId(R.id.password)).check(matches(withText("")))
                                     .perform(typeText("198943371hhh"));
        onView(withId(R.id.sign_in_button)).perform(click());
    }

    @Test
    public void seeTheMainPage(){
        onView(withId(R.id.toolbar));
    }
}
