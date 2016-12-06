package com.bt.newsfeddappusinghandler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Monika on 11/29/2016.
 * Custom adapter to bind view and data for recyclerView
 */

class NewsRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<News> mNewsList;
    private LayoutInflater mLayoutInflator;
    // View holder class for three TextViews
    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mPlaceTextView;
        // constructor
        ViewHolder (View view) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.title);
            mDateTextView = (TextView) view.findViewById(R.id.date);
            mPlaceTextView = (TextView) view.findViewById(R.id.place);
        }
    }

    /**
     *  constructor for adapter
     * @param newsList array list of all the news items
     */
    NewsRecyclerAdapter(Context context, ArrayList<News> newsList){
        mNewsList = newsList;
        mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /**
     *  inflating the view
     * @param parent parent view
     * @param viewType viewType is set internally
     * @return custom view holder is returned
     */
    @Override
    public NewsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflator.inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    /**
     * override the binding method
     * @param holder view holder
     * @param position position of the data item within the adapter
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        News tempNews = mNewsList.get(position);
        vh.mTitleTextView.setText(tempNews.getTitle());
        vh.mDateTextView.setText(tempNews.getDate());
        vh.mPlaceTextView.setText(tempNews.getPlace());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    /*public void setNewsList(ArrayList<News> mNewsList) {
        this.mNewsList = mNewsList;
        notifyDataSetChanged();
    }*/
}
