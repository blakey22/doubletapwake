package com.blakey22.doubletapwake;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Switch bootSwitch;
    private Switch wakeSwitch;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preference = new Preference(MainActivity.this);
        bootSwitch = (Switch)findViewById(R.id.boot_switch);
        wakeSwitch = (Switch)findViewById(R.id.wake_switch);

        // setup switch
        bootSwitch.setChecked(preference.getItem(Preference.ITEM_ON_BOOT));
        bootSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (!Util.setDoubleTapWakeState(MainActivity.this, checked)) {
                    Util.showToastMessage(MainActivity.this, R.string.no_root_perm);
                    wakeSwitch.setChecked(false);
                } else {
                    wakeSwitch.setChecked(checked);
                }
                preference.setItem(Preference.ITEM_ON_BOOT, checked);
                preference.setItem(Preference.ITEM_ENABLE_WAKEUP, checked);
            }
        });

        wakeSwitch.setChecked(Util.isDoubleTapWakeEnabled());
        wakeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((Switch) v).isChecked();
                if (!Util.setDoubleTapWakeState(MainActivity.this, checked)) {
                    Util.showToastMessage(MainActivity.this, R.string.no_root_perm);
                    wakeSwitch.setChecked(false);
                }
                else {
                    wakeSwitch.setChecked(checked);
                }
                preference.setItem(Preference.ITEM_ENABLE_WAKEUP, checked);
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
            builder.setMessage("Author: BlakeY <blakeyang22@gmail.com>\n\nThanks to XDA developer Flar2 for discovering this tweak.");
            builder.create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
