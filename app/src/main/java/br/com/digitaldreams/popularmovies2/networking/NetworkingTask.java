package br.com.digitaldreams.popularmovies2.networking;

/**
 * Created by josecostamartins on 7/25/16.
 */
public interface NetworkingTask {

    public void onFinished(String json, String type);
    public void onProgress(Double percentage);

}
