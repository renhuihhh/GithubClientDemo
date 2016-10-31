package open.hui.ren.githubclientdemo.component;

import javax.inject.Singleton;

import dagger.Component;
import open.hui.ren.githubclientdemo.fragments.events.EventsCenter;
import open.hui.ren.githubclientdemo.fragments.followers.FollowersSupplier;
import open.hui.ren.githubclientdemo.fragments.followers.helper.FollowersAdapterHelper;
import open.hui.ren.githubclientdemo.fragments.following.FollowingsSupplier;
import open.hui.ren.githubclientdemo.fragments.following.helper.FollowingAdapterHelper;
import open.hui.ren.githubclientdemo.fragments.overview.OverViewSupplier;
import open.hui.ren.githubclientdemo.fragments.repositories.RepositoriesSupplier;
import open.hui.ren.githubclientdemo.fragments.stars.StarsSupplier;
import open.hui.ren.githubclientdemo.login.LoginSupplier;
import open.hui.ren.githubclientdemo.modules.AppModule;
import open.hui.ren.githubclientdemo.modules.NetModule;

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
}
