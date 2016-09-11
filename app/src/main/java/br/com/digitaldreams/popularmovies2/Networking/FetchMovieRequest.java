package br.com.digitaldreams.popularmovies2.Networking;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.digitaldreams.popularmovies2.Interface.NetworkingTask;
import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.models.Movies;
import br.com.digitaldreams.popularmovies2.models.Reviews;
import br.com.digitaldreams.popularmovies2.models.Trailers;

/**
 * Created by josecostamartins on 7/25/16.
 */
public class FetchMovieRequest extends AsyncTask<Void, Void, String> {

    private final static String LOG_TAG = FetchMovieRequest.class.getSimpleName();
    private String baseURL;
    private boolean shouldSort = false;
    private String sortOrder;
    private String APIKey;
    private Context context;
    private NetworkingTask listener;
    private String type;


    public FetchMovieRequest(Context context, NetworkingTask listener, String tag, Movies movie){
        if (context == null){
            throw new NullPointerException("Context can't be null");
        }
        if (listener == null) {
            throw new NullPointerException("Listener can't be null");
        }
        if (tag == null) {
            throw new NullPointerException("Type can't be null");
        }


        if (tag.equalsIgnoreCase("t")){
            if (movie == null) {
                throw new NullPointerException("Class can't be null");
            }
            this.baseURL = Trailers.getURL(movie.getId());
        }
        else if (tag.equalsIgnoreCase("r")){
            if (movie == null) {
                throw new NullPointerException("Class can't be null");
            }
            this.baseURL = Reviews.getURL(movie.getId());
        }
        else { //defaults to movie
            this.baseURL = Movies.getURL();
            shouldSort = true;
        }


        this.APIKey = Movies.getKey();
        this.context = context;
        this.listener = listener;
        this.type = tag;

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this.context);
        sortOrder = sharedPrefs.getString(context.getString(R.string.sort_order_key), "popular");
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onFinished(s, type);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Void... params) {
        if (this.baseURL == null){
            throw new NullPointerException("BaseURL can't be null");
        }
        if (this.APIKey == null){
            throw new NullPointerException("APIKey can't be null");
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;


        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            String MOVIE_URL = this.baseURL + "?api_key=" + this.APIKey;
            if (shouldSort) {
                MOVIE_URL = this.baseURL + "/" + this.sortOrder + "?api_key=" + this.APIKey;
            }

            URL url = new URL(MOVIE_URL);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return movieJsonStr;

    }
}
