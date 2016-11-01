package open.hui.ren.githubclientdemo.fragments.repositories;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.fragments.repositories.adapter.RepositoriesAdapter;
import open.hui.ren.githubclientdemo.main.MainContracts;
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;
import open.hui.ren.githubclientdemo.widgets.RippleItemAnimator;

/**
 * @author renhui
 * @date 16-10-25
 * @desc Repositories二级界面的view
 */

public class RepositoriesFragment extends Fragment implements RepositoriesContracts.View {
    private static final String TAG                = "RepositoriesFragment";
    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2         = "param2";

    private String username;
    private String mParam2;

    @BindView(R.id.over_view_repo_recycler_view)
    RecyclerView mOverViewRepoRecyclerView;

    // Custom
    private RepositoriesContracts.Presenter mPresenter;
    private RepositoriesAdapter             mRepositoriesAdapter;


    public RepositoriesFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username 当前登陆用户的用户名
     * @param param2   Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    public static RepositoriesFragment newInstance(String username, String param2) {
        RepositoriesFragment fragment = new RepositoriesFragment();
        Bundle               args     = new Bundle();
        args.putString(ARG_PARAM_USERNAME, username);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM_USERNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setPresenter(new RepsitoriesPresenter(this));
        mPresenter.start();
    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        mPresenter.pause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_repositories, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        mOverViewRepoRecyclerView.addItemDecoration(new MarginDecoration(getCtx()));
        mOverViewRepoRecyclerView.setItemAnimator(new RippleItemAnimator());
        mOverViewRepoRecyclerView.setHasFixedSize(true);
        mOverViewRepoRecyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 1));
        mRepositoriesAdapter = new RepositoriesAdapter(new ArrayList<Repo>());
        mOverViewRepoRecyclerView.setAdapter(mRepositoriesAdapter);
    }


    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d(TAG, "onHiddenChanged: is hidden " + hidden);
        super.onHiddenChanged(hidden);
    }


    @Override
    public String hitUserName() {
        return username;
    }

    @Override
    public void onReposFetchFailed(Throwable error) {
        Log.d(TAG, "onReposFetchFailed :" + error.getMessage());
    }

    @Override
    public void onReposFetchSuccess(ArrayList<Repo> repos) {
        Log.d(TAG, "onReposFetchSuccess :" + repos);
        mRepositoriesAdapter.updateAll(repos);
    }

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void setPresenter(RepositoriesContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public RepositoriesContracts.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public Context getCtx() {
        return getActivity();
    }

    @Override
    public MyApplication getAppContext() {
        return (MyApplication) getActivity().getApplication();
    }

    @Override
    public MainContracts.View hitMainView() {
        return (MainContracts.View) getActivity();
    }
}
