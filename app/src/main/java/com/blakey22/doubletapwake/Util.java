package com.blakey22.doubletapwake;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;


public class Util {
    public static boolean setDoubleTapWakeState(boolean value) {
        ArrayList<String> commands = new ArrayList<String>();
        String command = String.format("echo %d > /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture", value ? 1 : 0);
        commands.add(command);
        return ExecuteCommand.runAsRoot(commands);

    }

    public static boolean isDoubleTapWakeEnabled() {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        ExecuteCommand.run("cat /sys/devices/soc.0/f9924000.i2c/i2c-2/2-0070/input/input0/wake_gesture", buffer);
        String output = new String(buffer.array(), 0, buffer.remaining());

        if (output.trim().equals("1")) {
            Log.d("blakey22", "isDoubleTapWakeEnabled: " + true);
            return true;
        }
        else {
            Log.d("blakey22", "isDoubleTapWakeEnabled (false): " + output.trim());
        }

        return false;
    }
}
