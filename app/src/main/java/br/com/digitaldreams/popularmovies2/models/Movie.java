package br.com.digitaldreams.popularmovies2.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.digitaldreams.popularmovies2.data.PopularMovieContract;

/**
 * Created by josecostamartins on 7/24/16.
 */
public class Movie implements Parcelable {

    private static final String key = "***REMOVED***";
    private static final String URL = "https://api.themoviedb.org/3/movie";

    private static int currentPage = 0; //request
    private static int total_results; //request
    private static int total_pages; //request

    private Integer localId;
    private Integer id;
    private Boolean adult;
    private Boolean video;
    private Integer voteCount;
    private Double populatity;
    private Double voteAverage;
    private String backdropPath; //image
    private String originalLanguage;
    private String originalTitle;
    private String overview;
    private String title;
    private String posterPath;
    private ArrayList<Integer> genreIds;
    private Date releaseDate;

    private static final String[] imageSizes = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

    public Movie() {
        if (key == null) {
            throw new NullPointerException("ApiKey cannot be null");
        }

    }

    public Movie(Parcel parcel) {
        if (key == null) {
            throw new NullPointerException("ApiKey cannot be null");
        }

        adult = parcel.readByte() != 0;
//        video = parcel.readByte() != 0;
        id = parcel.readInt();
        voteCount = parcel.readInt();
        populatity = parcel.readDouble();
        voteAverage = parcel.readDouble();
        backdropPath = parcel.readString();
        originalLanguage = parcel.readString();
        originalTitle = parcel.readString();
        overview = parcel.readString();
        title = parcel.readString();
        posterPath = parcel.readString();
        releaseDate = stringToDate(parcel.readString());

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //ids
        parcel.writeByte((byte) (adult ? 1 : 0));
//        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeInt(id);
        parcel.writeInt(voteCount);
        parcel.writeDouble(populatity);
        parcel.writeDouble(voteAverage);
        parcel.writeString(backdropPath);
        parcel.writeString(originalLanguage);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(dateToString());
    }

    public static String getKey() {
        return key;
    }

    public static String getURL() {
        return URL;
    }

    public static ArrayList<Movie> parseMovieList(String json) {
        if (json == null) {
            return null;
        }
        try {
            JSONObject data = new JSONObject(json);
            currentPage = data.optInt("page");
            JSONArray results = data.getJSONArray("results");

            ArrayList<Movie> movieList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject current = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setAdult(current.optBoolean("adult", false));
                movie.setId(current.optInt("id", 0));
                movie.setOverview(current.optString("overview", ""));
                movie.setPosterPath(current.optString("poster_path", ""));
                movie.setOriginalTitle(current.optString("original_title", ""));
                movie.setOriginalLanguage(current.optString("original_language", ""));
                movie.setTitle(current.optString("title", ""));
                movie.setBackdropPath(current.optString("backdrop_path", ""));
                movie.setPopulatity(current.optDouble("popularity", 0.0));
                movie.setVoteCount(current.optInt("vote_count", 0));
                movie.setVoteAverage(current.optDouble("vote_average", 0.0));
                movie.setVideo(current.optBoolean("video", false));
                movie.setReleaseDate(stringToDate(current.getString("release_date")));
//                movie.setGenreIds();
                movieList.add(movie);
            }

            return movieList;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the movie image URL with size w185
     */
    public String getMovieImageURL() {
        return getMovieImageURL(2);
    }


    /**
     * size has the following configuration:
     * 0: "w92",
     * 1: "w154",
     * 2: "w185",
     * 3: "w342",
     * 4: "w500",
     * 5: "w780",
     * 6: "original".
     *
     * @param size
     * @return the movie image URL with the specified size
     */
    public String getMovieImageURL(Integer size) {
        String finalUrl;
        if (size == null) {
            throw new NullPointerException("Please inform image size");
        }

        finalUrl = "http://image.tmdb.org/t/p/" + imageSizes[size] + "/" + this.posterPath;

        return finalUrl;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    private Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getPopulatity() {
        return populatity;
    }

    public void setPopulatity(Double populatity) {
        this.populatity = populatity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public static Boolean integerToBoolean(Integer value) {
        if (value == 0) {
            return false;
        }
        return true;
    }

    public static Integer booleanToInteger(Boolean value) {
        if (value) {
            return 1;
        }
        return 0;
    }

    public static String dateToString(Date date) {
        String dateFormat = "yyyy-MM-dd";
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(dateFormat);

        return df.format(date);
    }

    private static Date stringToDate(String date) {
        String dateFormat = "yyyy-MM-dd";
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(dateFormat);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public String dateToString() {
        String dateFormat = "yyyy-MM-dd";
        if (releaseDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(dateFormat);
        return df.format(releaseDate);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };


    public static String[] movieProjection(){

        return new String[]{PopularMovieContract.MovieEntry.COLUMN_ADULT,
        PopularMovieContract.MovieEntry.COLUMN_MOVIE_ID,
        PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT,
        PopularMovieContract.MovieEntry.COLUMN_POPULARITY,
        PopularMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
        PopularMovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
        PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
        PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
        PopularMovieContract.MovieEntry.COLUMN_OVERVIEW,
        PopularMovieContract.MovieEntry.COLUMN_TITLE,
        PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH,
        PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE};
    }

    public ContentValues getMovieContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_ADULT, booleanToInteger(getAdult()));
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_MOVIE_ID, getId());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT, getVoteCount());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_POPULARITY, getPopulatity().toString());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, getVoteAverage().toString());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_BACKDROP_PATH, getBackdropPath());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, getOriginalLanguage());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, getOriginalTitle());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_OVERVIEW, getOverview());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_TITLE, getTitle());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH, getPosterPath());
        contentValues.put(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE, dateToString(getReleaseDate()));

        return contentValues;
    }

    public static ArrayList<Movie> getMovies(Cursor cursor){
        ArrayList<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()){
            Movie movie = new Movie();

//            movie.setLocalId(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry._ID)));
            movie.setId(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_MOVIE_ID)));
            movie.setTitle(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_TITLE)));
            movie.setAdult(integerToBoolean(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_ADULT))));
            movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT)));
            movie.setReleaseDate(stringToDate(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE))));
            movie.setPopulatity(cursor.getDouble(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_POPULARITY)));
            movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_BACKDROP_PATH)));
            movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
            movie.setOverview(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_OVERVIEW)));
            movie.setPosterPath(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH)));
            movieList.add(movie);
        }

        return movieList;

    }

    public static Movie getMovie(Cursor cursor) {
        if (!cursor.moveToFirst()){
            return null;
        }

        Movie movie = new Movie();

//        movie.setLocalId(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry._ID)));
        movie.setId(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_MOVIE_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_TITLE)));
        movie.setAdult(integerToBoolean(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_ADULT))));
        movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT)));
        movie.setReleaseDate(stringToDate(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE))));
        movie.setPopulatity(cursor.getDouble(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_POPULARITY)));
        movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_BACKDROP_PATH)));
        movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE)));
        movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movie.setOverview(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH)));

        return movie;

    }
}