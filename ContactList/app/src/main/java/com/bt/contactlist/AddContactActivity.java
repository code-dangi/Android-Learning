package com.bt.contactlist;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Monika on 12/29/2016.
 * Activity to add new contact
 */

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = AddContactActivity.class.getSimpleName();
    private TextView mName;
    private TextView mPhoneNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        Toolbar childToolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(childToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mName = (TextView) findViewById(R.id.enter_name);
        mPhoneNumber = (TextView) findViewById(R.id.enter_phone);
        findViewById(R.id.save_contact).setOnClickListener(this);
        getAccountDetails();
    }

    @Override
    public void onClick(View v) {

        final String ACCOUNT_TYPE = "phoneContact";
        final String ACCOUNT_NAME = "contactList";
        if (v.getId() == R.id.save_contact) {
            ContentResolver contentResolver = getContentResolver();
            ContentValues values = new ContentValues();
            values.put(ContactsContract.RawContacts.ACCOUNT_NAME, ACCOUNT_NAME);
            values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, ACCOUNT_TYPE);
            Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI,
                    values);
            long rawContactId = ContentUris.parseId(rawContactUri);
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.
                    StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, mName
                    .getText().toString());
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
            values.clear();
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds
                    .Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, mPhoneNumber.getText().
                    toString());
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);
            Log.d(TAG, "onClick: values  added successfully");
        }
        finish();
    }

    private void getAccountDetails() {
        String permission = "android.permission.GET_ACCOUNTS";
        int res = checkCallingOrSelfPermission(permission);
        if(res == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(this).getAccounts();
            for (Account account : accounts) {
                Log.d(TAG, "getAccountDetails: Account Name  "+ account.name);
                Log.d(TAG, "getAccountDetails: Account type  "+ account.type);
            }
        }

    }

}
