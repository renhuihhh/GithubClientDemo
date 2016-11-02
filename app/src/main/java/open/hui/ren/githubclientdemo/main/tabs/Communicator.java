package open.hui.ren.githubclientdemo.main.tabs;

import open.hui.ren.githubclientdemo.main.MainContracts;

/**
 * @author renhui
 * @date 16-10-26
 * @desc 用来让二级fragment得到mainView的接口类，这样的接口往下服务于presenter层，因为二级fragment的业务需要拿到parent View的服务
 */

public interface Communicator {
    interface SubViewBehaviour {
        MainContracts.View hitMainView();
    }

    interface MainViewBehaviour {
    }
}
