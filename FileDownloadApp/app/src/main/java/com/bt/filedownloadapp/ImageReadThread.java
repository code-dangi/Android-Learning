package com.bt.filedownloadapp;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Monika on 12/9/2016.
 * thread to read image from url
 */

public class ImageReadThread extends Thread {
    private String mImagePath;
    private Handler mImageHandler;

    public void setImagePath(String imagePath) {
        mImagePath =imagePath;
    }

    public void setHandler(Handler handler) {
        mImageHandler = handler;
    }
    @Override
    public void run() {
        Message msg = Message.obtain();
        msg.obj = BitmapFactory.decodeFile(mImagePath);
        mImageHandler.sendMessage(msg);
    }
}
