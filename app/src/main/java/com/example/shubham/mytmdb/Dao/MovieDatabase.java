package com.example.shubham.mytmdb.Dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.shubham.mytmdb.Retrofit.ResponseModels.MovieModel;

/**
 * Created by Shubham on 05-04-2018.
 */
@Database(entities = MovieModel.ResultsBean.class , version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase INSTANCE;
//
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Movies "
//                    + " ADD COLUMN type");
//        }
//    };

    public static MovieDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,"movie_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract MovieDAO getmovieDAO();
}