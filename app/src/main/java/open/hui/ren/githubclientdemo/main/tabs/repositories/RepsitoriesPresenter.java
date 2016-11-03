package open.hui.ren.githubclientdemo.main.tabs.repositories;

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
import open.hui.ren.githubclientdemo.params.RepoQueryParams;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-25
 * @desc Repositories模块的presenter
 */

public class RepsitoriesPresenter implements RepositoriesContracts.Presenter, Updatable, Receiver<ArrayList<Repo>> {
    private static final String TAG = "RepsitoriesPresenter";

    private RepositoriesContracts.View mView;
    private Context                    mContext;
    private RepositoriesSupplier       mRepositoriesSupplier;

    // for agera
    private ExecutorService                    networkExecutor;
    private Executor                           uiExecutor;
    private MutableRepository<RepoQueryParams> mMutableRepository;//上层事件驱动入口

    public RepsitoriesPresenter(RepositoriesContracts.View view) {
        mView = view;
        mContext = mView.getCtx();
    }

    private Repository<Result<ArrayList<Repo>>> mLoadDataRepository;//数据拉取入口

    @Override
    public void start() {
        Log.d(TAG, "start...");
        setUpAgera();
    }

    private void setUpAgera() {
        networkExecutor = Executors.newSingleThreadExecutor();
        uiExecutor = UiThreadExecutor.newUiThreadExecutor();
        mMutableRepository = Repositories.mutableRepository(new RepoQueryParams(mView.hitUserName(), "all",
            "full_name", "asc"));
        mRepositoriesSupplier = new RepositoriesSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<ArrayList<Repo>>absent())
                        .observe(mMutableRepository)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(mRepositoriesSupplier)
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
        return mRepositoriesSupplier;
    }

    @Override
    public BasePersistence getPersistence() {
        return null;
    }

    @Override
    public void end() {

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
            mView.onReposFetchSuccess(result.get());
            mRepositoriesSupplier.saveData(result.get());
            ((RepositoriesContracts.View) getView()).hitMainView()
                                                    .updateOverView(4);
        }
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
                        mView.onReposFetchFailed(error);
                    }
                });
                return Result.absent();
            }
        };
    }

    @Override
    public void refreshRepoes() {
        mMutableRepository.accept(new RepoQueryParams(mView.hitUserName(), "all",
            "full_name", "asc"));
    }
}
