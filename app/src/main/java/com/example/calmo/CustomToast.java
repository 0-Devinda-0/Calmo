package com.example.calmo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {
    private static Toast currentToast;

    public static void showToast(Context context, String message, int duration, int backgroundColor, int textColor) {
        cancelCurrentToast();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast_layout, null);
        TextView toastTextView = layout.findViewById(R.id.toastTextView);

        toastTextView.setText(message);
        toastTextView.setBackgroundColor(backgroundColor);
        toastTextView.setTextColor(textColor);

        Toast toast = new Toast(context);
        //toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(layout);


        currentToast = toast;
        toast.show();
    }

    public static void cancelCurrentToast() {
        if (currentToast != null) {
            currentToast.cancel();
            currentToast = null;
        }
    }

    public static void showErrorToast(Context context, String message, int duration){
        cancelCurrentToast();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_for_errors, null);
        TextView toastTextView = layout.findViewById(R.id.toastTextViewError);

        toastTextView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);

        currentToast = toast;
        toast.show();

    }

    public static void showDoneToast(Context context, String message, int duration){
        cancelCurrentToast();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_for_done, null);
        TextView toastTextView = layout.findViewById(R.id.toastTextViewDone);

        toastTextView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);

        currentToast = toast;
        toast.show();
    }

    public static void showInfoToast(Context context, String message, int duration){
        cancelCurrentToast();

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_for_info, null);
        TextView toastTextView = layout.findViewById(R.id.toastTextViewInfo);

        toastTextView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);

        currentToast = toast;
        toast.show();
    }
}
