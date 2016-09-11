package br.com.digitaldreams.popularmovies2.UI;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.digitaldreams.popularmovies2.Interface.NetworkingTask;
import br.com.digitaldreams.popularmovies2.Networking.FetchMovieRequest;
import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.adapter.ReviewRecyclerAdapter;
import br.com.digitaldreams.popularmovies2.adapter.TrailerRecyclerAdapter;
import br.com.digitaldreams.popularmovies2.models.Movies;
import br.com.digitaldreams.popularmovies2.models.Reviews;
import br.com.digitaldreams.popularmovies2.models.Trailers;


public class MovieDetailFragment extends Fragment implements NetworkingTask {

    private Movies movie;
    private static final String MOVIE_PARAM = "movie";
    private View mView;
    private RecyclerView trailersRecyclerView;
    private TrailerRecyclerAdapter trailerRecyclerAdapter;
    private ArrayList<Trailers> trailersArrayList;
    private RecyclerView reviewsRecyclerView;
    private ReviewRecyclerAdapter reviewRecyclerAdapter;
    private ArrayList<Reviews> reviewsArrayList;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(Movies movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_PARAM, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable(MOVIE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.movies_detail_item, container, false);
        TextView movieTitle = (TextView) mView.findViewById(R.id.movie_title);
        ImageView moviePoster = (ImageView) mView.findViewById(R.id.movie_poster);
        TextView movieYear = (TextView) mView.findViewById(R.id.movie_year);
        TextView movieRating = (TextView) mView.findViewById(R.id.movie_rating);
        TextView movieOverview = (TextView) mView.findViewById(R.id.movie_overview);

        trailersArrayList = new ArrayList<>();
        trailerRecyclerAdapter = new TrailerRecyclerAdapter(trailersArrayList, getActivity());

        trailersRecyclerView = (RecyclerView) mView.findViewById(R.id.trailers_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        trailersRecyclerView.setLayoutManager(linearLayoutManager);
        trailersRecyclerView.setAdapter(trailerRecyclerAdapter);

        reviewsArrayList = new ArrayList<>();
        reviewRecyclerAdapter = new ReviewRecyclerAdapter(reviewsArrayList, getActivity());

        reviewsRecyclerView = (RecyclerView) mView.findViewById(R.id.reviews_list_view);
        LinearLayoutManager reviewsLinearLayoutManager = new LinearLayoutManager(getActivity());
        reviewsRecyclerView.setLayoutManager(reviewsLinearLayoutManager);
        reviewsRecyclerView.setAdapter(reviewRecyclerAdapter);

        movieTitle.setText(movie.getTitle());
        Picasso.with(getContext()).load(movie.getMovieImageURL())
                .placeholder(R.drawable.popcorn)
                .error(R.drawable.caution)
                .into(moviePoster);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movie.getReleaseDate());
        movieYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        movieRating.setText(String.valueOf(movie.getVoteAverage()) + "/10");
        movieOverview.setText(movie.getOverview());

        getMovieTrailers();
        getMovieReviews();


        trailersRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), trailersRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                    watchYoutubeVideo(trailersArrayList.get(position).getKey());
                    }
                })
        );


        return mView;
    }

    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);

        }
    }

    public void getMovieReviews() {
        try {
            FetchMovieRequest movieRequest = new FetchMovieRequest(getActivity(), this, "r", movie); //is this ok?
            movieRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMovieTrailers() {
        try {
            FetchMovieRequest movieRequest = new FetchMovieRequest(getActivity(), this, "t", movie); //is this ok?
            movieRequest.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinished(String json, String tag) {
        if (tag.equalsIgnoreCase("t")) {
            trailersArrayList = Trailers.parseTrailerList(json);
            trailerRecyclerAdapter.notifyDataSetChanged(trailersArrayList);
        }
        else{
            reviewsArrayList = Reviews.parseReviewsList(json);
            reviewRecyclerAdapter.notifyDataSetChanged(reviewsArrayList);
        }

    }

    @Override
    public void onProgress(Double percentage) {

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
