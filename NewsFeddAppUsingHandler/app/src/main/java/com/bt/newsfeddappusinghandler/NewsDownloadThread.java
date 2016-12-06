package com.bt.newsfeddappusinghandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
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
    private String mResult;

    public interface IDeliverResponse {
        void setResponse(List<News> newsList);
    }
    /**
     * Constructor
     * @param url url to access news
     */
    NewsDownloadThread (String url, IDeliverResponse callBack) {
        mNewsUrlString = url;
        mCallBack = callBack;
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mCallBack.setResponse((ArrayList<News>) msg.obj);
            }
        };
    }
    @Override
    public void run() {
        HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection)  new URL(mNewsUrlString).openConnection();
                InputStream in = urlConnection.getInputStream();
                mResult = UtilityMethods.readInputStream(in);
            } catch (MalformedURLException ex) {
                Log.e("httptest", Log.getStackTraceString(ex));
            } catch (IOException io) {
                Log.d(TAG, "run: io exception occurred");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            final Message msg = Message.obtain();
            msg.obj = UtilityMethods.readJsonInNewsArray(mResult);
            mHandler.sendMessage(msg);
        }


}
