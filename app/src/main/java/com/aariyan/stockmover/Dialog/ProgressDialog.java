package com.aariyan.stockmover.Dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.aariyan.stockmover.MainActivity;
import com.aariyan.stockmover.R;

import dmax.dialog.SpotsDialog;

public class ProgressDialog {

    static Dialog dialog;

    public static void showDialog(Context context) {
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        dialog.setContentView(view);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogTheme;
        dialog.getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);


        dialog.show();
    }

    public static void dialogDismiss() {
        dialog.dismiss();
    }

    public static void showSpotsDialog(String message, Context context) {
        new SpotsDialog.Builder()
                .setContext(context)
                .setMessage(message)
                .build()
        .show();
    }

    public static void dismissSpotsDialog(Context context) {
        new SpotsDialog.Builder()
                .setContext(context)
                .build()
                .dismiss();
    }
}
