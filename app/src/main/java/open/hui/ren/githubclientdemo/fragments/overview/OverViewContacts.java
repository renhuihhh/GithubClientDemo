package open.hui.ren.githubclientdemo.fragments.overview;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView模块的契约接口
 */

public interface OverViewContacts {
    interface View extends BaseView<OverViewContacts.Presenter> {

        void onRefreshFailed(Throwable error);

        void onFollowersUpdate(ArrayList<UserInfo> followers);

        void onFollowingsUpdate(ArrayList<UserInfo> followings);

        void onStarredUpdate(ArrayList<Repo> starred);

        void onRepoUpdate(ArrayList<Repo> repos);
    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
