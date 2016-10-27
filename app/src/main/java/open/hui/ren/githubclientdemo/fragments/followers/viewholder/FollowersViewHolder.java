package open.hui.ren.githubclientdemo.fragments.followers.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.R;

/**
 * @author renhui
 * @date 16-10-27
 * @desc open.hui.ren.githubclientdemo.fragments.followers.viewholder
 */

public class FollowersViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.user_avatar)
    public ImageView userAvatar;
    @BindView(R.id.follower_login_name)
    public TextView  userLoginName;
    @BindView(R.id.follower_name)
    public TextView  userName;
    @BindView(R.id.user_bio)
    public TextView  userBio;
    @BindView(R.id.user_company_name)
    public TextView  userCompanyName;
    @BindView(R.id.user_location_name)
    public TextView  userLocationName;

    public FollowersViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
