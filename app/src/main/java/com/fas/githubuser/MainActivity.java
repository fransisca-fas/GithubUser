package com.fas.githubuser;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

import com.fas.githubuser.adapter.UserAdapter;
import com.fas.githubuser.model.UserModel;
import com.fas.githubuser.presenter.MainActivityPresenter;
import com.fas.githubuser.view.MainActivityView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    private MainActivityPresenter presenter;
    private UserAdapter adapter;
    private Handler handler;

    @BindView(R.id.user_list) RecyclerView userList;
    @BindView(R.id.user_searchview) SearchView userSearch;
    @BindView(R.id.loading_container) View loadingContainer;
    @BindView(R.id.warning_container) View warningContainer;
    @BindView(R.id.warning_view) TextView warningView;

    @BindString(R.string.empty_user) String emptyUser;
    @BindString(R.string.try_again_warning) String tryAgainStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        userList.setLayoutManager(layoutManager);

        handler = new Handler();
        adapter = new UserAdapter(this, userList);
        userList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new UserAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(long lastuserId, int current_page) {
                adapter.addUserModel(null);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);

                if (presenter.isSearching()) {
                    presenter.searchUsers(userSearch.getQuery().toString(), current_page);
                } else {
                    presenter.getUsers(lastuserId);
                }
            }
        });

        userSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.setSearching(true);
                presenter.searchUsers(query, 1);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                presenter.setSearching(true);
                //sleep for 1 second after textchanged, so the application will not call the API everytime there is a textchanged event occurs
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!newText.isEmpty())
                            presenter.searchUsers(newText, 1);
                        else {
                            presenter.setSearching(false);
                            presenter.getUsers(0);
                        }
                    }
                }, 1000);
                return true;
            }
        });
        userSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                presenter.setSearching(false);
                return false;
            }
        });
    }

    @Override
    public void onFetched(List<UserModel> userModels) {
        adapter.removeUserModel(adapter.getItemCount() - 1);
        showLoading(false);
        if (userModels.size() == 0)
            showWarning(emptyUser);
        else
            showWarning("");
        adapter.setUserModels(userModels);
        adapter.notifyDataSetChanged();
        adapter.setLoading(false);
    }

    @Override
    public void onUserAdded(List<UserModel> userModels) {
        adapter.removeUserModel(adapter.getItemCount() - 1);
        showLoading(false);
        adapter.addUserModels(userModels);
        adapter.notifyDataSetChanged();
        adapter.setLoading(false);
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow)
            loadingContainer.setVisibility(View.VISIBLE);
        else
            loadingContainer.setVisibility(View.GONE);
    }

    @Override
    public void showWarning(String warningText) {
        if (warningText.trim().isEmpty()) {
            warningContainer.setVisibility(View.GONE);
        } else {
            warningContainer.setVisibility(View.VISIBLE);
            warningView.setText(warningText.contains("403") ? tryAgainStr : warningText);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new MainActivityPresenter();
        presenter.onAttachView(this);
        presenter.getUsers(0);
    }

    @OnClick(R.id.user_searchview)
    public void userSearchOnClick(View view) {
        userSearch.setIconified(false);
    }
}
