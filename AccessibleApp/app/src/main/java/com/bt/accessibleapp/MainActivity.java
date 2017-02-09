package com.bt.accessibleapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bt.accessibleapp.dataobjects.Quote;
import com.bt.accessibleapp.db.QuoteContract;
import com.bt.accessibleapp.db.QuotesDbHelper;
import com.bt.accessibleapp.services.MyAccessibilityService;
import com.bt.accessibleapp.ui.NewQuote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static com.bt.accessibleapp.IConstants.IConstants.ACTION;
import static com.bt.accessibleapp.IConstants.IConstants.EXTRA_QUOTE;
import static com.bt.accessibleapp.IConstants.IConstants.EXTRA_SPEAK_OPTION;
import static com.bt.accessibleapp.IConstants.IConstants.SERVICE_MESSAGE;
import static com.bt.accessibleapp.IConstants.IConstants.START_SPEAK;
import static com.bt.accessibleapp.IConstants.IConstants.STOP_SPEAK;

/**
 * Created by Monika on 2/3/2017.
 * Launching app
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener{
    private static final int MY_DATA_CHECK_CODE = 1;
    private RecyclerView mQuoteList;
    private final int REQUEST_CODE = 2;
    private QuoteAdapterSecond mQuoteAdapter;
    private TextToSpeech mTts;
    private BroadcastReceiver mBroadcastReceiver;
    private static final String SPEAK_ID = "com.bt.accessibileapp.speakId";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_extended);
        CollapsingToolbarLayout cb = (CollapsingToolbarLayout) findViewById(R.id.collapsing_app_bar);
        cb.setTitle(getString(R.string.app_name));
        cb.setCollapsedTitleTextColor(Color.BLACK);
        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        mQuoteList = (RecyclerView) findViewById(R.id.quote_list);
        mQuoteList.setLayoutManager(new LinearLayoutManager(this));
        ImageView imageView = (ImageView) findViewById(R.id.fab3);
        imageView.setOnClickListener(this);
        checkTalkToSpeech();
        getData();
        startAccessibleService();
        setReceiver();
    }

    private void setReceiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int option = intent.getIntExtra(EXTRA_SPEAK_OPTION, 0);
                if ( option == START_SPEAK) {
                    mTts.speak(intent.getStringExtra(SERVICE_MESSAGE), TextToSpeech.QUEUE_ADD, null, SPEAK_ID);
                } else if (option == STOP_SPEAK) {
                    mTts.stop();
                }
            }
        };
    }
    /**
     * Registers the phone state observing broadcast receiver.
     */
    private void registerBroadCastReceiver() {
        // Create a filter with the broadcast intents we are interested in.
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // Register for broadcasts of interest.
        registerReceiver(mBroadcastReceiver, filter, null, null);
    }
    private void startAccessibleService() {
        Intent i = new Intent(this, MyAccessibilityService.class);
        startService(i);
    }

    private void checkTalkToSpeech() {
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(ACTION));
    }

    private void getData() {
        QuotesDbHelper dbHelper = new QuotesDbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(QuoteContract.Quote.TABLE_NAME);
        Cursor cursor = queryBuilder.query(database, QuoteContract.Quote.PROJECTION_ALL, null,
                null, null, null, null);
        ArrayList<Quote> quoteList = new ArrayList<>();
        Quote quote;
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                quote = new Quote();
                quote.setQuote(cursor.getString(cursor.getColumnIndexOrThrow(QuoteContract.Quote
                        .COLUMN_NAME_QUOTE)));
                quote.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(QuoteContract.Quote
                        .COLUMN_NAME_AUTHOR_NAME)));
                quote.setDetail(cursor.getString(cursor.getColumnIndexOrThrow(QuoteContract.Quote
                        .COLUMN_NAME_DETAIL)));
                quote.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QuoteContract.Quote
                        .COLUMN_NAME_ID)));
                quoteList.add(quote);
                cursor.moveToNext();
            }
            if (!quoteList.isEmpty()) {
                mQuoteAdapter = new QuoteAdapterSecond(this, quoteList);
                mQuoteList.setAdapter(mQuoteAdapter);
            } else {
                showNotification("Database return empty list check first",
                        Snackbar.LENGTH_INDEFINITE);
            }
        } else {
            //setAdapter();
            showNotification("No data to display add first", Snackbar.LENGTH_INDEFINITE);
        }

    }

    private void setAdapter() {
        ArrayList<String> quote_list = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.quote_array)));
        ArrayList<String> author_list = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.author_array)));
        mQuoteList.setAdapter(new QuoteAdapter(this, quote_list, author_list));
    }

    @Override
    public void onClick(View v) {
        // launch new activity to get input from user new quote and author
        startActivityForResult(new Intent(this, NewQuote.class), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ( requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Snackbar snackBar = Snackbar.make(mQuoteList, "Added successfully", Snackbar.LENGTH_SHORT);
            snackBar.show();
            Quote q = data.getParcelableExtra(EXTRA_QUOTE);
            if (mQuoteAdapter == null) {
                mQuoteAdapter = new QuoteAdapterSecond(this, new ArrayList<Quote>());
            }
            mQuoteAdapter.addToList(q);
            mQuoteAdapter.notifyDataSetChanged();
        } else if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, this);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
            if (mTts.isLanguageAvailable(Locale.UK) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                mTts.setLanguage(Locale.UK);
                showNotification("Language locale UK is set", Snackbar.LENGTH_SHORT);
            } else {
                showNotification("Can not use Locale Uk Try different Local", Snackbar.LENGTH_SHORT);
            }
        }

        //super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onInit(int status) {
        // Called to signal the completion of the TextToSpeech engine initialization
        showNotification("text to speech is ready", Snackbar.LENGTH_SHORT);
        registerBroadCastReceiver();
    }
   private void showNotification(String message, int length) {
       Snackbar snackBar = Snackbar.make(mQuoteList, message, length);
       snackBar.show();
   }

    @Override
    protected void onDestroy() {
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }
}
