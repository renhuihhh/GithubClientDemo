package open.hui.ren.githubclientdemo.main.tabs.overview;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Event;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.tabs.overview.adapter.OverViewEventsAdapter;
import open.hui.ren.githubclientdemo.main.tabs.overview.adapter.PopularRepoAdapter;
import open.hui.ren.githubclientdemo.main.MainContracts;
import open.hui.ren.githubclientdemo.utils.DateUtil;
import open.hui.ren.githubclientdemo.utils.ScreenUtil;
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;
import open.hui.ren.githubclientdemo.widgets.RippleItemAnimator;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView二级界面的view
 */
public class OverViewFragment extends Fragment implements OverViewContracts.View {
    private static final String TAG = "OverViewFragment";
    public static final  int    ID  = 101;

    private static final String URL_CONTRIBUTIONS = "https://github" +
        ".com/users/%1$s/contributions?from=%2$s&to=%3$s&full_graph=1";

    private static int SVG_WIDTH = 720;

    private static final String ARG_USERNAME = "param1";
    private static final String ARG_PARAM2   = "param2";


    private String mUserName;
    private String mParam2;

    // ButterKnife
    @BindView(R.id.over_view_popular_repo_recycler_view)
    RecyclerView       mPopularRepoRecyclerView;
    @BindView(R.id.over_view_events_recycler_view)
    RecyclerView       mOverViewEventsRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeContainer;
    @BindView(R.id.overview_contribution_web_view)
    WebView            mContributionsWebView;

    // Custom
    private OverViewContracts.Presenter mPresenter;
    private PopularRepoAdapter          mPopularRepoAdapter;
    private OverViewEventsAdapter       mOverViewEventsAdapter;

    public OverViewFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverViewFragment.
     */
    public static OverViewFragment newInstance(String param1, String param2) {
        OverViewFragment fragment = new OverViewFragment();
        Bundle           args     = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USERNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setPresenter(new OverViewPresenter(this));
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_over_view, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        mPopularRepoRecyclerView.addItemDecoration(new MarginDecoration(getCtx()));
        mPopularRepoRecyclerView.setItemAnimator(new RippleItemAnimator());
        mPopularRepoRecyclerView.setHasFixedSize(true);
        mPopularRepoRecyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 2));
        mPopularRepoAdapter = new PopularRepoAdapter(new ArrayList<Repo>());
        mPopularRepoRecyclerView.setAdapter(mPopularRepoAdapter);
        mContributionsWebView.setVisibility(View.INVISIBLE);
        mContributionsWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(final WebView view, final String url) {
                super.onPageFinished(view, url);
                view.setVisibility(View.VISIBLE);
                view.invalidate();
                view.requestLayout();
                view.setInitialScale(getScale());
            }
        });
        mContributionsWebView.loadUrl(getContributionsUrl());

        mOverViewEventsRecyclerView.addItemDecoration(new MarginDecoration(getCtx()));
        mOverViewEventsRecyclerView.setItemAnimator(new RippleItemAnimator());
        mOverViewEventsRecyclerView.setHasFixedSize(true);
        mOverViewEventsRecyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 1));
        mOverViewEventsAdapter = new OverViewEventsAdapter(new ArrayList<Event>());
        mOverViewEventsRecyclerView.setAdapter(mOverViewEventsAdapter);

        mSwipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeContainer.setRefreshing(true);
                mPresenter.refreshPopularRepoes();
                mPresenter.refreshEvents();
                mContributionsWebView.reload();
            }
        });
    }

    private int getScale() {
        return ScreenUtil.getWebViewScale(getActivity(), SVG_WIDTH);
    }

    private String getContributionsUrl() {
        String url          = "";
        Date   endDate      = DateUtil.getLastDayOfMonth();
        String endDateStr   = DateUtil.formatDate(endDate, DateUtil.FORMAT_2);
        Date   startDate    = DateUtil.getFirstDayOfMonth();
        String startDateStr = DateUtil.formatDate(startDate, DateUtil.FORMAT_2);
        if (!TextUtils.isEmpty(mUserName)) {
            url = String.format(URL_CONTRIBUTIONS, mUserName, startDateStr, endDateStr);
        }
        return url;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onResume");
        super.onPause();
        mPresenter.pause();
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
    public void onRefreshFailed(Throwable error) {
        Log.d(TAG, "onRefreshFailed: " + error);
    }

    @Override
    public void onFollowersUpdate(ArrayList<UserInfo> followers) {
        Log.d(TAG, "onFollowersUpdate: " + followers);
    }

    @Override
    public void onFollowingsUpdate(ArrayList<UserInfo> followings) {
        Log.d(TAG, "onFollowingsUpdate: " + followings);
    }

    @Override
    public void onStarredUpdate(ArrayList<Repo> starred) {
        Log.d(TAG, "onStarredUpdate: " + starred);
    }

    @Override
    public void onRepoUpdate(ArrayList<Repo> repos) {
        Log.d(TAG, "onRepoUpdate: " + repos);
        mPopularRepoAdapter.updateAll(repos);
        if(mSwipeContainer.isRefreshing()){
            mSwipeContainer.setRefreshing(false);
        }
    }

    @Override
    public void onEventsUpdate(ArrayList<Event> events) {
        Log.d(TAG, "onEventsUpdate: " + events);
        mOverViewEventsAdapter.updateAll(events);
        if(mSwipeContainer.isRefreshing()){
            mSwipeContainer.setRefreshing(false);
        }
    }

    @Override
    public int getViewId() {
        return ID;
    }

    @Override
    public void setPresenter(OverViewContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public OverViewContracts.Presenter getPresenter() {
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
