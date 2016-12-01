package com.bt.newsfeedapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Monika on 11/28/2016.
 * It shows the list of news downloaded from internet
 */

public class MainActivity extends AppCompatActivity implements DownloadNewsAsyncTask.OnInteractToActivity {
    private RecyclerView mNewsRecyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        String urlString;
        DownloadNewsAsyncTask newsAsyncTask;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mNewsRecyclerView.setHasFixedSize(true);

        // downloading data from internet
        urlString = "https://api.myjson.com/bins/433e5";
        newsAsyncTask = new DownloadNewsAsyncTask(this);
        newsAsyncTask.execute(urlString);

        // linear layout manager
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setting the item decoration
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        if (drawable != null) {
            mNewsRecyclerView.addItemDecoration(new DividerItemDecoration(drawable));
        } else {
            Log.d(TAG, "onCreate: drawable is null");
        }
    }

    @Override
    public void addNewsTosList(ArrayList<News> newses) {
        if(newses != null) {
            mNewsRecyclerView.setAdapter(new NewsRecyclerAdapter(this, newses));
        } else {
            Log.d(TAG, "onCreate : news list is empty");
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
