package com.abc.myfirstandroidapp.BroadcastExample;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class BroadcastExActivity extends AppCompatActivity {

    private int lastBatteryLevel = -1;
    private ConnectivityManager connectivityManager;

    BroadcastReceiver systemReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            // 🔵 Bluetooth ON / OFF
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                        checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
                                != PackageManager.PERMISSION_GRANTED) return;

                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                if (state == BluetoothAdapter.STATE_ON)
                    Toast.makeText(context, "🔵 Bluetooth ON", Toast.LENGTH_SHORT).show();
                else if (state == BluetoothAdapter.STATE_OFF)
                    Toast.makeText(context, "⚪ Bluetooth OFF", Toast.LENGTH_SHORT).show();
            }

            // 🔋 Battery percentage every 10%
            else if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", -1);
                int scale = intent.getIntExtra("scale", -1);
                if (level == -1 || scale == -1) return;

                int batteryPercent = (int) ((level / (float) scale) * 100);
                if (batteryPercent % 10 == 0 && batteryPercent != lastBatteryLevel) {
                    lastBatteryLevel = batteryPercent;
                    Toast.makeText(context, "🔋 Battery: " + batteryPercent + "%", Toast.LENGTH_SHORT).show();
                }
            }

            // 🔋 Battery Low / OK
            else if (Intent.ACTION_BATTERY_LOW.equals(action))
                Toast.makeText(context, "🔋 Battery Low! Please charge.", Toast.LENGTH_LONG).show();
            else if (Intent.ACTION_BATTERY_OKAY.equals(action))
                Toast.makeText(context, "✅ Battery OK! You can unplug charger.", Toast.LENGTH_LONG).show();

                // ✈️ Airplane Mode
            else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
                boolean isOn = intent.getBooleanExtra("state", false);
                Toast.makeText(context,
                        isOn ? "✈️ Airplane Mode ON" : "🌐 Airplane Mode OFF",
                        Toast.LENGTH_LONG).show();
            }

            // 🔓 Screen ON / 🔒 Screen OFF
            else if (Intent.ACTION_SCREEN_ON.equals(action))
                Toast.makeText(context, "🔓 Screen ON", Toast.LENGTH_SHORT).show();
            else if (Intent.ACTION_SCREEN_OFF.equals(action))
                Toast.makeText(context, "🔒 Screen OFF", Toast.LENGTH_SHORT).show();
        }
    };

    // 🌍 Modern network callback
    ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            runOnUiThread(() ->
                    Toast.makeText(BroadcastExActivity.this, "🌍 Internet Connected", Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onLost(Network network) {
            runOnUiThread(() ->
                    Toast.makeText(BroadcastExActivity.this, "❌ No Internet Connection", Toast.LENGTH_LONG).show());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(android.R.layout.simple_list_item_1);

        // 🔔 Request Bluetooth permission (Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 100);
            }
        }

        // 🔔 Register system broadcasts
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(systemReceiver, filter);

        // 🌍 Register internet callback
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(systemReceiver);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,
                        "✅ Bluetooth permission granted",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,
                        "❌ Bluetooth permission denied",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
