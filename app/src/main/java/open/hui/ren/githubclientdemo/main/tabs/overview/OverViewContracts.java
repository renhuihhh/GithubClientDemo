package open.hui.ren.githubclientdemo.main.tabs.overview;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.Event;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.Communicator;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView模块的契约接口
 */

public interface OverViewContracts {
    interface View extends BaseView<OverViewContracts.Presenter>, Communicator.SubViewBehaviour {

        void onRefreshFailed(Throwable error);

        void onFollowersUpdate(ArrayList<UserInfo> followers);

        void onFollowingsUpdate(ArrayList<UserInfo> followings);

        void onStarredUpdate(ArrayList<Repo> starred);

        void onRepoUpdate(ArrayList<Repo> repos);

        void onEventsUpdate(ArrayList<Event> events);

        void onEventsNotUpdate(Throwable failure);
    }

    interface Presenter extends BasePresenter {
        void refreshEvents();

        void refreshPopularRepoes();
    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
