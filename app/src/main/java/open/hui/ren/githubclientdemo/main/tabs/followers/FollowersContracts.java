package open.hui.ren.githubclientdemo.main.tabs.followers;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.Communicator;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.followers
 */

public interface FollowersContracts {
    interface View extends BaseView<Presenter>, Communicator.SubViewBehaviour {
        void onFollowersFetchFailed(Throwable throwable);

        void onFollowersFetchSuccess(ArrayList<UserInfo> followers);

        String hitUserName();
    }

    interface Presenter extends BasePresenter {
    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
