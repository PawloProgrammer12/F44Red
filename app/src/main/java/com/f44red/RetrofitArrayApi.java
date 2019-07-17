package com.f44red;

import com.model.Media;
import com.model.WPPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit API Interface. It loads post information and sends it to list.
 * @author Paweł Turoń
 */

public interface RetrofitArrayApi {
    @GET("wp-json/wp/v2/posts")
    Call<List<WPPost>> getPostInfo();

    @GET("wp-json/wp/v2/posts/{id}")
    Call<List<WPPost>> getPostById(@Path("id") int postId);

    @GET("wp-json/wp/v2/media/{featured_media}")
    Call<Media> getPostThumbnail(@Path("featured_media") int media);
}
