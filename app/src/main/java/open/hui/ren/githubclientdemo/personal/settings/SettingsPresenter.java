package open.hui.ren.githubclientdemo.personal.settings;

import android.content.Context;
import android.util.Log;

import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BaseSupplier;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-11-1
 * @desc Settings的presenter
 */

public class SettingsPresenter implements SettingsContracts.Presenter {
    private static final String TAG = "SettingsPresenter";

    private SettingsContracts.View mView;
    private Context                mContext;

    private SettingsModel mSettingsModel;

    public SettingsPresenter(SettingsContracts.View view) {
        mView = view;
        mContext = mView.getCtx();
    }

    @Override
    public void start() {
        mSettingsModel = new SettingsModel(this, null);
    }

    @Override
    public void resume() {
        Log.d(TAG, "resume");
    }

    @Override
    public void pause() {
        Log.d(TAG, "pause");
    }

    @Override
    public BaseView getView() {
        return mView;
    }

    @Override
    public BaseSupplier getSupplier() {
        return mSettingsModel;
    }

    @Override
    public BasePersistence getPersistence() {
        return mSettingsModel;
    }

    @Override
    public void end() {
        Log.d(TAG, "end");
    }

    @Override
    public void logOut() {
        Log.d(TAG, "logOut");
        mSettingsModel.doLogoOut();
    }

    @Override
    public void afterLogout() {
        Log.d(TAG, "afterLogout");
       mView.jump2LoginPage();
    }
}
