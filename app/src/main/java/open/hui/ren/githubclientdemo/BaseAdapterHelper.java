package open.hui.ren.githubclientdemo;

import android.support.v7.widget.RecyclerView;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

/**
 * @author renhui
 * @date 16-10-28
 * @desc 抽象的recycle item与adapter之间的helper
 */

public abstract class BaseAdapterHelper<T> implements Supplier<Result<T>>, Updatable {
    protected RecyclerView.Adapter mAdapter;
    protected int                  mIndex;
    protected BaseView             mBaseView;

    public abstract BaseAdapterHelper<T> with(RecyclerView.ViewHolder holder);

    public abstract void load(T object);

    public abstract BaseAdapterHelper<T> on(RecyclerView.Adapter adapter);

    public abstract BaseAdapterHelper<T> indexOf(int position);

    public abstract BaseAdapterHelper<T> inView(BaseView view);
}
