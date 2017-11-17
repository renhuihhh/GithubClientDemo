package open.hui.ren.githubclientdemo.main.tabs.overview.viewholder;

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

public class PullRequestEventViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.event_type)
    public TextView  eventType;
    @BindView(R.id.repo_name)
    public TextView  eventRepoName;
    @BindView(R.id.event_actor_avatar)
    public ImageView userAvatar;
    @BindView(R.id.event_actor_name)
    public TextView  userName;
    @BindView(R.id.event_pull_request_url)
    public TextView  pullRequestUrl;
    @BindView(R.id.event_action)
    public TextView  eventAction;

    public PullRequestEventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
