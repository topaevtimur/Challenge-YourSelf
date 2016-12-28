package v.challengeyourself.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maria on 15.12.16.
 */
public class Challenge {
    public int id;
    public long start;
    public String deadDate;
    public String deadTime;
    public String challenge;
    public String details;
    public long deadLine;
    public int closed;

    public Challenge (int id, long start, String deadDate, String deadTime, String chall, String details, long deadLine, int closed) {
        this.id = id;
        this.start = start;
        this.deadDate = deadDate;
        this.deadTime = deadTime;
        this.challenge = chall;
        this.details = details;
        this.deadLine = deadLine;
        this.closed = closed;
    }
}
