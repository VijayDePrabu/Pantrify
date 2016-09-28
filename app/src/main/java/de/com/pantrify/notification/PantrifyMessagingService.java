package de.com.pantrify.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.com.pantrify.MainActivity;
import de.com.pantrify.R;

/**
 * Created by MSF201Vijay on 08-09-2016.
 */
public class PantrifyMessagingService extends FirebaseMessagingService {

    private final String TAG ="MyFirebase";

    private final int NOTIFICATION_ID = 22;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

/*        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");

        //generating your own notifications
        sendNotification(message);

    }



    private void sendNotification(String msg) {

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        int notificationSmallIcon = R.drawable.breakfast_over;
        int notificationSmallIconBG= ContextCompat.getColor(this,R.color.notificationClosedIconBG);
        if(msg.equalsIgnoreCase(getString(R.string.open))){
            notificationSmallIcon = R.drawable.breakfast_time;
            notificationSmallIconBG =  ContextCompat.getColor(this,R.color.notificationCOpenIconBG);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(notificationSmallIcon)
                .setColor(notificationSmallIconBG)
                .setContentTitle(getString(R.string.breakfast))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setLights(0xff00ff00, 100, 3000)
                .setVibrate(new long[]{100, 250})
                .setDefaults( Notification.DEFAULT_SOUND)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
