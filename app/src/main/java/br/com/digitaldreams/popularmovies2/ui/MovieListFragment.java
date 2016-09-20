package br.com.digitaldreams.popularmovies2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.digitaldreams.popularmovies2.models.Movie;
import br.com.digitaldreams.popularmovies2.networking.NetworkingTask;
import br.com.digitaldreams.popularmovies2.networking.FetchMovieRequest;
import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.adapter.MovieRecyclerAdapter;

/**
 * This class is no longer being used
 */
public class MovieListFragment extends Fragment implements NetworkingTask{

    private static final String LOG_TAG = MovieListFragment.class.getSimpleName();
    private GridLayoutManager movieGridLayout;
    private RecyclerView movieRecyclerView;
    private MovieRecyclerAdapter movieRecyclerAdapter;
    private View mView;
    private ArrayList<Movie> movies;
    private boolean mTwoPane = false;


    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")){
            getMovieList();
        }
        else{
            movies = savedInstanceState.getParcelableArrayList("movies");
            movieRecyclerAdapter.notifyDataSetChanged(movies);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putParcelableArrayList("movies", movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_movies, container, false);

        mTwoPane = getArguments().getBoolean("two_pane");

        movieRecyclerView = (RecyclerView) mView.findViewById(R.id.movies_list);
        movieRecyclerView.setHasFixedSize(true);

        int spanCount = getResources().getInteger(R.integer.span_count);
        movieGridLayout = new GridLayoutManager(getActivity(), spanCount);

        movieRecyclerAdapter = new MovieRecyclerAdapter(movies, getContext());

        movieRecyclerView.setLayoutManager(movieGridLayout);
        movieRecyclerView.setAdapter(movieRecyclerAdapter);

        movieRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), movieRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (mTwoPane){
                            Movie movie = movies.get(position);

                            MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(movie);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.movie_details_two_pane_fragment, movieDetailFragment)
                                    .commit();
                        }
                        else {
                            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                            intent.putExtra("movie", movies.get(position));
                            startActivity(intent);
                        }
                    }
                })
        );

         Log.d(LOG_TAG, movies.toString());
        return mView;

    }

    public void getMovieList() {
        FetchMovieRequest movieRequest = new FetchMovieRequest(getContext(), this, "m", null);
        movieRequest.execute();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onFinished(String json, String tag) {
        movies = Movie.parseMovieList(json);
        movieRecyclerAdapter.notifyDataSetChanged(movies);
    }

    @Override
    public void onProgress(Double percentage) {

    }
}
