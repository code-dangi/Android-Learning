package com.bt.contactlist;

/**
 * Created by Monika on 11/11/2016.
 * Launching Activity
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements ListFragment.onClickItemListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // handle the Rotation of screen
        if(savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(isTablet(this)) {
                ListFragment listFragment = new ListFragment();
                transaction.add(R.id.listContainer, listFragment, getResources().getString(R.string.tag_list_frag));
                DetailContactFragment detailContactFragment = new DetailContactFragment();
                transaction.add(R.id.detailContainer, detailContactFragment, getResources().getString(R.string.tag_detail_frag));
                transaction.commit();
            }
            else{
                ListFragment listFragment = new ListFragment();
                transaction.add(R.id.listContainer, listFragment, getResources().getString(R.string.tag_list_frag));
                transaction.commit();
            }
        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ListFragment listFragment = new ListFragment();
            transaction.add(R.id.listContainer, listFragment, getResources().getString(R.string.tag_list_frag));
            transaction.commit();
        }
    }

    /**
     * on click of the item of contact list
     * @param contacts instance of the contact that is clicked
     */
    public void onItemClick(Contacts contacts){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        com.bt.contactlist.DetailContactFragment detailFragment =
                (com.bt.contactlist.DetailContactFragment)getSupportFragmentManager()
                        .findFragmentByTag(getResources().getString(R.string.tag_detail_frag));
        if(detailFragment == null){
            DetailContactFragment detailContactFragment=new DetailContactFragment();
            Bundle bundle= new Bundle();
            bundle.putString(getResources().getString(R.string.name), contacts.getName());
            bundle.putString(getResources().getString(R.string.number), contacts.getNumber());
            detailContactFragment.setArguments(bundle);
            transaction.replace(R.id.listContainer, detailContactFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
        else    {
            detailFragment.setTextView(contacts.getName(), contacts.getNumber());
            transaction.commit();
        }

    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
