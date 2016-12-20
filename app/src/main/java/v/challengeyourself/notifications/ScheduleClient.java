package v.challengeyourself.notifications;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.Calendar;

/**
 * Created by maria on 20.12.16.
 */
public class ScheduleClient {
    private ScheduleService mBoundService;
    private Context mContext;
    private boolean mIsBound;

    public ScheduleClient(Context context) {
        mContext = context;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = ((ScheduleService.ServiceBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

    public void doBindService() {
        mContext.bindService(new Intent(mContext, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void setAlarmForNotification(Calendar c) {
        mBoundService.setAlarm(c);
    }

    public void doUnbindService() {
        if (mIsBound) {
            mContext.unbindService(mConnection);
            mIsBound = false;
        }
    }
}
