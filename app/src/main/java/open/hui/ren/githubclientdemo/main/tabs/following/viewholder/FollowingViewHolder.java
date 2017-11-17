package open.hui.ren.githubclientdemo.main.tabs.following.viewholder;

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
 * @desc Following界面adapter的viewHolder
 */

public class FollowingViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.user_avatar)
    public ImageView userAvatar;
    @BindView(R.id.following_login_name)
    public TextView  userLoginName;
    @BindView(R.id.following_name)
    public TextView  userName;
    @BindView(R.id.user_bio)
    public TextView  userBio;
    @BindView(R.id.user_company_name)
    public TextView  userCompanyName;
    @BindView(R.id.user_location_name)
    public TextView  userLocationName;

    public FollowingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
