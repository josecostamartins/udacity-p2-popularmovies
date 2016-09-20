package br.com.digitaldreams.popularmovies2.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import br.com.digitaldreams.popularmovies2.data.PopularMovieContract;
import br.com.digitaldreams.popularmovies2.models.Movie;
import br.com.digitaldreams.popularmovies2.networking.NetworkingTask;
import br.com.digitaldreams.popularmovies2.networking.FetchMovieRequest;
import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.adapter.MovieRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements NetworkingTask, SharedPreferences.OnSharedPreferenceChangeListener {


    private boolean mTwoPane = false;
    //    MovieListFragment movieListFragment;
    private ArrayList<Movie> movies;
    private MovieRecyclerAdapter movieRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            getMovieList();
        } else {
            movies = savedInstanceState.getParcelableArrayList("movies");
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefs.registerOnSharedPreferenceChangeListener(this);


//        if (savedInstanceState == null) { // first time loading this activity

        // checks if the fragment in sw-600dp-land exists,
        // if does then we are in landscape mode
        // else we are in portrait mode
        if (findViewById(R.id.movie_details_two_pane_fragment) != null) {
            mTwoPane = true;
        }

//        movieListFragment = new MovieListFragment();
//        Bundle args = new Bundle();
//        args.putBoolean("two_pane", mTwoPane);
//        movieListFragment.setArguments(args);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.movies_fragment, movieListFragment)
//                .commit();
//        }

        RecyclerView movieRecyclerView = (RecyclerView) findViewById(R.id.movies_list);
        movieRecyclerView.setHasFixedSize(true);

        int spanCount = getResources().getInteger(R.integer.span_count);
        GridLayoutManager movieGridLayout = new GridLayoutManager(this, spanCount);

        movieRecyclerAdapter = new MovieRecyclerAdapter(movies, this);

        movieRecyclerView.setLayoutManager(movieGridLayout);
        movieRecyclerView.setAdapter(movieRecyclerAdapter);

        movieRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, movieRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (mTwoPane) {
                            Movie movie = movies.get(position);

                            MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movie);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.movie_details_two_pane_fragment, movieDetailFragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                            intent.putExtra("movie", movies.get(position));
                            startActivity(intent);
                        }
                    }
                })
        );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), 10);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void getMovieList() {
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        String tag = sharedPrefs.getString(this.getString(R.string.sort_order_key), "popular");

        if (!tag.equalsIgnoreCase("favorite")) {
            tag = "m";
        }

        try {
            FetchMovieRequest movieRequest = new FetchMovieRequest(this, this, tag, null); //is this ok?
            movieRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFinished(String json, String tag) {
        if (tag.equalsIgnoreCase("favorite")) {
            Cursor cursor = this.getContentResolver().query(PopularMovieContract.MovieEntry.CONTENT_URI,
                    Movie.movieProjection(), null, null, null);

            movies = Movie.getMovies(cursor);
        } else {
            movies = Movie.parseMovieList(json);
        }
        movieRecyclerAdapter.notifyDataSetChanged(movies);
    }

    @Override
    public void onProgress(Double percentage) {
        //do nothing
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getMovieList();
    }
}
