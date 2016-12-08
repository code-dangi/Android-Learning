package com.bt.filedownloadapp;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Monika on 12/5/2016.
 * Intent service to download image, for any type of image png or jpg
 */

public class ImageDownloadService extends IntentService {
    private final String TAG = ImageDownloadService.class.getSimpleName();

    /**
     * constructor for Intent Service
     */
    public ImageDownloadService() {
        super(ImageDownloadService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: in intent handler");
        String imageUrlString = intent.getExtras().getString(IConstants.EXTRA_URL);
        Log.i(TAG, "onHandleIntent:"+getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        final ResultReceiver resultReceiver = intent.getParcelableExtra(IConstants.EXTRA_RECEIVER);
        try {
            URL imageUrl = new URL(imageUrlString);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            int length = connection.getContentLength();
            Log.d(TAG, "onHandleIntent: response code from the request "+ connection.getResponseCode());
            if (connection.getResponseCode() != IConstants.REQUEST_OK) {
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast noInternetNotification = Toast.makeText(getApplicationContext(),
                                IConstants.TOAST_MESSAGE, Toast.LENGTH_SHORT);
                        noInternetNotification.show();
                    }
                });            }
            InputStream in = connection.getInputStream();
            Bundle bundle = new Bundle();
            /*bundle.putByteArray(IConstants.BUNDLE_BYTE_ARRAY, UtilityMethods.convertToByteArray(in));*/
            bundle.putString(IConstants.BUNDLE_PATH, UtilityMethods.saveImage(imageUrlString, in, length));
            in.close();
            resultReceiver.send(IConstants.FINISH_DOWNLOAD, bundle);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast noInternetNotification = Toast.makeText(getApplicationContext(),
                            IConstants.TOAST_MESSAGE, Toast.LENGTH_SHORT);
                    noInternetNotification.show();
                }
            });

        } catch (MalformedURLException me) {
            me.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }


    }
}
