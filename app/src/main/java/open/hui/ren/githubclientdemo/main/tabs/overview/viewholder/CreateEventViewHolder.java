package open.hui.ren.githubclientdemo.main.tabs.overview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.R;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo.fragments.overview.viewholder
 */

public class CreateEventViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.event_type)
    public TextView  eventType;
    @BindView(R.id.repo_name)
    public TextView  eventRepoName;
    @BindView(R.id.event_actor_avatar)
    public ImageView userAvatar;
    @BindView(R.id.event_actor_name)
    public TextView  userName;
    @BindView(R.id.event_ref_type)
    public TextView refType;
    @BindView(R.id.event_ref_name)
    public TextView refName;
    @BindView(R.id.repo_desc)
    public TextView repoDesc;

    public CreateEventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
