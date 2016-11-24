package com.bt.usageofdrawable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Monika on 11/23/2016.
 * Launching Activity
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestFocusOnButton();
    }
    /**
     * This function will set button focusable
     */
    private void requestFocusOnButton(){
        Button button = (Button) findViewById(R.id.button_focus);
        button.setFocusableInTouchMode(true);
    }
    /**
     * How to access drawable using java code
     */
    /*private void accessDrawable(){
        Drawable drawable = getResources().getDrawable(R.drawable.android_logo, getTheme());

    }*/
}
