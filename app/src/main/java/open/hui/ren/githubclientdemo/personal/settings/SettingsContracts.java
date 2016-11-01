package open.hui.ren.githubclientdemo.personal.settings;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseView;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.stars.StarsContracts;

/**
 * @author renhui
 * @date 16-11-1
 * @desc open.hui.ren.githubclientdemo.personal.settings
 */

public interface SettingsContracts {
    interface View extends BaseView<StarsContracts.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }

    interface Persistence extends BasePersistence<UserInfo> {

    }
}
