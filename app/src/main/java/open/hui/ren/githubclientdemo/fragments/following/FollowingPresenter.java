package open.hui.ren.githubclientdemo.fragments.following;

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
import open.hui.ren.githubclientdemo.apiservices.params.FollowingsParams;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.following
 */

public class FollowingPresenter implements FollowingContracts.Presenter, Updatable, Receiver<ArrayList<UserInfo>> {
    private static final String TAG = "FollowingPresenter";

    private FollowingContracts.View mView;
    private Context                 mContext;
    private FollowingsSupplier      mFollowingsSupplier;

    // for agera
    private ExecutorService                         networkExecutor;
    private Executor                                uiExecutor;
    private MutableRepository<FollowingsParams>     mMutableRepository;//上层事件驱动入口
    private Repository<Result<ArrayList<UserInfo>>> mLoadDataRepository;//数据拉取入口

    public FollowingPresenter(FollowingContracts.View view) {
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
        mMutableRepository = Repositories.mutableRepository(new FollowingsParams(mView.hitUserName()));
        mFollowingsSupplier = new FollowingsSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<ArrayList<UserInfo>>absent())
                        .observe(mMutableRepository)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(mFollowingsSupplier)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
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
    public BaseView getView() {
        return mView;
    }

    @Override
    public BaseSupplier getSupplier() {
        return mFollowingsSupplier;
    }

    @Override
    public BasePersistence getPersistence() {
        return mFollowingsSupplier;
    }

    @Override
    public void end() {

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
            mView.onFollowingFetchSuccess(result.get());
            mFollowingsSupplier.saveData(result.get());
            ((FollowingContracts.View) getView()).hitMainView()
                                                 .updateOverView(2);
        } else {
            uiExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mView.onFollowingsFetchFailed(result.getFailure());
                }
            });
        }
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
                        mView.onFollowingsFetchFailed(error);
                    }
                });
                return Result.absent();
            }
        };
    }
}
