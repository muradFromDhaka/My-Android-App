package com.abc.myfirstandroidapp.BroadcastExample;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class SystemBroadcastService extends Service {

    private int lastBatteryLevel = -1;
    private ConnectivityManager connectivityManager;

    private BroadcastReceiver systemReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                        checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
                                != PackageManager.PERMISSION_GRANTED) return;

                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                showToast(state == BluetoothAdapter.STATE_ON ? "🔵 Bluetooth ON" : "⚪ Bluetooth OFF");
            } else if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", -1);
                int scale = intent.getIntExtra("scale", -1);
                if (level == -1 || scale == -1) return;

                int percent = (int) ((level / (float) scale) * 100);
                if (percent % 10 == 0 && percent != lastBatteryLevel) {
                    lastBatteryLevel = percent;
                    showToast("🔋 Battery: " + percent + "%");
                }
            } else if (Intent.ACTION_BATTERY_LOW.equals(action))
                showToast("🔋 Battery Low!");
            else if (Intent.ACTION_BATTERY_OKAY.equals(action))
                showToast("✅ Battery OK!");
            else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action))
                showToast(intent.getBooleanExtra("state", false) ? "✈️ Airplane ON" : "🌐 Airplane OFF");
            else if (Intent.ACTION_SCREEN_ON.equals(action))
                showToast("🔓 Screen ON");
            else if (Intent.ACTION_SCREEN_OFF.equals(action))
                showToast("🔒 Screen OFF");
        }
    };

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            runOnUiThread(() -> showToast("🌍 Internet Connected"));
        }

        @Override
        public void onLost(Network network) {
            runOnUiThread(() -> showToast("❌ No Internet"));
        }
    };

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "sys_broadcast_service";
            NotificationChannel channel = new NotificationChannel(channelId, "System Broadcast Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("Broadcast Listener")
                    .setContentText("Listening to System events")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setOngoing(true);

            startForeground(1, builder.build());
        }

        // Register BroadcastReceiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(systemReceiver, filter);

        // Register network callback
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(systemReceiver);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void runOnUiThread(Runnable r) {
        new android.os.Handler(getMainLooper()).post(r);
    }
}
