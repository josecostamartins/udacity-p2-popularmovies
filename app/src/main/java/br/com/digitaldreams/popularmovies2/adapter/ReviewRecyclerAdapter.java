package br.com.digitaldreams.popularmovies2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.models.Reviews;

/**
 * Created by josecostamartins on 9/10/16.
 */
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder>{

    private ArrayList<Reviews> mDataSet;
    private Context mContext;


    public ReviewRecyclerAdapter(ArrayList<Reviews> mDataSet, Context mContext) {

        if (mContext == null){
            throw new NullPointerException("Context cannot be null");
        }

        this.mDataSet = mDataSet;
        this.mContext = mContext;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView reviewTitle;
        public TextView reviewAuthor;
        public ViewHolder(View v) {
            super(v);
            reviewTitle = (TextView) v.findViewById(R.id.review_content);
            reviewAuthor = (TextView) v.findViewById(R.id.review_author);
        }
    }

    public void notifyDataSetChanged(ArrayList<Reviews> mDataSet){

        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reviews reviews = mDataSet.get(position);
        holder.reviewAuthor.setText("Author: " + reviews.getAuthor());
        holder.reviewTitle.setText(reviews.getContent());
    }

    @Override
    public int getItemCount() {
        if (mDataSet != null) {
            return mDataSet.size();
        }
        return 0;
    }


}
