package open.hui.ren.githubclientdemo.main.tabs.overview.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.agera.Function;
import com.google.android.agera.MutableRepository;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.RepositoryConfig;
import com.google.android.agera.Result;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import open.hui.ren.githubclientdemo.BaseAdapterHelper;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Event;
import open.hui.ren.githubclientdemo.main.tabs.overview.adapter.OverViewEventsAdapter;
import open.hui.ren.githubclientdemo.main.tabs.overview.viewholder.CreateEventViewHolder;
import open.hui.ren.githubclientdemo.main.tabs.overview.viewholder.ForkEventViewHolder;
import open.hui.ren.githubclientdemo.main.tabs.overview.viewholder.PullRequestEventViewHolder;
import open.hui.ren.githubclientdemo.main.tabs.overview.viewholder.PushEventViewHolder;
import open.hui.ren.githubclientdemo.main.tabs.overview.viewholder.WatchEventViewHolder;

import static com.google.android.agera.Result.absent;
import static com.google.android.agera.Result.absentIfNull;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.fragments.overview.helper
 */

public class OverViewEventHelper extends BaseAdapterHelper<Event> {
    private static final String TAG = "CreateEventHelper";

    private RecyclerView.ViewHolder mHolder;
    private OverViewEventsAdapter   mAdapter;
    private int                     mPosition;
    private BaseView                mBaseView;

    //for agera
    private MutableRepository<Event>  mSupplier;//上游数据supplier,主要负责参数输入
    private Repository<Result<Event>> mLoadDataRepository;

    private ExecutorService networkExecutor = Executors.newSingleThreadExecutor();

    public OverViewEventHelper() {
        super();
    }

    @Override
    public BaseAdapterHelper<Event> with(RecyclerView.ViewHolder holder) {
        mHolder = holder;
        return this;
    }

    @Override
    public void load(Event event) {
        mSupplier = Repositories.mutableRepository(event);
        mLoadDataRepository =
            Repositories.repositoryWithInitialValue(Result.<Event>absent())
                        .observe(mSupplier)
                        .onUpdatesPerLoop()
                        .goTo(networkExecutor)
                        .attemptGetFrom(this)
                        .orEnd(getThrowableFunction())
                        .thenTransform(getTransferFunction())
                        .onDeactivation(RepositoryConfig.SEND_INTERRUPT)
                        .compile();
        mLoadDataRepository.addUpdatable(this);
        mSupplier.accept(event);
    }

    @Override
    public BaseAdapterHelper<Event> on(RecyclerView.Adapter adapter) {
        mAdapter = (OverViewEventsAdapter) adapter;
        return this;
    }

    @Override
    public BaseAdapterHelper<Event> indexOf(int position) {
        mPosition = position;
        return this;
    }

    @Override
    public BaseAdapterHelper<Event> inView(BaseView view) {
        mBaseView = view;
        return this;
    }

    @NonNull
    @Override
    public Result<Event> get() {
        return Result.success(mSupplier.get());
    }

    @Override
    public void update() {
        Result<Event> result = mLoadDataRepository.get();
        if (result.succeeded()) {
            Event event = result.get();
            //TODO: view holder update
            int viewType = mAdapter.getItemViewType(mPosition);
            if (viewType == OverViewEventsAdapter.ITEM_VIEW_TYPE_PUSH) {
                updatePushEventViewHolder(event);
            } else if (viewType == OverViewEventsAdapter.ITEM_VIEW_TYPE_WATCH) {
                updateWatchEventViewHolder(event);
            } else if (viewType == OverViewEventsAdapter.ITEM_VIEW_TYPE_PULL_REQUEST) {
                updatePullRequestViewHolder(event);
            } else if (viewType == OverViewEventsAdapter.ITEM_VIEW_TYPE_FORK) {
                updateForkEventViewHolder(event);
            } else if (viewType == OverViewEventsAdapter.ITEM_VIEW_TYPE_CREATE) {
                updateCreateViewHolder(event);
            }
        }
    }

    private void updateCreateViewHolder(Event event) {
        CreateEventViewHolder holder = (CreateEventViewHolder) mHolder;
        holder.eventType.setText(event.type);
        holder.eventRepoName.setText(event.repo.name);
        holder.refName.setText(event.payload.ref);
        holder.refType.setText(event.payload.refType);
        holder.repoDesc.setText(event.repo.description);
        holder.userName.setText(event.actor.login);
        Picasso.with(holder.userAvatar.getContext())
               .load(event.actor.avatarUrl)
               .placeholder(R.drawable.ic_perm_identity_white_24dp)
               .error(R.drawable.ic_perm_identity_white_24dp)
               .into(holder.userAvatar);
    }

    private void updateForkEventViewHolder(Event event) {
        ForkEventViewHolder holder = (ForkEventViewHolder) mHolder;
        holder.eventType.setText(event.type);
        holder.eventRepoName.setText(event.repo.name);
        holder.repoDesc.setText(event.repo.description);
        holder.repoUrl.setText(event.repo.fullName);
        Picasso.with(holder.userAvatar.getContext())
               .load(event.actor.avatarUrl)
               .placeholder(R.drawable.ic_perm_identity_white_24dp)
               .error(R.drawable.ic_perm_identity_white_24dp)
               .into(holder.userAvatar);
        holder.userName.setText(event.actor.login);
    }

    private void updatePullRequestViewHolder(Event event) {
        PullRequestEventViewHolder holder = (PullRequestEventViewHolder) mHolder;
        holder.eventType.setText(event.type);
        holder.eventAction.setText(event.payload.action);
        holder.eventRepoName.setText(event.repo.name);
        holder.userName.setText(event.actor.login);
        Picasso.with(holder.userAvatar.getContext())
               .load(event.actor.avatarUrl)
               .placeholder(R.drawable.ic_perm_identity_white_24dp)
               .error(R.drawable.ic_perm_identity_white_24dp)
               .into(holder.userAvatar);
    }

    private void updateWatchEventViewHolder(Event event) {
        WatchEventViewHolder holder = (WatchEventViewHolder) mHolder;
        holder.eventType.setText(event.type);
        holder.eventRepoName.setText(event.repo.name);
        Picasso.with(holder.userAvatar.getContext())
               .load(event.actor.avatarUrl)
               .placeholder(R.drawable.ic_perm_identity_white_24dp)
               .error(R.drawable.ic_perm_identity_white_24dp)
               .into(holder.userAvatar);
        holder.userName.setText(event.actor.login);
    }

    private void updatePushEventViewHolder(Event event) {
        PushEventViewHolder holder = (PushEventViewHolder) mHolder;
        holder.userName.setText(event.actor.login);
        Picasso.with(holder.userAvatar.getContext())
               .load(event.actor.avatarUrl)
               .placeholder(R.drawable.ic_perm_identity_white_24dp)
               .error(R.drawable.ic_perm_identity_white_24dp)
               .into(holder.userAvatar);
        holder.eventRepoName.setText(event.repo.name);
        holder.eventType.setText(event.type);
        String commitTimes = holder.userAvatar.getContext()
                                              .getString(R.string.event_text_commits);
        commitTimes = String.format(commitTimes, String.valueOf(event.payload
            .commits
            .size()));
        holder.pushCommitMsg.setText(commitTimes);
        holder.repoUrl.setText(event.repo.fullName);
    }

    @NonNull
    private Function<Event, Result<Event>> getTransferFunction() {
        return new Function<Event,
            Result<Event>>() {
            @NonNull
            @Override
            public Result<Event> apply(@NonNull Event input) {
                return absentIfNull(input);
            }
        };
    }

    @NonNull
    private Function<Throwable, Result<Event>> getThrowableFunction() {//处理throwable的异常
        return new Function<Throwable, Result<Event>>() {
            @NonNull
            @Override
            public Result<Event> apply(@NonNull final Throwable error) {
                // check the throwable and do possible explicit error handling here, or recover
                Log.d(TAG, "throwable　exception catching");
                return absent();
            }
        };
    }
}
