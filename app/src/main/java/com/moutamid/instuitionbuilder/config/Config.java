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

    public static ArrayList<DataModel> secondRoundDataArrayList() {
        ArrayList<DataModel> arrayList = new ArrayList<>();


        DataModel dataModel1 = new DataModel();
        dataModel1.text = "Boat";
        dataModel1.image = R.drawable.boat;
        dataModel1.audio = R.raw.boat;
        arrayList.add(dataModel1);

        DataModel dataModel2 = new DataModel();
        dataModel2.text = "Car";
        dataModel2.image = R.drawable.cat;
        dataModel2.audio = R.raw.car;
        arrayList.add(dataModel2);

        DataModel dataModel3 = new DataModel();
        dataModel3.text = "Road";
        dataModel3.image = R.drawable.library;
        dataModel3.audio = R.raw.library;
        arrayList.add(dataModel3);

        DataModel dataModel4 = new DataModel();
        dataModel4.text = "Mountain";
        dataModel4.image = R.drawable.leg;
        dataModel4.audio = R.raw.leg;
        arrayList.add(dataModel4);

        DataModel dataModel5 = new DataModel();
        dataModel5.text = "Bike";
        dataModel5.image = R.drawable.ambulance;
        dataModel5.audio = R.raw.ambulance;
        arrayList.add(dataModel5);

        DataModel dataModel6 = new DataModel();
        dataModel6.text = "Helmet";
        dataModel6.image = R.drawable.bear;
        dataModel6.audio = R.raw.bear;
        arrayList.add(dataModel6);

        DataModel dataModel7 = new DataModel();
        dataModel7.text = "Soldier";
        dataModel7.image = R.drawable.diamond;
        dataModel7.audio = R.raw.piano;
        arrayList.add(dataModel7);

        DataModel dataModel8 = new DataModel();
        dataModel8.text = "Tree";
        dataModel8.image = R.drawable.tree;
        dataModel8.audio = R.raw.tree;
        arrayList.add(dataModel8);

        DataModel dataModel9 = new DataModel();
        dataModel9.text = "Jungle";
        dataModel9.image = R.drawable.dolphin;
        dataModel9.audio = R.raw.dolphin;
        arrayList.add(dataModel9);

        DataModel dataModel10 = new DataModel();
        dataModel10.text = "Tiger";
        dataModel10.image = R.drawable.grandfather;
        dataModel10.audio = R.raw.grandfather;
        arrayList.add(dataModel10);
        DataModel dataModel11 = new DataModel();
        dataModel11.text = "Parrot";
        dataModel11.image = R.drawable.parrot;
        dataModel11.audio = R.raw.parrot;
        arrayList.add(dataModel11);
        DataModel dataModel12 = new DataModel();
        dataModel12.text = "Coconut";
        dataModel12.image = R.drawable.grandfather;
        dataModel12.audio = R.raw.grandfather;
        arrayList.add(dataModel12);
        DataModel dataModel13 = new DataModel();
        dataModel13.text = "Rain";
        dataModel13.image = R.drawable.grandfather;
        dataModel13.audio = R.raw.grandfather;
        arrayList.add(dataModel13);
        DataModel dataModel14 = new DataModel();
        dataModel14.text = "Surfer";
        dataModel14.image = R.drawable.grandfather;
        dataModel14.audio = R.raw.grandfather;
        arrayList.add(dataModel14);
        DataModel dataModel15 = new DataModel();
        dataModel15.text = "Shark";
        dataModel15.image = R.drawable.grandfather;
        dataModel15.audio = R.raw.grandfather;
        arrayList.add(dataModel15);
        return arrayList;
    }

    public static ArrayList<StreakModel> streakArrayList() {
        ArrayList<StreakModel> arrayList = new ArrayList<>();
//1. Charging (like charging up power) *5 streak*
//2. Potential (potential energy) *8 streak*
//3. Containing light *13 streak*
//4. Streaming light *21 streak*
//5. Wielder of Light (like wielding a sword) *34 streak*
//6. Possessed by the Light *55 streak
//7. Creator of worlds *87 streak*
//8. Key to the gates of heaven *144 streak*
        StreakModel dataModel1 = new StreakModel();
        dataModel1.text = "x5";
        dataModel1.image = R.drawable.charging;
        arrayList.add(dataModel1);
        StreakModel dataModel2 = new StreakModel();
        dataModel2.text = "x8";
        dataModel2.image = R.drawable.potential;
        arrayList.add(dataModel2);

        StreakModel dataModel3 = new StreakModel();
        dataModel3.text = "x13";
        dataModel3.image = R.drawable.containg_light;
        arrayList.add(dataModel3);

        StreakModel dataModel4 = new StreakModel();
        dataModel4.text = "x21";
        dataModel4.image = R.drawable.streaming_light;
        arrayList.add(dataModel4);


        StreakModel dataModel5 = new StreakModel();
        dataModel5.text = "x34";
        dataModel5.image = R.drawable.wielder_of_light;
        arrayList.add(dataModel5);

        StreakModel dataModel6 = new StreakModel();
        dataModel6.text = "x55";
        dataModel6.image = R.drawable.possessed_by_light;
        arrayList.add(dataModel6);


        StreakModel dataModel7 = new StreakModel();
        dataModel7.text = "x87";
        dataModel7.image = R.drawable.creator_of_worlds;
        arrayList.add(dataModel7);


        StreakModel dataModel8 = new StreakModel();
        dataModel8.text = "x144";
        dataModel8.image = R.drawable.tree;
        arrayList.add(dataModel8);


        return arrayList;
    }
}

