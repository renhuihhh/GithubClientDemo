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
 * @desc starred模块的契约接口
 */

public interface StarsContracts {
    interface View extends BaseView<StarsContracts.Presenter>, Communicator.SubViewBehaviour {

        String hitUserName();

        void onStarsFetchFailed(Throwable error);

        void onStarsFetchSuccess(ArrayList<Repo> repos);
    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
