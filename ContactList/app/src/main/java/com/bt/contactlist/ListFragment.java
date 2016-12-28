package com.bt.contactlist;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.*;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;

/**
 * Created by Monika on 11/11/2016.
 * Class for list fragment
 */

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks {
    private onClickItemListener mOnItemSelectionListener;
    private static ArrayList<Contact> mContact;
    private final String TAG = ListFragment.class.getSimpleName();
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
    public ListFragment () {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mContact = new ArrayList<Contact>(mContacts);
        if (savedInstanceState == null) {
            Contact c;
            for (int i = 0; i < mContacts; i++) {
                c = new Contact(i);
                mContact.add(c);
            }
        }
        // restore Contact from the Bundle after Rotation
        else{
            mContact =  (ArrayList<Contact>) savedInstanceState.getSerializable(getResources().getString(R.string.name_list));
        }*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new CustomAdapter(getContext());
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        // Creates a contact lookup Uri from contact ID and lookup_key
        final Uri uri = Contacts.getLookupUri(
                cursor.getLong(ContactsQuery.ID),
                cursor.getString(ContactsQuery.LOOK_UP_KEY));
        mOnItemSelectionListener.onContactSelectedListener(uri);
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
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == ContactsQuery.QUERY_ID) {
            mAdapter.swapCursor((Cursor) data);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        if (loader.getId() == ContactsQuery.QUERY_ID) {
            mAdapter.swapCursor(null);
        }
    }

    public interface onClickItemListener {
        void onContactSelectedListener(Uri uri);
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
                SORT_ORDER
        };
        // for fast look up assign int value to each column
        int ID = 0;
        int LOOK_UP_KEY = 1;
        int DISPLAY_NAME = 2;
        int SORT_KEY = 3;


    }
}

