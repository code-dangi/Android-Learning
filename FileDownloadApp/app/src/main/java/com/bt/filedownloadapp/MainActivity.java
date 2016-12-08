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
    private Snackbar mSnackBar;
    private Button mButton;
    private ProgressBar mDownloadProgressBar;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button_download);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        downloadImage();
    }
    /**
     * to start intent service
     */
    private void startImageDownload() {
        mButton.setEnabled(false);
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
            if (mSnackBar != null && mSnackBar.isShown()) {
                mSnackBar.dismiss();
            }
            startImageDownload();
        } else {
            mSnackBar = Snackbar
                    .make(mButton, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction(IConstants.RE_TRY,this);
            mSnackBar.show();
        }
    }

    /**
     * Method called by result receiver after completion of download
     * @param resultCode the status of download
     * @param resultData bundle containing byte array of bitmap
     */
    @Override
    public void onReceiveFinish(int resultCode, Bundle resultData) {
        if (resultCode == IConstants.FINISH_DOWNLOAD) {
            mDownloadProgressBar.setIndeterminate(false);
            mDownloadProgressBar.setVisibility(View.GONE);
            mButton.setEnabled(true);
            byte[] imageByteArray = resultData.getByteArray(IConstants.BUNDLE_BYTE_ARRAY);
            loadImage(imageByteArray);
        }
    }

    /**
     * Method to load image from bitmap byte array by launching a thread and using handler
     * @param bitmapByteArray input byte array
     */
    private void loadImage(final byte[] bitmapByteArray) {
        mHandler = new Handler() {
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
                    mHandler.sendMessage(msg);
                }
            }
        };
        loadingThread.start();
    }
}
