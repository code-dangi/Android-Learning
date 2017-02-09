package com.bt.accessibleapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bt.accessibleapp.db.QuoteContract.Quote.*;

/**
 * Created by Monika on 2/4/2017.
 * DB helper to store and access stored quotes
 */

public class QuotesDbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "quoteDb";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + QuoteContract
            .Quote._ID + " INTEGER PRIMARY KEY, " + QuoteContract.Quote.COLUMN_NAME_QUOTE +
            TEXT_TYPE + COMMA_SEP + QuoteContract.Quote.COLUMN_NAME_AUTHOR_NAME + TEXT_TYPE +
            COMMA_SEP + QuoteContract.Quote.COLUMN_NAME_ID + " INTEGER, " + QuoteContract.Quote
            .COLUMN_NAME_DETAIL + TEXT_TYPE + " )";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF NOT EXIST " + QuoteContract.Quote.TABLE_NAME;

    public QuotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
    }
}
