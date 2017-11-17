package open.hui.ren.githubclientdemo.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.MainActivity;
import tom.hui.ren.utils.DialogUtil;

/**
 * @author renhui
 * @date 16-9-22
 * @desc 登陆UI
 */
public class LoginActivity extends AppCompatActivity implements LoginContracts.View {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.username)
    AutoCompleteTextView mUsername;
    @BindView(R.id.password)
    EditText             mPassword;
    @BindView(R.id.sign_in_button)
    Button               mSignInButton;
    @BindView(R.id.login_form_pane)
    LinearLayout         mLoginFormPane;
    //custom
    public  AlertDialog              mLoadDialog;
    private LoginContracts.Presenter mPresenter;
    //view data
    private UserInfo                 mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
        setPresenter(new LoginPresenter(this));
        getPresenter().start();
    }

    @Override
    public void initViews() {
    }

    @Override
    public void showLoginDialog() {
        if (mLoadDialog == null) {
            mLoadDialog = DialogUtil.getAndShowLoadingDialog(this, getString(R.string.app_loading));
            return;
        }
        mLoadDialog.show();
    }

    @Override    public void dismissLoginDialog() {
        if (mLoadDialog != null) {
            mLoadDialog.dismiss();
        }
    }

    @Override
    public void doLogin() {
        String userNameOnUI = mUsername.getText()
                                       .toString()
                                       .trim();
        String passwordOnUI = mPassword.getText()
                                       .toString()
                                       .trim();
        if (checkUserNamePasswordVaildate(userNameOnUI, passwordOnUI)) {
            showLoginDialog();
            mPresenter.fetchUserInfoByUserName(userNameOnUI, passwordOnUI);
        }
    }

    @Override
    public void onLoginSuccess(UserInfo userInfo) {
        dismissLoginDialog();
        mUserInfo = userInfo;
        jumpToMain();
    }

    @Override
    public void jumpToMain() {
        if (mUserInfo == null) {
            mUserInfo = getPresenter().getPersistence()
                                      .retrieveData();
        }
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("userInfo", mUserInfo);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(Throwable error) {
        dismissLoginDialog();
        String msg = error.getMessage();
        if (msg.equals(ConstConfig.S_INIT_PARAMS)) {
            return;
        }
    }

    private boolean checkUserNamePasswordVaildate(String userNameOnUI, String passwordOnUI) {
        if (TextUtils.isEmpty(userNameOnUI) || TextUtils.isEmpty(passwordOnUI)) {
            Log.w(TAG, "username or password wrong...");
            return false;
        }
        return true;
    }

    @OnClick(R.id.sign_in_button)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                doLogin();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        mPresenter.resume();
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        mPresenter.pause();
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void setPresenter(LoginContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public LoginContracts.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public Context getCtx() {
        return this;
    }

    @Override
    public MyApplication getAppContext() {
        return (MyApplication) getApplication();
    }

    @Override
    public int getViewId() {
        return 0;
    }
}

