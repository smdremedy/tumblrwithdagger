package com.soldiersofmobile.tumblrviewer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by madejs on 25.04.16.
 */
public interface TumblrApi {



    @GET("/v2/blog/{blogName}.tumblr.com/posts?api_key=fD0HOvNDa2z10uyozPZNnjeb4fEFGVGm58zttH6cXSe4K0qC64")
    Call<PostsResponse> getPosts(@Path("blogName") String blogName,
                                 @Query("limint") int limit,
                                 @Query("offset") int offset);
}
