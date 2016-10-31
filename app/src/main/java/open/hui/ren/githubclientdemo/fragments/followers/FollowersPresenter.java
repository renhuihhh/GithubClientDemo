package open.hui.ren.githubclientdemo.fragments.followers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Receiver;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.params.FollowersParams;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.followers
 */

public class FollowersPresenter implements FollowersContacts.Presenter, Updatable, Receiver<ArrayList<UserInfo>> {
    private static final String TAG = "FollowersPresenter";

    private FollowersContacts.View mView;
    private Context                mContext;
    private FollowersSupplier      mFollowersSupplier;

    //for agera
    private ExecutorService                         networkExecutor;
    private Executor                                uiExecutor;
    private MutableRepository<FollowersParams>      mMutableRepository;//上层事件驱动入口
    private Repository<Result<ArrayList<UserInfo>>> mLoadDataRepository;//数据拉取入口

    public FollowersPresenter(FollowersContacts.View view) {
        mView = view;
        mContext = mView.getCtx();
    }

    @Override
    public void start() {
        Log.d(TAG, "start...");
        setUpAgera();
    }

    private void setUpAgera() {
        networkExecutor = Executors.newSingleThreadExecutor();
        uiExecutor = UiThreadExecutor.newUiThreadExecutor();
        mMutableRepository = Repositories.mutableRepository(new FollowersParams(mView.hitUserName()));
        mFollowersSupplier = new FollowersSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<ArrayList<UserInfo>>absent())
                        .observe(mMutableRepository)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(mFollowersSupplier)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
    }

    @Override
    public void accept(@NonNull ArrayList<UserInfo> value) {
        Log.d(TAG, "accept...");
        //数据流继续向下
    }

    @Override
    public void update() {
        Log.d(TAG, "update...");
        final Result<ArrayList<UserInfo>> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            mView.onFollowersFetchSuccess(result.get());
            mFollowersSupplier.saveData(result.get());
//            ((FollowersContacts.View) getView()).hitMainView()
//                                                .updateOverView(1);
        } else {
            uiExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mView.onFollowersFetchFailed(result.getFailure());
                }
            });
        }
    }

    @Override
    public void resume() {
        Log.d(TAG, "resume...");
        mLoadDataRepository.addUpdatable(this);
    }

    @Override
    public void pause() {
        Log.d(TAG, "pause...");
        mLoadDataRepository.removeUpdatable(this);
    }

    @Override
    public void end() {
        Log.d(TAG, "end...");
    }

    @Override
    public BaseView getView() {
        return mView;
    }

    @Override
    public BaseSupplier getSupplier() {
        return mFollowersSupplier;
    }

    @Override
    public BasePersistence getPersistence() {
        return null;
    }

    @NonNull
    private Function<ArrayList<UserInfo>, Result<ArrayList<UserInfo>>> getTransferFunction() {
        return new Function<ArrayList<UserInfo>,
            Result<ArrayList<UserInfo>>>() {
            @NonNull
            @Override
            public Result<ArrayList<UserInfo>> apply(@NonNull ArrayList<UserInfo> input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<ArrayList<UserInfo>>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<ArrayList<UserInfo>>>() {
            @NonNull
            @Override
            public Result<ArrayList<UserInfo>> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                uiExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mView.onFollowersFetchFailed(error);
                    }
                });
                return Result.absent();
            }
        };
    }
}
