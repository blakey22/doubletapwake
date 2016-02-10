package com.blakey22.doubletapwake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class TapReceiver extends BroadcastReceiver {
    private static final String TAG = "blakey22";

    @Override
    public void onReceive(Context context, Intent intent) {
        Preference preference = new Preference(context);
        boolean startOnBoot = preference.getItem(Preference.ITEM_ON_BOOT);
        boolean enable = preference.getItem(Preference.ITEM_ENABLE_WAKEUP);

        Log.d(TAG, "TapReceiver: onboot=" + startOnBoot);
        Log.d(TAG, "TapReceiver: enable=" + enable);
        if ((startOnBoot && enable) && !Util.isDoubleTapWakeEnabled()) {
            boolean state;
            Util.setDoubleTapWakeState(context, true);
            state = Util.isDoubleTapWakeEnabled();
            Log.d(TAG, "TapReceiver: result=" + state);
        }
    }
}
