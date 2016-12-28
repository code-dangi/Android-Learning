package com.bt.employeedataaccessapp.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bt.employeedataaccessapp.EmployeeDbHelper;
import com.bt.employeedataaccessapp.EmployeeDetailContract;

/**
 * Created by Monika on 12/20/2016.
 * provider to access employee data stored in database
 */

public class EmployeeDataProvider extends ContentProvider {
    private EmployeeDbHelper mDbHelper;
    private SQLiteDatabase mDataBase;
    private static final int EMPLOYEE_LIST = 1;
    private static final int EMPLOYEE_DETAIL = 2;
    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(EmployeeDetailContract.AUTHORITY, "employees", EMPLOYEE_LIST);
        URI_MATCHER.addURI(EmployeeDetailContract.AUTHORITY, "employees/#", EMPLOYEE_DETAIL);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new EmployeeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        if (URI_MATCHER.match(uri) != EMPLOYEE_LIST) {
            throw new IllegalArgumentException("unsupported uri by the application" + uri);
        } else {
            mDataBase = mDbHelper.getWritableDatabase();
            long id = mDataBase.insert(
                    EmployeeDetailContract.Employee.TABLE_NAME, null, contentValues);
            if (id > 0) {
                Uri idAppendedUri = ContentUris.withAppendedId(uri, id);
                Context context = getContext();
                if (context != null) {
                    ContentResolver contentResolver = context.getContentResolver();
                    contentResolver.notifyChange(idAppendedUri, null);
                }
                return idAppendedUri;
            }
            throw new SQLException("problem inserting Uri "+uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case EMPLOYEE_LIST:
                return EmployeeDetailContract.Employee.CONTENT_TYPE;
            case EMPLOYEE_DETAIL:
                return EmployeeDetailContract.Employee.CONTENT_EMPLOYEE_TYPE;
            default:
                return null;
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        mDataBase = mDbHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case EMPLOYEE_LIST:
                queryBuilder.setTables(EmployeeDetailContract.Employee.TABLE_NAME);
                break;
            default:
                Log.d("provider", "query: no match to uri");
        }
        return queryBuilder.query(mDataBase, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }
}
