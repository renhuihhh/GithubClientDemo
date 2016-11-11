package open.hui.ren.githubclientdemo.personal.settings;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-11-1
 * @desc Settings的契约接口
 */

public interface SettingsContracts {
    interface View extends BaseView<Presenter> {

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
