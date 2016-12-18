package v.challengeyourself.storage;

import android.provider.BaseColumns;

/**
 * Created by maria on 16.12.16.
 */
public class DBContract {
    public interface TableColumns extends BaseColumns {
        String ID = "_id";
        String START = "start";
        String DEADDATE = "deadDate";
        String DEADTIME = "deadTime";
        String CHALLENGE = "challenge";
        String DETAILS = "details";
        String DEADLINE = "deadline";
        String CLOSED = "closed";
    }

    public static final class Columns implements TableColumns {
        public static final String TABLE_NAME = "challTable";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                + "("
                + ID + " INTEGER PRIMARY KEY, "
                + START + " INTEGER, "
                + DEADDATE + " INTEGER, "
                + DEADTIME + " INTEGER, "
                + CHALLENGE + " TEXT, "
                + DETAILS  + " TEXT, "
                + DEADLINE + " INTEGER, "
                + CLOSED + " INTEGER"
                + ")";

        public static final String[] allArgs = {ID, START, DEADDATE, DEADTIME, CHALLENGE, DETAILS, DEADLINE, CLOSED};
    }

    private DBContract() {}
}
