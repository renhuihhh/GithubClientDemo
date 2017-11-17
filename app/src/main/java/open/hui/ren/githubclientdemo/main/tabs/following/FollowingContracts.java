package open.hui.ren.githubclientdemo.main.tabs.following;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.Communicator;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.following
 */

public interface FollowingContracts {
    interface View extends BaseView<Presenter>, Communicator.SubViewBehaviour {

        String hitUserName();

        void onFollowingsFetchFailed(Throwable error);

        void onFollowingFetchSuccess(ArrayList<UserInfo> userInfos);
    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
