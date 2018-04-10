package com.example.shubham.mytmdb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shubham on 24-03-2018.
 */

public class ApiClient {
    private static ApiClient INSTANCE;

    private TmdbApi tmdbApi;

    private ApiClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tmdbApi = retrofit.create(TmdbApi.class);
    }

    public static ApiClient getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    public TmdbApi getTmdbApiApi() {
        return tmdbApi;
    }
}
