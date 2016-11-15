package com.example.monika.secondapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_name);
        Intent intent =getIntent();
        String message= intent.getStringExtra(MainActivity.EXTRA_NAME);
        TextView textView= new TextView(this);
        textView.setTextSize(20);
        textView.setText(message);
        ViewGroup layout=(ViewGroup) findViewById(R.id.activity_display_name);
        layout.addView(textView);
        Log.d("sec screen", "onCreate: display");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("sec screen", "onStart: display ");

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("sec screen", "onResume:display ");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d("sec screen", "onPause: display");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("sec screen", "onStop:display ");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("sec screen", "onDestroy:display ");
    }
}
