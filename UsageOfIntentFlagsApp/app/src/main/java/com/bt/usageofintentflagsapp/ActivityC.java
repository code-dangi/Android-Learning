package com.bt.usageofintentflagsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Monika on 11/22/2016.
 * This Activity starts activities having specific intent flag on button click
 */

public class ActivityC extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
    }
    /**
     * Implement onclick and setting all button actions
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_c1:
                launchActivitySingleTop();
                break;
            case R.id.button_c2:
                launchActivityOnSameStack();
                break;
            case R.id.button_c3:
                launchActivityOnNewStack();
                break;
            case R.id.button_c4:
                resumeActivityClearStack();
                break;
            case R.id.button_c5:
                launchActivityClearingStack();
                break;
        }
    }

    /**
     * Launch activity C single Top, No new instance will be created
     */
    private void launchActivitySingleTop(){
        Intent intent = new Intent(this, ActivityC.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /**
     * Launch activity A on the top of same stack
     */
    private void launchActivityOnSameStack(){
        Intent intent = new Intent(this, ActivityC.class);
        startActivity(intent);
    }

    /**
     * Launch Activity in new stack
     */
    private void launchActivityOnNewStack(){
        Intent intent = new Intent(this, ActivityA.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * Launch Activity A, it will clear the stack and new instance will be the root of the stack
     */
    private void launchActivityClearingStack(){
        Intent intent = new Intent(this, ActivityA.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    /**
     * Resume the Activity A and clear the stack top of it
     */
    private void resumeActivityClearStack(){
        Intent intent = new Intent(this, ActivityA.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
