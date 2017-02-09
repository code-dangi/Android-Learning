package com.bt.accessibleapp;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.accessibleapp.dataobjects.Quote;

import java.util.ArrayList;

/**
 * Created by Monika on 2/4/2017.
 * to bind array of quote object
 */

public class QuoteAdapterSecond extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private ArrayList<Quote> mQuoteList;
    private LayoutInflater mInflater;
    QuoteAdapterSecond(Context context, ArrayList<Quote> quotes) {
        mInflater = LayoutInflater.from(context);
        mQuoteList = quotes;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.quote_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.mQuote.setText(mQuoteList.get(position).getQuote());
        viewHolder.mAuthor.setText(mQuoteList.get(0).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mQuoteList.size();
    }

    @Override
    public void onClick(View v) {
        int radius = (int) Math.hypot(v.getWidth()/2, v.getHeight()/2);
        Animator anim =  ViewAnimationUtils.createCircularReveal(v,
                v.getWidth()/2, v.getHeight()/2, 0, radius);
        anim.start();
    }

    public boolean addToList(Quote q) {
       return mQuoteList.add(q);
    }
    private static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mQuote;
        TextView mAuthor;

        MyViewHolder(View view) {
            super(view);
            mQuote = (TextView) view.findViewById(R.id.tv_quote);
            mAuthor = (TextView) view.findViewById(R.id.author2);
        }
    }
}
