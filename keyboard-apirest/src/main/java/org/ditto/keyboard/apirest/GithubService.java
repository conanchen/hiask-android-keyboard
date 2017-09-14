package org.ditto.keyboard.apirest;

import android.arch.lifecycle.LiveData;


import org.ditto.keyboard.apirest.data.GitUser;
import org.ditto.keyboard.apirest.util.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * REST API access points
 */
public interface GithubService {
    //https://api.github.com/users/conanchen
    @GET("users/{login}")
    LiveData<ApiResponse<GitUser>> getUser(@Path("login") String login);

//
//
//    @GET("search/repositories")
//    Call<RepoSearchResponse> searchRepos(@Query("q") String query, @Query("page") int page);
}