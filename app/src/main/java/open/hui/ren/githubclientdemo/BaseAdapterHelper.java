package open.hui.ren.githubclientdemo;

import android.support.v7.widget.RecyclerView;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;


/**
 * @author renhui
 * @date 16-10-28
 * @desc open.hui.ren.githubclientdemo
 */

public abstract class BaseAdapterHelper<T> implements Supplier<Result<T>>, Updatable {
    protected RecyclerView.Adapter mAdapter;
    protected int                  mIndex;

    public abstract BaseAdapterHelper<T> with(RecyclerView.ViewHolder holder);

    public abstract void load(T object);

    public abstract BaseAdapterHelper<T> on(RecyclerView.Adapter adapter);

    public abstract BaseAdapterHelper<T> indexOf(int position);
}
