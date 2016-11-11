package open.hui.ren.githubclientdemo.main.tabs.followers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.apiservices.FollowersAPIService;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.params.FollowersParams;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Call;
import retrofit2.Retrofit;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseSupplier;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments
 */

public class FollowersSupplier extends BaseSupplier<ArrayList<UserInfo>> implements
    BasePersistence<ArrayList<UserInfo>> {

    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;

    private MutableRepository<FollowersParams> mSupplier;//上游数据supplier,主要负责参数输入
    private Context                            mContext;

    public FollowersSupplier(BasePresenter presenter, MutableRepository supplier) {
        super(presenter, supplier);
        mSupplier = supplier;
        mPresenter = presenter;
        mContext = mPresenter.getView()
                             .getCtx();
        ((MyApplication) mPresenter.getView()
                                   .getAppContext())
            .getComponent()
            .inject(this);
    }

    @NonNull
    @Override
    public Result<ArrayList<UserInfo>> get() {
        return loadData();
    }

    @Override
    public void saveData(ArrayList<UserInfo> data) {

    }

    @Override
    public void deleteData(ArrayList<UserInfo> data) {

    }

    @Override
    public ArrayList<UserInfo> retrieveData() {
        return null;
    }

    @Override
    public Result<ArrayList<UserInfo>> loadData() {
        FollowersParams params = mSupplier.get();
        if (params == null) {
            return Result.failure();
        } else if (TextUtils.isEmpty(params.userName)) {
            return Result.failure(new Throwable("params username is empty"));
        }
        Call<ArrayList<UserInfo>> call;
        call = mRetrofit.create(FollowersAPIService.class)
                        .getUserFollowers(params.userName);
        ArrayList<UserInfo> data;
        try {
            data = call.execute()
                       .body();
            mACache.put(ConstConfig.S_FOLLOWERS, data);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure(e);
        }
        return Result.success(data);
    }
}
