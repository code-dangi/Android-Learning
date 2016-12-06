package com.bt.newsfeedapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Monika on 11/28/2016.
 * It shows the list of news downloaded from internet
 */

public class MainActivity extends AppCompatActivity implements DownloadNewsAsyncTask.OnInteractToActivity {
    private RecyclerView mNewsRecyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Snackbar mSnackbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewsRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mNewsRecyclerView.setHasFixedSize(true);
        downloadNews();
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

    /**
     * Method to start async task after checking the internet connection
     */
    private void downloadNews() {
        DownloadNewsAsyncTask newsAsyncTask;
        if (UtilityMethods.isConnectedToInternet(this)) {
            if (mSnackbar != null && mSnackbar.isShown()) {
                mSnackbar.dismiss();
            }
            newsAsyncTask = new DownloadNewsAsyncTask(this);
            newsAsyncTask.execute(IConstants.URL_STRING);
        } else {
            mSnackbar = Snackbar
                    .make(mNewsRecyclerView, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Re-try", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadNews();
                        }
                    });
            mSnackbar.show();
        }
    }

    @Override
    public void addNewsTosList(ArrayList<News> newses) {
        if (newses != null) {
            mNewsRecyclerView.setAdapter(new NewsRecyclerAdapter(this, newses));
        } else {
            Log.d(TAG, "onCreate : news list is empty");
        }
    }


}
