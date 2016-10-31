package open.hui.ren.githubclientdemo;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

/**
 * @author renhui
 * @date 16-10-31
 * @desc 综合型“事件监听”,“数据获取”的supplier，主要用来适应"独立无界面"的此种业务场景
 */

public abstract class BaseCompactSupplier<T> implements Supplier<Result<T>>, Updatable {
    protected BaseView mBaseView;

    public abstract BaseCompactSupplier<T> inView(BaseView view);
}
