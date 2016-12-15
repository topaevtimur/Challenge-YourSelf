package v.challengeyourself.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import static v.challengeyourself.storage.DBContract.Columns.*;

/**
 * Created by maria on 16.12.16.
 */
public class ChallengeStorage {
    private static final String TAG = "CHALLENGEDB";
    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ChallengeStorage(Context context) {
        this.context = context;
    }

    public void put(String start,
                    String deadDate,
                    String deadTime,
                    String challenge,
                    String details) {
        dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        Log.d(TAG, "Transaction started");
        SQLiteStatement insert = null;
        try {
            String statement = "INSERT INTO " + TABLE_NAME + " ("
                    + START + ", "
                    + DEADDATE + ", "
                    + DEADTIME + ", "
                    + CHALLENGE + ", "
                    + DETAILS;
            String request = ") VALUES (?, ?, ?, ?, ?)";
            insert = db.compileStatement(statement + request);
            int pos = 0;
            insert.bindString(++pos, start);
            insert.bindString(++pos, deadDate);
            insert.bindString(++pos, deadTime);
            insert.bindString(++pos, challenge);
            insert.bindString(++pos, details);

            insert.executeInsert();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        //db.close();
    }

    public void showStorage() {
        Log.d(TAG, "--- Rows in mytable: ---");
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idi = c.getColumnIndex(ID);
            int si = c.getColumnIndex(START);
            int ddi = c.getColumnIndex(DEADDATE);
            int dti = c.getColumnIndex(DEADTIME);
            int ci = c.getColumnIndex(CHALLENGE);
            int di = c.getColumnIndex(DETAILS);

            do {
                Log.d(TAG, "id = " + c.getInt(idi) + "start = " + c.getString(si)
                        + ", deadDate=" + c.getString(ddi)
                        + ", deadTime=" + c.getString(dti)
                        + ", challenge=" + c.getString(ci)
                        + ", details=" + c.getString(di));
            } while (c.moveToNext());
        } else {
            Log.d(TAG, "0 rows");
        }
        c.close();
    }
}
