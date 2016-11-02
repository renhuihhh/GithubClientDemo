package open.hui.ren.githubclientdemo.modules;

import android.app.Application;

import com.baoyz.treasure.Treasure;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.utils.ACache;

/**
 * @author renhui
 * @date 16-9-26
 * @desc app基础的DI组件加载
 */

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    PreferenceService provideSimplePreferences(){
        return Treasure.get(mApplication, PreferenceService.class);
    }

    @Singleton
    @Provides
    ACache provideACache(){
        return ACache.get(mApplication);
    }


    @Provides
    @Singleton
    ExecutorService provideExecutorService() {//非UI线程上的线程池
        return Executors.newCachedThreadPool();
    }

}
