package open.hui.ren.githubclientdemo.main;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * 自定义drawerlayout状态响应事件
 */
class MyDrawerListener extends ActionBarDrawerToggle {

    private DrawerLayout.DrawerListener mDrawerListener;

    public MyDrawerListener(Activity activity, DrawerLayout drawerLayout,
        @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public MyDrawerListener(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
        @StringRes int openDrawerContentDescRes, @StringRes int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public void setDrawerListener(DrawerLayout.DrawerListener drawerListener) {
        mDrawerListener = drawerListener;
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        if (mDrawerListener != null) {
            mDrawerListener.onDrawerOpened(drawerView);
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (mDrawerListener != null) {
            mDrawerListener.onDrawerClosed(drawerView);
        }
    }


    @Override
    public void onDrawerStateChanged(@BottomSheetBehavior.State int newState) {
        super.onDrawerStateChanged(newState);
        if (mDrawerListener != null) {
            mDrawerListener.onDrawerStateChanged(newState);
        }
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        if (mDrawerListener != null) {
            mDrawerListener.onDrawerSlide(drawerView, slideOffset);
        }
    }
}
