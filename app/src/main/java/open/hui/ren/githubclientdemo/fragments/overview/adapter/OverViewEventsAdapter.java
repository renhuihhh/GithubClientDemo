package open.hui.ren.githubclientdemo.fragments.overview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Event;
import open.hui.ren.githubclientdemo.fragments.overview.helper.OverViewEventHelper;
import open.hui.ren.githubclientdemo.fragments.overview.viewholder.CreateEventViewHolder;
import open.hui.ren.githubclientdemo.fragments.overview.viewholder.ForkEventViewHolder;
import open.hui.ren.githubclientdemo.fragments.overview.viewholder.PullRequestEventViewHolder;
import open.hui.ren.githubclientdemo.fragments.overview.viewholder.PushEventViewHolder;
import open.hui.ren.githubclientdemo.fragments.overview.viewholder.WatchEventViewHolder;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.fragments.overview.adapter
 */

public class OverViewEventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_VIEW_TYPE_CREATE       = 1;
    public static final int ITEM_VIEW_TYPE_WATCH        = 2;
    public static final int ITEM_VIEW_TYPE_FORK         = 3;
    public static final int ITEM_VIEW_TYPE_PUSH         = 4;
    public static final int ITEM_VIEW_TYPE_PULL_REQUEST = 5;

    private ArrayList<Event> mEventArrayList;

    public OverViewEventsAdapter(ArrayList<Event> eventArrayList) {
        mEventArrayList = eventArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_CREATE) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_overview_create_event, parent, false);
            return new CreateEventViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_FORK) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_overview_fork_event, parent, false);
            return new ForkEventViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_PULL_REQUEST) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_overview_pull_request_event, parent, false);
            return new PullRequestEventViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_WATCH) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_overview_watch_event, parent, false);
            return new WatchEventViewHolder(view);
        } else if (viewType == ITEM_VIEW_TYPE_PUSH) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.item_overview_push_event, parent, false);
            return new PushEventViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mEventArrayList.isEmpty()) {
            return;
        } else if (holder != null) {
            new OverViewEventHelper().with(holder)
                                     .indexOf(position)
                                     .on(this)
                                     .load(mEventArrayList.get(position));
        }
    }

    public void updateAll(ArrayList<Event> data) {
        mEventArrayList.clear();
        mEventArrayList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEventArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Event event = mEventArrayList.get(position);
        if (event != null) {
            if ("CreateEvent".equals(event.type)) {
                return ITEM_VIEW_TYPE_CREATE;
            } else if ("WatchEvent".equals(event.type)) {
                return ITEM_VIEW_TYPE_WATCH;
            } else if ("ForkEvent".equals(event.type)) {
                return ITEM_VIEW_TYPE_FORK;
            } else if ("PushEvent".equals(event.type)) {
                return ITEM_VIEW_TYPE_PUSH;
            } else if ("PullRequestEvent".equals(event.type)) {
                return ITEM_VIEW_TYPE_PULL_REQUEST;
            } else {
                return ITEM_VIEW_TYPE_WATCH;
            }
        }
        return super.getItemViewType(position);
    }
}
