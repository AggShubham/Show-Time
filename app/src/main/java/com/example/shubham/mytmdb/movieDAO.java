package com.example.shubham.mytmdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.graphics.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 05-04-2018.
 */

@Dao
public interface movieDAO {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(movie.ResultsBean movieobj);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertMovie(ArrayList<movie.ResultsBean> users);

//    @Insert
//    void addTwoUsers(movie user, movie user1);

//    @Delete
//    void deleteMovie(movie movieobj);

//    @Update
//    void updateMovie(movie movieobj);


    @Query("SELECT * FROM Movies WHERE movie_type = :type ")
    List<movie.ResultsBean> getAllMovies(String type);


}
