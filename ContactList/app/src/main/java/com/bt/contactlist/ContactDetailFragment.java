package com.bt.contactlist;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.bt.contactlist.IConstants.REQUEST_CODE_EDIT_CONTACT;

/**
 * Created by Monika on 11/11/2016.
 * Detail fragment that shows phone number and Name from the contact
 */

public class ContactDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = ContactDetailFragment.class.getSimpleName();
    private Uri mContactUri;
    private TextView mContactName;
    private TextView mContactNumber;
    private ImageView mDisplayPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    public ContactDetailFragment() {}

    public void setContact(Uri contactLookUpUri) {
        mContactUri = contactLookUpUri;
        loadDisplayImage(contactLookUpUri);
        getLoaderManager().restartLoader(ContactDetailQuery.QUERY_ID, null, this);
        getLoaderManager().restartLoader(ContactPhoneNumberQuery.QUERY_ID, null, this);
    }

    private void loadDisplayImage(Uri contactLookUpUri) {
        Uri displayPhotoUri = Uri.withAppendedPath(contactLookUpUri, Contacts.Photo.DISPLAY_PHOTO);
            Glide.with(getActivity()).load(displayPhotoUri).into(mDisplayPhoto); // check getActivity for null
       /* if (mDisplayPhoto.getDrawable() == null) {
            mDisplayPhoto.setImageResource(R.mipmap.ic_launcher_contact);
        }*/
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_contact_detail, container, false);
        mContactName = (TextView) view.findViewById(R.id.name);
        mContactNumber = (TextView) view.findViewById(R.id.number);
        mDisplayPhoto = (ImageView) view.findViewById(R.id.contact_photo);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT_CONTACT) {
            showNotification("Edited successfully");

        }
    }
    private void showNotification(String message) {
        Snackbar snackbar = Snackbar.make(mContactNumber, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ContactDetailQuery.QUERY_ID:
                return new CursorLoader(getActivity(), mContactUri, ContactDetailQuery.PROJECTION,
                        null, null, null);
            case ContactPhoneNumberQuery.QUERY_ID:
                final Uri uri = Uri.withAppendedPath(mContactUri, Contacts.Data.CONTENT_DIRECTORY);
                return new CursorLoader(getActivity(), uri, ContactPhoneNumberQuery.PROJECTION,
                        ContactPhoneNumberQuery.SELECTION, null, null);

        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mContactUri == null) {
            return;
        }
        // check for data null
        switch (loader.getId()) {
            case ContactDetailQuery.QUERY_ID:
                if (data.moveToFirst()) {
                    final String contactName = data.getString(ContactDetailQuery.DISPLAY_NAME);
                    mContactName.setText(contactName);
                }
                break;
            case ContactPhoneNumberQuery.QUERY_ID:
                if (data.moveToFirst()) {
                    final String contactNumber = data.getString(ContactPhoneNumberQuery.PHONE_NUMBER);
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
        int DISPLAY_NAME = 1;
    }

    public interface ContactPhoneNumberQuery {
        int QUERY_ID = 3;
        String[] PROJECTION = {CommonDataKinds.Phone._ID, CommonDataKinds.Phone.NUMBER};
        String SELECTION = Data.MIMETYPE + " = '" + CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'";
        int PHONE_NUMBER = 1;
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(IConstants.SAVED_DETAIL_URI, mContactUri);
    }
}

