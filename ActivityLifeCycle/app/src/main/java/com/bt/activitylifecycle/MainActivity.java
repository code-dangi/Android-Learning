package com.bt.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * This application sends launch new activity on button click and communicate with new activity
 * by sending data and getting changed data back
 * Created by Monika on 11/17/2016.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onCreate");
    }

    /**
     * Method invokes when user click the button to send text to new activity
     * @param view
     */
    public void sendText(View view){
        Intent intent = new Intent(this, DisplayNameActivity.class);
        EditText editText = (EditText) findViewById(R.id.name);
        intent.putExtra(Constants.EXTRA_NAME, editText.getText().toString());
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    /**
     * On recieve of result from display activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK){
            EditText editText = (EditText) findViewById(R.id.name);
            editText.setText(data.getStringExtra(Constants.EXTRA_NAME));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG_MAIN_ACTIVITY, "onDestroy ");
    }
}
