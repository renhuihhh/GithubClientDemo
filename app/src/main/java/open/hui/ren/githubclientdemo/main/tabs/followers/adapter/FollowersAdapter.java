package open.hui.ren.githubclientdemo.main.tabs.followers.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.followers.FollowersContracts;
import open.hui.ren.githubclientdemo.main.tabs.followers.helper.FollowersAdapterHelper;
import open.hui.ren.githubclientdemo.main.tabs.followers.viewholder.FollowersViewHolder;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-10-27
 * @desc followers的adapter
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersViewHolder> {
    private static final int ITEM_VIEW_TYPE_NORMAL = 0;
    private ArrayList<UserInfo>     mUserInfoArrayList;
    private FollowersContracts.View mView;

    public FollowersAdapter(ArrayList<UserInfo> userInfoArrayList, BaseView view) {
        mUserInfoArrayList = userInfoArrayList;
        mView = (FollowersContracts.View) view;
    }

    @Override
    public FollowersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(
                                     R.layout.item_followers_info, parent, false);
        }
        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FollowersViewHolder holder, int position) {
        if (mUserInfoArrayList.isEmpty()) {
            return;
        } else if (holder != null) {
            new FollowersAdapterHelper().with(holder)
                                        .on(this)
                                        .indexOf(position)
                                        .inView(mView)
                                        .load(mUserInfoArrayList.get(position));
        }
    }

    public void updateAll(ArrayList<UserInfo> data) {
        mUserInfoArrayList.clear();
        mUserInfoArrayList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUserInfoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_NORMAL;
    }
}
