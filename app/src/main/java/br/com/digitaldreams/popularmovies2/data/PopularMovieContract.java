package br.com.digitaldreams.popularmovies2.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by josecostamartins on 9/13/16.
 * using https://guides.codepath.com/android/Creating-Content-Providers
 */
public class PopularMovieContract {

    /**
     * The Content Authority is a name for the entire content provider, similar to the relationship
     * between a domain name and its website. A convenient string to use for content authority is
     * the package name for the app, since it is guaranteed to be unique on the device.
     */
    public static final String CONTENT_AUTHORITY = "br.com.digitaldreams.popularmovies2";

    /**
     * The content authority is used to create the base of all URIs which apps will use to
     * contact this content provider.
     */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * A list of possible paths that will be appended to the base URI for each of the different
     * tables.
     */
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailer";
    public static final String PATH_REVIEW = "review";


    /**
     * Create one class for each table that handles all information regarding the table schema and
     * the URIs related to it.
     */
    public static final class MovieEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_MOVIE;

        // Define the table schema
        public static final String TABLE_NAME = "movieTable";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_BACKDROP_PATH = "backdropPath";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                PopularMovieContract.MovieEntry.TABLE_NAME + " (" +
                PopularMovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                PopularMovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                PopularMovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                PopularMovieContract.MovieEntry.COLUMN_ADULT + " INTEGER, " +
                PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
                PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_POPULARITY + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT " +
                ");";


        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }


    /**
     *
     * Not going to store trailer data, only movie
     *
     *
     */
    /*public static final class TrailerEntry implements BaseColumns {
        // Content URI represents the base location for the table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_TRAILER;

        // Define the table schema
        public static final String TABLE_NAME = "trailerTable";
        public static final String COLUMN_MOVIE_ID = "movieID";
        public static final String COLUMN_VIDEO_ID = "videoID";
        public static final String COLUMN_ISO_639_1 = "iso_639_1";
        public static final String COLUMN_ISO_3166_1 = "iso_3166_1";
        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SITE = "site";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_URL = "URL";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                PopularMovieContract.TrailerEntry.TABLE_NAME + " (" +
                PopularMovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY, " +
                PopularMovieContract.TrailerEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                PopularMovieContract.TrailerEntry.COLUMN_VIDEO_ID + " TEXT NOT NULL, " +
                PopularMovieContract.TrailerEntry.COLUMN_ISO_639_1 + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_ISO_3166_1 + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_KEY + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_NAME + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_SITE + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_SIZE + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_TYPE + " TEXT, " +
                PopularMovieContract.TrailerEntry.COLUMN_URL + " TEXT " +
                ");";


        // Define a function to build a URI to find a specific movie by it's identifier
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }*/


}
