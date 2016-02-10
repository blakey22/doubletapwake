package com.blakey22.doubletapwake;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    public static final String FILE = "com.blakey22.config";
    public static final String ITEM_ON_BOOT = "on_boot";
    public static final String ITEM_ENABLE_WAKEUP = "enable_wake";

    private SharedPreferences preferences;

    public Preference(Context context) {
        preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    public boolean getItem(String name) {
        return preferences.getBoolean(name, false);
    }

    public void setItem(String name, boolean value) {
        preferences.edit().putBoolean(name, value).apply();
    }
}
