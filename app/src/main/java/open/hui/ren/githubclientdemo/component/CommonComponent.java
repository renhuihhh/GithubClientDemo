package open.hui.ren.githubclientdemo.component;

import javax.inject.Singleton;

import dagger.Component;
import open.hui.ren.githubclientdemo.main.tabs.events.EventsCenter;
import open.hui.ren.githubclientdemo.main.tabs.followers.FollowersSupplier;
import open.hui.ren.githubclientdemo.main.tabs.followers.helper.FollowersAdapterHelper;
import open.hui.ren.githubclientdemo.main.tabs.following.FollowingsSupplier;
import open.hui.ren.githubclientdemo.main.tabs.following.helper.FollowingAdapterHelper;
import open.hui.ren.githubclientdemo.main.tabs.overview.OverViewSupplier;
import open.hui.ren.githubclientdemo.main.tabs.repositories.RepositoriesSupplier;
import open.hui.ren.githubclientdemo.main.tabs.stars.StarsSupplier;
import open.hui.ren.githubclientdemo.login.LoginSupplier;
import open.hui.ren.githubclientdemo.modules.AppModule;
import open.hui.ren.githubclientdemo.modules.NetModule;
import open.hui.ren.githubclientdemo.personal.settings.SettingsModel;

/**
 * @author renhui
 * @date 16-9-22
 * @desc 网络访问相关业务的组件(模组的集合)
 */
@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface CommonComponent {
    void inject(LoginSupplier loginSupplier);
    void inject(RepositoriesSupplier repositoriesSupplier);
    void inject(StarsSupplier starsSupplier);
    void inject(FollowersSupplier followersSupplier);
    void inject(FollowingsSupplier followingsSupplier);
    void inject(OverViewSupplier overViewSupplier);
    void inject(FollowersAdapterHelper followersAdapterHelper);
    void inject(FollowingAdapterHelper followingAdapterHelper);
    void inject(EventsCenter eventsCenter);
    void inject(SettingsModel settingsModel);
}
