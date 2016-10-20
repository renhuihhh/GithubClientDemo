package open.hui.ren.githubclientdemo.main;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;

/**
 * @author renhui
 * @date 16-10-9
 * @desc open.hui.ren.githubclientdemo.main
 */

public class MainContracts {
    interface View extends BaseView<MainContracts.Presenter> {
        void initViews();
        void onViewUpdate(Throwable throwable);
        void setupFragments();
        void navigateToFragment(String tag);
    }
    interface Presenter extends BasePresenter {
        MainContracts.Persistence getPersistence();
    }

    interface Persistence extends BasePersistence<UserInfo> {
    }
}
