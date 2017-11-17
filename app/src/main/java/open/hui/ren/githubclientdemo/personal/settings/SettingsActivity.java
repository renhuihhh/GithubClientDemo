package open.hui.ren.githubclientdemo.personal.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.login.LoginActivity;

import static open.hui.ren.githubclientdemo.R.id.toolbar;

/**
 * @author renhui
 * @date 16-10-31
 * @desc Settings activity
 */

public class SettingsActivity extends AppCompatActivity implements SettingsContracts.View {
    @BindView(toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.user_logout_card_view)
    CardView mUserLogoutCardView;
    private SettingsContracts.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initViews();
        mPresenter = new SettingsPresenter(this);
        getPresenter().start();
    }

    private void initViews() {
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @OnClick({R.id.user_logout_card_view})
    void whenClick(View view) {
        if (view.getId() == R.id.user_logout_card_view) {
            logout();
        }
    }

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void setPresenter(SettingsContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public SettingsContracts.Presenter getPresenter() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void jump2LoginPage() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void logout() {
        mPresenter.logOut();
    }
}
