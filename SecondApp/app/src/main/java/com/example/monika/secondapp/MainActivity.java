package com.example.monika.secondapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.name;
import static android.R.attr.supportsAssist;

public class MainActivity extends AppCompatActivity {
    static final String EXTRA_NAME = "com.example.EXTRA_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("my app", "onCreate: main activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("my app", "onRestart:main activity ");
    }

    @Override
    protected void onStart(){
     super.onStart();
        Log.d("my app", "onStart: main activity");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("my app", "onResume: main activity");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("my app", "onPause: main activity");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("my app", "onStop:  main activity");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("my app", "onDestroy: main activity");
    }
    public void display(View view)
    {
        Intent intent = new Intent(this, DisplayName.class);
        //Button button = (Button) findViewById(R.id.button1);
        EditText editText= (EditText) findViewById(R.id.textView);
        String FirstName= editText.getText().toString();
        intent.putExtra(EXTRA_NAME, FirstName);
        startActivity(intent);
    }

}
