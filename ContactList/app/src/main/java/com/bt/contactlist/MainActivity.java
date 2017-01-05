package com.bt.contactlist;

/**
 * Created by Monika on 11/11/2016.
 * Launching Activity
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.bt.contactlist.com.bt.contactlist.utilities.UtilityMethods;


public class MainActivity extends AppCompatActivity implements ListFragment.onClickItemListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final String TAG_CONTACT_DETAIL_FRAGMENT = "detailFragment";
    private final int CONTACT_READ_WRITE_PERMISSIONS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final String TAG_CONTACT_LIST_FRAGMENT = "contactListFragment";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        if (checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, CONTACT_READ_WRITE_PERMISSIONS);
        } else {
            
        }
        // handle the Rotation of screen
        if(savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(isTablet(this)) {
                ListFragment listFragment = new ListFragment();
                transaction.add(R.id.contact_list_container, listFragment, TAG_CONTACT_LIST_FRAGMENT);
                ContactDetailFragment detailContactFragment = new ContactDetailFragment();
                transaction.add(R.id.detailContainer, detailContactFragment,
                        TAG_CONTACT_DETAIL_FRAGMENT);
                transaction.commit();
            }
            else{
                ListFragment listFragment = new ListFragment();
                transaction.add(R.id.contact_list_container, listFragment, TAG_CONTACT_LIST_FRAGMENT);
                transaction.commit();
            }
        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ListFragment listFragment = new ListFragment();
            transaction.add(R.id.contact_list_container, listFragment, TAG_CONTACT_LIST_FRAGMENT);
            transaction.commit();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
       if (requestCode == CONTACT_READ_WRITE_PERMISSIONS) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // permission granted
           }
       }
    }

    @Override
    public void onContactSelectedListener(Uri uri) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ContactDetailFragment detailFragment =
                (ContactDetailFragment)getSupportFragmentManager()
                        .findFragmentByTag(TAG_CONTACT_DETAIL_FRAGMENT);
        if (detailFragment == null) {
            detailFragment = new ContactDetailFragment();
            Bundle bundle= new Bundle();
            bundle.putParcelable(IConstants.EXTRA_URI, uri);
            detailFragment.setArguments(bundle);
            transaction.replace(R.id.contact_list_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_toolbar, menu);
        super.onCreateOptionsMenu(menu);
        menu.setGroupVisible(R.id.menu_group, false);
        return true;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
