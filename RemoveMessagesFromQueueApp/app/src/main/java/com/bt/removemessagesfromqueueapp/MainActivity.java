package com.bt.removemessagesfromqueueapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Monika on 12/12/2016.
 * launching activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static Handler mHandler;
    private  Notification mNotification;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.save_button).setOnClickListener(this);
        findViewById(R.id.cancel_button).setOnClickListener(this);
        setHandler();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_button:
                showNotification();
                break;
            case R.id.cancel_button:
                cancelNotification();
        }
    }
    private void setHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Toast toast = (Toast) msg.obj;
                toast.show();
            }
        };
    }
    private void showNotification() {
        mNotification = new Notification(this);
        mNotification.setHandler(mHandler);
        mNotification.start();

    }
    private void cancelNotification() {
        if (mHandler.hasMessages(100)) {
            mHandler.removeMessages(100);
            Toast toast = Toast.makeText(this, "message deleted", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "no messages", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
