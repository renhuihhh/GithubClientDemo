package open.hui.ren.githubclientdemo.personal.settings;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import javax.inject.Inject;

import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Retrofit;

/**
 * @author renhui
 * @date 16-11-1
 * @desc Settings的业务模型
 */

public class SettingsModel extends BaseSupplier<UserInfo> implements SettingsContracts.Persistence {
    private static final String TAG = "SettingsModel";
    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;

    private MutableRepository<String> mSupplier;//上游数据supplier,主要负责参数输入

    private Context mContext;

    public SettingsModel(SettingsContracts.Presenter presenter, MutableRepository<String> supplier) {
        super(presenter, supplier);
        mPresenter = presenter;
        mSupplier = supplier;
        mContext = mPresenter.getView()
                             .getCtx();
        mPresenter.getView()
                  .getAppContext()
                  .getNetComponent()
                  .inject(this);
    }

    @NonNull
    @Override
    public Result<UserInfo> get() {
        return null;
    }

    @Override
    public void saveData(UserInfo data) {

    }

    @Override
    public void deleteData(UserInfo data) {

    }

    @Override
    public UserInfo retrieveData() {
        return null;
    }

    @Override
    public Result<UserInfo> loadData() {
        return null;
    }

    @Override
    public void doLogoOut() {
        Log.d(TAG, "doLogoOut");
        mPreferenceService.setBasicCredential("");
        ((SettingsContracts.Presenter) mPresenter).afterLogout();
    }
}
