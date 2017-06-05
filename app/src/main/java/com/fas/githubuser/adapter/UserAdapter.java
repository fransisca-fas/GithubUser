package com.fas.githubuser.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fas.githubuser.R;
import com.fas.githubuser.model.UserModel;

import java.util.List;

/**
 * Created by fas on 6/4/17.
 */

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<UserModel> userModels;
    private Activity activity;

    public UserAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setUserModels(List<UserModel> userModels) {
        this.userModels = userModels;
    }

    public void addUserModels(List<UserModel> userModels) {
        this.userModels.addAll(userModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
            ImageView avatarView = (ImageView) view.findViewById(R.id.avatar);
            TextView usernameView = (TextView) view.findViewById(R.id.username);

            MyHolder holder = new MyHolder(view, avatarView, usernameView);
            return holder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item, parent, false);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_item);

            LoadingHolder loadingHolder = new LoadingHolder(view, progressBar);
            return loadingHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder myHolder = (MyHolder) holder;
            Glide.with(activity).load(userModels.get(position).getUserIcon())
                    .into(myHolder.avatarView);
            myHolder.usernameView.setText(userModels.get(position).getUsername());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return userModels.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return userModels ==null ? 0 : userModels.size();
    }

    @Override
    public long getItemId(int position) {
        return userModels.get(position).getId();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView avatarView;
        public TextView usernameView;

        public MyHolder(View itemView, ImageView avatarView, TextView usernameView) {
            super(itemView);
            this.avatarView = avatarView;
            this.usernameView = usernameView;
        }
    }

    public class LoadingHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingHolder(View itemView, ProgressBar progressBar) {
            super(itemView);
            this.progressBar = progressBar;
        }
    }
}
