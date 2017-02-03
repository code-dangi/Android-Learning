package com.bt.contactlist;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static com.bt.contactlist.IConstants.REQUEST_CODE_ADD_CONTACT;

/**
 * Created by Monika on 12/29/2016.
 * Activity to add new contact
 */

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {
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
            String Name = mName.getText().toString();
            String PhoneNumber =  mPhoneNumber.getText().toString();
            if (Name.equals("") || PhoneNumber.equals("")) { // use Text Utils to check empty text view
                showNotification("Both Name and Phone number are mandatory");
            } else {
                insertContact(contentResolver, Name, PhoneNumber);
                setResult(REQUEST_CODE_ADD_CONTACT, null);
                finish();
            }
        }
    }

    public boolean insertContact(ContentResolver contactAdder, String firstName, String mobileNumber) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, mAccountType)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, mAccountName).build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract
                        .CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,firstName).build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE).withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,mobileNumber).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        try {
            contactAdder.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) { // no general
            return false;
        }
        return true;
    }

    /**
     * called to show snack bar with title
     * @param title title for dialog
     */
    private void showNotification(String title) {
        Snackbar notification = Snackbar.make(mName, title, Snackbar.LENGTH_SHORT);
        notification.show();
    }
    private void getAccountDetails() {
        String permission = "android.permission.GET_ACCOUNTS";
        int res = checkCallingOrSelfPermission(permission);
        if(res == PackageManager.PERMISSION_GRANTED) {
            Account[] accounts = AccountManager.get(this).getAccounts();
            // check for null
            for (Account account : accounts) {
                if (account.type.equals("com.google")) {
                    mAccountName = account.name;
                    mAccountType = account.type;
                }
            }
        }

    }

    // to add values in contact table one by one
    private void addDetail() {
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
    }
}
