package com.bt.contactlist.com.bt.contactlist;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.bt.contactlist.MainActivity;

/**
 * Created by Monika on 1/5/2017.
 * fragment alert dialog
 */

public class Dialog extends DialogFragment implements DialogInterface.OnClickListener {
    private static final String DIALOG_TITLE = "dialogTitleBundleArg";
    private static final String DIALOG_OK_BUTTON = "OK";

    public static Dialog newInstance(String title) {
        Dialog dialog = new Dialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_TITLE, title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(DIALOG_TITLE);
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(DIALOG_OK_BUTTON, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        ((MainActivity) getActivity()).doPositiveClick();
    }
}