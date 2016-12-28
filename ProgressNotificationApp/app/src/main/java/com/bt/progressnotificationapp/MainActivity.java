package com.bt.progressnotificationapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Monika on 12/15/2016.
 * launching activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressSenderService mProgressService;
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mIsBounded;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsBounded = false;
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setMax(10000);
        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent serviceIntent = new Intent(this, ProgressSenderService.class);
        if (getApplicationContext().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "onStart: bind service returned true");
        } else {
            Log.d(TAG, "onStart: bind service returned false");
        }
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ProgressSenderService.LocalBinder localBinder = (ProgressSenderService.LocalBinder) iBinder;
            mProgressService = localBinder.getService();
            if (mProgressService == null) {
                Log.d(TAG, "onServiceConnected: service returned is null");
            }
            mIsBounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsBounded = false;
        }
    };

    @Override
    public void onClick(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setProgress(0);
        int increment = 1;
        while (mProgressBar.getProgress() < 10000) {
            if (mProgressService != null) {
                mProgressBar.setProgress(increment);
                increment++;
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBounded) {
            unbindService(serviceConnection);
            mIsBounded = false;
        }
    }
}
