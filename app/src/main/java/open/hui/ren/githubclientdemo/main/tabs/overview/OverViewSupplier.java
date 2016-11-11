package open.hui.ren.githubclientdemo.main.tabs.overview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import java.util.ArrayList;

import javax.inject.Inject;

import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.params.OverViewParams;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Retrofit;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseSupplier;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView模块的业务模型，数据的获取、事件的推送、以及有关于数据存储的业务都由这里进行封装
 */

public class OverViewSupplier extends BaseSupplier<OverViewParams> {

    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;

    private MutableRepository<OverViewParams> mSupplier;//上游数据supplier,主要负责参数输入

    private Context mContext;

    public ArrayList<UserInfo> mFollowers;
    public ArrayList<UserInfo> mFollowings;
    public ArrayList<Repo>     mStarred;
    public ArrayList<Repo>     mRepos;

    public OverViewSupplier(BasePresenter presenter, MutableRepository supplier) {
        super(presenter, supplier);
        mPresenter = presenter;
        mSupplier = supplier;
        mContext = mPresenter.getView()
                             .getCtx();
        ((MyApplication) mPresenter.getView()
                                   .getAppContext())
            .getComponent()
            .inject(this);
    }

    @Override
    public Result<OverViewParams> loadData() {
        OverViewParams params = mSupplier.get();
        if (params == null) {
            return Result.failure();
        } else if (TextUtils.isEmpty(params.tabName) || TextUtils.isEmpty(params.index)) {
            return Result.failure(new Throwable("params username is empty"));
        }
        String tab = params.tabName;
        if (tab.equals("followers")) {
            return Result.success(new OverViewParams("followers", "1"));
        } else if (tab.equals("followings")) {
            return Result.success(new OverViewParams("followings", "2"));
        } else if (tab.equals("starred")) {
            return Result.success(new OverViewParams("starred", "3"));
        } else if (tab.equals("repos")) {
            return Result.success(new OverViewParams("repos", "4"));
        } else if (tab.equals("events")) {
            return Result.success(new OverViewParams("events", "9"));
        }
        return Result.failure();
    }

    @NonNull
    @Override
    public Result<OverViewParams> get() {
        return loadData();
    }

    public ArrayList<UserInfo> getFollowers() {
        Object object = mACache.getAsObject(ConstConfig.S_FOLLOWERS);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<UserInfo>) object;
        }
    }

    public ArrayList<UserInfo> getFollowings() {
        Object object = mACache.getAsObject(ConstConfig.S_FOLLOWINGS);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<UserInfo>) object;
        }
    }

    public ArrayList<Repo> getStarred() {
        Object object = mACache.getAsObject(ConstConfig.S_STARRED);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<Repo>) object;
        }
    }

    public ArrayList<Repo> getRepos() {
        Object object = mACache.getAsObject(ConstConfig.S_REPOES);
        if (object == null) {
            return new ArrayList<>();
        } else {
            return (ArrayList<Repo>) object;
        }
    }
}
