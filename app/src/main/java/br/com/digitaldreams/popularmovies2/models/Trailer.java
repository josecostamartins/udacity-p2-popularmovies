package br.com.digitaldreams.popularmovies2.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josecostamartins on 9/3/16.
 */
public class Trailer {

    private String movieID;
    private String videoID;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;
    private static String URL;

    public static ArrayList<Trailer> parseTrailerList(String json) {

        if (json == null) {
            return null;
        }
        try {
            JSONObject data = new JSONObject(json);
            JSONArray results = data.getJSONArray("results");

            ArrayList<Trailer> trailerList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject current = results.getJSONObject(i);
                Trailer trailer = new Trailer();
                trailer.setMovieID(data.getString("id"));
                trailer.setVideoID(current.optString("id"));
                trailer.setIso_639_1(current.optString("iso_639_1"));
                trailer.setIso_3166_1(current.optString("iso_3166_1"));
                trailer.setKey(current.optString("key"));
                trailer.setName(current.optString("name"));
                trailer.setSite(current.optString("site"));
                trailer.setSize(current.optInt("size", 0));
                trailer.setType(current.optString("type"));

//                movies.setGenreIds();
                trailerList.add(trailer);
            }

            return trailerList;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getURL(Integer movieID) {
        return "http://api.themoviedb.org/3/movie/" + movieID + "/videos";
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return getName();
    }
}
