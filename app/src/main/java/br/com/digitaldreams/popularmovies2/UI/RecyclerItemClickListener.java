package br.com.digitaldreams.popularmovies2.UI;

/**
 * Created by josecostamartins on 8/6/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;
    private RecyclerView recyclerView;

    public RecyclerItemClickListener(Context context, RecyclerView view, OnItemClickListener listener) {
        mListener = listener;
        this.recyclerView = view;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                mListener.onItemClick(recyclerView, recyclerView.getChildLayoutPosition(childView));
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {

        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildLayoutPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        view.setSelected(motionEvent.getAction() == motionEvent.ACTION_DOWN);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}

