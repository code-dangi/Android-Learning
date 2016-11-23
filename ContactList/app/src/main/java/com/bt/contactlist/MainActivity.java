package com.bt.contactlist;

/**
 * Created by Monika on 11/11/2016.
 * Launching Activity
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements ListFragment.onClickItemListner {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(isTablet(this)) {
                ListFragment fragment1 = new ListFragment();
                transaction.add(R.id.container, fragment1, getResources().getString(R.string.tag_list_frag));
                DetailContactFragment detailContactFragment = new DetailContactFragment();
                transaction.add(R.id.container2, detailContactFragment, getResources().getString(R.string.tag_detail_frag));
                transaction.commit();
            }
            else{
                ListFragment fragment1 = new ListFragment();
                transaction.add(R.id.container, fragment1, getResources().getString(R.string.tag_list_frag));
                transaction.commit();
            }
        }
        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ListFragment fragment1=new ListFragment();
            transaction.add(R.id.container,fragment1,getResources().getString(R.string.tag_list_frag));
            transaction.commit();
        }
    }

    public void onItemClick(Contacts contacts){

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        com.bt.contactlist.DetailContactFragment fragment2 =(com.bt.contactlist.DetailContactFragment)fragmentManager.findFragmentByTag("frag2");
        if(fragment2 == null){
            DetailContactFragment detailContactFragment=new DetailContactFragment();
            Bundle bundle= new Bundle();
            bundle.putString("Name",contacts.getName());
            bundle.putString("Number",contacts.getNumber());
            detailContactFragment.setArguments(bundle);
            transaction.replace(R.id.container,detailContactFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
        else    {
            fragment2.setTextView(contacts.getName(),contacts.getNumber());
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

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
