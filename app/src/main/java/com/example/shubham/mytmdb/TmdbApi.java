package com.example.shubham.mytmdb;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shubham on 24-03-2018.
 */

public interface TmdbApi {

    @GET("movie/now_playing")
    Call<movie> getmovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<movie> getpopmovies(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Call<movie> gettopratedmovies(@Query("api_key") String apikey);

    @GET("movie/upcoming")
    Call<movie> getupcomingmovies(@Query("api_key") String apikey);

    @GET("movie/{movie_id}/credits")
    Call<MovieCredits> getcastMovies (@Path("movie_id") int id,
                                      @Query("api_key") String key);

    @GET("3/movie/{movie_id}/videos")
    Call<TrailerClass> getmovietrailers(@Path("movie_id") int id,
                                         @Query("api_key") String key,
                                         @Query("language") String en);

}
