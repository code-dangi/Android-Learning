package com.bt.readsdcardfiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Monika on 12/8/2016.
 * sample app to access the sdcard
 */

public class MainActivity extends AppCompatActivity {
    Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/img7.png";
        loadImage(path);
    }
    private void loadImage(final String path) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
               Bitmap bmp = (Bitmap) msg.obj;
                if (bmp != null) {
                    ((ImageView)findViewById(R.id.image1)).setImageBitmap(bmp);
                }
            }
        };
        Thread thread = new Thread() {
            @Override
            public void run() {
                File image = new File(path);
                if (image.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap != null) {
                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        };
        thread.start();
    }
}
