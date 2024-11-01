package com.example.android_dz6;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyNotificationManager {

    private static final String CHANNEL_ID = "my_notification_id";
    private static final String CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESCRIPTION = "My Channel Description";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("NotificationManager", "Creating notification channel: " + CHANNEL_ID);
            int importance = NotificationManager.IMPORTANCE_HIGH; // Изменено на HIGH
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d("NotificationManager", "Notification channel created: " + CHANNEL_ID);
        }
    }


    public static void showNotification(Context context, String title, String message) {
        Log.d("NotificationManager", "Attempting to show notification with title: " + title + " and message: " + message);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Here is more detail about the notification that you should see."))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
            Log.d("NotificationManager", "Notification displayed successfully.");
        } else {
            Log.e("NotificationManager", "NotificationManager is null.");
        }
    }
}