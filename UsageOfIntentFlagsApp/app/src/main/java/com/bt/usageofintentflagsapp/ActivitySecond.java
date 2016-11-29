package com.bt.usageofintentflagsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Monika on 11/22/2016.
 * Second Activity
 */

public class ActivitySecond extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        Button button = (Button) findViewById(R.id.button_b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivityC();
            }
        });
    }

    /**
     * Launching Activity C on button click
     * Button View ass parameter
     */
    private void launchActivityC(){
        Intent intent = new Intent(this, ActivityThird.class);
        startActivity(intent);
    }

}
