package com.bt.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Monika on 11/17/2016.
 */

public class DisplayNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_display_name);
        EditText editText = (EditText) findViewById(R.id.edit_text_name);
        if(getIntent().getStringExtra(Constants.EXTRA_NAME).equals("")){
            editText.setText("No text sent");
        }
        else {
            editText.setText(getIntent().getStringExtra(Constants.EXTRA_NAME) );
        }

        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onCreate ");
    }

    /**
     * On button click event send the result back to mainactivity
     * @param view takes View as param on button click
     */
    public void saveText(View view){
        Intent resultIntent = new Intent();
        EditText editText = (EditText) findViewById(R.id.edit_text_name);
        resultIntent.putExtra(Constants.EXTRA_NAME, editText.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG_DISPLAY_ACTIVITY, "onDestroy ");
    }

}
