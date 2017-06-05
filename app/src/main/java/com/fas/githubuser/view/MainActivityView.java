package com.fas.githubuser.view;

import com.fas.githubuser.model.UserModel;

import java.util.List;

/**
 * Created by fas on 6/3/17.
 */

public interface MainActivityView {

    void initView();
    void onFetched(List<UserModel> userModels);
    void onUserAdded(List<UserModel> userModels);
    void showLoading(boolean isShow);
    void showWarning(String warningText);
}
