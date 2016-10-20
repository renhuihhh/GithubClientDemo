package open.hui.ren.githubclientdemo;

import android.app.Application;

import open.hui.ren.githubclientdemo.component.CommonComponent;
import open.hui.ren.githubclientdemo.component.DaggerCommonComponent;
import open.hui.ren.githubclientdemo.modules.AppModule;
import open.hui.ren.githubclientdemo.modules.NetModule;

/**
 * @author renhui
 * @date 16-9-22
 * @desc 应用的application
 */

public class MyApplication extends Application {

    private CommonComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerComponent();
    }

    private void initDaggerComponent() {
        buildCommonComponent();
    }

    public void buildCommonComponent(){
        NetModule netModule = new NetModule(this, "https://api.github.com");
        AppModule appModule = new AppModule(this);
        netComponent = DaggerCommonComponent.builder()
            .netModule(netModule)
            .appModule(appModule)
            .build();
    }

    public CommonComponent getNetComponent() {
        return netComponent;
    }

    public void setNetComponent(CommonComponent netComponent) {
        this.netComponent = netComponent;
    }
}
