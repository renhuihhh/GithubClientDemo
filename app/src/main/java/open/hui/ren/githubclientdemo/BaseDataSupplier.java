package open.hui.ren.githubclientdemo;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

/**
 * @author renhui
 * @date 16-10-31
 * @desc open.hui.ren.githubclientdemo
 */

public abstract class BaseDataSupplier<T> implements Supplier<Result<T>>, Updatable {
    protected BaseView mBaseView;

    public abstract BaseDataSupplier<T> inView(BaseView view);
}
