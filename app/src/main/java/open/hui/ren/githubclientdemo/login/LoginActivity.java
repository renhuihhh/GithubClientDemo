package open.hui.ren.githubclientdemo.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.main.MainActivity;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.UserInfo;

/**
 * @author renhui
 * @date 16-9-22
 * @desc 登陆UI
 */
public class LoginActivity extends AppCompatActivity implements LoginContracts.View {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.login_progress)
    ProgressBar          mLoginProgress;
    @BindView(R.id.username)
    AutoCompleteTextView mUsername;
    @BindView(R.id.password)
    EditText             mPassword;
    @BindView(R.id.sign_in_button)
    Button               mSignInButton;
    @BindView(R.id.login_form_pane)
    LinearLayout         mLoginFormPane;
    @BindView(R.id.login_form)
    //custom
    ScrollView           mLoginForm;
    private LoginContracts.Presenter mPresenter;

    //ui behaviour
    private SweetAlertDialog mDialog;

    //view data
    private UserInfo mUserInfo;

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
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
    }

    @Override
    public void showLoginDialog() {
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        mDialog.setTitleText("Login...");
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void dismissLoginDialog() {
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void doLogin() {
        String userNameOnUI = mUsername.getText().toString().trim();
        String passwordOnUI = mPassword.getText().toString().trim();
        checkUserNamePasswordVaildate(userNameOnUI, passwordOnUI);
    }


    @Override
    public void onUserInfoUpdate(UserInfo userInfo) {
        dismissLoginDialog();
        mUserInfo = userInfo;
        jumpToMain();
    }

    @Override
    public void jumpToMain() {
        if (mUserInfo == null) {
            mUserInfo = getPresenter().getPersistence().retrieveData();
        }else if(mUserInfo == null){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.putExtra("userInfo", mUserInfo);
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserInfoFailed(Throwable error) {
        dismissLoginDialog();
        String msg = error.getMessage();
        if (msg.equals(ConstConfig.S_INIT_PARAMS)) {
            return;
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void checkUserNamePasswordVaildate(String userNameOnUI, String passwordOnUI) {
        if (TextUtils.isEmpty(userNameOnUI) || TextUtils.isEmpty(passwordOnUI)) {
            Log.w(TAG, "username or password wrong...");
            return;
        }
        showLoginDialog();
        mPresenter.fetchUserInfoByUserName(userNameOnUI, passwordOnUI);
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
}

