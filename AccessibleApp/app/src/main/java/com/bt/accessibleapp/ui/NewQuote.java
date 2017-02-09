package com.bt.accessibleapp.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bt.accessibleapp.R;
import com.bt.accessibleapp.dataobjects.Quote;
import com.bt.accessibleapp.db.QuoteContract;
import com.bt.accessibleapp.db.QuotesDbHelper;

import static com.bt.accessibleapp.IConstants.IConstants.EXTRA_QUOTE;

/**
 * Created by Monika on 2/6/2017.
 * add quote
 */

public class NewQuote extends AppCompatActivity implements View.OnClickListener{
    static int mLastQuoteId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);
        findViewById(R.id.save_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        QuotesDbHelper dbHelper = new QuotesDbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        Quote q = new Quote();
        TextView tv = (TextView) findViewById(R.id.quote);
        String quote =  tv.getText().toString();
        tv = (TextView) findViewById(R.id.author3);
        String author;
        author = tv.getText().toString();
        if (!quote.isEmpty() && !author.isEmpty()) {
            cv.put(QuoteContract.Quote.COLUMN_NAME_QUOTE, quote);
            q.setQuote(quote);
            cv.put(QuoteContract.Quote.COLUMN_NAME_AUTHOR_NAME, author);
            q.setAuthor(author);
            cv.put(QuoteContract.Quote.COLUMN_NAME_ID, mLastQuoteId + 1);
            q.setId(mLastQuoteId + 1);
            tv = (TextView) findViewById(R.id.detail);
            quote =  tv.getText().toString().isEmpty() ? "default" : tv.getText().toString();
            cv.put(QuoteContract.Quote.COLUMN_NAME_DETAIL, quote);
            q.setDetail(quote);
            database.insert(QuoteContract.Quote.TABLE_NAME, null, cv);
            Intent i = new Intent();
            i.putExtra(EXTRA_QUOTE, q);
            setResult(RESULT_OK, i);
        } else {
            setResult(RESULT_CANCELED, null);
        }

        finish();
    }
}
