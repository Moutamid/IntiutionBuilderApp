package com.moutamid.instuitionbuilder.config;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    SharedPreferences userSharedPref;
    SharedPreferences.Editor userSharedPrefEditor;

    public SharedPreferencesUtils(Context context) {
        userSharedPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE);
        userSharedPrefEditor = userSharedPref.edit();
    }


    public void setString(String sharedName, String value) {
        userSharedPrefEditor.putString(sharedName, value);
        userSharedPrefEditor.apply();
    }

    public String getString(String sharedName, String defaultValue) {
        return userSharedPref.getString(sharedName, defaultValue);

    }


}


