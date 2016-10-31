package open.hui.ren.githubclientdemo.fragments.overview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.R;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.fragments.overview.viewholder
 */

public class PushEventViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.event_type)
    public TextView  eventType;
    @BindView(R.id.repo_name)
    public TextView  eventRepoName;
    @BindView(R.id.event_actor_avatar)
    public ImageView userAvatar;
    @BindView(R.id.event_actor_name)
    public TextView  userName;
    @BindView(R.id.repo_url)
    public TextView  repoUrl;
    @BindView(R.id.event_push_commit_msg)
    public TextView  pushCommitMsg;

    public PushEventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
