package com.bt.progressnotificationapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Monika on 12/15/2016.
 *Service to communicate the progress to main activity
 */

public class ProgressSenderService extends Service {
    private final IBinder localBinder = new LocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }
    public class LocalBinder extends Binder {
        ProgressSenderService getService() {
            return ProgressSenderService.this;
        }
    }
    public int getProgress() {
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return 1;
    }
}
