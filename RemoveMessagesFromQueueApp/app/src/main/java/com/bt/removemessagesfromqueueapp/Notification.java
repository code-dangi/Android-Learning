package com.bt.removemessagesfromqueueapp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by Monika on 12/12/2016.
 * thread to show notification
 */

public class Notification extends Thread {
    private Context mContext;
    private Handler mHandler;

    public Notification(Context context) {
        mContext = context;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast notification = Toast.makeText(mContext,
                        "Hello, this is the toast message after 5 ses", Toast.LENGTH_SHORT);
                Message msg = Message.obtain();
                msg.obj = notification;
                msg.what = 100;
                mHandler.sendMessageDelayed(msg, 5000);
            }
        });

    }
}
