package com.antplay.antplayApp.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.antplay.antplayApp.R;
import com.google.android.material.snackbar.Snackbar;


public class SnackBarUtils {


    @SuppressLint("RestrictedApi")
    public static void customSnackBar(Activity mActivity,  boolean isConnected) {

        Activity activity = mActivity;
        // initialize color and message
        String message;
        int snackBarColor, textColor;
        int SNACK_BAR_VISIBILITY_TIME;

        // check condition
        if (isConnected) {

            // when internet is connected
            // set message
            message = "Back online";

            // set text color
            snackBarColor = activity.getResources().getColor(R.color.green);
            textColor = Color.WHITE;
            SNACK_BAR_VISIBILITY_TIME = Snackbar.LENGTH_LONG;

        } else {

            // when internet
            // is disconnected
            // set message
            message = "You are offline";

            // set text color
            snackBarColor = activity.getResources().getColor(R.color.red);
            textColor = Color.WHITE;
            SNACK_BAR_VISIBILITY_TIME = Snackbar.LENGTH_INDEFINITE;
        }

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.rlParentLayout), "", SNACK_BAR_VISIBILITY_TIME);

        // initialize view
        View view = activity.getLayoutInflater().inflate(R.layout.snackbar_layout, null);
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams parentParams = (FrameLayout.LayoutParams) snackBarView.getLayoutParams();
        parentParams.height = 60;
        parentParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
        snackBarView.setLayoutParams(parentParams);
        snackBarView.addView(view);
        snackbar.setBackgroundTint(snackBarColor);
        TextView txtSnackBarMessage = snackbar.getView().findViewById(R.id.txtSnackBarMessage);
        txtSnackBarMessage.setText(message);
        txtSnackBarMessage.setTextColor(textColor);

        snackbar.show();
    }


}
