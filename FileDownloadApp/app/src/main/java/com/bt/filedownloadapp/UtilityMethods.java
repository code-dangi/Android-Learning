package com.bt.filedownloadapp;

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
}
