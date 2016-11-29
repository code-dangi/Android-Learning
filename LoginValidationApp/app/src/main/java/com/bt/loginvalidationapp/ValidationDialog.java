package com.bt.loginvalidationapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Monika on 11/25/2016.
 * Dialog to show if password nd user-id is valid or not
 */

public class ValidationDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private static final String TAG = ValidationDialog.class.getSimpleName();
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_layout, container, false);
        view.findViewById(R.id.ok).setOnClickListener(this);
        return view;
    }

    /**
     * Method to set the text in dialog
     * @param view to set text of this view
     * @param text text value
     */
    public void setDialogText(View view, String text) {
        ((TextView) view.findViewById(R.id.dialog_title)).setText(text);
    }

    /**
     * on click of Ok button
     * @param view: Button that is click
     */
    @Override
    public void onClick(View view) {
        dismiss();
        Log.d(TAG, "onClick: Ok button is clicked ");
    }
}
