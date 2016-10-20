package open.hui.ren.githubclientdemo;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

/**
 * @author renhui
 * @date 16-9-11
 * @desc 数据推送者("数据存储"和"数据提交"的父类)
 * 提交数据的数据流其实和获取数据的代码方式一样，不同点在于数据的流向是相反的
 */
public abstract class BasePusher<T> implements Supplier<Result<T>> {
}
