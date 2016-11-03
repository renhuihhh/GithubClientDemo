package open.hui.ren.githubclientdemo.main.tabs.overview;

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
import open.hui.ren.githubclientdemo.main.tabs.events.EventsCenter;
import open.hui.ren.githubclientdemo.params.OverViewParams;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView模块的presenter
 */

public class OverViewPresenter implements OverViewContracts.Presenter, Updatable, Receiver<OverViewParams> {
    private static final String TAG = "OverViewPresenter";

    private OverViewContracts.View mView;
    private Context                mContext;
    private OverViewSupplier       mOverViewSupplier;

    // for agera
    private ExecutorService                    networkExecutor;
    private Executor                           uiExecutor;
    private MutableRepository<OverViewParams>  mMutableRepository;//上层事件驱动入口
    private Repository<Result<OverViewParams>> mLoadDataRepository;//数据拉取入口

    public OverViewPresenter(OverViewContracts.View view) {
        mView = view;
        mContext = mView.getCtx();
    }

    @Override
    public void start() {
        Log.d(TAG, "start...");
        setUpAgera();
        refreshEvents();
    }

    @Override
    public void refreshEvents() {
        OverViewParams params = new OverViewParams("events", "9");
        EventsCenter.getInstance()
                    .load(mView, params);
    }

    @Override
    public void refreshPopularRepoes() {
        mView.hitMainView()
             .updateOverView(5);
    }

    private void setUpAgera() {
        networkExecutor = Executors.newSingleThreadExecutor();
        uiExecutor = UiThreadExecutor.newUiThreadExecutor();
        mMutableRepository = Repositories.mutableRepository(new OverViewParams("", ""));
        mOverViewSupplier = new OverViewSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<OverViewParams>absent())
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
        mView.hitMainView()
             .updateOverView(0);
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
    public void accept(@NonNull OverViewParams value) {
        Log.d(TAG, "accept...");
        //数据流继续向下
    }

    @Override
    public void update() {
        final Result<OverViewParams> result = mLoadDataRepository.get();
        Log.d(TAG, "update..." + result.get());
        if (result.succeeded()) {
            switch (Integer.valueOf(result.get().index)) {
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
        }
    }

    @NonNull
    private Function<OverViewParams, Result<OverViewParams>> getTransferFunction() {
        return new Function<OverViewParams,
            Result<OverViewParams>>() {
            @NonNull
            @Override
            public Result<OverViewParams> apply(@NonNull OverViewParams input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<OverViewParams>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<OverViewParams>>() {
            @NonNull
            @Override
            public Result<OverViewParams> apply(@NonNull final Throwable error) {
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
