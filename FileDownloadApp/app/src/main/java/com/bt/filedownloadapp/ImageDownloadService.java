package com.bt.filedownloadapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

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
        String fileUrlString = intent.getExtras().getString(IConstants.EXTRA_URL);
        String downloadedFilePath = null;
        final ResultReceiver downloadResultReceiver = intent.getParcelableExtra(IConstants.EXTRA_RECEIVER);
        try {
            URL fileUrl = new URL(fileUrlString);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            int length = connection.getContentLength();
            Log.d(TAG, "onHandleIntent: response code from the request "+ connection.getResponseCode());
            if (connection.getResponseCode() != IConstants.REQUEST_OK_CODE) {
                showToastNotification(IConstants.ERROR_MESSAGE);
                downloadResultReceiver.send(IConstants.DOWNLOAD_ERROR_CODE, null);
            } else {
                InputStream in = connection.getInputStream();
                Bundle bundle = new Bundle();
                downloadedFilePath = UtilityMethods.saveFile(fileUrlString, in, length);
                in.close();
                if (downloadedFilePath == null) {
                    downloadResultReceiver.send(IConstants.DOWNLOAD_ERROR_CODE, null);
                } else {
                    bundle.putString(IConstants.BUNDLE_PATH, downloadedFilePath);
                    SharedPreferences.Editor editor = getSharedPreferences(IConstants.PREFERENCE_NAME, MODE_PRIVATE).edit();
                    editor.putString(fileUrlString, downloadedFilePath);
                    editor.commit();
                    downloadResultReceiver.send(IConstants.CODE_DOWNLOAD_FINISH, bundle);
                }
            }
        } catch (MalformedURLException me) {
            me.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
    private void showToastNotification(final String message) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast successDownloadNotification = Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_SHORT);
                successDownloadNotification.show();
            }
        });
    }
}
