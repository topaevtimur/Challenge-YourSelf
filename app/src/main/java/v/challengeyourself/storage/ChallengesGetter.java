package v.challengeyourself.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import v.challengeyourself.model.Challenge;
import v.challengeyourself.storage.DBContract.*;

import static v.challengeyourself.Constants.DATE_FORMAT;

/**
 * Created by penguinni on 18.12.16.
 */

public class ChallengesGetter {
    private final Context context;

    private final DBHelper dbHelper;

    public ChallengesGetter(Context context) {
        this.context = context.getApplicationContext();
        this.dbHelper = DBHelper.getInstance(context);
    }

    public List<Challenge> getRunning(Date date) throws FileNotFoundException {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                Columns.TABLE_NAME,
                Columns.allArgs,
                Columns.DEADDATE + "=? AND " +
                Columns.CLOSED + "=?",
                new String[] {DATE_FORMAT.format(date.getTime()), String.valueOf(0)},
                null, null, null);

        List<Challenge> result = new ArrayList<>();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                while (true) {
                    int pos = 0;
                    result.add(new Challenge(
                            cursor.getString(++pos),
                            cursor.getString(++pos),
                            cursor.getString(++pos),
                            cursor.getString(++pos),
                            cursor.getString(++pos),
                            cursor.getLong(++pos),
                            cursor.getInt(++pos)
                    ));

                    if (!cursor.moveToNext()) {
                        break;
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (result.isEmpty()) {
            throw new FileNotFoundException("No challenges that expire on " + DATE_FORMAT.format(date));
        }
        return result;
    }
}
