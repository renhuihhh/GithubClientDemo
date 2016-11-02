package open.hui.ren.githubclientdemo.fragments.repositories;

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
 * @desc Repositories模块的契约类
 */

public interface RepositoriesContracts {
    interface View extends BaseView<RepositoriesContracts.Presenter>, Communicator.SubViewBehaviour {

        String hitUserName();

        void onReposFetchFailed(Throwable error);

        void onReposFetchSuccess(ArrayList<Repo> repos);

        void refreshRepoes();
    }

    interface Presenter extends BasePresenter {
        void refreshRepoes();
    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
