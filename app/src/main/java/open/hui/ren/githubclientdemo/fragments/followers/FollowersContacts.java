package open.hui.ren.githubclientdemo.fragments.followers;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.Communicator;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.followers
 */

public interface FollowersContacts {
    interface View extends BaseView<FollowersContacts.Presenter>, Communicator {
        void onFollowersFetchFailed(Throwable throwable);

        void onFollowersFetchSuccess(ArrayList<UserInfo> followers);

        String hitUserName();
    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
