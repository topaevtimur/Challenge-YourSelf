package v.challengeyourself.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import v.challengeyourself.HomeActivity;
import v.challengeyourself.R;

/**
 * Created by maria on 20.12.16.
 */
public class NotifyService extends Service {
    private final String TAG = "NOTIFY_SERVICE";

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    private static final int NOTIFICATION = 123;
    public static final String INTENT_NOTIFY = "INTENT_NOTIFY";
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Received startId " + startId + ": " + intent);
        if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            showNotification();
        }
        return START_NOT_STICKY;
    }
    private final IBinder mBinder = new ServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void showNotification() {
        CharSequence title = "U MUST DO SMTH TODAY";
        CharSequence text = "Deadline is coming";
        long time = System.currentTimeMillis();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(text);

        Intent goIntent = new Intent(this, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(goIntent);

        PendingIntent goPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(goPendingIntent);
        mNM.notify(NOTIFICATION, mBuilder.build());
        stopSelf();
    }
}
