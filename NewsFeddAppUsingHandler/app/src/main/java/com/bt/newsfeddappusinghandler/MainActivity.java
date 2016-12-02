package com.bt.newsfeddappusinghandler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 11/28/2016.
 * It shows the list of news downloaded from internet
 */

public class MainActivity extends AppCompatActivity implements NewsDownloadThread.IDeliverResponse {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mNewsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String urlString;

        NewsDownloadThread newsDownloadThread;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mNewsRecyclerView.setHasFixedSize(true);

        // downloading data from internet
        urlString = "https://api.myjson.com/bins/433e5";
        newsDownloadThread = new NewsDownloadThread(this, urlString, this);
        Thread thread = new Thread(newsDownloadThread);
        thread.start();

        // linear layout manager
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewsRecyclerView.addItemDecoration(new DividerItemDecoration());

    }

    @Override
    public void setResponse(List<News> newsList) {
        if (newsList != null) {
            mNewsRecyclerView.setAdapter(new NewsRecyclerAdapter(this, (ArrayList<News>) newsList));
        } else {
            Log.d(TAG, "onCreate: news list is not assigned well");
        }
    }


    /**
     *  either fetch news feed from internet or default news feed
     */
   /* private void setNewsList() {
        final int mNewsCount = 20;
        mNewsList = new ArrayList<>(mNewsCount);
        for (int i = 0; i < mNewsCount; i++ ) {
            News news = new News(i);
            mNewsList.add(news);
        }
    }*/

}
