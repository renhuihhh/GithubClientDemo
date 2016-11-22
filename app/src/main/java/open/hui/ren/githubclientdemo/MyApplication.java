package open.hui.ren.githubclientdemo;

import open.hui.ren.githubclientdemo.component.CommonComponent;
import open.hui.ren.githubclientdemo.component.DaggerCommonComponent;
import open.hui.ren.githubclientdemo.modules.AppModule;
import open.hui.ren.githubclientdemo.modules.NetModule;
import tom.hui.ren.core.BaseApplication;

/**
 * @author renhui
 * @date 16-9-22
 * @desc 应用的application
 */

public class MyApplication extends BaseApplication<CommonComponent> {

    private CommonComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
    }

    private void initDaggerComponent() {
        buildCommonComponent();
    }

    @Override
    public void buildCommonComponent() {
        NetModule netModule = new NetModule(this, "https://api.github.com");
        AppModule appModule = new AppModule(this);
        netComponent = DaggerCommonComponent.builder()
                                            .netModule(netModule)
                                            .appModule(appModule)
                                            .build();
        ComponentHolder.setComponent(getComponent());
    }

    @Override
    public CommonComponent getComponent() {
        return netComponent;
    }

    public static class ComponentHolder {
        private static CommonComponent sCommonComponent;

        public static void setComponent(CommonComponent component) {
            sCommonComponent = component;
        }

        public static CommonComponent getCommonComponent() {
            return sCommonComponent;
        }
    }
}
