package v.challengeyourself;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import v.challengeyourself.storage.ChallengeStorage;

/**
 * Created by penguinni on 27.11.16.
 */
public class EditorActivity extends AppCompatActivity {
    private final String TAG = "NEW CHALLENGE: ";

    private EditText getDate, getTime, challenge, details;
    private Button save;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormat;

    private ChallengeStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        findViewsById();
        chooseDateTime();
    }

    private void findViewsById() {
        getDate = (EditText) findViewById(R.id.get_date);
        getDate.setInputType(InputType.TYPE_NULL);
        getDate.requestFocus();

        getTime = (EditText) findViewById(R.id.get_time);
        getTime.setInputType(InputType.TYPE_NULL);

        challenge =(EditText)findViewById(R.id.challenge);
        details = (EditText) findViewById(R.id.details);

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSave();
            }
        });
        storage = new ChallengeStorage(getApplicationContext());
    }

    private void chooseDateTime() {
        Calendar cd = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                getDate.setText(dateFormat.format(newDate.getTime()));
            }
        }, cd.get(Calendar.YEAR), cd.get(Calendar.MONTH), cd.get(Calendar.DAY_OF_MONTH));

        Calendar ct = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
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

        String start = dateFormat.format(new Date());
        String deadDate = getDate.getText().toString();
        String deadTime = getTime.getText().toString();
        String chall = challenge.getText().toString();
        String det = details.getText().toString();

        Log.d(TAG, "Write to storage: start = " + start
                + ", deadDate = " + deadDate
                + ", deadTime = " + deadTime
                + ", challenge = " + chall
                + ", details = " + det);
        storage.put(start, deadDate, deadTime, chall, det);
        storage.showStorage();
    }
}