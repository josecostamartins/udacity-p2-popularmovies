package br.com.digitaldreams.popularmovies2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import br.com.digitaldreams.popularmovies2.Interface.NetworkingTask;
import br.com.digitaldreams.popularmovies2.Networking.FetchMovieRequest;
import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.adapter.MovieRecyclerAdapter;
import br.com.digitaldreams.popularmovies2.models.Movies;

public class MainActivity extends AppCompatActivity implements NetworkingTask{



    private boolean mTwoPane = false;
    //    MovieListFragment movieListFragment;
    private ArrayList<Movies> movies;
    private GridLayoutManager movieGridLayout;
    private RecyclerView movieRecyclerView;
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

        movieRecyclerView = (RecyclerView) findViewById(R.id.movies_list);
        movieRecyclerView.setHasFixedSize(true);

        int spanCount = getResources().getInteger(R.integer.span_count);
        movieGridLayout = new GridLayoutManager(this, spanCount);

        movieRecyclerAdapter = new MovieRecyclerAdapter(movies, this);

        movieRecyclerView.setLayoutManager(movieGridLayout);
        movieRecyclerView.setAdapter(movieRecyclerAdapter);

        movieRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, movieRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (mTwoPane) {
                            Movies movie = movies.get(position);

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
        try {
            FetchMovieRequest movieRequest = new FetchMovieRequest(this, this, null); //is this ok?
            movieRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinished(String json) {
        movies = Movies.parseMovieList(json);
        movieRecyclerAdapter.notifyDataSetChanged(movies);
    }

    @Override
    public void onProgress(Double percentage) {
        //do nothing
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovieList();
    }
}
