package com.bt.newsfeedapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Monika on 11/29/2016.
 * Custom adapter to bind view and data
 */

class CustomAdapter extends RecyclerView.Adapter {
    private ArrayList<News> mNewsList;

    /**
     *  constructor for adapter
     */
    private class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        // constructor
        ViewHolder (View view) {
            super(view);
            mView = view;
        }

    }

    /**
     *  constructor for adapter
     * @param newsList array list of all the news items
     */
    CustomAdapter(ArrayList<News> newsList){
        mNewsList = newsList;
    }


    /**
     *  constructor for adapter
     * @param parent parent view
     * @param viewType viewType is set internally
     * @return
     */
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
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
        ((TextView) vh.mView.findViewById(R.id.title)).setText(mNewsList.get(position).getTitle());
        ((TextView) vh.mView.findViewById(R.id.date)).setText(mNewsList.get(position).getDate());
        ((TextView) vh.mView.findViewById(R.id.place)).setText(mNewsList.get(position).getPlace());
    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
