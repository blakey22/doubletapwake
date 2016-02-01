package com.blakey22.doubletapwake;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public abstract class ExecuteCommand
{
    private static final String TAG = "blakey22";

    public static int runAsRoot(ArrayList<String> commands, ByteBuffer result)
    {
        int retval = -1; // false
        byte[] buffer = new byte[result.capacity()];
        Process process;

        try
        {
            process = Runtime.getRuntime().exec("su");

            DataOutputStream processOut = new DataOutputStream(process.getOutputStream());
            BufferedInputStream processIn = new BufferedInputStream(process.getInputStream());

            for (String cmd : commands)
            {
                processOut.writeBytes(cmd + "\n");
                processOut.flush();
            }

            processOut.writeBytes("exit\n");
            processOut.flush();
            int count = processIn.read(buffer);
            retval = process.waitFor();
            if (count > 0) {
                result.put(buffer, 0, count);
            }
        }
        catch (Exception e)
        {
            String message = String.format("Root access is rejected [%s]: %s", e.getClass().getName(), e.getMessage());
            result.put(message.getBytes());
            Log.d(TAG, message);
        }
        finally {
            // for read operation
            result.flip();
        }

        return retval;
    }

    public static boolean isRoot()
    {
        boolean retval = false;
        ByteBuffer buffer = ByteBuffer.allocate(256);
        ArrayList<String> commands = new ArrayList<>();

        commands.add("id");
        runAsRoot(commands, buffer);
        String msg = new String(buffer.array(), 0, buffer.remaining());
        if (msg.contains("uid=0"))
        {
            retval = true;
            Log.d(TAG, "Root access granted");
        }

        return retval;
    }


    public static boolean runAsRoot(ArrayList<String> commands)
    {
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        if (!isRoot()) {
            return false;
        }

        int execVal = runAsRoot(commands, buffer);
        if (execVal != 0)
        {
            return false;
        }
        return true;
    }

    public static int run(String command, ByteBuffer result)
    {
        int retval = -1; // false
        byte[] buffer = new byte[result.capacity()];
        Process process;

        try
        {
            process = Runtime.getRuntime().exec(command);

            DataOutputStream processOut = new DataOutputStream(process.getOutputStream());
            BufferedInputStream processIn = new BufferedInputStream(process.getInputStream());

            int count = processIn.read(buffer);
            retval = process.waitFor();
            if (count > 0) {
                result.put(buffer, 0, count);
            }
        }
        catch (Exception e)
        {
            String message = String.format("ExecuteCommand::run [%s]: %s", e.getClass().getName(), e.getMessage());
            result.put(message.getBytes());
            Log.d(TAG, message);
        }
        finally {
            // for read operation
            result.flip();
        }

        return retval;
    }


}