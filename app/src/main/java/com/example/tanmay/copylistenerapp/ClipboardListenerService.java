package com.example.tanmay.copylistenerapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ClipboardListenerService extends Service {
    private final String TAG = ClipboardListenerService.class.getSimpleName();
    private ClipboardManager.OnPrimaryClipChangedListener listener =
            new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            Log.i(TAG, "onPrimaryClipChanged");
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE))
                .addPrimaryClipChangedListener(listener);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.lithium_logo)
                            .setContentTitle(getString(R.string.copy_noti_title))
                            .setContentText(getString(R.string.copy_noti_text));
            int mNotificationId = 001;
            notificationManager.notify(mNotificationId, mBuilder.build());
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE))
                .removePrimaryClipChangedListener(listener);
        super.onDestroy();
    }
}
