package com.bt.newsfeedapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Monika on 11/30/2016.
 * Custom class to get the news from internet in Json format using AsyncTask
 */

    public class DownloadNewsAsyncTask extends AsyncTask<String, Void, ArrayList<News> > {
    private static final String TAG = DownloadNewsAsyncTask.class.getSimpleName();
    private Context mContext;
    private OnInteractToActivity mInteractionListener;

    /**
     * to check if the calling activity has implemented the required interface or not
     */
    protected void onPreExecute() {
        if (mContext instanceof OnInteractToActivity) {
            mInteractionListener = (OnInteractToActivity) mContext;
        } else {
            throw new RuntimeException(mContext.toString()+" should Implement activity interact listener");
        }
    }

    public DownloadNewsAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        URL newsURL;
        HttpURLConnection urlConnection = null;
        String mResult = "";
        // checking for the connection
            try {
                newsURL = new URL(strings[0]);
                urlConnection = (HttpURLConnection) newsURL.openConnection();
                InputStream in = urlConnection.getInputStream();
                mResult = UtilityMethods.readInputStream(in);

            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException io) {
                Log.d(TAG, "doInBackground: io exception occurred");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return UtilityMethods.readJsonInNewsArray(mResult);
        }

    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        super.onPostExecute(newses);
        mInteractionListener.addNewsTosList(newses);
    }

    // interface to interact with MainActivity
    public interface OnInteractToActivity {
        void addNewsTosList(ArrayList<News> newses);
    }
}
