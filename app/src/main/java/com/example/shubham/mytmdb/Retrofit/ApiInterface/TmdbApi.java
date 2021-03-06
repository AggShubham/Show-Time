package com.example.shubham.mytmdb.Retrofit.ApiInterface;

import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieCredits;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieModel;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.SearchClass;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.TVClass;
import com.example.shubham.mytmdb.Retrofit.ResponseModels.TrailerClassModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shubham on 24-03-2018.
 */

public interface TmdbApi {

    @GET("movie/now_playing")
    Call<MovieModel> getmovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieModel> getpopmovies(@Query("api_key") String apikey);

    @GET("movie/top_rated")
    Call<MovieModel> gettopratedmovies(@Query("api_key") String apikey);

    @GET("movie/upcoming")
    Call<MovieModel> getupcomingmovies(@Query("api_key") String apikey);

    @GET("movie/{movie_id}/credits")
    Call<MovieCredits> getcastMovies (@Path("movie_id") int id,
                                      @Query("api_key") String key);

    @GET("movie/{movie_id}/videos")
    Call<TrailerClassModel> getmovietrailers(@Path("movie_id") int id,
                                             @Query("api_key") String key,
                                             @Query("language") String en);

    @GET("tv/airing_today")
    Call<TVClass> gettvshows(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<TVClass> getpopshows(@Query("api_key") String apikey);

    @GET("tv/top_rated")
    Call<TVClass> gettopratedshows(@Query("api_key") String apikey);

    @GET("search/multi")
    Call<SearchClass> getsearchall (@Query("api_key") String key,
                                    @Query("language") String en,
                                    @Query("query") String query,
                                    @Query("page") int page,
                                    @Query("include_adult") boolean a);

    }
