package com.bt.employeedataaccessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bt.employeedataaccessapp.EmployeeDetailContract.*;

/**
 * Created by Monika on 12/19/2016.
 * Data access helper class
 */

public class EmployeeDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EmployeeDb";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + Employee.TABLE_NAME + "("
            + Employee._ID + " INTEGER PRIMARY KEY, " + Employee.COLUMN_NAME_E_NAME + TEXT_TYPE
            + COMMA_SEP + Employee.COLUMN_NAME_D_NAME + TEXT_TYPE + ")";
    private static final  String SQL_DELETE_TABLE = "DROP TABLE IF NOT EXIST " + Employee.TABLE_NAME;

    public EmployeeDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
