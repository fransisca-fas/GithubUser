package com.fas.githubuser.presenter;

import com.fas.githubuser.model.SearchUsersResultModel;
import com.fas.githubuser.model.UserModel;
import com.fas.githubuser.network.BaseNetworkManager;
import com.fas.githubuser.network.api.UserAPI;
import com.fas.githubuser.view.MainActivityView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fas on 6/3/17.
 */

public class MainActivityPresenter implements BasePresenter<MainActivityView> {

    private MainActivityView mainView;
    private static UserAPI userAPI;
    private boolean isSearching = false;

    private final int USER_LIMIT = 10;

    public static UserAPI getUserAPI() {
        if (userAPI == null) {
            userAPI = new BaseNetworkManager().getRetrofit().create(UserAPI.class);
        }
        return userAPI;
    }

    @Override
    public void onAttachView(MainActivityView mainActivityView) {
        mainView = mainActivityView;
    }

    @Override
    public void onDetachView(MainActivityView mainActivityView) {
        mainView = null;
    }

    public void searchUsers(String search, final int page) {
        if (page <= 1)
            mainView.showLoading(true);

        getUserAPI().searchUsersByName(search, page, USER_LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<SearchUsersResultModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(SearchUsersResultModel userModels) {
                        if (page <= 1)
                            mainView.onFetched(userModels.getItems());
                        else
                            mainView.onUserAdded(userModels.getItems());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        mainView.showWarning(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void getUsers(final long lastUserId) {
        if (lastUserId == 0)
            mainView.showLoading(true);

        getUserAPI().getUsers(lastUserId, USER_LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(List<UserModel> userModels) {
                        if (lastUserId == 0)
                            mainView.onFetched(userModels);
                        else
                            mainView.onUserAdded(userModels);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        mainView.showWarning(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void setSearching(boolean searching) {
        isSearching = searching;
    }

    public boolean isSearching() {
        return isSearching;
    }
}
