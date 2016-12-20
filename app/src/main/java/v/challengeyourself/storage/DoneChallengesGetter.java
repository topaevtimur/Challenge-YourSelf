package v.challengeyourself.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import v.challengeyourself.model.Challenge;

import static v.challengeyourself.Constants.DATE_FORMAT;

/**
 * Created by AdminPC on 18.12.2016.
 */

public class DoneChallengesGetter {
    private final Context context;
    private final DBHelper dbHelper;

    public DoneChallengesGetter(Context context) {
        this.context = context.getApplicationContext();
        this.dbHelper = DBHelper.getInstance(context);
    }

    public List<Challenge> getRunning() throws FileNotFoundException {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                DBContract.Columns.TABLE_NAME,
                DBContract.Columns.allArgs,
                DBContract.Columns.CLOSED + "=?",
                new String[] {String.valueOf(2)},
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
            throw new FileNotFoundException("No challenges that expire on ");
        }
        return result;
    }
}
