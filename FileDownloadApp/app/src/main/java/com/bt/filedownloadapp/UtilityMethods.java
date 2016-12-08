package com.bt.filedownloadapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Monika on 12/6/2016.
 * Utility Methods
 */
public class UtilityMethods {
    private static final String TAG = "UtilityMethods";
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
     * function to convert input stream to bitmap and bitmap to byte array
     */
    public static byte[] convertToByteArray(InputStream in) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    /**
     * utility method to save input stream into a file at external storage
     */
    public static String saveImage(String imageUrlString, InputStream in, int len) {
        File fileDir = null;
        File imageFile = null;
        String fileName = null;
        try {
            fileName = imageUrlString.substring(imageUrlString.lastIndexOf("/") + 1);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "saveImage: " + fileName);
        if (UtilityMethods.isExternalStorageReadable()) {
            String defaultLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            fileDir = new File(defaultLocation + IConstants.FILE_LOCATION);
            if (fileDir.exists()) {
                fileDir.delete();
            }
            fileDir.mkdirs();
            if (fileName != null) {
                imageFile = new File(fileDir, fileName);
                Log.d(TAG, "saveImage: file location " + imageFile.getAbsolutePath());
            }

            if (imageFile.exists()) {
                imageFile.delete();
            }

        } else {
            Log.d(TAG, "saveImage: check read and write permissions again");
            return null;
        }
        DataInputStream dataInputStream = new DataInputStream(in);
        byte[] buffer = new byte[len];
        try {
            dataInputStream.readFully(buffer);
            dataInputStream.close();
            if (buffer.length > 0) {
                DataOutputStream out;
                FileOutputStream fos = new FileOutputStream(imageFile);
                out = new DataOutputStream(fos);
                out.write(buffer);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile.getAbsolutePath();
        /*need to add some code into the service to make it work, create new urlconnection
        * int length = connection.getContentLength();
        * connection = (HttpURLConnection) imageUrl.openConnection();
        * and call this function only setting the file paths like:
        * */
    }
}
