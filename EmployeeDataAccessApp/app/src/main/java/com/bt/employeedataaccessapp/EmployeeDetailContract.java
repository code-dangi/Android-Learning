package com.bt.employeedataaccessapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Monika on 12/19/2016.
 * Defines all the constants used through out the DB
 */

public final class EmployeeDetailContract {
    public static final String AUTHORITY = "com.bt.employeedataaccessapp.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String READ_PERMISSION = "com.bt.employeedataaccessapp.provider.READ_PROVIDER";
    public static final String WRITE_PERMISSION = "com.bt.employeedataaccessapp.provider.WRITE_PROVIDER";
    private EmployeeDetailContract () {}

    public static class Employee implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                EmployeeDetailContract.CONTENT_URI, "employees");
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+
                "/vnd.com.bt.employeedataaccessapp.employees";
        public static final String CONTENT_EMPLOYEE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+
                "/vnd.com.bt.employeedataaccessapp.employee_detail";
        public static final String TABLE_NAME = "Employee";
        public static final String COLUMN_NAME_E_NAME = "EmployeeName";
        public static final String COLUMN_NAME_D_NAME = "DepartmentName";

        public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME_E_NAME, COLUMN_NAME_D_NAME};

    }
}
