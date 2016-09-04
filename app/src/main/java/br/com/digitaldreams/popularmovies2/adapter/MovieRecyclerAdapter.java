package br.com.digitaldreams.popularmovies2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.models.Movies;

/**
 * Created by josecostamartins on 7/24/16.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>{

    private ArrayList<Movies> mDataSet;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.movie_item_image);
        }
    }

    public MovieRecyclerAdapter(ArrayList<Movies> mDataSet, Context mContext) {
        /*if (mDataSet == null){
            throw new NullPointerException("DataSet cannot be null");
        }*/
        if (mContext == null){
            throw new NullPointerException("Context cannot be null");
        }

        this.mDataSet = mDataSet;
        this.mContext = mContext;
    }

    public void notifyDataSetChanged(ArrayList<Movies> mDataSet){
        /*if (mDataSet == null){
            throw new NullPointerException("DataSet cannot be null");
        }*/

        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movies movie = mDataSet.get(position);
        Picasso.with(mContext).load(movie.getMovieImageURL()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (mDataSet != null) {
            return mDataSet.size();
        }
        return 0;
    }
}
