package v.challengeyourself.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maria on 15.12.16.
 */
public class Challenge {
    public String start;
    public String deadDate;
    public String deadTime;
    public String challenge;
    public String details;
    public long deadLine;
    public int done;

    public Challenge (String start, String deadDate, String deadTime, String chall, String details, long deadLine, int done) {
        this.start = start;
        this.deadDate = deadDate;
        this.deadTime = deadTime;
        this.challenge = chall;
        this.details = details;
        this.deadLine = deadLine;
        this.done = done;
    }
}
