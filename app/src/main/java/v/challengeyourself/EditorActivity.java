package v.challengeyourself;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import v.challengeyourself.model.Challenge;
import v.challengeyourself.notifications.AlarmReceiver;
import v.challengeyourself.storage.ChallengeStorage;

import static v.challengeyourself.Constants.DATE_FORMAT;

/**
 * Created by penguinni on 27.11.16.
 */
public class EditorActivity extends AppCompatActivity {
    private final String TAG = "NEW CHALLENGE: ";
    private final String ALARM = "ALARM";

    private EditText getDate, getTime, challenge, details;
    private Button save, share;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private ChallengeStorage storage;
    private Calendar full = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        findViewsById();
        chooseDateTime();
        checkSpecified();
    }

    private void checkSpecified() {
        getDate.setText(findInExtra("date"));
        challenge.setText(findInExtra("challenge"));
        details.setText(findInExtra("details"));
    }

    private String findInExtra(String key) {
        String result = this.getIntent().getStringExtra(key);
        return result == null ? "" : result;
    }

    private void findViewsById() {
        getDate = (EditText) findViewById(R.id.get_date);
        getDate.setInputType(InputType.TYPE_NULL);
        getDate.requestFocus();

        getTime = (EditText) findViewById(R.id.get_time);
        getTime.setInputType(InputType.TYPE_NULL);

        challenge = (EditText) findViewById(R.id.challenge);
        details = (EditText) findViewById(R.id.details);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        storage = new ChallengeStorage(getApplicationContext());

        share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                save();

                Intent intent = new Intent(EditorActivity.this, ShareActivity.class);
                intent.putExtra("chal", details.getEditableText().toString());
                Log.d("mylogs", String.valueOf(challenge.getText()));
                startActivity(intent);
            }
        });
    }
    private void save(){}
    private void chooseDateTime() {

        Calendar cd = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                full.set(Calendar.YEAR, year);
                full.set(Calendar.MONTH, month);
                full.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getDate.setText(DATE_FORMAT.format(newDate.getTime()));
            }
        }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH));

        Calendar ct = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                full.set(Calendar.HOUR_OF_DAY, hourOfDay);
                full.set(Calendar.MINUTE, minute);
                getTime.setText(hourOfDay + ":" + minute);
            }
        }, ct.get(Calendar.HOUR_OF_DAY), ct.get(Calendar.MINUTE), false);
    }

    public void onClickDateTime(View view) {
        if (view == getDate) {
            datePickerDialog.show();
        } else if (view == getTime) {
            timePickerDialog.show();
        }
    }

    public void onClickSave() {
        Toast.makeText(getApplicationContext(), "Saving", Toast.LENGTH_SHORT).show();

        String start = DATE_FORMAT.format(new Date());
        String deadDate = getDate.getText().toString();
        String deadTime = getTime.getText().toString();
        String chall = challenge.getText().toString();
        String det = details.getText().toString();
        Challenge newch = new Challenge(-1, start, deadDate, deadTime, chall, det, full.getTimeInMillis(), 0);
        Log.d(TAG, "Write to storage: start = " + newch.start
                + ", deadDate = " + newch.deadDate
                + ", deadTime = " + newch.deadTime
                + ", challenge = " + newch.challenge
                + ", details = " + newch.details
                + ", deadLine(time in millis) " + newch.deadLine
                + ", closed? " + newch.closed);
        storage.put(newch);
        //storage.showStorage();
        storage.sortByDeadLines();
        scheduleAlarm(full, newch);
    }

    private void scheduleAlarm(Calendar c, Challenge chall) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long deadline = c.getTimeInMillis();

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(Constants.CHALL, chall.challenge);
        PendingIntent pending = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d(ALARM, "ALARM IS SET WITHIN 5 SEC");
        intent.putExtra(Constants.HOTNESS, 1);
        manager.set(AlarmManager.RTC_WAKEUP, deadline - AlarmManager.INTERVAL_DAY * 2, pending);

        Log.d(ALARM, "ALARM IS SET WITHIN 10 SEC");
        intent.putExtra(Constants.HOTNESS, 2);
        pending = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, deadline - AlarmManager.INTERVAL_DAY, pending);

        Log.d(ALARM, "ALARM IS SET WITHIN 20 SEC");
        intent.putExtra(Constants.HOTNESS, 3);
        pending = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, deadline - AlarmManager.INTERVAL_HALF_DAY, pending);
    }

    private void cancelAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pending = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), new Intent(this, AlarmReceiver.class), PendingIntent.FLAG_CANCEL_CURRENT);
        manager.cancel(pending);
        storage.showStorage();
        //storage.sortByDeadLines();
    }
}
