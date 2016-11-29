package com.bt.customdialogapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Monika on 11/24/2016.
 * Sample dialog using alert dialog builder
 */

public class SampleDialog extends DialogFragment implements DialogInterface.OnClickListener{

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog
                .setMessage(R.string.sample_message)
                .setNegativeButton(R.string.cancel, this)
                .setIcon(R.mipmap.ic_launcher)
                .setNeutralButton(R.string.neutral, this)
                .setPositiveButton(R.string.ok, this)
                .setTitle(R.string.sample_title);
        return alertDialog.create();

    }
    /**
     * onClick listeners for all buttons
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        // nothing to do
    }
}
