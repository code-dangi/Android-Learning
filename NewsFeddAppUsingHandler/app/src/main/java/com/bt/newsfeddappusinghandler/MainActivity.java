package com.bt.newsfeddappusinghandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.bt.newsfeddappusinghandler.R.layout.activity_main;

/**
 * Created by Monika on 11/28/2016.
 * It shows the list of news downloaded from internet
 */

public class MainActivity extends AppCompatActivity implements NewsDownloadThread.IDeliverResponse {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mNewsRecyclerView;
    private Snackbar mSnackbar;
    private NewsDownloadThread mNewsDownloadThread;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        mNewsRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mNewsRecyclerView.setHasFixedSize(true);
        downloadNews();

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
     * showing snackBar when there is no internet connection and keep checking internet
     */
    private void downloadNews() {
        if (isConnectedToInternet()) {
            if (mSnackbar != null && mSnackbar.isShown()) {
                mSnackbar.dismiss();
            }
            mNewsDownloadThread = new NewsDownloadThread(IConstants.URL_STRING, this);
            Thread thread = new Thread(mNewsDownloadThread);
            thread.start();
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
    /**
     *  Function to check internet connectivity
     * @return true if internet connection is there else false
     */
    private boolean isConnectedToInternet() {
        NetworkInfo activeNetwork = null;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        } else {
            Log.d(TAG, "run: no connectivity manager");
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting() ;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
