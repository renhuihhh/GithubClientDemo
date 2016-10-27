package open.hui.ren.githubclientdemo.fragments.stars;


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
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;
import open.hui.ren.githubclientdemo.fragments.stars.adapter.StarredRepoAdapter;
import open.hui.ren.githubclientdemo.main.MainContracts;

/**
 * @author renhui
 * @date 16-10-25
 * @desc starred二级界面的view
 */

public class StarsFragment extends Fragment implements StarsContacts.View {
    private static final String TAG                = "StarsFragment";
    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2         = "param2";

    private String username;
    private String mParam2;

    // Custom
    private StarsContacts.Presenter mPresenter;
    private StarredRepoAdapter      mStarredRepoAdapter;

    @BindView(R.id.starred_recycler_view)
    RecyclerView mStarredRecyclerView;


    public StarsFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username 当前用户的用户名.
     * @param param2   Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StarsFragment newInstance(String username, String param2) {
        StarsFragment fragment = new StarsFragment();
        Bundle        args     = new Bundle();
        args.putString(ARG_PARAM_USERNAME, username);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM_USERNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setPresenter(new StarsPresenter(this));
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_stars, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        mStarredRecyclerView.addItemDecoration(new MarginDecoration(getCtx()));
        mStarredRecyclerView.setHasFixedSize(true);
        mStarredRecyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 1));
        mStarredRepoAdapter = new StarredRepoAdapter(new ArrayList<Repo>());
        mStarredRecyclerView.setAdapter(mStarredRepoAdapter);
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
    public String hitUserName() {
        return username;
    }

    @Override
    public void onStarsFetchFailed(Throwable error) {
        Log.d(TAG, "onStarsFetchFailed :" + error.getMessage());
    }

    @Override
    public void onStarsFetchSuccess(ArrayList<Repo> repos) {
        Log.d(TAG, "onStarsFetchSuccess :" + repos);
        mStarredRepoAdapter.updateAll(repos);
    }

    @Override
    public void setPresenter(StarsContacts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public StarsContacts.Presenter getPresenter() {
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
