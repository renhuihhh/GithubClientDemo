package open.hui.ren.githubclientdemo;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

/**
 * @author renhui
 * @date 16-8-26
 * @desc 数据提供者的父类
 */
public abstract class BaseSupplier<T> implements Supplier<Result<T>> {
    protected BasePresenter mPresenter;

    public BaseSupplier() {
    }

    public BaseSupplier(BasePresenter presenter, MutableRepository supplier) {
        mPresenter = presenter;
    }

    public abstract Result<T> loadData();
}
