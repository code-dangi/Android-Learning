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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.bt.contactlist.com.bt.contactlist.Dialog;


public class MainActivity extends AppCompatActivity implements ListFragment.onClickItemListener,
        ContactDetailFragment.onDeleteContactListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private final String DIALOG_FRAGMENT_TAG = "notificationDialog";
    private final String TAG_CONTACT_DETAIL_FRAGMENT = "detailFragment";
    private final int CONTACT_READ_WRITE_PERMISSIONS = 1;
    private ContactDetailFragment mDetailFragment;
    private ListFragment mListFragment;
    private Bundle mSavedInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSavedInstance = savedInstanceState;
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        readContacts();

    }

    private void readContacts() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, CONTACT_READ_WRITE_PERMISSIONS);
        } else {
            loadContacts();
        }

    }

    private void loadContacts() {
        final String TAG_CONTACT_LIST_FRAGMENT = "contactListFragment";
        // handle the Rotation of screen
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(isTablet(this)) {
                mListFragment = new ListFragment();
                transaction.add(R.id.contact_list_container, mListFragment, TAG_CONTACT_LIST_FRAGMENT);
                ContactDetailFragment detailContactFragment = new ContactDetailFragment();
                transaction.add(R.id.detailContainer, detailContactFragment,
                        TAG_CONTACT_DETAIL_FRAGMENT);
                transaction.commit();
            }
            else{
                mListFragment = new ListFragment();
                transaction.add(R.id.contact_list_container, mListFragment, TAG_CONTACT_LIST_FRAGMENT);
                transaction.commit();
            }
         /*else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mListFragment = new ListFragment();
            transaction.add(R.id.contact_list_container, mListFragment, TAG_CONTACT_LIST_FRAGMENT);
            transaction.commit();
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
       if (requestCode == CONTACT_READ_WRITE_PERMISSIONS) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // permission granted
               loadContacts();
           } else {
               showDialog("user denied the permissions");
           }
       }
    }

    /**
     * called to show dialog with title
     * @param title title for dialog
     */
    private void showDialog(String title) {
        Dialog dialog = Dialog.newInstance(title);
        dialog.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    /**
     * on click of OK button of dialog
     */
    public void doPositiveClick() {
        finish();
    }

    @Override
    public void onContactSelectedListener(Uri uri) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         mDetailFragment = (ContactDetailFragment)getSupportFragmentManager()
                        .findFragmentByTag(TAG_CONTACT_DETAIL_FRAGMENT);
        if (mDetailFragment == null) {
            mDetailFragment = new ContactDetailFragment();
            Bundle bundle= new Bundle();
            bundle.putParcelable(IConstants.EXTRA_URI, uri);
            mDetailFragment.setArguments(bundle);
            mDetailFragment.setOnDeleteListener(this);
            transaction.replace(R.id.contact_list_container, mDetailFragment);
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

    @Override
    public void onDelete() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mDetailFragment != null) {
           fragmentManager.saveFragmentInstanceState(mListFragment);
        }
        showDialog("Are You Sure ??");
    }
}
