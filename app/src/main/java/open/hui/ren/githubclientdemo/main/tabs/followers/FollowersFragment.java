package open.hui.ren.githubclientdemo.main.tabs.followers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.UserInfo;
import open.hui.ren.githubclientdemo.main.MainContracts;
import open.hui.ren.githubclientdemo.main.tabs.followers.adapter.FollowersAdapter;
import open.hui.ren.githubclientdemo.widgets.MarginDecoration;
import open.hui.ren.githubclientdemo.widgets.RippleItemAnimator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FollowersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FollowersFragment extends Fragment implements FollowersContracts.View {
    private static final String TAG                = "FollowersFragment";
    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2         = "param2";

    private String                       mUserName;
    private String                       mParam2;
    // custom
    private FollowersContracts.Presenter mPresenter;
    private FollowersAdapter             mFollowersAdapter;

    @BindView(R.id.followers_recycler_view)
    RecyclerView mFollowersRecyclerView;

    public FollowersFragment() {
        super();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userName username.
     * @param param2   Parameter 2.
     * @return A new instance of fragment FollowersFragment.
     */
    public static FollowersFragment newInstance(String userName, String param2) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle            args     = new Bundle();
        args.putString(ARG_PARAM_USERNAME, userName);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_PARAM_USERNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setPresenter(new FollowersPresenter(this));
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
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_followers, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews() {
        mFollowersRecyclerView.addItemDecoration(new MarginDecoration(getCtx()));
        mFollowersRecyclerView.setItemAnimator(new RippleItemAnimator());
        mFollowersRecyclerView.setHasFixedSize(true);
        mFollowersRecyclerView.setLayoutManager(new GridLayoutManager(getCtx(), 1));
        mFollowersAdapter = new FollowersAdapter(new ArrayList<UserInfo>(), mPresenter.getView());
        mFollowersRecyclerView.setAdapter(mFollowersAdapter);
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
    public void onFollowersFetchFailed(Throwable throwable) {
        Log.d(TAG, "onFollowersFetchFailed :" + throwable.getMessage());
    }

    @Override
    public void onFollowersFetchSuccess(ArrayList<UserInfo> followers) {
        Log.d(TAG, "onFollowersFetchSuccess :" + followers);
        mFollowersAdapter.updateAll(followers);
    }

    @Override
    public String hitUserName() {
        return mUserName;
    }

    @Override
    public int getViewId() {
        return 0;
    }

    @Override
    public void setPresenter(FollowersContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public FollowersContracts.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public Context getCtx() {
        return getContext();
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
