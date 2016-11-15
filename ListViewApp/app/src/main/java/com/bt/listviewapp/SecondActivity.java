package com.bt.listviewapp;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Created by Monika on 11/10/2016.
 */

public class SecondActivity extends AppCompatActivity {
    ArrayAdapter<Contacts> mAdapter;
    static final int nContacts=20;
    static final Contacts[] Contact= new Contacts[nContacts];

    static String name;
    static final String SELECTION = "((" + name + " NOTNULL) AND (" + name + " != '' ))";

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);

        mAdapter = new ArrayAdapter<Contacts>(this, android.R.layout.simple_list_item_1, Contact);
        listView.setAdapter(mAdapter);

    }
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // nothing do ono click
//    }

        @Override
        public void onStart() {
           super.onStart();
        }

        @Override
        public void onStop() {
            super.onStop();
        }
    public SecondActivity(){
        for(int i=0;i<nContacts;i++){
            Contact[i]=new Contacts(i);

        }

    }

}
