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
 * @desc 根据mvp的设计目标， presenter作为view、model之间的middle man的角色代理了view的行为和
 * model的业务的调用，model层的作为事件的分发者、数据的提供者必须期待presenter层作为观察者的身份而存在，因此presenter的创立
 * 之初完成了对model的一系列函数式的加载，这些加载有些是用来绑定，有些是用来监听，有些是用来获取数据。
 * 总的来说，presenter充当了MVP模式中的“boss”, 随着ui逻辑的爆炸、业务逻辑的扩展，可能presenter会变得更加臃肿和肥胖，对此可
 * 以有MVVM设计模式作为重构方案，但是个人并不倾向于MVVM那种打破代码可测试性、以及过度依赖配置的方式。因此，个人的应对方案是将
 * presenter中的业务模块化到另一层，也就是在mvp的模式之外再给presenter增加一层高度可复用的business，这样business层的服务
 * 可以模块化的随着不同业务模块的需要进行构建，以达到对presenter层的减肥，并且将一些通用的业务模块化
 */

public class MainPresenter implements MainContracts.Presenter, Updatable, Receiver<UserInfo> {

    private static final String TAG = "MainPresenter";

    private MainContracts.View mView;
    private Context            mContext;

    // for agera
    private ExecutorService              networkExecutor;
    private Executor                     uiExecutor;
    private MutableRepository<String>    mMutableRepository;//上层事件驱动入口
    private Repository<Result<UserInfo>> mLoadDataRepository;//数据拉取入口

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

    private void setUpAgera() {
        networkExecutor = Executors.newSingleThreadExecutor();
        uiExecutor = UiThreadExecutor.newUiThreadExecutor();
        mMutableRepository = Repositories.mutableRepository("");
        mMainSupplier = new MainSupplier(this, mMutableRepository);
        mLoadDataRepository = Repositories.repositoryWithInitialValue(Result.<UserInfo>absent())
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
