package com.blakey22.doubletapwake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch bootSwitch;
    private Switch wakeSwitch;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String pref_file = getResources().getString(R.string.preference_file_key);
        preferences = getSharedPreferences(pref_file, Context.MODE_PRIVATE);

        bootSwitch = (Switch) findViewById(R.id.boot_switch);
        String onboot_key = getResources().getString(R.string.preference_onboot_key);
        bootSwitch.setChecked(preferences.getBoolean(onboot_key, false));
        bootSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (!Util.setDoubleTapWakeState(checked)) {
                    String msg = getResources().getString(R.string.no_root_perm);
                    Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
                    toast.show();
                    wakeSwitch.setChecked(false);
                } else {
                    wakeSwitch.setChecked(checked);
                }
                String onboot_key = getResources().getString(R.string.preference_onboot_key);
                String enable_wake_key = getResources().getString(R.string.preference_enable_wake);
                preferences.edit().putBoolean(onboot_key, checked).apply();
                preferences.edit().putBoolean(enable_wake_key, checked).apply();
            }
        });

        wakeSwitch = (Switch) findViewById(R.id.wake_switch);
        wakeSwitch.setChecked(Util.isDoubleTapWakeEnabled());
        wakeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (!Util.setDoubleTapWakeState(checked)) {
                    String msg = getResources().getString(R.string.no_root_perm);
                    Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
                    toast.show();
                    wakeSwitch.setChecked(false);
                }
                else {
                    wakeSwitch.setChecked(checked);
                }
                String enable_wake_key = getResources().getString(R.string.preference_enable_wake);
                preferences.edit().putBoolean(enable_wake_key, checked).apply();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("About");
            builder.setMessage("Author: BlakeY <stfl0622@gmail.com>\n\nThanks to XDA developer Flar2 for discovering this tweak.");
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
