package com.moutamid.instuitionbuilder.config;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.moutamid.instuitionbuilder.Model.DataModel;
import com.moutamid.instuitionbuilder.R;

import java.util.ArrayList;
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

    public static ArrayList<DataModel> dataArrayList() {
        ArrayList<DataModel> arrayList = new ArrayList<>();

        DataModel dataModel1 = new DataModel();
        dataModel1.text = "Church";
        dataModel1.image = R.drawable.church;
        dataModel1.audio = R.raw.church;
        arrayList.add(dataModel1);

        DataModel dataModel2 = new DataModel();
        dataModel2.text = "Bread";
        dataModel2.image = R.drawable.bread;
        dataModel2.audio = R.raw.bread;
        arrayList.add(dataModel2);

        DataModel dataModel3 = new DataModel();
        dataModel3.text = "Library";
        dataModel3.image = R.drawable.library;
        dataModel3.audio = R.raw.library;
        arrayList.add(dataModel3);

        DataModel dataModel4 = new DataModel();
        dataModel4.text = "Leg";
        dataModel4.image = R.drawable.leg;
        dataModel4.audio = R.raw.leg;
        arrayList.add(dataModel4);

        DataModel dataModel5 = new DataModel();
        dataModel5.text = "Ambulance";
        dataModel5.image = R.drawable.ambulance;
        dataModel5.audio = R.raw.ambulance;
        arrayList.add(dataModel5);

        DataModel dataModel6 = new DataModel();
        dataModel6.text = "Bear";
        dataModel6.image = R.drawable.bear;
        dataModel6.audio = R.raw.bear;
        arrayList.add(dataModel6);

        DataModel dataModel7 = new DataModel();
        dataModel7.text = "Piano";
        dataModel7.image = R.drawable.diamond;
        dataModel7.audio = R.raw.piano;
        arrayList.add(dataModel7);

        DataModel dataModel8 = new DataModel();
        dataModel8.text = "Tree";
        dataModel8.image = R.drawable.tree;
        dataModel8.audio = R.raw.tree;
        arrayList.add(dataModel8);

        DataModel dataModel9 = new DataModel();
        dataModel9.text = "Dolphin";
        dataModel9.image = R.drawable.dolphin;
        dataModel9.audio = R.raw.dolphin;
        arrayList.add(dataModel9);

        DataModel dataModel10 = new DataModel();
        dataModel10.text = "Grandfather";
        dataModel10.image = R.drawable.grandfather;
        dataModel10.audio = R.raw.grandfather;
        arrayList.add(dataModel10);

        return arrayList;
    }
}

