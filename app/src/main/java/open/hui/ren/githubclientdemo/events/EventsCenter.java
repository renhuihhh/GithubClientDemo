package open.hui.ren.githubclientdemo.events;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import open.hui.ren.githubclientdemo.BaseDataSupplier;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.apiservices.EventsAPIService;
import open.hui.ren.githubclientdemo.entities.Event;
import open.hui.ren.githubclientdemo.fragments.overview.OverViewContacts;
import open.hui.ren.githubclientdemo.params.OverViewParams;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.google.android.agera.Result.absent;
import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.events
 */

public class EventsCenter extends BaseDataSupplier<ArrayList<Event>> {
    private static final String TAG = "PopRepoAdapterHelper";
    //for agera
    private MutableRepository<OverViewParams>    mSupplier;//上游数据supplier,主要负责参数输入
    private Repository<Result<ArrayList<Event>>> mLoadDataRepository;

    private ExecutorService networkExecutor = Executors.newSingleThreadExecutor();

    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;
    @Inject
    ExecutorService   mNetExecutorService;

    public EventsCenter() {
        super();
    }

    public void load(OverViewParams params) {
        mBaseView.getAppContext()
                 .getNetComponent()
                 .inject(this);
        mSupplier = Repositories.mutableRepository(params);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<ArrayList<Event>>absent())
                        .observe(mSupplier)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(this)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
        mLoadDataRepository.addUpdatable(this);
        mSupplier.accept(params);
    }

    @NonNull
    @Override
    public Result<ArrayList<Event>> get() {
        OverViewParams params = mSupplier.get();
        if (params == null) {
            return Result.failure();
        }
        Call<ArrayList<Event>> call;
        call = mRetrofit.create(EventsAPIService.class)
                        .getEvents();
        ArrayList<Event> data = null;
        try {
            data = call.execute()
                       .body();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success(data);
    }

    @Override
    public void update() {
        Result<ArrayList<Event>> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            ((OverViewContacts.View) mBaseView).onEventsUpdate(result.get());
        }
    }

    @NonNull
    private Function<ArrayList<Event>, Result<ArrayList<Event>>> getTransferFunction() {
        return new Function<ArrayList<Event>,
            Result<ArrayList<Event>>>() {
            @NonNull
            @Override
            public Result<ArrayList<Event>> apply(@NonNull ArrayList<Event> input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<ArrayList<Event>>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<ArrayList<Event>>>() {
            @NonNull
            @Override
            public Result<ArrayList<Event>> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                return absent();
            }
        };
    }

    @Override
    public BaseDataSupplier<ArrayList<Event>> inView(BaseView view) {
        mBaseView = view;
        return this;
    }
}
