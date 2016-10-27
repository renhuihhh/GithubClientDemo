package open.hui.ren.githubclientdemo.fragments.following.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import open.hui.ren.githubclientdemo.R;

/**
 * @author renhui
 * @date 16-10-27
 * @desc open.hui.ren.githubclientdemo.fragments.following.viewholder
 */

public class FollowingViewHolder extends RecyclerView.ViewHolder {
    public ImageView userAvatar;
    public  TextView  userLoginName;
    public  TextView  userName;
    public  TextView  userBio;
    public  TextView  userCompanyName;
    public  TextView  userLocationName;

    public FollowingViewHolder(View itemView) {
        super(itemView);
        userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
        userLoginName = (TextView) itemView.findViewById(R.id.following_login_name);
        userName = (TextView) itemView.findViewById(R.id.following_name);
        userBio = (TextView) itemView.findViewById(R.id.user_bio);
        userCompanyName = (TextView) itemView.findViewById(R.id.user_company_name);
        userLocationName = (TextView) itemView.findViewById(R.id.user_location_name);
    }
}
