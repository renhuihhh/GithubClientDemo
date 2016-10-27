package open.hui.ren.githubclientdemo.fragments.overview;


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
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.overview.adapter.PopularRepoAdapter;
import open.hui.ren.githubclientdemo.widgets.RippleItemAnimator;
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;

/**
 * @author renhui
 * @date 16-10-25
 * @desc OverView二级界面的view
 */
public class OverViewFragment extends Fragment implements OverViewContacts.View {
    private static final String TAG = "OverViewFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    // ButterKnife
    @BindView(R.id.over_view_popular_repo_recycler_view)
    RecyclerView mPopularRepoRecyclerView;

    // Custom
    private OverViewContacts.Presenter mPresenter;
    private PopularRepoAdapter         mPopularRepoAdapter;

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
    // TODO: Rename and change types and number of parameters
    public static OverViewFragment newInstance(String param1, String param2) {
        OverViewFragment fragment = new OverViewFragment();
        Bundle           args     = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
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
    }

    @Override
    public void setPresenter(OverViewContacts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public OverViewContacts.Presenter getPresenter() {
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
}
