package com.fas.githubuser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fas on 6/3/17.
 */

public class UserModel {
    private long id;
    @SerializedName("login") private String username;
    @SerializedName("avatar_url") private String userIcon;

    public UserModel(long id, String name, String userIcon) {
        this.id = id;
        this.username = name;
        this.userIcon = userIcon;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserIcon() {
        return userIcon;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userIcon='" + userIcon + '\'' +
                '}';
    }
}
