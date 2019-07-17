package com.f44red;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WPClient {
    private static final String BASE_URL = "http://f44red.com";

    public static Retrofit getRetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static RetrofitArrayApi getApiService(){
        return getRetrofitInstance().create(RetrofitArrayApi.class);
    }
}
