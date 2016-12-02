package com.bt.newsfeddappusinghandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import java.util.List;

/**
 * Created by Monika on 12/2/2016.
 * thread to download the news from Internet
 */

public class NewsDownloadThread implements Runnable {
    private final String TAG = NewsDownloadThread.class.getSimpleName();
    private final IDeliverResponse mCallBack;
    private final Handler mHandler;
    private String mNewsUrlString;
    private Context mParentContext;
    private String mResult;


    public interface IDeliverResponse {
        void setResponse(List<News> newsList);
    }

    @Override
    public void run() {
        HttpURLConnection urlConnection = null;
        NetworkInfo activeNetwork = null;
        ArrayList<News> newsList;


        // checking for the connection
        ConnectivityManager connectivityManager = (ConnectivityManager) mParentContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        } else {
            Log.d(TAG, "run: no connectivity manager");
        }
        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting())
        {
            try {
                urlConnection = (HttpURLConnection)  new URL(mNewsUrlString).openConnection();
                InputStream in = urlConnection.getInputStream();
                mResult = readInputStream(in);

            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException io) {
                Log.d(TAG, "run: io exception occurred");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            newsList = readJsonInNewsArray(mResult);
            final Message msg = Message.obtain();
            msg.obj = newsList;
            mHandler.sendMessage(msg);
        } else {
            Log.d(TAG, "doInBackground: There is no internet connection");

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


    /**
     * Constructor
     * @param parentContext get the context from launching activity
     * @param url url to access news
     */
    NewsDownloadThread (Context parentContext, String url, IDeliverResponse callBack) {
        mNewsUrlString = url;
        mParentContext = parentContext;
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mCallBack.setResponse((ArrayList<News>) msg.obj);
            }
        };
    }
}
