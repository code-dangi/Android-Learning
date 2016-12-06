package com.bt.filedownloadapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Monika on 12/5/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Snackbar mSnackbar;
    private View mView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.button_download);
        mView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        downloadImage();
    }
    /**
     * to start intent service
     */
    private void startFileDownload() {
        Intent imageDownloadIntent = new Intent(this, ImageDownloadIntentService.class);
        imageDownloadIntent.putExtra(getResources().getString(R.string.EXTRA_URL), IConstants.URL_STRING);
        imageDownloadIntent.putExtra(getResources().getString(R.string.EXTRA_FILE_NAME), IConstants.FILE_NAME);
        startService(imageDownloadIntent);
    }
    /**
     * showing snackBar when there is no internet connection and keep checking internet
     */
    private void downloadImage() {
        if (UtilityMethods.isConnectedToInternet(this)) {
            if (mSnackbar != null && mSnackbar.isShown()) {
                mSnackbar.dismiss();
            }
            startFileDownload();
        } else {
            mSnackbar = Snackbar
                    .make(mView, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Re-try",this);
            mSnackbar.show();
        }
    }


}
