package br.com.digitaldreams.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josecostamartins on 9/14/16.
 */
public class MovieDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "popularMovies.db";

    public MovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is first created.
     * @param db The database being created, which all SQL statements will be executed on.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PopularMovieContract.MovieEntry.CREATE_TABLE);
    }

    /**
     * Called whenever DATABASE_VERSION is incremented. This is used whenever schema changes need
     * to be made or new tables are added.
     * @param db The database being updated.
     * @param oldVersion The previous version of the database. Used to determine whether or not
     *                   certain updates should be run.
     * @param newVersion The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
