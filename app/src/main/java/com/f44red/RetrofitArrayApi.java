package com.f44red;

import com.model.WPPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit API Interface. It loads post information and sends it to list.
 * @author Paweł Turoń
 */

public interface RetrofitArrayApi {
    @GET("wp-json/wp/v2/posts?_embed")
    Call<List<WPPost>> getPostInfo();
}
