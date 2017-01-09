package com.bt.contactlist;

/**
 * Created by Monika on 11/11/2016.
 * Launching Activity
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bt.contactlist.com.bt.contactlist.Dialog;

import static com.bt.contactlist.IConstants.REQUEST_CODE_EDIT_CONTACT;


public class MainActivity extends AppCompatActivity implements ContactListFragment
        .onClickItemListener {
    private final String TAG_CONTACT_DETAIL_FRAGMENT = "detailFragment";
    private static final int READ_EXTERNAL_STORAGE_CODE = 5;
    private final int CONTACT_READ_WRITE_PERMISSIONS_CODE = 1;
    private final int NO_CONTACTS_ACCESS_PERMISSION_CODE = 3;
    private final int DELETE_CONTACT_CODE = 4;
    private ContactListFragment mContactListFragment;
    private Menu mActionBar;
    private Uri mContactUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        readContacts();
    }

    private void readContacts() {
        if (checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, CONTACT_READ_WRITE_PERMISSIONS_CODE);
            if  (checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_CODE);
            }
        } else {
            loadContacts();
        }
    }

    private void loadContacts() {
        final String TAG_CONTACT_LIST_FRAGMENT = "contactListFragment";
        // handle the Rotation of screen
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isTablet(this)) {
            mContactListFragment = new ContactListFragment();
            transaction.add(R.id.contact_list_container, mContactListFragment, TAG_CONTACT_LIST_FRAGMENT);
            ContactDetailFragment detailContactFragment = new ContactDetailFragment();
            transaction.add(R.id.detailContainer, detailContactFragment,
                    TAG_CONTACT_DETAIL_FRAGMENT);
            transaction.commit();
        } else{
            mContactListFragment = new ContactListFragment();
            transaction.add(R.id.contact_list_container, mContactListFragment, TAG_CONTACT_LIST_FRAGMENT);
            transaction.commitAllowingStateLoss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CONTACT_READ_WRITE_PERMISSIONS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    loadContacts();
                } else {
                    showDialog("user denied the permissions", NO_CONTACTS_ACCESS_PERMISSION_CODE);
                }
                break;
           /* case READ_EXTERNAL_STORAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                    Log.d(TAG, "onRequestPermissionsResult: read external storage permission granted");
                } else {
                    showDialog("user denied the permissions for accessing photo the contacts " +
                            "will not show contact person photo",
                            NO_EXTERNAL_STORAGE_ACCESS_PERMISSION_CODE);
                }*/
        }

    }

    /**
     * called to show dialog with title
     * @param title title for dialog
     */
    private void showDialog(String title, int code) {
        final String DIALOG_FRAGMENT_TAG = "notificationDialog";
        Dialog dialog = Dialog.newInstance(title, code);
        dialog.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    /**
     * on click of OK button of dialog
     */
    public void doPositiveClick(int identificationCode) {
        switch (identificationCode) {
            case NO_CONTACTS_ACCESS_PERMISSION_CODE:
                finish();
                break;
            case DELETE_CONTACT_CODE:
                getContentResolver().delete(mContactUri, null, null);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();
                fragmentTransaction.replace(R.id.contact_list_container, mContactListFragment);
                fragmentTransaction.commit();
        }
    }

    @Override
    public void onContactSelectionListener(Uri uri) {
        ContactDetailFragment detailFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        detailFragment = (ContactDetailFragment)getSupportFragmentManager()
                .findFragmentByTag(TAG_CONTACT_DETAIL_FRAGMENT);
        mContactUri = uri;
        if (detailFragment == null) {
            detailFragment = new ContactDetailFragment();
            Bundle bundle= new Bundle();
            bundle.putParcelable(IConstants.EXTRA_URI, uri);
            detailFragment.setArguments(bundle);
            transaction.replace(R.id.contact_list_container, detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            mActionBar.setGroupVisible(R.id.menu_group, true);
        } else {
            Bundle bundle= new Bundle();
            bundle.putParcelable(IConstants.EXTRA_URI, uri);
            detailFragment.setArguments(bundle);
            transaction.add(R.id.detailContainer, detailFragment);
            transaction.commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_contact:
                Intent intent = new Intent(Intent.ACTION_EDIT, mContactUri);
                startActivityForResult(intent, REQUEST_CODE_EDIT_CONTACT);
                break;
            case R.id.delete_contact:
                showDialog("Are You Sure ??", DELETE_CONTACT_CODE);
                break;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_toolbar, menu);
        mActionBar = menu;
        super.onCreateOptionsMenu(menu);
        menu.setGroupVisible(R.id.menu_group, false);
        return true;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
