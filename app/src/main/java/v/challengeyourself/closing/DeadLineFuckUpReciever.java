package v.challengeyourself.closing;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import v.challengeyourself.HomeActivity;
import v.challengeyourself.storage.ChallengeStorage;
import v.challengeyourself.storage.DBHelper;

import static v.challengeyourself.storage.DBContract.Columns.TABLE_NAME;
import static v.challengeyourself.storage.DBContract.TableColumns.DEADLINE;

/**
 * Created by maria on 18.12.16.
 */
public class DeadLineFuckUpReciever extends BroadcastReceiver {
    public ChallengeStorage storage;

    @Override
    public void onReceive(Context context, Intent intent) {
        long currentTime = System.currentTimeMillis();
        storage = new ChallengeStorage(context);
        Cursor c = storage.getCursorToSortedTable();
        long nearest = 0L;
        if (c.moveToFirst()) {
            do {
                nearest = c.getLong(c.getColumnIndex(DEADLINE));
                if (timeCame(currentTime, nearest)) {
                    //TODO ЗДЕСЬ ИМЕЕМ ДОСТУП КО ВСЕМ ЧЕЛЕНДЖАМ, КОТОРЫЕ СГОРЯТ СЕГОДНЯ, ПОКА ВЫВОДИТСЯ В ЛОГ, ПОДУМАТЬ ГДЕ ХОТИМ ИХ ВИДЕТЬ
                    Log.d("TIME CAME ", "current time= " + currentTime + " deadline time " + nearest);
                }
            } while (c.moveToNext());
        }
        c.close();
    }

    private Calendar getCalendarFromMillis (long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar;
    }

    private boolean timeCame(long system, long deadline) {
        Calendar s = getCalendarFromMillis(system);
        Calendar d = getCalendarFromMillis(deadline);
        if (s.get(Calendar.YEAR) == d.get(Calendar.YEAR)
                && s.get(Calendar.MONTH) == d.get(Calendar.MONTH)
                && s.get(Calendar.DAY_OF_MONTH) == d.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }
}
