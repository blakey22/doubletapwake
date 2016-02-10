package com.blakey22.doubletapwake;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.ArrayList;


public class Util {
    private static final String TAG = "blakey22";

    public static boolean setDoubleTapWakeState(Context context, boolean value) {
        ArrayList<String> commands = new ArrayList<>();

        if (!isScreenOn(context)) {
            commands.add("input keyevent KEYCODE_POWER");
        }
        String command = String.format("echo %d > /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture", value? 1 : 0);
        commands.add(command);
        return ExecuteCommand.runAsRoot(commands);

    }

    public static boolean isDoubleTapWakeEnabled() {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        ExecuteCommand.run("cat /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture", buffer);
        String output = new String(buffer.array(), 0, buffer.remaining());

        if (output.trim().equals("1")) {
            Log.d(TAG, "isDoubleTapWakeEnabled: " + true);
            return true;
        }
        else {
            Log.d(TAG, "isDoubleTapWakeEnabled (false): " + output.trim());
        }

        return false;
    }

    public static void showToastMessage(Context context, int resId) {
        String msg = context.getResources().getString(resId);
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static boolean isScreenOn(Context context) {
        DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
        for (Display display : dm.getDisplays()) {
            if (display.getState() != Display.STATE_OFF) {
                return true;
            }
        }

        return false;
    }
}
