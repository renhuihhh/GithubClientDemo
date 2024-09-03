package open.hui.ren.githubclientdemo.main.tabs.stars;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.main.MainContracts;
import open.hui.ren.githubclientdemo.main.tabs.stars.adapter.StarredRepoAdapter;
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;

/**
 * @author renhui
 * @date 16-10-25
 * @desc starred二级界面的view
 */

public class StarsFragment extends Fragment implements StarsContracts.View {
    private static final String TAG                = "StarsFragment";
    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2         = "param2";
    private String username;

    private String                   mParam2;
    // Custom
    private StarsContracts.Presenter mPresenter;

    private StarredRepoAdapter mStarredRepoAdapter;

    @BindView(R.id.starred_recycler_view)
    RecyclerView mStarredRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;

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

        mSwipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeContainer.setRefreshing(true);
                refreshStars();
            }
        });
    }

    @Override
    public void refreshStars() {
        mPresenter.refreshStars();
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
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void onStarsFetchSuccess(ArrayList<Repo> repos) {
        Log.d(TAG, "onStarsFetchSuccess :" + repos);
        mStarredRepoAdapter.updateAll(repos);
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void setPresenter(StarsContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public StarsContracts.Presenter getPresenter() {
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
