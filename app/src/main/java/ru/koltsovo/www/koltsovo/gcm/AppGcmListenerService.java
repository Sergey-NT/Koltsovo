package ru.koltsovo.www.koltsovo.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import ru.koltsovo.www.koltsovo.Constants;
import ru.koltsovo.www.koltsovo.MainActivity;
import ru.koltsovo.www.koltsovo.R;

public class AppGcmListenerService extends GcmListenerService {

    private static final String TAG = "AppGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        int icon;

        String message = data.getString("message");
        String title = getString(R.string.plane_desc_flight) + " " + data.getString("title") + " " + data.getString("plane_direction");
        String direction = data.getString("direction");

        if (Constants.LOG_ON) {
            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + message);
            Log.d(TAG, "Title: " + title);
            Log.d(TAG, "Direction: " + direction);
        }

        assert direction != null;
        if (direction.equals("arrival")) {
            icon = R.mipmap.ic_flight_land_white_24dp;
        } else {
            icon = R.mipmap.ic_flight_takeoff_white_24dp;
        }

        sendNotification(title, message, icon);
    }

    private void sendNotification(String title, String message, int icon) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
