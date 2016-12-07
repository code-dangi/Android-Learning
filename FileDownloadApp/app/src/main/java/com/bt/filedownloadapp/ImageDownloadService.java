package com.bt.filedownloadapp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
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
        String fileName = intent.getExtras().getString(IConstants.EXTRA_FILE_NAME);
        Log.i(TAG, "onHandleIntent:"+getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        final ResultReceiver resultReceiver = intent.getParcelableExtra(IConstants.EXTRA_RECEIVER);
        String defaultLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File fileDir = null;
        File imageFile = null;
        if (UtilityMethods.isExternalStorageReadable()) {
            fileDir = new File(defaultLocation+IConstants.FILE_LOCATION);
            if (fileDir.exists()) {
                fileDir.delete();
            }
            if (!fileDir.mkdirs()) {
                Log.d(TAG, "onHandleIntent: Directory not created");
            }
            imageFile = new File(fileDir, fileName);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        } else {
            Log.d(TAG, "onHandleIntent: external memory is not readable");
            return;
        }
        try {
            URL imageUrl = new URL(imageUrlString);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            if (connection.getResponseCode() != IConstants.REQUEST_OK) {
                throw new RuntimeException("Request for image download was not successful");
            }
            InputStream in = connection.getInputStream();
            FileOutputStream out = new FileOutputStream(imageFile);
            UtilityMethods.copyStreams(in, out);
            Bundle bundle = new Bundle();
            bundle.putString(IConstants.DOWNLOADED_IMAGE, out.toString());
            out.flush();
            out.close();
            in.close();
            resultReceiver.send(IConstants.PROGRESS_FINISH, bundle);
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
