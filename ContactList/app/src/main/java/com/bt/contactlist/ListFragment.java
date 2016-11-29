package com.bt.contactlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ListFragment extends Fragment {
    private onClickItemListener mItemListener;
    private static final int mContacts = 20; // default no contacts
    private static ArrayList<Contacts> mContact;
    private final String TAG = ListFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof onClickItemListener)
        {
            mItemListener = (onClickItemListener) context;
        }
        else{
            throw new RuntimeException(context.toString()+" should Implement item click listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContact = new ArrayList<Contacts>(mContacts);
        if (savedInstanceState == null) {
            Contacts c;
            for (int i = 0; i < mContacts; i++) {
                c = new Contacts(i);
                mContact.add(c);
            }
        }
        // restore Contacts from the Bundle after Rotation
        else{
            mContact =  (ArrayList<Contacts>) savedInstanceState.getSerializable(getResources().getString(R.string.name_list));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView  mListView = (ListView) view.findViewById(R.id.listView);
        CustomAdapter mAdapter = new CustomAdapter(getActivity(), mContact);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItemListener.onItemClick(mContact.get(position));
            }
        });
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(getResources().getString(R.string.name_list), mContact);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    public interface onClickItemListener {
        void onItemClick(Contacts object);
    }

}

