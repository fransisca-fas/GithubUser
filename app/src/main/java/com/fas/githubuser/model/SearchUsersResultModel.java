package com.fas.githubuser.model;

import java.util.List;

/**
 * Created by fas on 6/4/17.
 */

public class SearchUsersResultModel {
    private List<UserModel> items;

    public SearchUsersResultModel(List<UserModel> items) {
        this.items = items;
    }

    public List<UserModel> getItems() {
        return items;
    }
}
