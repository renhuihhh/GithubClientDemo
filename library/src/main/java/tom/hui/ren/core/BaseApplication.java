package tom.hui.ren.core;

import android.app.Application;

/**
 * @author renhui
 * @date 16-11-11
 * @desc 基础抽象的Application
 */

public abstract class BaseApplication<T> extends Application {
    public abstract void buildCommonComponent();

    public abstract T getComponent();
}
