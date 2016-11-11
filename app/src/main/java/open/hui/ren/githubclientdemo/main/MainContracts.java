package open.hui.ren.githubclientdemo.main;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.Communicator;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-10-9
 * @desc 主界面的契约接口
 */

public interface MainContracts {
    interface View extends BaseView<Presenter>, Communicator.MainViewBehaviour {
        void initViews();

        void onViewUpdate(Throwable throwable);

        void setupFragments();

        void appendFragments();

        void navigateToFragment(String tag);

        void updateOverView(Integer tabIndex);
    }

    interface Presenter extends BasePresenter {
        MainContracts.Persistence getPersistence();
    }

    interface Persistence extends BasePersistence<UserInfo> {
    }
}
