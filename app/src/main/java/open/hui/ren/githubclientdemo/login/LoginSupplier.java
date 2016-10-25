package open.hui.ren.githubclientdemo.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Credentials;
import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.apiservices.APIServiceNeedToken;
import open.hui.ren.githubclientdemo.apiservices.params.UserLoginParams;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * @author renhui
 * @date 16-10-8
 * @desc open.hui.ren.githubclientdemo.login
 */

public class LoginSupplier extends BaseSupplier<UserInfo> implements LoginContracts.Persistence {
    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;

    private MutableRepository<UserLoginParams> mSupplier;//上游数据supplier,主要负责参数输入
    private Context                            mContext;

    public LoginSupplier(LoginContracts.Presenter presenter, MutableRepository<UserLoginParams> supplier) {
        super();
        mContext = presenter.getView()
                            .getCtx();
        mSupplier = supplier;
        mPresenter = presenter;
        mPresenter.getView()
                  .getAppContext()
                  .getNetComponent()
                  .inject(this);
    }

    @Override
    public Result<UserInfo> loadData() {
        UserLoginParams params = mSupplier.get();
        if (params == null) {
            return Result.failure();
        } else if (TextUtils.isEmpty(params.userName) || TextUtils.isEmpty(params.passWord)) {
            return Result.failure(new Throwable("empty params"));
        }
        String credential = Credentials.basic(params.userName, params.passWord);
        Call<UserInfo> call = mRetrofit
            .create(APIServiceNeedToken.class)
            .getUserInfoByAuthorization(credential);
        UserInfo userInfo;
        try {
            userInfo = call.execute()
                           .body();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure(e);
        }
        if (userInfo == null) {
            return Result.failure();
        } else {
            saveBasicLoginInfo(params.userName, credential);
            return Result.success(userInfo);
        }
    }

    @NonNull
    @Override
    public Result<UserInfo> get() {
        return loadData();
    }

    @Override
    public void saveBasicLoginInfo(String loginAccount, String basicCredential) {
        mPreferenceService.setLoginAccount(loginAccount);
        if (whetherNeedLogin()) {//认证需要重置
            mPreferenceService.setBasicCredential(basicCredential);
            mPresenter.getView()
                      .getAppContext()
                      .buildCommonComponent();
        }
    }

    @Override
    public boolean whetherNeedLogin() {
        return TextUtils.isEmpty(mPreferenceService.getBasicCredential());
    }

    @Override
    public void saveData(UserInfo data) {
        mACache.put(ConstConfig.S_USER_INFO, data);
    }

    @Override
    public void deleteData(UserInfo data) {
        mACache.remove(ConstConfig.S_USER_INFO);
    }

    @Override
    public UserInfo retrieveData() {
        return (UserInfo) mACache.getAsObject(ConstConfig.S_USER_INFO);
    }
}
