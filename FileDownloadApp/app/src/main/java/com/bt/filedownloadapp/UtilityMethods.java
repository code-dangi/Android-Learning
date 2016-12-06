package com.bt.filedownloadapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
