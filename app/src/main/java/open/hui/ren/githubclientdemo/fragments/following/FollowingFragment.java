package open.hui.ren.githubclientdemo.fragments.following;

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
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.fragments.following.adapter.FollowingAdapter;
import open.hui.ren.githubclientdemo.main.MainContracts;
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;
import open.hui.ren.githubclientdemo.widgets.RippleItemAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowingFragment extends Fragment implements FollowingContracts.View {
    private static final String TAG                = "FollowingFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2         = "param2";
    private String username;

    private String                       mParam2;
    // Custom
    private FollowingContracts.Presenter mPresenter;
    private FollowingAdapter             mFollowingAdapter;

    @BindView(R.id.following_recycler_view)
    RecyclerView mFollowingRecyclerView;


    public FollowingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.
     * @param param2   Parameter 2.
     * @return A new instance of fragment FollowingFragment.
     */
    public static FollowingFragment newInstance(String username, String param2) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle            args     = new Bundle();
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
        setPresenter(new FollowingPresenter(this));
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
        Log.d(TAG, "onResume");
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_following, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        mFollowingRecyclerView.addItemDecoration(new MarginDecoration(getCtx()));
        mFollowingRecyclerView.setItemAnimator(new RippleItemAnimator());
        mFollowingRecyclerView.setHasFixedSize(true);
        mFollowingRecyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 1));
        mFollowingAdapter = new FollowingAdapter(new ArrayList<UserInfo>(), mPresenter.getView());
        mFollowingRecyclerView.setAdapter(mFollowingAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d(TAG, "onHiddenChanged: is hidden " + hidden);
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public String hitUserName() {
        return username;
    }

    @Override
    public void onFollowingsFetchFailed(Throwable error) {
        Log.d(TAG, "onStarsFetchFailed :" + error.getMessage());
    }

    @Override
    public void onFollowingFetchSuccess(ArrayList<UserInfo> followings) {
        Log.d(TAG, "onStarsFetchSuccess :" + followings);
        mFollowingAdapter.updateAll(followings);
    }

    @Override
    public void setPresenter(FollowingContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public FollowingContracts.Presenter getPresenter() {
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
