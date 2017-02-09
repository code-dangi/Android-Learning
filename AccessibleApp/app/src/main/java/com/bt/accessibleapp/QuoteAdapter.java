package com.bt.accessibleapp;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Monika on 2/3/2017.
 * Recycler View data binding
 */

public class QuoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private ArrayList<String> mQuoteList;
    private ArrayList<String> mAuthorList;
    private LayoutInflater mInflater;

    public QuoteAdapter(Context context, ArrayList<String> quoteList, ArrayList<String> authorList) {
        mQuoteList = quoteList;
        mAuthorList = authorList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.quote_item, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.mQuote.setText(mQuoteList.get(position));
        myHolder.mAuthor.setText(mAuthorList.get(0));
    }

    @Override
    public int getItemCount() {
        return mQuoteList.size();
    }

    @Override
    public void onClick(View v) {
        int radius = (int) Math.hypot(v.getWidth()/2, v.getHeight()/2);
        Animator anim =  ViewAnimationUtils.createCircularReveal(v,
                (int)v.getWidth()/2, (int)v.getHeight()/2, 0, radius);
        anim.start();
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
