package com.bt.newsfeedapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        if(mContext instanceof OnInteractToActivity)
        {
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
        NetworkInfo activeNetwork = null;
        // checking for the connection
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        } else {
            Log.d(TAG, "doInBackground: no connectivity manager");
        }
        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting())
        {
            try {
                newsURL = new URL(strings[0]);
                urlConnection = (HttpURLConnection) newsURL.openConnection();
                InputStream in = urlConnection.getInputStream();
                mResult = readInputStream(in);

            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException io) {
                Log.d(TAG, "doInBackground: io exception occurred");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return readJsonInNewsArray(mResult);
        } else {
            Log.d(TAG, "doInBackground: There is no internet connection");
            return null;
        }
    }

    /**
     * To convert the input stream
     * @param in input stream from the http connection
     * @return converted string from input stream
     */
    private String readInputStream(InputStream in) {
        String result = "";
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line);
            }
            in.close();
            result = sBuilder.toString();
        } catch (Exception e) {
            Log.d(TAG, "readInputStream: buffer reader error");
        }
        return result;
    }

    /**
     * Converting the input string in array of the News
     */

    private ArrayList<News> readJsonInNewsArray(String result) {
        final int mNewsCount = 20;
        ArrayList<News> tempList = new ArrayList<>(mNewsCount);
        //parse JSON data
        try {
            JSONArray jArray = new JSONArray(result);
            Log.i(TAG, "readJsonInNewsArray:"+ result);
            for(int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                tempList.add(new News(jObject.getString("title"), jObject.getString("date"),
                        jObject.getString("place")));
            } // End Loop
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
        return tempList;
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
