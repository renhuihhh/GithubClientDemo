package open.hui.ren.githubclientdemo.fragments.stars;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.Communicator;

/**
 * @author renhui
 * @date 16-10-25
 * @desc open.hui.ren.githubclientdemo.fragments.stars
 */

public interface StarsContacts {
    interface View extends BaseView<StarsContacts.Presenter>, Communicator {

        String hitUserName();

        void onStarsFetchFailed(Throwable error);

        void onStarsFetchSuccess(ArrayList<Repo> repos);
    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
