package com.bt.filedownloadapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
        downloadImageIfInternetConnection();
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
     * showing snackbar when there is no internet connection and keep checking internet
     */
    private void downloadImageIfInternetConnection() {
        mSnackbar = Snackbar
                .make(mView, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                .setAction("Re-try", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickReTry();
                    }
                });
        if (isConnectedToInternet()) {
            startFileDownload();
        } else {
            mSnackbar.show();
        }
    }
    /**
     * onClick snack bar Re-try button
     */
    private void onClickReTry() {
        if (isConnectedToInternet()) {
            if (mSnackbar.isShown()) {
                mSnackbar.dismiss();
            }
            startFileDownload();
        } else {
            mSnackbar = Snackbar
                    .make(mView, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Re-try", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClickReTry();
                        }
                    });
            mSnackbar.show();
        }
    }
    /**
     *  Function to check internet connectivity
     * @return true if internet connection is there else false
     */
    private boolean isConnectedToInternet() {
        NetworkInfo activeNetwork = null;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting() ;
    }

}
