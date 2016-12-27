package v.challengeyourself.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import v.challengeyourself.model.Challenge;

import static v.challengeyourself.Constants.DATE_FORMAT;
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
        this.dbHelper = DBHelper.getInstance(context);
    }

    public void changeState(Challenge challenge, int state) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("UPDATE " + DBContract.Columns.TABLE_NAME
                + " SET " + DBContract.Columns.CLOSED + "=" + state
                + " WHERE " + DBContract.Columns.ID + "=" + challenge.id);
    }

    public List<Challenge> getRunningByDate(Date date) throws FileNotFoundException {
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DBContract.Columns.TABLE_NAME,
                DBContract.Columns.allArgs,
                DBContract.Columns.DEADDATE + "=? AND " +
                        DBContract.Columns.CLOSED + "=?",
                new String[] {DATE_FORMAT.format(date.getTime()), String.valueOf(0)},
                null, null, null);

        return get(cursor);
    }

    public List<Challenge> getCompleted() throws FileNotFoundException {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                DBContract.Columns.TABLE_NAME,
                DBContract.Columns.allArgs,
                DBContract.Columns.CLOSED + "=?",
                new String[] {String.valueOf(2)},
                null, null, null);

        return get(cursor);
    }

    private List<Challenge> get(Cursor cursor) throws FileNotFoundException {
        List<Challenge> result = new ArrayList<>();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                while (true) {
                    int pos = 0;
                    result.add(new Challenge(
                            cursor.getInt(pos++),
                            cursor.getString(pos++),
                            cursor.getString(pos++),
                            cursor.getString(pos++),
                            cursor.getString(pos++),
                            cursor.getString(pos++),
                            cursor.getLong(pos++),
                            cursor.getInt(pos)
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
            throw new FileNotFoundException("Challenges not found");
        }
        return result;
    }

    public void put(Challenge newch) {
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
                    + DETAILS + ", "
                    + DEADLINE + ", "
                    + CLOSED;
            String request = ") VALUES (?, ?, ?, ?, ?, ?, ?)";
            insert = db.compileStatement(statement + request);
            int pos = 0;
            insert.bindString(++pos, newch.start);
            insert.bindString(++pos, newch.deadDate);
            insert.bindString(++pos, newch.deadTime);
            insert.bindString(++pos, newch.challenge);
            insert.bindString(++pos, newch.details);
            insert.bindLong(++pos, newch.deadLine);
            insert.bindLong(++pos, newch.closed);

            insert.executeInsert();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        //db.close();
    }

    public void showStorage() {
        db = dbHelper.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        watchTableByCursor(c);
    }

    public Cursor getCursorToSortedTable() {
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + DEADLINE + " ASC", null);
        return c;
    }

    public void sortByDeadLines() {
        Cursor c = getCursorToSortedTable();
        watchTableByCursor(c);
    }

    private void watchTableByCursor(Cursor c) {
        Log.d(TAG, "--- Rows in mytable: ---");
        if (c.moveToFirst()) {
            int idi = c.getColumnIndex(ID);
            int si = c.getColumnIndex(START);
            int ddi = c.getColumnIndex(DEADDATE);
            int dti = c.getColumnIndex(DEADTIME);
            int ci = c.getColumnIndex(CHALLENGE);
            int di = c.getColumnIndex(DETAILS);
            int timei = c.getColumnIndex(DEADLINE);
            int closei = c.getColumnIndex(CLOSED);

            do {
                Log.d(TAG, "id = " + c.getInt(idi)
                        + ", start = " + c.getString(si)
                        + ", deadDate=" + c.getString(ddi)
                        + ", deadTime=" + c.getString(dti)
                        + ", challenge=" + c.getString(ci)
                        + ", details=" + c.getString(di)
                        + ", deadline=" + c.getLong(timei)
                        + ", closed=" + c.getLong(closei));
            } while (c.moveToNext());
        } else {
            Log.d(TAG, "0 rows");
        }
        c.close();
    }
}
