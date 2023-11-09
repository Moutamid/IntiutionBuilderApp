package com.moutamid.instuitionbuilder.config;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.moutamid.instuitionbuilder.R;

import java.util.Objects;

public class Config {
    public static String NOTIFICATIONAPIURL = "https://fcm.googleapis.com/fcm/send";
    public static String ServerKey = "AAAAzzvbhX8:APA91bGavDjgYZn9tdcqZCSxPEZtmvOxUSRbNxSrpakLAvMAZ8uZ5pmaqBxo4AVmpued6aKR-Nwkj8pngfV_yhNvdAytaTh_8wuGcZ-ueTYe90LFF_zgwzVXtEyYLQv42JJae9SWdHC9";


    private static Dialog progressDialog;
    public static void showProgressDialog(Context context) {
        progressDialog = new Dialog(context);
        progressDialog.setContentView(R.layout.loading);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

