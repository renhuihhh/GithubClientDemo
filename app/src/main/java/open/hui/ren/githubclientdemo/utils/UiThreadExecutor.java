package open.hui.ren.githubclientdemo.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author renhui
 * @date   16/9/8
 * @desc   uiThread线程
 */
public class UiThreadExecutor implements Executor {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(Runnable command) {
        mHandler.post(command);
    }

    public void shutdown(){
    }

    // how to release it?
    public static Executor newUiThreadExecutor() {
        return new UiThreadExecutor();
    }

    private UiThreadExecutor() {
    }
}
