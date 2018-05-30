package open.hui.ren.githubclientdemo.main.tabs.followers.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.apiservices.UserInfoAPIService;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.followers.viewholder.FollowersViewHolder;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Call;
import retrofit2.Retrofit;
import tom.hui.ren.core.BaseAdapterHelper;
import tom.hui.ren.core.BaseView;

import static com.google.android.agera.Result.absent;
import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-28
 * @desc adapter与viewHolder之间的helper， 灵感来自于adam powell的google IO 2016大会中关于recycleView的分享
 */

public class FollowersAdapterHelper extends BaseAdapterHelper<UserInfo> {
    private static final String TAG = "FollowersItemHelper";
    //for agera
    private MutableRepository<UserInfo>  mSupplier;//上游数据supplier,主要负责参数输入
    private FollowersViewHolder          mFollowersViewHolder;
    private Repository<Result<UserInfo>> mLoadDataRepository;

    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;
    @Inject
    ExecutorService   mNetExecutorService;

    public FollowersAdapterHelper() {
        super();
    }

    @Override
    public BaseAdapterHelper<UserInfo> with(RecyclerView.ViewHolder holder) {
        mFollowersViewHolder = (FollowersViewHolder) holder;
        return this;
    }

    @Override
    public BaseAdapterHelper<UserInfo> on(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        return this;
    }

    @Override
    public BaseAdapterHelper<UserInfo> indexOf(int position) {
        mIndex = position;
        return this;
    }

    @Override
    public BaseAdapterHelper<UserInfo> inView(BaseView view) {
        mBaseView = view;
        return this;
    }

    @Override
    public void load(UserInfo userInfo) {
        ((MyApplication) mBaseView.getAppContext())
            .getComponent()
            .inject(this);
        mSupplier = Repositories.mutableRepository(userInfo);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<UserInfo>absent())
                        .observe(mSupplier)
                        .onUpdatesPerLoop()
                        .goTo(mNetExecutorService)
                        .attemptGetFrom(this)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
        mLoadDataRepository.addUpdatable(this);
        mSupplier.accept(userInfo);
    }

    @NonNull
    @Override
    public Result<UserInfo> get() {
        UserInfo origin = mSupplier.get();
        Call<UserInfo> call = mRetrofit.create(UserInfoAPIService.class)
                                       .getUserInfo(origin.login);
        UserInfo actual = null;
        try {
            actual = call.execute()
                         .body();
        } catch (IOException e) {
            e.printStackTrace();
            Result.success(origin);
        }
        if (actual != null) {
            origin = actual;
        }
        return Result.success(origin);
    }

    @Override
    public void update() {
        Result<UserInfo> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            UserInfo userInfo = result.get();
            Picasso.get()
                   .load(userInfo.avatarUrl)
                   .placeholder(R.drawable.ic_perm_identity_white_24dp)
                   .error(R.drawable.ic_perm_identity_white_24dp)
                   .into(mFollowersViewHolder.userAvatar);
            mFollowersViewHolder.userName.setText(userInfo.name);
            mFollowersViewHolder.userLoginName.setText(userInfo.login);
            if (userInfo.bio != null) {
                mFollowersViewHolder.userBio.setText(userInfo.bio.toString());
            }
            mFollowersViewHolder.userCompanyName.setText(userInfo.company);
            mFollowersViewHolder.userLocationName.setText(userInfo.location);
        }
    }

    @NonNull
    private Function<UserInfo, Result<UserInfo>> getTransferFunction() {
        return new Function<UserInfo,
            Result<UserInfo>>() {
            @NonNull
            @Override
            public Result<UserInfo> apply(@NonNull UserInfo input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<UserInfo>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<UserInfo>>() {
            @NonNull
            @Override
            public Result<UserInfo> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                return absent();
            }
        };
    }
}
