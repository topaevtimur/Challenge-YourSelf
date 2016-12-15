package v.challengeyourself;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by maria on 15.12.16.
 */
public class Challenge {
    private int id;
    private Calendar start;
    private Calendar deadDate;
    private Calendar deadTime;
    private String challenge;
    private String details;

    public Challenge (int id, Calendar start, Calendar end, String chall, String details) {
        this.id = id;
        this.start = start;
        this.deadDate = end;
        this.deadTime = end;
        this.challenge = chall;
        this.details = details;
    }
}
