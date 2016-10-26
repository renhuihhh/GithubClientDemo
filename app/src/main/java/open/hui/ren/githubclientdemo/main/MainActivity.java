package open.hui.ren.githubclientdemo.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.apiservices.params.OverViewParams;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.followers.FollowersFragment;
import open.hui.ren.githubclientdemo.fragments.following.FollowingFragment;
import open.hui.ren.githubclientdemo.fragments.overview.OverViewContacts;
import open.hui.ren.githubclientdemo.fragments.overview.OverViewFragment;
import open.hui.ren.githubclientdemo.fragments.overview.OverViewPresenter;
import open.hui.ren.githubclientdemo.fragments.repositories.RepositoriesFragment;
import open.hui.ren.githubclientdemo.fragments.stars.StarsFragment;

/**
 * @author renhui
 * @date 16-9-22
 * @desc 主activity
 */

public class MainActivity extends AppCompatActivity implements MainContracts.View, DrawerLayout.DrawerListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar        mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout   mDrawerLayout;
    @BindView(R.id.avatar)
    ImageView      mAvatar;
    @BindView(R.id.user_account)
    TextView       mUserAccount;
    @BindView(R.id.user_org)
    TextView       mUserOrg;
    @BindView(R.id.user_position)
    TextView       mUserPosition;
    @BindView(R.id.user_join_date)
    TextView       mUserJoinDate;
    @BindView(R.id.item_menu_overview)
    RelativeLayout mItemMenuOverview;
    @BindView(R.id.item_menu_repositories)
    RelativeLayout mItemMenuRepositories;
    @BindView(R.id.item_menu_stars)
    RelativeLayout mItemMenuStars;
    @BindView(R.id.item_menu_followers)
    RelativeLayout mItemMenuFollowers;
    @BindView(R.id.item_menu_following)
    RelativeLayout mItemMenuFollowing;
    @BindView(R.id.status_item_menu_overview)
    ImageView      mStatusItemMenuOverview;
    @BindView(R.id.status_item_menu_repositories)
    ImageView      mStatusItemMenuRepositories;
    @BindView(R.id.status_item_menu_stars)
    ImageView      mStatusItemMenuStars;
    @BindView(R.id.status_item_menu_followers)
    ImageView      mStatusItemMenuFollowers;
    @BindView(R.id.status_item_menu_following)
    ImageView      mStatusItemMenuFollowing;
    @BindView(R.id.fragment_container)
    LinearLayout   mFragmentContainer;

    // custom
    private MainContracts.Presenter mPresenter;

    // view data
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
        ButterKnife.bind(this);
        initViews();
        setPresenter(new MainPresenter(this));
    }

    @Override
    public void initViews() {
        setUpToolbar();
        initData();
        setupFragments();
        mStatusItemMenuOverview.setVisibility(View.VISIBLE);
        mItemMenuOverview.setSelected(true);
    }

    @Override
    public void onViewUpdate(Throwable throwable) {

    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyDrawerListener actionBarDrawerToggle = new MyDrawerListener(this, mDrawerLayout, R.string.open_string, R.string.close_string);
        actionBarDrawerToggle.setDrawerListener(this);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void initData() {
        setToolbarData();
    }

    private void setToolbarData() {
        if (mUserInfo != null) {
            setTitle(String.format(getString(R.string.main_title_suffix), mUserInfo.name));
            mToolbar.setSubtitle(String.format(getString(R.string.main_sub_title_suffix), mUserInfo.name,
                String.valueOf(mUserInfo.id)));
            setUpUserInfo();
        }
    }

    private void setUpUserInfo() {
        Picasso.with(this)
               .load(mUserInfo.avatarUrl)
               .placeholder(R.drawable.ic_account_box_white_48dp)
               .error(R.drawable.ic_account_box_white_48dp)
               .into(mAvatar);
        mUserAccount.setText(mUserInfo.login);
        mUserOrg.setText(mUserInfo.company);
        mUserPosition.setText(mUserInfo.location);
        mUserJoinDate.setText(mUserInfo.createdAt);
    }

    @Override
    public void setupFragments() {
        FragmentTransaction transaction =
            getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,
            OverViewFragment.newInstance("title", "subject"), OverViewFragment.class
                .getSimpleName());
        transaction.add(R.id.fragment_container,
            RepositoriesFragment.newInstance(mUserInfo.login, "subject"), RepositoriesFragment.class
                .getSimpleName());
        transaction.add(R.id.fragment_container,
            StarsFragment.newInstance(mUserInfo.login, "subject"), StarsFragment.class
                .getSimpleName());
        transaction.add(R.id.fragment_container,
            FollowersFragment.newInstance(mUserInfo.login, "subject"), FollowersFragment.class
                .getSimpleName());
        transaction.add(R.id.fragment_container,
            FollowingFragment.newInstance(mUserInfo.login, "subject"), FollowingFragment.class
                .getSimpleName());
        transaction.commit();
    }


    @OnClick({R.id.item_menu_overview, R.id.item_menu_repositories, R.id.item_menu_stars, R.id.item_menu_followers, R.id
        .item_menu_following})
    public void whenClick(View view) {
        int i = view.getId();
        if (i == R.id.item_menu_overview || i == R.id.item_menu_repositories || i == R.id.item_menu_stars || i == R.id
            .item_menu_followers || i == R.id.item_menu_following) {
            navigateItemSelected(i);
        }
    }

    private void navigateItemSelected(int i) {
        mStatusItemMenuOverview.setVisibility(View.INVISIBLE);
        mItemMenuOverview.setSelected(false);
        mStatusItemMenuRepositories.setVisibility(View.INVISIBLE);
        mItemMenuRepositories.setSelected(false);
        mStatusItemMenuStars.setVisibility(View.INVISIBLE);
        mItemMenuStars.setSelected(false);
        mStatusItemMenuFollowers.setVisibility(View.INVISIBLE);
        mItemMenuFollowers.setSelected(false);
        mStatusItemMenuFollowing.setVisibility(View.INVISIBLE);
        mItemMenuFollowing.setSelected(false);
        if (i == R.id.item_menu_overview) {
            mStatusItemMenuOverview.setVisibility(View.VISIBLE);
            mItemMenuOverview.setSelected(true);
            navigateToFragment(OverViewFragment.class.getSimpleName());
        } else if (i == R.id.item_menu_repositories) {
            mStatusItemMenuRepositories.setVisibility(View.VISIBLE);
            mItemMenuRepositories.setSelected(true);
            navigateToFragment(RepositoriesFragment.class.getSimpleName());
        } else if (i == R.id.item_menu_stars) {
            mStatusItemMenuStars.setVisibility(View.VISIBLE);
            mItemMenuStars.setSelected(true);
            navigateToFragment(StarsFragment.class.getSimpleName());
        } else if (i == R.id.item_menu_followers) {
            mStatusItemMenuFollowers.setVisibility(View.VISIBLE);
            mItemMenuFollowers.setSelected(true);
            navigateToFragment(FollowersFragment.class.getSimpleName());
        } else if (i == R.id.item_menu_following) {
            mStatusItemMenuFollowing.setVisibility(View.VISIBLE);
            mItemMenuFollowing.setSelected(true);
            navigateToFragment(FollowingFragment.class.getSimpleName());
        }
    }

    @Override
    public void navigateToFragment(String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment            toShow      = getSupportFragmentManager().findFragmentByTag(tag);
        List<Fragment>      toHideList  = new ArrayList<>(getSupportFragmentManager().getFragments());
        if (toShow == null) {
            return;
        } else if (toHideList.contains(toShow)) {
            toHideList.remove(toShow);
            transaction.show(toShow);
        }
        if (!toHideList.isEmpty()) {
            for (Fragment fragment : toHideList) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void updateOverView(Integer tabIndex) {
        Log.d(TAG, "updateOverView tabIndex : " + tabIndex);
        Fragment overViewFragment = getSupportFragmentManager().findFragmentByTag(OverViewFragment.class
            .getSimpleName());
        OverViewContacts.View overView          = (OverViewContacts.View) overViewFragment;
        OverViewPresenter     overViewPresenter = (OverViewPresenter) overView.getPresenter();
        OverViewParams        params            = new OverViewParams("", "");
        params.index = String.valueOf(tabIndex);
        switch (tabIndex) {
            case 1:
                params.tabName = "followers";
                break;
            case 2:
                params.tabName = "followings";
                break;
            case 3:
                params.tabName = "starred";
                break;
            case 4:
                params.tabName = "repos";
                break;
            default:
                break;
        }
        overViewPresenter.getMutableRepository()
                         .accept(params);
    }

    @Override
    public void setPresenter(MainContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public MainContracts.Presenter getPresenter() {
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
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)
                ) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        //TODO: when side menu slide out
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        //TODO: after side menu slide out tobe opened state
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        //TODO: after side menu slide back tobe closed state
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        //TODO: when drawer layout state changed
    }
}