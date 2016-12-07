package com.bt.filedownloadapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
    ProgressBar mDownloadProgressBar;
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
    private void startFileDownload() {
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
            startFileDownload();
        } else {
            mSnackBar = Snackbar
                    .make(mButton, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Re-try",this);
            mSnackBar.show();
        }
    }

    @Override
    public void onReceiveFinish(int resultCode, Bundle resultData) {
        if (resultCode == IConstants.PROGRESS_FINISH) {
            mDownloadProgressBar.setIndeterminate(false);
            mDownloadProgressBar.setVisibility(View.INVISIBLE);
            mButton.setEnabled(true);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap downloadedImage = BitmapFactory.decodeFile(resultData.getString(IConstants.DOWNLOADED_IMAGE), options);
            ((ImageView) findViewById(R.id.image)).setImageBitmap(downloadedImage);
        }
    }

}
