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
 * It shows the list of news
 */

public class MainActivity extends AppCompatActivity implements DownloadNewsAsyncTask.OnInteractToActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<News> mNewsList;
    private DownloadNewsAsyncTask mAsyncTask;
    private String mUrlString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);

        // downloading data from internet
        mUrlString = "https://api.myjson.com/bins/2r8bl";
        mAsyncTask = new DownloadNewsAsyncTask(this);
        mAsyncTask.execute(mUrlString);

        // linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // setting the item decoration
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        if (drawable != null) {
            mRecyclerView.addItemDecoration( new DividerItemDecoration(drawable));
        }
        else {
            Log.d(TAG, "onCreate: drawable is null");
        }
    }

    @Override
    public void addNewsList(ArrayList<News> newses) {
        mNewsList = newses;
        if(mNewsList != null) {
            mRecyclerAdapter = new CustomAdapter( mNewsList);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }
        else {
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
