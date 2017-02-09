package com.bt.recyclerviewwithoutprefetch;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bt.recyclerviewwithoutprefetch.constants.IConstants;
import com.bt.recyclerviewwithoutprefetch.model.Image;
import com.bt.recyclerviewwithoutprefetch.utils.UtilityMethods;
import com.bt.recyclerviewwithoutprefetch.widget.CustomAdapter;

import java.util.ArrayList;

/**
 * Created by Monika on 1/11/2017.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity {
    private Snackbar mSnackBar;
    private final String PREFERENCE_NAME = "savePrefetchChoice";
    private RecyclerView mRecyclerView;
    private final String PREFETCH_CHOICE = "isPrefetch";
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean mIsPrefetch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) (findViewById(R.id.app_toolbar)));
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mIsPrefetch = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).getBoolean(PREFETCH_CHOICE, false);
        setPrefetch(mIsPrefetch);
        if (mLayoutManager.isItemPrefetchEnabled()) {
           Snackbar snackbar = Snackbar.make(mRecyclerView, "yes prefetch is enabled", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(mRecyclerView, "prefetch is de-sabled", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        downloadImages();
    }

    private void setPrefetch(boolean b) {
        mLayoutManager.setItemPrefetchEnabled(b);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem prefetch = menu.findItem(R.id.toggle);
        prefetch.setCheckable(true);
        prefetch.setChecked(mIsPrefetch);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit();
        switch (item.getItemId()) {
            case R.id.toggle:
               if (!item.isChecked()) {
                   editor.putBoolean(PREFETCH_CHOICE, true);
                   editor.apply();
               } else {
                  editor.putBoolean(PREFETCH_CHOICE, false);
                   editor.apply();
               }
                return true;
        }
        return false;
    }

    private void downloadImages() {
        if (UtilityMethods.isConnectedToInternet(this)) {
            if (mSnackBar != null && mSnackBar.isShown()) {
                mSnackBar.dismiss();
            }
            // set the adapter here
            ArrayList<Image> images = new ArrayList<>();
            String[] urls = getResources().getStringArray(R.array.url_string_array);
            for (int i = 0; i < urls.length; i++) {
                images.add(new Image(urls[i], "description "+ i));
            }
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(), images);
            mRecyclerView.setAdapter(adapter);

        } else {
            mSnackBar = Snackbar
                    .make(mRecyclerView, IConstants.NO_INTERNET_ERROR, Snackbar.LENGTH_INDEFINITE)
                    .setAction(IConstants.RE_TRY, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadImages();
                        }
                    });
            mSnackBar.show();
        }
    }
}
