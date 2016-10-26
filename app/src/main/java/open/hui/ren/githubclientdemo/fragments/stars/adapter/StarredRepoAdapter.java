package open.hui.ren.githubclientdemo.fragments.stars.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.fragments.stars.viewholder.StarredRepoViewHolder;

/**
 * @author renhui
 * @date 16-10-26
 * @desc open.hui.ren.githubclientdemo.fragments.stars.adapter
 */

public class StarredRepoAdapter extends RecyclerView.Adapter<StarredRepoViewHolder> {
    private static final int ITEM_VIEW_TYPE_NORMAL = 0;
    private ArrayList<Repo> mRepoArrayList;


    public StarredRepoAdapter(ArrayList<Repo> repoArrayList) {
        mRepoArrayList = repoArrayList;
    }

    @Override
    public StarredRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(
                                     R.layout.item_starred_repo, parent, false);
        }
        return new StarredRepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarredRepoViewHolder holder, int position) {
        if (mRepoArrayList.isEmpty()) {
            return;
        } else if (holder != null) {
            Repo repo = mRepoArrayList.get(position);
            holder.tv_title
                .setText(repo.name);
            holder.tv_desc
                .setText(repo.description);
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
