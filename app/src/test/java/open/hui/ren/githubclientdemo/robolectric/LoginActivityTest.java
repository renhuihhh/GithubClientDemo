package open.hui.ren.githubclientdemo.robolectric;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.RoboTestBase;
import open.hui.ren.githubclientdemo.login.LoginActivity;
import open.hui.ren.githubclientdemo.main.MainActivity;

import static org.robolectric.Shadows.shadowOf;

/**
 * @author renhui
 * @date 16-11-21
 * @desc open.hui.ren.githubclientdemo.robolectric
 */
public class LoginActivityTest extends RoboTestBase {
    private ActivityController  mController;
    private LoginActivity       mLoginActivity;

    EditText mUsername;
    EditText mPassword;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mController = Robolectric.buildActivity(LoginActivity.class)
                                 .create()
                                 .start()
                                 .visible();
        mLoginActivity = (LoginActivity) mController.get();
        mUsername = (EditText) mLoginActivity.findViewById(R.id.username);
        mPassword = (EditText) mLoginActivity.findViewById(R.id.password);
        mController.start()
                   .resume();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        mController.pause()
                   .destroy();
    }

    @Test
    public void testOnCreateNotNull() {
        Assert.assertNotNull(mLoginActivity);
        Assert.assertNotNull(mUsername);
        Assert.assertNotNull(mPassword);
    }

    @Test
    public void testLogin() {
        AlertDialog alertDialog = Mockito.mock(AlertDialog.class);
        alertDialog.setTitle("123");
        mLoginActivity.mLoadDialog = alertDialog;
        mUsername.setText("renhuiabc");
        mPassword.setText("198943371");
        mLoginActivity.findViewById(R.id.sign_in_button)
                      .performClick();
        Intent expectedIntent = new Intent(mLoginActivity, MainActivity.class);
        Assert.assertEquals(expectedIntent,shadowOf(mLoginActivity).getNextStartedActivity());
    }
}