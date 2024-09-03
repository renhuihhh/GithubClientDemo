package open.hui.ren.githubclientdemo.login;

import static com.google.android.agera.Result.absentIfNull;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

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

import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.params.UserLoginParams;
import open.hui.ren.githubclientdemo.utils.UiThreadExecutor;
import tom.hui.ren.core.BaseSupplier;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-9-26
 * @desc open.hui.ren.githubclientdemo.login
 */

public class LoginPresenter implements LoginContracts.Presenter, Updatable, Receiver<UserInfo> {

    private static final String TAG = "LoginPresenter";

    private LoginContracts.View mView;
    private Context             mContext;
    private LoginSupplier       mLoginSupplier;

    //for agera
    private ExecutorService                    networkExecutor;
    private Executor                           uiExecutor;
    private MutableRepository<UserLoginParams> mMutableRepository;//上层事件驱动入口

    private Repository<Result<UserInfo>> mLoadDataRepository;//数据拉取入口

    public LoginPresenter(LoginContracts.View view) {
        setView(view);
        mContext = view.getCtx();
    }

    @Override
    public void start() {
        Log.d(TAG, "start...");
        setUpAgera();
        if (!whetherNeedLog()) {
            mView.jumpToMain();
            return;
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
    public void setView(BaseView view) {
        mView = (LoginContracts.View) view;
    }

    @Override
    public void end() {
        Log.d(TAG, "end...");
    }

    private void setUpAgera() {
        networkExecutor = Executors.newSingleThreadExecutor();
        uiExecutor = UiThreadExecutor.newUiThreadExecutor();
        mMutableRepository = Repositories.mutableRepository(new UserLoginParams("", ""));
        mLoginSupplier = new LoginSupplier(this, mMutableRepository);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<UserInfo>absent())
                        .observe(mMutableRepository)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(mLoginSupplier)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
    }

    @Override
    public void fetchUserInfoByUserName(String username, String password) {
        Log.d(TAG, "fetchUserInfoByUserName...");
        UserLoginParams params = new UserLoginParams(username, password);
        mMutableRepository.accept(params);
    }

    @Override
    public boolean whetherNeedLog() {
        return mLoginSupplier.whetherNeedLogin();
    }

    @Override
    public BaseView getView() {
        return mView;
    }

    @Override
    public BaseSupplier getSupplier() {
        return mLoginSupplier;
    }

    @Override
    public LoginContracts.Persistence getPersistence() {
        return mLoginSupplier;
    }

    @Override
    public void accept(@NonNull UserInfo value) {
        Log.d(TAG, "accept...");
        //数据流继续向下
    }

    @Override
    public void update() {
        Log.d(TAG, "update...");
        final Result<UserInfo> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            mView.onLoginSuccess(result.get());
            mLoginSupplier.saveData(result.get());
        } else if (result.failed()){
            mView.onLoginFailed(result.getFailure());
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
                uiExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mView.onLoginFailed(error);
                    }
                });
                return Result.absent();
            }
        };
    }
}
