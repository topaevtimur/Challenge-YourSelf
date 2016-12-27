package v.challengeyourself.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import v.challengeyourself.Constants;
import v.challengeyourself.R;

/**
 * Created by maria on 27.12.16.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive (Context context, Intent intent) {

        String chall = intent.getStringExtra(Constants.CHALL);
        Log.d("RECEIVER", "Gonna make NEW NOTIFICATION");
        if (intent.getIntExtra(Constants.HOTNESS, 1) == 1) {
            showNotification(context, "Deadline Notification", "Hey, you gonna have deadline in 2 days " + chall);
        } else if (intent.getIntExtra(Constants.HOTNESS, 2) == 2) {
            showNotification(context, "Deadline Notification", "Hurry up! Deadline is coming " + chall);
        } else if (intent.getIntExtra(Constants.HOTNESS, 3) == 3) {
            showNotification(context, "Deadline Notification", "LAST FUCKING NIGHT AS ALWAYS, GOOD LUCK" + chall);
        }
    }

    private void showNotification(Context context, CharSequence title, CharSequence text) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }
}
