package com.bt.samplerecyclerviewwithcursoradapters;

import android.provider.BaseColumns;

/**
 * Created by Monika on 12/21/2016.
 * Defines all the constants used through out the DB
 */

public class EmployeeDetailContract {
    private EmployeeDetailContract () {}
    public static class Employee implements BaseColumns {
        public static final String TABLE_NAME = "Employee";
        public static final String COLUMN_NAME_E_NAME = "EmployeeName";
        public static final String COLUMN_NAME_D_NAME = "DepartmentName";

        public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME_E_NAME, COLUMN_NAME_D_NAME};

    }
}
