package com.fas.githubuser.presenter;

/**
 * Created by fas on 6/3/17.
 */

public interface BasePresenter<T> {
    public void onAttachView(T t);
    public void onDetachView(T t);
}
