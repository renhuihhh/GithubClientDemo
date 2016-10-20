package open.hui.ren.githubclientdemo.main;

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

import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;

import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-9
 * @desc open.hui.ren.githubclientdemo.main
 */

public class MainPresenter implements MainContracts.Presenter,Updatable,Receiver<UserInfo> {

    private static final String TAG = "MainPresenter";

    private MainContracts.View mView;
    private Context            mContext;

    // for agera
    private ExecutorService              networkExecutor;
    private     Executor                     uiExecutor;
    private     MutableRepository<String>    mMutableRepository;//上层事件驱动入口
    private     Repository<Result<UserInfo>> mLoadDataRepository;//数据拉取入口

    private MainSupplier mMainSupplier;

    public MainPresenter(MainContracts.View view) {
        mView = view;
        mContext = mView.getCtx();
    }

    @Override
    public void start() {
        Log.d(TAG, "start...");
        setUpAgera();
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

    private void setUpAgera(){
        networkExecutor = Executors.newSingleThreadExecutor();
        uiExecutor = UiThreadExecutor.newUiThreadExecutor();
        mMutableRepository = Repositories.mutableRepository("");
        mMainSupplier = new MainSupplier(this, mMutableRepository);
        mLoadDataRepository =   Repositories.repositoryWithInitialValue(Result.<UserInfo>absent())
            .observe(mMutableRepository)
            .onUpdatesPerLoop()
            .goTo(networkExecutor)
            .attemptGetFrom(mMainSupplier)
            .orEnd(getThrowableFunction())
            .thenTransform(getTransferFunction())
            .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
            .compile();
    }

    @Override
    public BaseView getView() {
        return mView;
    }

    @Override
    public void accept(@NonNull UserInfo value) {

    }

    @Override
    public void update() {

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
                uiExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mView.onViewUpdate(error);
                    }
                });
                return Result.absent();
            }
        };
    }

    @Override
    public BaseSupplier getSupplier() {
        return null;
    }

    @Override
    public MainContracts.Persistence getPersistence() {
        return null;
    }
}
