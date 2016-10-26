package open.hui.ren.githubclientdemo.fragments.repositories;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import open.hui.ren.githubclientdemo.MyApplication;
import open.hui.ren.githubclientdemo.R;
import open.hui.ren.githubclientdemo.entities.Repo;
import open.hui.ren.githubclientdemo.main.MainContracts;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RepositoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepositoriesFragment extends Fragment implements RepositoriesContacts.View {
    private static final String TAG                = "RepositoriesFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_USERNAME = "param1";
    private static final String ARG_PARAM2         = "param2";

    // TODO: Rename and change types of parameters
    private String username;
    private String mParam2;

    // Custom
    private RepositoriesContacts.Presenter mPresenter;


    public RepositoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.
     * @param param2   Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public void onPause(){
        Log.d(TAG, "onPause");
        super.onPause();
        mPresenter.pause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repositories, container, false);
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
    }

    @Override
    public void setPresenter(RepositoriesContacts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public RepositoriesContacts.Presenter getPresenter() {
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
