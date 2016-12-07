package com.bt.filedownloadapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Monika on 12/7/2016.
 * class to handle status of download
 */

public class DownloadStatusReceiver extends ResultReceiver {
    private IReceiver mIReceiver;
    public DownloadStatusReceiver(Handler handler) {
        super(handler);
    }
    // set receiver
    public void setReceiver(IReceiver receiver) {
        mIReceiver = receiver;
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mIReceiver != null) {
            mIReceiver.onReceiveFinish(resultCode, resultData);
        }
    }
    public interface IReceiver {
        void onReceiveFinish(int resultCode, Bundle resultData);
    }
}
