package com.bt.filedownloadapp;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Monika on 12/12/2016.
 * to set the path for downloaded file
 */

public class FileReadThread extends Thread {
    private String mFilePath;
    private Handler mFileHandler;
    // combine with image thread..
    public void setImagePath(String imagePath) {
        mFilePath =imagePath;
    }

    public void setHandler(Handler handler) {
        mFileHandler = handler;
    }
    @Override
    public void run() {
        Message msg = Message.obtain();
        msg.obj = mFilePath;
        msg.what = IConstants.PDF_MESSAGE_WHAT;
        mFileHandler.sendMessageDelayed(msg, 100);
    }
}
