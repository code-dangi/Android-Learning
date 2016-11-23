package com.bt.usageofintentflagsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Monika on 11/22/2016.
 * launching Activity
 */

public class ActivityA extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Button button = (Button) findViewById(R.id.button_a);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityB(v);
            }
        });
    }

    /**
     * Launching Activity B on button click
     * @param v
     */
    private void launchActivityB(View v){
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
