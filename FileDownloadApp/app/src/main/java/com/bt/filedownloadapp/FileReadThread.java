package com.bt.filedownloadapp;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Monika on 12/12/2016.
 * to set the path for downloaded file
 */

public class FileReadThread extends Thread {
    private String mFilePath;
    private Handler mFileHandler;
    private boolean mIsImage;
    // combine with image thread..
    public void setImagePath(String imagePath) {
        mFilePath =imagePath;
    }

    public void setHandler(Handler handler) {
        mFileHandler = handler;
    }
    public void setIsImage(boolean isImage) {
        mIsImage = isImage;
    }
    @Override
    public void run() {
        if (mIsImage) {
            readImage();
        }
        else {
            readFile();
        }
    }

    private void readFile() {
        Message msg = Message.obtain();
        msg.obj = mFilePath;
        msg.what = IConstants.PDF_MESSAGE_WHAT;
        mFileHandler.sendMessageDelayed(msg, 100);
    }

    private void readImage() {
        Message msg = Message.obtain();
        msg.obj = BitmapFactory.decodeFile(mFilePath);
        msg.what = IConstants.IMAGE_MESSAGE_WHAT;
        mFileHandler.sendMessageDelayed(msg, 300);
    }
}
