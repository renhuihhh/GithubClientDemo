package open.hui.ren.githubclientdemo.fragments.overview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.fragments.overview.helper.PopularRepoAdapterHelper;
import open.hui.ren.githubclientdemo.fragments.overview.viewholder.PopularRepoViewHolder;

/**
 * @author renhui
 * @date 16-10-26
 * @desc PopularRepo的repo的列表adapter
 */

public class PopularRepoAdapter extends RecyclerView.Adapter<PopularRepoViewHolder> {
    private static final int ITEM_VIEW_TYPE_NORMAL = 0;

    private ArrayList<Repo> mRepoArrayList;

    public PopularRepoAdapter(ArrayList<Repo> repos) {
        mRepoArrayList = repos;
    }

    @Override
    public PopularRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(
                                     R.layout.item_popular_repo, parent, false);
        }
        return new PopularRepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PopularRepoViewHolder holder, int position) {
        if (mRepoArrayList.isEmpty()) {
            return;
        } else if (holder != null) {
            new PopularRepoAdapterHelper().with(holder)
                                          .on(this)
                                          .indexOf(position)
                                          .load(mRepoArrayList.get(position));
        }
    }

    public void addItems(ArrayList<Repo> data) {
        mRepoArrayList.addAll(mRepoArrayList.size(), data);
        notifyItemRangeInserted(mRepoArrayList.size(), data.size());
    }

    public void updateAll(ArrayList<Repo> data) {
        mRepoArrayList.clear();
        mRepoArrayList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRepoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_NORMAL;
    }
}
