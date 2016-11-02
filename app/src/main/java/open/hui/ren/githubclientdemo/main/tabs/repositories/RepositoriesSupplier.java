package open.hui.ren.githubclientdemo.main.tabs.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.agera.MutableRepository;
import com.google.android.agera.Result;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import open.hui.ren.githubclientdemo.BasePersistence;
import open.hui.ren.githubclientdemo.BasePresenter;
import open.hui.ren.githubclientdemo.BaseSupplier;
import open.hui.ren.githubclientdemo.ConstConfig;
import open.hui.ren.githubclientdemo.PreferenceService;
import open.hui.ren.githubclientdemo.apiservices.RepositoriesAPIService;
import open.hui.ren.githubclientdemo.params.RepoQueryParams;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.utils.ACache;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.google.android.agera.Result.failure;

/**
 * @author renhui
 * @date 16-10-20
 * @desc Repositories模块的业务模型，数据的获取、事件的推送、以及有关于数据存储的业务都由这里进行封装
 */

public class RepositoriesSupplier extends BaseSupplier<ArrayList<Repo>> implements BasePersistence<ArrayList<Repo>> {
    @Inject
    ACache            mACache;
    @Inject
    Retrofit          mRetrofit;
    @Inject
    PreferenceService mPreferenceService;

    private MutableRepository<RepoQueryParams> mSupplier;//上游数据supplier,主要负责参数输入
    private Context                            mContext;

    public RepositoriesSupplier(BasePresenter presenter, MutableRepository<RepoQueryParams> supplier) {
        mPresenter = presenter;
        mSupplier = supplier;
        mContext = mPresenter.getView()
                             .getCtx();
        mPresenter.getView()
                  .getAppContext()
                  .getNetComponent()
                  .inject(this);
    }

    @NonNull
    @Override
    public Result<ArrayList<Repo>> get() {
        return loadData();
    }

    @Override
    public Result<ArrayList<Repo>> loadData() {
        RepoQueryParams params = mSupplier.get();
        if (params == null) {
            return failure();
        }
        Call<ArrayList<Repo>> call;
        call = mRetrofit.create(RepositoriesAPIService.class)
                        .getUserRepos(params.userName, params.type, params.sort, params.direction);
        ArrayList<Repo> data;
        try {
            data = call.execute()
                       .body();
            mACache.put(ConstConfig.S_REPOES, data);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure(e);
        }
        return Result.success(data);
    }

    @Override
    public void saveData(ArrayList<Repo> data) {

    }

    @Override
    public void deleteData(ArrayList<Repo> data) {

    }

    @Override
    public ArrayList<Repo> retrieveData() {
        return null;
    }
}
