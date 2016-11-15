package com.example.monika.fragmentsapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (findViewById(R.id.activity_main) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
        }
        ItemFragment itemFragment= new ItemFragment();
        itemFragment.setArguments(getIntent().getExtras());

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity_main2, itemFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // replacing the detail fragment after selection of any item

    }
}
