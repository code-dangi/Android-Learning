package com.bt.recyclerviewwithoutprefetch.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Monika on 1/11/2017.
 * Utility methods
 */

public class UtilityMethods {
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
