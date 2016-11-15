package com.example.monika.fragmentsapp;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.monika.fragmentsapp.dummy.DummyContent;

import static com.example.monika.fragmentsapp.R.id.activity_main;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener,DetailItem.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
     public void onListFragmentInteraction(DummyContent.DummyItem item) {
        DetailItem detailItem = (DetailItem)getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
         if (detailItem != null) {
             // If detailItem is available, we're in two-pane layout...

             // Call a method in the ArticleFragment to update its content
            detailItem.updateView(item);
             Log.d("TAG", "onListFragmentInteraction: detail item is not present in single view only");
         } else {
             DetailItem newDetailItem = DetailItem.newInstance("to be uploaded","wait a min");
             FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
             fragmentTransaction.replace(R.id.activity_main,newDetailItem );
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();

         }
    }
    public void onFragmentInteraction(Uri uri){
        // as there is no interaction on this fragment
    }
}
