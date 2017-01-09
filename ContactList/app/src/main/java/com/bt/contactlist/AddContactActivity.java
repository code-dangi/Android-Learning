package com.bt.contactlist;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static com.bt.contactlist.IConstants.REQUEST_CODE_ADD_CONTACT;

/**
 * Created by Monika on 12/29/2016.
 * Activity to add new contact
 */

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = AddContactActivity.class.getSimpleName();
    private TextView mName;
    private TextView mPhoneNumber;
    private String mAccountType;
    private String mAccountName;
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
        if (v.getId() == R.id.save_contact) {
            ContentResolver contentResolver = getContentResolver();
            insertContact(contentResolver, mName.getText().toString(), mPhoneNumber.getText().toString());
            /*ContentValues values = new ContentValues();
            values.put(ContactsContract.RawContacts.ACCOUNT_NAME, mAccountName);
            values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, mAccountType);
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
            */
            setResult(REQUEST_CODE_ADD_CONTACT, null);
            finish();
        }
    }
    public static boolean insertContact(ContentResolver contactAdder, String firstName, String mobileNumber) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,firstName).build());

        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI).withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,mobileNumber).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());

        try {
            contactAdder.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private void getAccountDetails() {
        String permission = "android.permission.GET_ACCOUNTS";
        int res = checkCallingOrSelfPermission(permission);
        if(res == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(this).getAccounts();
            for (Account account : accounts) {
                if (account.type.equals("com.google")) {
                    mAccountName = account.name;
                    mAccountType = account.type;
                }
            }
        }

    }

}