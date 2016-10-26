package open.hui.ren.githubclientdemo.fragments.overview;

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

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.apiservices.params.OverViewParams;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView模块的presenter
 */

public class OverViewPresenter implements OverViewContacts.Presenter, Updatable, Receiver<Integer> {
    private static final String TAG = "StarsPresenter";

    private OverViewContacts.View mView;
    private Context               mContext;
    private OverViewSupplier      mOverViewSupplier;

    // for agera
    private ExecutorService                   networkExecutor;
    private Executor                          uiExecutor;
    private MutableRepository<OverViewParams> mMutableRepository;//上层事件驱动入口
    private Repository<Result<Integer>>       mLoadDataRepository;//数据拉取入口

    public OverViewPresenter(OverViewContacts.View view) {
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
        mMutableRepository = Repositories.mutableRepository(new OverViewParams("", ""));
        mOverViewSupplier = new OverViewSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<Integer>absent())
                        .observe(mMutableRepository)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(mOverViewSupplier)
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
        return mOverViewSupplier;
    }

    @Override
    public BasePersistence getPersistence() {
        return null;
    }

    @Override
    public void end() {

    }

    @Override
    public void accept(@NonNull Integer value) {
        Log.d(TAG, "accept...");
        //TODO: 数据流继续向下
    }

    @Override
    public void update() {
        Log.d(TAG, "update...");
        final Result<Integer> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            switch (result.get()) {
                case 1:
                    mView.onFollowersUpdate(mOverViewSupplier.getFollowers());
                    break;
                case 2:
                    mView.onFollowingsUpdate(mOverViewSupplier.getFollowings());
                    break;
                case 3:
                    mView.onStarredUpdate(mOverViewSupplier.getStarred());
                    break;
                case 4:
                    mView.onRepoUpdate(mOverViewSupplier.getRepos());
                    break;
                default:
                    break;
            }
        } else {
            uiExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mView.onRefreshFailed(result.getFailure());
                }
            });
        }
    }

    @NonNull
    private Function<Integer, Result<Integer>> getTransferFunction() {
        return new Function<Integer,
            Result<Integer>>() {
            @NonNull
            @Override
            public Result<Integer> apply(@NonNull Integer input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<Integer>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<Integer>>() {
            @NonNull
            @Override
            public Result<Integer> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                uiExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mView.onRefreshFailed(error);
                    }
                });
                return Result.absent();
            }
        };
    }


    public MutableRepository<OverViewParams> getMutableRepository() {
        return mMutableRepository;
    }
}
