package com.example.shubham.mytmdb.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieModel;

import java.util.List;

/**
 * Created by Shubham on 05-04-2018.
 */

@Dao
public interface MovieDAO {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieModel.ResultsBean movieobj);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertMovie(ArrayList<MovieModel.ResultsBean> users);

//    @Insert
//    void addTwoUsers(MovieModel user, MovieModel user1);

//    @Delete
//    void deleteMovie(MovieModel movieobj);

//    @Update
//    void updateMovie(MovieModel movieobj);


    @Query("SELECT * FROM Movies WHERE movie_type = :type ")
    List<MovieModel.ResultsBean> getAllMovies(String type);


}
