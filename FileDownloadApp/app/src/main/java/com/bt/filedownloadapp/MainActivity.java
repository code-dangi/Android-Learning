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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Monika on 12/5/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DownloadStatusReceiver.IReceiver{
    private final String TAG = MainActivity.class.getSimpleName();
    private Snackbar mSnackBar;
    private Button mButton;
    private ProgressBar mDownloadProgressBar;
    private Handler mHandler;
    private Bitmap mDownloadedImage;

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
        imageDownloadIntent.putExtra(IConstants.EXTRA_FILE_NAME, IConstants.FILE_NAME);
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
            byte[] imageByteArray = resultData.getByteArray("imageBitmapByteArray");
            if (imageByteArray != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                ((ImageView) findViewById(R.id.image1)).setImageBitmap(bmp);
            }
        }
    }


}
