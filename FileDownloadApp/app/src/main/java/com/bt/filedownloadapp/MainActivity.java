package com.bt.filedownloadapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Monika on 12/5/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DownloadStatusReceiver.IReceiver{
    private Snackbar mInternetNotificationBar;
    private Button mDownloadButton;
    private ProgressBar mDownloadProgressBar;
    private Handler mHandlerToLoadImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadButton = (Button) findViewById(R.id.button_download);
        mDownloadButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        downloadImage();
    }
    /**
     * to start intent service
     */
    private void startImageDownload() {
        mDownloadButton.setEnabled(false);
        Intent imageDownloadIntent = new Intent(this, ImageDownloadService.class);
        imageDownloadIntent.putExtra(IConstants.EXTRA_URL, IConstants.URL_STRING);
        DownloadStatusReceiver statusReceiver= new DownloadStatusReceiver(new Handler());
        statusReceiver.setReceiver(this);
        imageDownloadIntent.putExtra(IConstants.EXTRA_RECEIVER, statusReceiver);
        mDownloadProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mDownloadProgressBar.setIndeterminate(true);
        mDownloadProgressBar.setVisibility(View.VISIBLE);
        startService(imageDownloadIntent);
    }
    /**
     * showing snackBar when there is no internet connection and keep checking internet
     */
    private void downloadImage() {
        if (UtilityMethods.isConnectedToInternet(this)) {
            if (mInternetNotificationBar != null && mInternetNotificationBar.isShown()) {
                mInternetNotificationBar.dismiss();
            }
            startImageDownload();
        } else {
            mInternetNotificationBar = Snackbar
                    .make(mDownloadButton, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction(IConstants.RE_TRY, this);
            mInternetNotificationBar.show();
        }
    }

    /**
     * Method called by result receiver after completion of download
     * @param resultCode the status of download
     * @param resultData bundle containing byte array of bitmap
     */
    @Override
    public void onReceiveFinish(int resultCode, Bundle resultData) {
        mDownloadProgressBar.setIndeterminate(false);
        mDownloadProgressBar.setVisibility(View.GONE);
        mDownloadButton.setEnabled(true);
        if (resultCode == IConstants.DOWNLOAD_FINISH) {
            loadImageFromPath(resultData.getString(IConstants.BUNDLE_PATH));
            /*byte[] imageByteArray = resultData.getByteArray(IConstants.BUNDLE_BYTE_ARRAY);
            loadImageFromByteArray(imageByteArray);*/
        }
    }

    private void loadImageFromPath(final String imagePath) {
        mHandlerToLoadImage = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ((ImageView) findViewById(R.id.image1)).setImageBitmap((Bitmap) msg.obj);
            }
        };
        Thread loadingThread = new Thread() {
            @Override
            public void run() {
                    Message msg = Message.obtain();
                    msg.obj = BitmapFactory.decodeFile(imagePath);
                    mHandlerToLoadImage.sendMessage(msg);
                }
            };
        loadingThread.start();
    }
    /**
     * Method to load image from bitmap byte array by launching a thread and using handler
     * @param bitmapByteArray input byte array
     */
    private void loadImageFromByteArray(final byte[] bitmapByteArray) {
        mHandlerToLoadImage = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bitmap bmp = (Bitmap) msg.obj;
                ((ImageView) findViewById(R.id.image1)).setImageBitmap(bmp);
            }
        };
        Thread loadingThread = new Thread() {
            @Override
            public void run() {
                if (bitmapByteArray != null) {
                    Message msg = Message.obtain();
                    msg.obj = BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.length);
                    mHandlerToLoadImage.sendMessage(msg);
                }
            }
        };
        loadingThread.start();
    }
}
