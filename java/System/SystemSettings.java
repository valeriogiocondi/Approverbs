package com.valeriogiocondi.user.proverbsapp.System;

import android.content.Context;
import android.content.SharedPreferences;


public class SystemSettings {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context parentContext;

    public SystemSettings(Context base) {

        this.parentContext = base;
    }

    public String get(String field) {

        prefs =  parentContext.getSharedPreferences("proverbs_settings", Context.MODE_PRIVATE);
        return prefs.getString(field, "");
    }

    public void set(String field, String update) {

        prefs = parentContext.getSharedPreferences("proverbs_settings", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putString(field, update);
        editor.commit();
    }
}
