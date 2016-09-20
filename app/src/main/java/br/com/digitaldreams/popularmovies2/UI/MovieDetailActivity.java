package br.com.digitaldreams.popularmovies2.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        if (savedInstanceState == null){
            Movie movie = getIntent().getParcelableExtra("movie");

            MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_details_fragment, movieDetailFragment)
                    .commit();
        }
    }
}
