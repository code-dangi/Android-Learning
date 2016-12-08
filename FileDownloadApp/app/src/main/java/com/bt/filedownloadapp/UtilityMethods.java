package com.bt.filedownloadapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Monika on 12/6/2016.
 * Utility Methods
 */
public class UtilityMethods {
    /**
     * to copy input stream to output stream
     */
    public static void copyStreams(InputStream in, OutputStream out) {
        byte [] buffer = new byte[1024];
        try {
            int len = in.read(buffer);
            while (len != -1) {
                out.write(buffer);
                len = in.read(buffer, 0 , len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *  Function to check internet connectivity
     * @return true if internet connection is there else false
     */
    public static boolean isConnectedToInternet(Context context) {
        NetworkInfo activeNetwork = null;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting() ;
    }

    /**
     * to check if external storage is available or not
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    /**
     * utility method to convert a file by filepath into bitmap using byte array
     * @param path absolute path to file
     * @return resulted bitmap
     */
    private Bitmap convertFileInputStreamToBitmapUsingByteArray(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        File image = new File(path);
        byte[] buffer = new byte[(int)image.length()];
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(image));
            inputStream.read(buffer, 0, (int)image.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
        return bitmap;
    }
}
