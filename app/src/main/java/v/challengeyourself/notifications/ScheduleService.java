package v.challengeyourself.notifications;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by maria on 20.12.16.
 */
public class ScheduleService extends Service {
    private final String TAG = "SCHEDULE_SERVICE";

    public class ServiceBinder extends Binder {
        ScheduleService getService() {
            return ScheduleService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Received start_id " + startId + ": " + intent);
        return START_STICKY;
    }

    private final IBinder mBinder = new ServiceBinder();

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setAlarm(Calendar c) {
        new AlarmTask(this, c).run();
    }
}
