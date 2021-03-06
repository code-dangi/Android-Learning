package com.bt.contactlist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static android.app.Activity.RESULT_OK;
import static com.bt.contactlist.IConstants.REQUEST_CODE_ADD_CONTACT;

/**
 * Created by Monika on 11/11/2016.
 * Class for list fragment
 */

public class ContactListFragment extends Fragment implements AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private onClickItemListener mOnItemSelectionListener;
    private final String TAG = ContactListFragment.class.getSimpleName();
    private ListView  mListView;
    private CustomAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onClickItemListener) {
            mOnItemSelectionListener = (onClickItemListener) context;
        } else{
            throw new RuntimeException(context.toString()+" should Implement item click listener");
        }
    }
    // fragment require a default constructor
    public ContactListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        view.findViewById(R.id.new_contact).setOnClickListener(this);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new CustomAdapter(getContext());
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
        /*getContactCursor(getActivity().getContentResolver(), "s");*/
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        // Creates a contact lookup Uri from contact ID and lookup_key
        Uri lookupUri = Contacts.getLookupUri(
                cursor.getLong(cursor.getColumnIndexOrThrow(Contacts._ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Contacts.LOOKUP_KEY)));
        mOnItemSelectionListener.onContactSelectionListener(lookupUri);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == ContactsQuery.QUERY_ID) {
            Uri contentUri = ContactsQuery.CONTENT_URI;
            return new CursorLoader(getActivity(), contentUri, ContactsQuery.PROJECTION,
                    ContactsQuery.SELECTION, null, ContactsQuery.SORT_ORDER);
        } else {
            Log.d(TAG, "onCreateLoader: cant create cursor loader");
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == ContactsQuery.QUERY_ID) {
            mAdapter.swapCursor(data);
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        if (loader.getId() == ContactsQuery.QUERY_ID) {
            mAdapter.swapCursor(null);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.new_contact) {
            Intent intent = new Intent(getActivity(), AddContactActivity.class);
            /*intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);*/
            startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == RESULT_OK) {
            getLoaderManager().restartLoader(ContactsQuery.QUERY_ID, null, this);
            showNotification("Contact Added Successfully");
        }
    }

    private void showNotification(String message) {
        Snackbar snackbar = Snackbar.make(mListView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public interface onClickItemListener {
        void onContactSelectionListener(Uri uri);
    }
    /*
    * this interface defines constants for cursor
    * */
    public interface ContactsQuery {
        // An identifier for the loader
        int QUERY_ID = 1;
        Uri CONTENT_URI = Contacts.CONTENT_URI;
        String SELECTION = Contacts.DISPLAY_NAME + "<> '' " + " AND " +
                Contacts.IN_VISIBLE_GROUP + " =1";
        String SORT_ORDER = Contacts.DISPLAY_NAME;
        String[] PROJECTION = {
                Contacts._ID,
                Contacts.LOOKUP_KEY,
                Contacts.DISPLAY_NAME,
                Contacts.PHOTO_THUMBNAIL_URI,
                SORT_ORDER
        };
    }
    // function to read contacts another method
    public void getContactCursor(ContentResolver contactHelper, String startsWith) {
        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
        Cursor cur = null;
        try {
            if (startsWith != null && !startsWith.equals("")) {
                cur = contactHelper.query (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like \"" + startsWith + "%\"", null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            } else {
                cur = contactHelper.query (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            }
            if (cur != null) {
                cur.moveToFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter.swapCursor(cur);
    }
}

