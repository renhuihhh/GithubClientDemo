package open.hui.ren.githubclientdemo.fragments.followers.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.followers.viewholder.FollowersViewHolder;

/**
 * @author renhui
 * @date 16-10-27
 * @desc open.hui.ren.githubclientdemo.fragments.followers.adapter
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersViewHolder> {
    private static final int ITEM_VIEW_TYPE_NORMAL = 0;
    private ArrayList<UserInfo> mUserInfoArrayList;

    public FollowersAdapter(ArrayList<UserInfo> userInfoArrayList) {
        mUserInfoArrayList = userInfoArrayList;
    }

    @Override
    public FollowersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(
                                     R.layout.item_user_info, parent, false);
        }
        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowersViewHolder holder, int position) {
        if (mUserInfoArrayList.isEmpty()) {
            return;
        } else if (holder != null) {
            UserInfo userInfo = mUserInfoArrayList.get(position);
            Picasso.with(holder.userAvatar.getContext())
                   .load(userInfo.avatarUrl)
                   .placeholder(R.drawable.ic_perm_identity_white_24dp)
                   .error(R.drawable.ic_perm_identity_white_24dp)
                   .into(holder.userAvatar);
            holder.userName.setText(userInfo.name);
            holder.userLoginName.setText(userInfo.login);
            //holder.userBio.setText(userInfo.bio.toString());
            holder.userCompanyName.setText(userInfo.company);
            holder.userLocationName.setText(userInfo.location);
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
