package open.hui.ren.githubclientdemo.main.tabs.repositories;

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
 * @desc Repositories模块的契约类
 */

public interface RepositoriesContracts {
    interface View extends BaseView<Presenter>, Communicator.SubViewBehaviour {

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
