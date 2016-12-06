package com.bt.filedownloadapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Monika on 12/5/2016.
 * Intent service to download image
 */

public class ImageDownloadIntentService extends IntentService {
    private final String TAG = ImageDownloadIntentService.class.getSimpleName();

    /**
     * constructor for Intent Service
     */
    public ImageDownloadIntentService() {
        super("ImageDownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: in intent handler");
        String imageUrlString = intent.getExtras().getString(getResources().getString(R.string.EXTRA_URL));
        String fileName = intent.getExtras().getString(getResources().getString(R.string.EXTRA_FILE_NAME));
        if (fileName != null) {
            try {
                URL imageUrl = new URL(imageUrlString);
                HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("Request for image download was not successful");
                }
                InputStream in = connection.getInputStream();
                FileOutputStream out;
                out =  getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                UtilityMethods.copyStreams(in, out);
                out.flush();
                out.close();
                in.close();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast shortNotification = Toast.makeText(getApplicationContext(),
                                "Download successfully", Toast.LENGTH_SHORT);
                        shortNotification.show();
                    }
                });

            } catch (MalformedURLException me) {
                me.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }

    }


}
