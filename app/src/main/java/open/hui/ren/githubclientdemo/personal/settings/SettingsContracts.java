package open.hui.ren.githubclientdemo.personal.settings;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;

/**
 * @author renhui
 * @date 16-11-1
 * @desc Settings的契约接口
 */

public interface SettingsContracts {
    interface View extends BaseView<SettingsContracts.Presenter> {

        void jump2LoginPage();

        void logout();
    }

    interface Presenter extends BasePresenter {
        void logOut();

        void afterLogout();
    }

    interface Persistence extends BasePersistence<UserInfo> {
        void doLogoOut();
    }
}
