package open.hui.ren.githubclientdemo.fragments.stars;

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
import open.hui.ren.githubclientdemo.params.StarsParams;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.fragments.following.FollowingContracts;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-25
 * @desc starred模块的presenter
 */

public class StarsPresenter implements StarsContracts.Presenter, FollowingContracts.Presenter, Updatable,
    Receiver<ArrayList<Repo>> {
    private static final String TAG = "StarsPresenter";

    private StarsContracts.View mView;
    private Context             mContext;
    private StarsSupplier       mStarsSupplier;

    // for agera
    private ExecutorService                     networkExecutor;
    private Executor                            uiExecutor;
    private MutableRepository<StarsParams>      mMutableRepository;//上层事件驱动入口
    private Repository<Result<ArrayList<Repo>>> mLoadDataRepository;//数据拉取入口

    public StarsPresenter(StarsContracts.View view) {
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
        mMutableRepository = Repositories.mutableRepository(new StarsParams(mView.hitUserName()));
        mStarsSupplier = new StarsSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<ArrayList<Repo>>absent())
                        .observe(mMutableRepository)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(mStarsSupplier)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
    }

    @Override
    public void accept(@NonNull ArrayList<Repo> value) {
        Log.d(TAG, "accept...");
        //数据流继续向下
    }

    @Override
    public void update() {
        Log.d(TAG, "update...");
        final Result<ArrayList<Repo>> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            mView.onStarsFetchSuccess(result.get());
            mStarsSupplier.saveData(result.get());
//            ((StarsContacts.View) getView()).hitMainView()
//                                            .updateOverView(3);
        } else {
            uiExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    mView.onStarsFetchFailed(result.getFailure());
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
    public BaseView getView() {
        return mView;
    }

    @Override
    public BaseSupplier getSupplier() {
        return mStarsSupplier;
    }

    @Override
    public BasePersistence getPersistence() {
        return mStarsSupplier;
    }

    @Override
    public void end() {

    }


    @NonNull
    private Function<ArrayList<Repo>, Result<ArrayList<Repo>>> getTransferFunction() {
        return new Function<ArrayList<Repo>,
            Result<ArrayList<Repo>>>() {
            @NonNull
            @Override
            public Result<ArrayList<Repo>> apply(@NonNull ArrayList<Repo> input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<ArrayList<Repo>>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<ArrayList<Repo>>>() {
            @NonNull
            @Override
            public Result<ArrayList<Repo>> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                uiExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mView.onStarsFetchFailed(error);
                    }
                });
                return Result.absent();
            }
        };
    }
}
