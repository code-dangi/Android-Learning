package com.bt.contactlist.com.bt.contactlist.utilities;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Monika on 1/5/2017.
 * class for all utility methods
 */

public class UtilityMethods {

    public static boolean checkAccountPermissions(Context context) {
        String permission = "android.permission.GET_ACCOUNTS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    public static boolean checkContactReadPermissions(Context context) {
        String permission = "android.permission.READ_CONTACTS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    public static boolean checkContactWritePermissions(Context context) {
        String permission = "android.permission.WRITE_CONTACTS";
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}
