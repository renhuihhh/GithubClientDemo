package open.hui.ren.githubclientdemo.fragments.stars.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import open.hui.ren.githubclientdemo.R;

/**
 * @author renhui
 * @date 16-10-26
 * @desc open.hui.ren.githubclientdemo.fragments.stars.viewholder
 */

public class StarredRepoViewHolder extends RecyclerView.ViewHolder {
    public TextView  tv_title;
    public TextView  tv_desc;
    public ImageView img_star;
    public ImageView img_radio;

    public StarredRepoViewHolder(View itemView) {
        super(itemView);
        tv_title = (TextView) itemView.findViewById(R.id.repo_title);
        tv_desc = (TextView) itemView.findViewById(R.id.repo_desc);
        img_star = (ImageView) itemView.findViewById(R.id.icon_star);
        img_radio = (ImageView) itemView.findViewById(R.id.icon_radio);
    }
}
