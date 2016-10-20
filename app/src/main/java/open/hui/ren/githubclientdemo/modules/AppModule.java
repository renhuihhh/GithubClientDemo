package open.hui.ren.githubclientdemo.modules;

import android.app.Application;

import com.baoyz.treasure.Treasure;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.utils.ACache;

/**
 * @author renhui
 * @date 16-9-26
 * @desc open.hui.ren.githubclientdemo.modules
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
}
