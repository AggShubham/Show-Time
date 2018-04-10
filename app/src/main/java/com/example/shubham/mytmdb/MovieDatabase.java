package com.example.shubham.mytmdb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by Shubham on 05-04-2018.
 */
@Database(entities = movie.ResultsBean.class , version = 2)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Movies "
                    + " ADD COLUMN type");
        }
    };

    public static MovieDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MovieDatabase.class,"movie_database")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    abstract movieDAO getmovieDAO();
}