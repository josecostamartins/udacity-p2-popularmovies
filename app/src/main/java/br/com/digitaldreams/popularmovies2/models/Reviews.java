package br.com.digitaldreams.popularmovies2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josecostamartins on 9/10/16.
 */
public class Reviews {

    private static Integer page = 1;
    private static Integer movieID;
    private String reviewID;
    private String author;
    private String content;
    private String url;
    private static Integer totalPages;
    private static Integer totalResults;
    private static String URL;

    public static ArrayList<Reviews> parseReviewsList(String json) {

        if (json == null) {
            return null;
        }
        try {
            JSONObject data = new JSONObject(json);
            JSONArray results = data.getJSONArray("results");
            movieID = data.getInt("id");
            totalPages = data.optInt("size", 0);
            totalResults = data.optInt("size", 0);

            ArrayList<Reviews> trailersList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject current = results.getJSONObject(i);
                Reviews reviews = new Reviews();
                reviews.setReviewID(current.optString("id"));
                reviews.setAuthor(current.optString("author"));
                reviews.setContent(current.optString("content"));
                reviews.setUrl(current.optString("url"));

//                movies.setGenreIds();
                trailersList.add(reviews);
            }

            return trailersList;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getURL(Integer movieID) {
        return "http://api.themoviedb.org/3/movie/" + movieID + "/reviews";
    }

    public static Integer getPage() {
        return page;
    }

    public static void setPage(Integer page) {
        Reviews.page = page;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }



}
