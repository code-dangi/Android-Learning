package com.bt.customdialogapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Monika on 11/24/2016.
 * Launching activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_sample).setOnClickListener(this);

    }
    /**
     * set onclick listeners to buttons
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_sample:
                SampleDialog sampleDialog = new SampleDialog();
                sampleDialog.show(getFragmentManager(), getResources().getString(R.string.sample_tag));
                break;
        }
    }
}
