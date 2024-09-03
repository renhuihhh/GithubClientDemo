package open.hui.ren.githubclientdemo.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import open.hui.ren.githubclientdemo.R;

/**
 * @author renhui
 * @date 16-10-26
 * @desc recycleView的item之间的边距修饰
 */

public class MarginDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MarginDecoration(Context context) {
        margin = context.getResources()
                        .getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    public void getItemOffsets(
        Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(margin, margin, margin, margin);
    }
}