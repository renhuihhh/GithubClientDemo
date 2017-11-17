package open.hui.ren.githubclientdemo.login;

import open.hui.ren.githubclientdemo.entities.UserInfo;
import tom.hui.ren.core.BasePersistence;
import tom.hui.ren.core.BasePresenter;
import tom.hui.ren.core.BaseView;

/**
 * @author renhui
 * @date 16-9-26
 * @desc 登陆模块的契约类
 */

public interface LoginContracts {
    interface View extends BaseView<Presenter> {
        void initViews();

        void showLoginDialog();

        void dismissLoginDialog();

        void doLogin();

        void onLoginSuccess(UserInfo userInfo);

        void onLoginFailed(Throwable error);

        void jumpToMain();
    }

    interface Presenter extends BasePresenter {
        void fetchUserInfoByUserName(String userName, String password);

        boolean whetherNeedLog();

        LoginContracts.Persistence getPersistence();
    }

    interface Persistence extends BasePersistence<UserInfo> {
        void saveBasicLoginInfo(String loginAccount, String basicCredential);

        boolean whetherNeedLogin();
    }
}
