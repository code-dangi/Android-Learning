package com.bt.loginvalidationapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Monika on 11/23/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Pattern VALID_EMAIL = Pattern.compile("^[a-z0-9_.-]+@[a-z0-9]+\\.[a-z]{2,3}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASS = Pattern.compile("[0-9]{8}");
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mIsValidEmail;
    private boolean mIsValidPass;
    private Button mButton;
    private EditText mUiTextView;
    private EditText mPwTextView;
    private Boolean mIsUiEmpty;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsUiEmpty = false;
        mButton = (Button) findViewById(R.id.button_login);
        mUiTextView = ((EditText) findViewById(R.id.ui));
        mPwTextView = (EditText) findViewById(R.id.pass);
        mButton.setOnClickListener(this);
        setTextWatcherOnUid();
        setTextWatcherOnPass();
    }
    /**
     * set text watcher to text editors
     */
    private void setTextWatcherOnUid(){

        mUiTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do
            }

            @Override
            public void afterTextChanged(Editable s) {
                String ui = mUiTextView.getText().toString();
                if (ui.isEmpty()) {
                    mIsValidEmail = false;
                    mIsUiEmpty = true;
                }
                else {
                    mIsValidEmail = validateEmail(ui);
                }
                hideKeyBoard(mUiTextView);
            }
        });
    }
    /**
     * set Text watcher on password edit box
     */
    private  void setTextWatcherOnPass(){
        mPwTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing to do
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mIsUiEmpty) {
                  mButton.setEnabled(true);
                }
                if(mPwTextView.getText().toString().isEmpty())
                {
                    mButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mIsValidPass = validatePass(mPwTextView.getText().toString());
                hideKeyBoard(mPwTextView);
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
        else {
            Log.d(TAG, "validatePass: invalid password ");
            return false;
        }
    }
    /**
     * on click of a log in button
     */
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        if (mIsValidEmail && mIsValidPass) {
            String number = mPwTextView.getText().toString();
            if (mUiTextView.getText().toString().equalsIgnoreCase(getResources()
                    .getString(R.string.user_id_default)) && number.equalsIgnoreCase(getResources()
                    .getString(R.string.number_default))) {
                Log.d(TAG, "onClick: email and pass are correct");
                // show valid dialog box
                ValidationDialog dialog = new ValidationDialog();
                dialog.show(getSupportFragmentManager(), getResources()
                        .getString(R.string.dialog_tag));
                dialog = (ValidationDialog) getSupportFragmentManager()
                        .findFragmentByTag(getResources().getString(R.string.dialog_tag));
                if(dialog != null){
                    dialog.setDialogText(dialog.getView(), getResources().getString(R.string.valid));
                }
                else {
                    Log.d(TAG, "onClick: returned dialog is null");
                }
            }
            else {
                Log.d(TAG, "onClick: email or pass is incorrect");
                // show invalid dialog
                ValidationDialog dialog = new ValidationDialog();
                dialog.show(getSupportFragmentManager(), getResources()
                        .getString(R.string.dialog_tag));
                dialog = (ValidationDialog) getSupportFragmentManager().
                        findFragmentByTag(getResources().getString(R.string.dialog_tag));
                if (dialog != null){
                    dialog.setDialogText(dialog.getView() ,getResources().getString(R.string.invalid));
                }
                else {
                    Log.d(TAG, "onClick: returned dialog is null");
                }
            }
        }
        else {
            Log.d(TAG, "onClick: not valid pass or email");
        }
    }

    /**
     * hiding keyboard
     */
    private void hideKeyBoard(TextView view){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);

    }
    /**
     * setting editor listener to user id
     */
    /*private void setEditorListenerToUserId(){
        mUiTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideKeyBoard(v);
                return false;
            }
        });
    }
    *//**
     * setting editor listener to password editView
     *//*
    private void setEditorListenerToPass(){
        mPwTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideKeyBoard(v);
                return false;
            }
        });
    }
    *//**
     * Controls the text in edit text like no enter should be pressed on edit box
     *//*
    private void setInputOptions() {
        mUiTextView.setImeOptions(IME_ACTION_DONE);
        mUiTextView.setImeOptions(IME_ACTION_NEXT);
        mUiTextView.setImeOptions(IME_ACTION_GO);
       mPwTextView.setImeOptions(IME_ACTION_DONE);
    }*/
    /**
     * set navigation of the focus
     */
    /*private void setNextFocus() {
        findViewById(R.id.ui).setNextFocusDownId(R.id.pass);
         findViewById(R.id.pass).setNextFocusDownId(R.id.button_login);
    }*/

}

