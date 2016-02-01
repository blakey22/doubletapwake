package com.blakey22.doubletapwake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class TapReceiver extends BroadcastReceiver {
    private static final String TAG = "blakey22";

    @Override
    public void onReceive(Context context, Intent intent) {
        String pref_file = context.getResources().getString(R.string.preference_file_key);
        String onboot_key = context.getResources().getString(R.string.preference_onboot_key);
        String enable_wake_key = context.getResources().getString(R.string.preference_enable_wake);

        SharedPreferences sharedPref = context.getSharedPreferences(pref_file, Context.MODE_PRIVATE);
        boolean startOnBoot = sharedPref.getBoolean(onboot_key, false);
        boolean enable = sharedPref.getBoolean(enable_wake_key, false);

        Log.d(TAG, "TapReceiver: onboot=" + startOnBoot);
        Log.d(TAG, "TapReceiver: enable=" + enable);
        if ((startOnBoot && enable) && !Util.isDoubleTapWakeEnabled()) {
            Util.setDoubleTapWakeState(true);
            boolean state = Util.isDoubleTapWakeEnabled();
            Log.d(TAG, "TapReceiver: result=" + state);
        }
    }
}
