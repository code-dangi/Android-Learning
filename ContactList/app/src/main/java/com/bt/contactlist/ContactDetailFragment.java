package com.bt.contactlist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monika on 11/11/2016.
 * Detail fragment that shows phone number and Name from the contact
 */

public class ContactDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks{
    private static final String TAG = ContactDetailFragment.class.getSimpleName();
    private Uri mContactUri;
    private TextView mContactName;
    private TextView mContactNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    public ContactDetailFragment() {}

    public void setContact(Uri contactLookUpUri) {
        mContactUri = contactLookUpUri;
        getLoaderManager().restartLoader(ContactDetailQuery.QUERY_ID, null, this);
        getLoaderManager().restartLoader(ContactPhoneNumberQuery.QUERY_ID, null, this);
        getLoaderManager().restartLoader(ContactAddressQuery.QUERY_ID, null, this);
    }
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_contact_detail, container, false);
        mContactName = (TextView) view.findViewById(R.id.name);
        mContactNumber = (TextView) view.findViewById(R.id.number);
        if(savedInstanceState != null){
            mContactName.setText(savedInstanceState.getCharSequence(
                    IConstants.BUNDLE_SAVED_INSTANCE_NAME));
            mContactNumber.setText(savedInstanceState.getCharSequence(
                    IConstants.BUNDLE_SAVED_INSTANCE_CONTACT_NUMBER));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            // Sets the argument extra as the currently displayed contact
            setContact(getArguments() != null ?
                    (Uri) getArguments().getParcelable(IConstants.EXTRA_URI) : null);
        } else {
            // If being recreated from a saved state, sets the contact from the incoming
            // savedInstanceState Bundle
            setContact((Uri) savedInstanceState.getParcelable(IConstants.SAVED_DETAIL_URI));
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ContactDetailQuery.QUERY_ID:
                return new CursorLoader(getActivity(), mContactUri, ContactDetailQuery.PROJECTION,
                        null, null, null);
            case ContactPhoneNumberQuery.QUERY_ID:
                final Uri uri = Uri.withAppendedPath(mContactUri, Contacts.Data.CONTENT_DIRECTORY);
                return new CursorLoader(getActivity(), uri, ContactPhoneNumberQuery.PROJECTION,
                        ContactPhoneNumberQuery.SELECTION, null, null);
            case ContactAddressQuery.QUERY_ID:
                final Uri uri1 = Uri.withAppendedPath(mContactUri, Contacts.Data.CONTENT_DIRECTORY);
                return new CursorLoader(getActivity(), uri1, ContactAddressQuery.PROJECTION,
                        ContactAddressQuery.SELECTION, null, null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (mContactUri == null) {
            return;
        }
        Cursor cursor = (Cursor) data;
        switch (loader.getId()) {
            case ContactDetailQuery.QUERY_ID:
                if (cursor.moveToFirst()) {
                    final String contactName = cursor.getString(ContactDetailQuery.DISPLAY_NAME);
                    mContactName.setText(contactName);
                }
                break;
            case ContactPhoneNumberQuery.QUERY_ID:
                if (cursor.moveToFirst()) {
                    final String contactNumber = cursor.getString(ContactPhoneNumberQuery.PHONE_NUMBER);
                    mContactNumber.setText(contactNumber);
                }
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader loader) {
        // nothing to do here
    }

    public interface ContactDetailQuery {
        int QUERY_ID = 2;
        String[] PROJECTION = {Contacts._ID, Contacts.DISPLAY_NAME};
        int ID = 0;
        int DISPLAY_NAME = 1;
    }

    public interface ContactPhoneNumberQuery {
        int QUERY_ID = 3;
        String[] PROJECTION = {CommonDataKinds.Phone._ID, CommonDataKinds.Phone.NUMBER};
        String SELECTION = Data.MIMETYPE + " = '" + CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";
        int PHONE_ID = 0;
        int PHONE_NUMBER = 1;
    }
    /**
     * This interface defines constants used by address retrieval queries.
     */
    public interface ContactAddressQuery {
        // A unique query ID to distinguish queries being run by the
        // LoaderManager.
        final static int QUERY_ID = 4;

        // The query projection (columns to fetch from the provider)
        final static String[] PROJECTION = {
                CommonDataKinds.StructuredPostal._ID,
                CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
                CommonDataKinds.StructuredPostal.TYPE,
                CommonDataKinds.StructuredPostal.LABEL,
        };

        // The query selection criteria. In this case matching against the
        // StructuredPostal content mime type.
        final static String SELECTION =
                Data.MIMETYPE + "='" + CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE + "'";

        // The query column numbers which map to each value in the projection
        final static int ID = 0;
        final static int ADDRESS = 1;
        final static int TYPE = 2;
        final static int LABEL = 3;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(IConstants.SAVED_DETAIL_URI, mContactUri);
    }
}

