package v.challengeyourself.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by maria on 16.12.16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "challDB";
    private final Context context;
    private static volatile DBHelper instance;

    static DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    public DBHelper (Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: " + DBContract.Columns.CREATE_TABLE);
        db.execSQL(DBContract.Columns.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    private final String TAG = "DBHelper";
}
