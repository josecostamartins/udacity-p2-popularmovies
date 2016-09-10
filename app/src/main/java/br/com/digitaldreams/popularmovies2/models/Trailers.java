package br.com.digitaldreams.popularmovies2.models;

import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josecostamartins on 9/3/16.
 */
public class Trailers {

    private static String trailerID;
    private String videoID;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;

    public static ArrayList<Trailers> parseTrailerList(String json) {

        if (json == null) {
            return null;
        }
        try {
            JSONObject data = new JSONObject(json);
            JSONArray results = data.getJSONArray("results");
            trailerID = data.getString("id");

            ArrayList<Trailers> trailersList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject current = results.getJSONObject(i);
                Trailers trailers = new Trailers();
                trailers.setVideoID(current.optString("id"));
                trailers.setIso_639_1(current.optString("iso_639_1"));
                trailers.setIso_3166_1(current.optString("iso_3166_1"));
                trailers.setKey(current.optString("key"));
                trailers.setName(current.optString("name"));
                trailers.setSite(current.optString("site"));
                trailers.setSize(current.optInt("size", 0));
                trailers.setType(current.optString("type"));

//                movies.setGenreIds();
                trailersList.add(trailers);
            }

            return trailersList;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTrailerID() {
        return trailerID;
    }

    public static void setTrailerID(String trailerID) {
        Trailers.trailerID = trailerID;
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
