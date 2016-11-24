package com.bt.loginvalidationapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_GO;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;

/**
 * Created by Monika on 11/23/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity {
    static final Pattern VALID_EMAIL = Pattern.compile("^[a-z0-9_.-]+@[a-z0-9]+\\.[a-z]{2,3}$", Pattern.CASE_INSENSITIVE);
    static final Pattern VALID_PASS = Pattern.compile("[0-9]{8}");
    static final String TAG = MainActivity.class.getSimpleName();
    private boolean mIsValidEmail;
    private boolean mIsValidPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_login).setEnabled(false);
        setInputOptions();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        setEditorListenerToUserId();
        setEditorListenerToPass();
    }
    /**
     * setting editor listener to user id
     */
    private void setEditorListenerToUserId(){
        ((EditText) findViewById(R.id.ui)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideKeyBoard(v);
                mIsValidEmail = validateEmail(v.getText().toString());
                return false;
            }
        });
    }
    /**
     * setting editor listener to password editView
     */
    private void setEditorListenerToPass(){
        ((EditText) findViewById(R.id.pass)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideKeyBoard(v);
                mIsValidPass = validatePass(v.getText().toString());
                if (mIsValidPass && mIsValidEmail){
                    Log.d(TAG, "onCreate: Both email and pass is valid");
                    findViewById(R.id.button_login).setEnabled(true);
                }
                else {
                    Log.d(TAG, "onCreate: email or pass is not valid");
                }
                return false;
            }
        });
    }
    /**
     * function to validate Email
     */
    private boolean validateEmail(String email){
        Matcher emailMatcher = VALID_EMAIL.matcher(email);
        if (emailMatcher.find()){
            Log.d(TAG, "validateEmail: Valid Email");
            return true;
        }
        else {
            Log.d(TAG, "validateEmail: Not valid Email");
            return false;
        }
    }

    /**
     *  Method to validate Password
     */
    private boolean validatePass(String pass){
        Matcher passMatcher = VALID_PASS.matcher(pass);
        if (passMatcher.find()){
            Log.d(TAG, "validatePass: valid password ");
            return true;
        }
        else {            Log.d(TAG, "validatePass: invalid password ");
            return false;
        }
    }
    /**
     * hiding keyboard
     */
    void hideKeyBoard(TextView view){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);

    }
    /**
     * Controls the text in edit text like no enter should be pressed on edit box
     */
    private void setInputOptions() {
        EditText editText = (EditText) findViewById(R.id.ui);
        editText.setImeOptions(IME_ACTION_DONE);
        editText.setImeOptions(IME_ACTION_NEXT);
        editText.setImeOptions(IME_ACTION_GO);
        ((EditText) findViewById(R.id.pass)).setImeOptions(IME_ACTION_DONE);
    }
    /**
     * set navigation of the focus
     */
    /*private void setNextFocus() {
        findViewById(R.id.ui).setNextFocusDownId(R.id.pass);
         findViewById(R.id.pass).setNextFocusDownId(R.id.button_login);
    }*/

}

