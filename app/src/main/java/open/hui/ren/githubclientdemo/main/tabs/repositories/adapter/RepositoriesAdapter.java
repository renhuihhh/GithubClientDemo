package open.hui.ren.githubclientdemo.main.tabs.repositories.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.main.tabs.repositories.helper.RepoAdapterHelper;
import open.hui.ren.githubclientdemo.main.tabs.repositories.viewholder.RepositoriesViewHolder;

/**
 * @author renhui
 * @date 16-10-26
 * @desc Repositories的repo的列表adapter
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesViewHolder> {
    private static final int ITEM_VIEW_TYPE_NORMAL = 0;
    private ArrayList<Repo> mRepoArrayList;

    public RepositoriesAdapter(ArrayList<Repo> repos) {
        mRepoArrayList = repos;
    }

    @Override
    public RepositoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(
                                     R.layout.item_repo, parent, false);
        }
        return new RepositoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoriesViewHolder holder, int position) {
        if (mRepoArrayList.isEmpty()) {
            return;
        } else if (holder != null) {
            new RepoAdapterHelper().with(holder)
                                   .on(this)
                                   .indexOf(position)
                                   .load(mRepoArrayList.get(position));
        }
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
