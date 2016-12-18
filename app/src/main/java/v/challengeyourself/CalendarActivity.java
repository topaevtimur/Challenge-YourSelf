package v.challengeyourself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import v.challengeyourself.storage.ChallengesGetter;
import v.challengeyourself.utils.CalendarChallsRecAdapter;
import v.challengeyourself.utils.RecyclerDividersDecorator;

public class CalendarActivity extends AppCompatActivity {
    private TextView noChalls;
    private RecyclerView challengesRecyclerView;
    private CalendarChallsRecAdapter adapter = null;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalendarActivity.this, EditorActivity.class));
                //TODO: передавать дату
            }
        });

        noChalls = (TextView) findViewById(R.id.no_challenges);

        challengesRecyclerView = (RecyclerView) findViewById(R.id.calendar_challenges_recycler);
        challengesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        challengesRecyclerView.addItemDecoration(
                new RecyclerDividersDecorator(getResources().getColor(R.color.colorPrimaryDark)));

        setAdapter(Calendar.getInstance().getTime());

        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                setAdapter(new GregorianCalendar(year, month, dayOfMonth).getTime());
            }
        });

    }

    void setAdapter(Date date) {
        if (adapter == null) {
            adapter = new CalendarChallsRecAdapter(context);
            challengesRecyclerView.setAdapter(adapter);
        }

        try {
            adapter.setChallenges(new ChallengesGetter(context).getRunning(date), date);
            noChalls.setVisibility(View.GONE);
            challengesRecyclerView.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            noChalls.setVisibility(View.VISIBLE);
            challengesRecyclerView.setVisibility(View.GONE);
        }
    }
}
