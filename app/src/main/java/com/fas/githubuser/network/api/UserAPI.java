package com.fas.githubuser.network.api;

import com.fas.githubuser.model.SearchUsersResultModel;
import com.fas.githubuser.model.UserModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**r
 * Created by fas on 6/3/17.
 */

public interface UserAPI {
    @GET("users")
    Observable<List<UserModel>> getUsers(@Query("since") long lastUserId,
                                         @Query("per_page") int limit);

    @GET("search/users")
    Observable<SearchUsersResultModel> searchUsersByName(@Query("q") String searchTerms,
                                                         @Query("page") int page,
                                                         @Query("per_page") int limit);
}
