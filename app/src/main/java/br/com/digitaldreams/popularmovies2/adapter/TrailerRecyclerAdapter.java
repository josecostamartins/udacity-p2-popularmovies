package br.com.digitaldreams.popularmovies2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.digitaldreams.popularmovies2.R;
import br.com.digitaldreams.popularmovies2.models.Trailer;

/**
 * Created by josecostamartins on 9/10/16.
 */
public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.ViewHolder>{

    private ArrayList<Trailer> mDataSet;
    private Context mContext;


    public TrailerRecyclerAdapter(ArrayList<Trailer> mDataSet, Context mContext) {

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
        public TextView trailerTitle;
        public ViewHolder(View v) {
            super(v);
            trailerTitle = (TextView) v.findViewById(R.id.trailer_name);
        }
    }

    public void notifyDataSetChanged(ArrayList<Trailer> mDataSet){
        /*if (mDataSet == null){
            throw new NullPointerException("DataSet cannot be null");
        }*/

        this.mDataSet = mDataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = mDataSet.get(position);
        holder.trailerTitle.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        if (mDataSet != null) {
            return mDataSet.size();
        }
        return 0;
    }


}
