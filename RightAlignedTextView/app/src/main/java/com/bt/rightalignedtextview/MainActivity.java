package com.bt.rightalignedtextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Monika on 11/18/2016.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showRightAlignedTextView(v);
            }
        });
    }

    /**
     * Button click event will leanch the activity that shows right Aligned text view
     * @param view
     */
    public void showRightAlignedTextView(View view){
        Intent intent = new Intent(this, RightAlignedTextViewActivity.class);
        startActivity(intent);
    }

}
