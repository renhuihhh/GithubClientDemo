package open.hui.ren.githubclientdemo.main.tabs.following;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.Communicator;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.following
 */

public interface FollowingContracts {
    interface View extends BaseView<FollowingContracts.Presenter>, Communicator.SubViewBehaviour {

        String hitUserName();

        void onFollowingsFetchFailed(Throwable error);

        void onFollowingFetchSuccess(ArrayList<UserInfo> userInfos);
    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
