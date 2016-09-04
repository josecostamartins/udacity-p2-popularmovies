package br.com.digitaldreams.popularmovies2.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.models.Movies;


public class MovieDetailFragment extends Fragment {

    private Movies movie;
    private static final String MOVIE_PARAM = "movie";
    private View mView;
    private ListView trailers;

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
//        TextView movieLength = (TextView) mView.findViewById(R.id.movie_length);
        TextView movieOverview = (TextView) mView.findViewById(R.id.movie_overview);

        movieTitle.setText(movie.getTitle());
        Picasso.with(getContext()).load(movie.getMovieImageURL()).into(moviePoster);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movie.getReleaseDate());
        movieYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        movieRating.setText(String.valueOf(movie.getVoteAverage()) + "/10");
//        movieLength.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());

        return mView;
    }

    public void setupView(View view){
        if (view.getId() == R.id.movie_poster){

        }
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
