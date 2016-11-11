package open.hui.ren.githubclientdemo.main.tabs.stars;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.Communicator;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-10-25
 * @desc starred模块的契约接口
 */

public interface StarsContracts {
    interface View extends BaseView<Presenter>, Communicator.SubViewBehaviour {

        String hitUserName();

        void onStarsFetchFailed(Throwable error);

        void onStarsFetchSuccess(ArrayList<Repo> repos);

        void refreshStars();
    }

    interface Presenter extends BasePresenter {
        void refreshStars();
    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
