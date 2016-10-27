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
 * @date 16-10-26
 * @desc PopularRepo的repo的列表viewHolder
 */

public class PopularRepoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.repo_title)
    public TextView  tv_title;
    @BindView(R.id.repo_desc)
    public TextView  tv_desc;
    @BindView(R.id.icon_star)
    public ImageView img_star;
    @BindView(R.id.icon_radio)
    public ImageView img_radio;

    public PopularRepoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
