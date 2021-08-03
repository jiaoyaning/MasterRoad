package com.jyn.masterroad.utils.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 * 玩Android开放API
 * https://www.wanandroid.com/blog/show/2
 */
public interface RetrofitService {
    @GET("article/list/{page}/json")
    Call<List<String>> getArticleList(@Path("page") int page);
}
