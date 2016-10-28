package open.hui.ren.githubclientdemo.fragments.repositories.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import open.hui.ren.githubclientdemo.BaseAdapterHelper;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.fragments.repositories.viewholder.RepositoriesViewHolder;

import static com.google.android.agera.Result.absent;
import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-28
 * @desc open.hui.ren.githubclientdemo.fragments.following.helper
 */

public class RepoAdapterHelper extends BaseAdapterHelper<Repo> {
    private static final String TAG = "FollowingAdapterHelper";
    //for agera
    private MutableRepository<Repo>  mSupplier;//上游数据supplier,主要负责参数输入
    private RepositoriesViewHolder   mRepositoriesViewHolder;
    private Repository<Result<Repo>> mLoadDataRepository;

    private ExecutorService networkExecutor = Executors.newSingleThreadExecutor();

    public RepoAdapterHelper() {
        super();
    }

    @Override
    public BaseAdapterHelper<Repo> with(RecyclerView.ViewHolder holder) {
        mRepositoriesViewHolder = (RepositoriesViewHolder) holder;
        return this;
    }

    @Override
    public void load(Repo repo) {
        mSupplier = Repositories.mutableRepository(repo);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<Repo>absent())
                        .observe(mSupplier)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(this)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
        mLoadDataRepository.addUpdatable(this);
        mSupplier.accept(repo);
    }

    @NonNull
    @Override
    public Result<Repo> get() {
        return Result.success(mSupplier.get());
    }

    @Override
    public void update() {
        Result<Repo> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            Repo repo = result.get();
            mRepositoriesViewHolder.tv_title
                .setText(repo.name);
            mRepositoriesViewHolder.tv_desc
                .setText(repo.description);
        }
    }

    @NonNull
    private Function<Repo, Result<Repo>> getTransferFunction() {
        return new Function<Repo,
            Result<Repo>>() {
            @NonNull
            @Override
            public Result<Repo> apply(@NonNull Repo input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<Repo>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<Repo>>() {
            @NonNull
            @Override
            public Result<Repo> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                return absent();
            }
        };
    }
}
