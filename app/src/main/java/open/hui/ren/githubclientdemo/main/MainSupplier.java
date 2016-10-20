package open.hui.ren.githubclientdemo.main;

import android.support.annotation.NonNull;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.entities.UserInfo;

/**
 * @author renhui
 * @date 16-10-9
 * @desc open.hui.ren.githubclientdemo.main
 */

public class MainSupplier extends BaseSupplier<UserInfo> implements MainContracts.Persistence{

    public MainSupplier(BasePresenter presenter, MutableRepository supplier) {
        super(presenter, supplier);
    }

    @NonNull
    @Override
    public Result<UserInfo> get() {
        return null;
    }

    @Override
    public void saveData(UserInfo data) {

    }

    @Override
    public void deleteData(UserInfo data) {

    }

    @Override
    public UserInfo retrieveData() {
        return null;
    }

    @Override
    public Result<UserInfo> loadData() {
        return null;
    }
}
