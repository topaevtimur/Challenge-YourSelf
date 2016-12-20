package v.challengeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

import v.challengeyourself.model.Challenge;

import static v.challengeyourself.Constants.DATE_FORMAT;

/**
 * Created by maria on 17.12.16.
 */
public class ChallengeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        final Spinner fitnessSpinner = (Spinner) findViewById(R.id.category_fitness);
        setSpinner(fitnessSpinner, R.array.fitness_offers, R.string.category_fitness);

        Spinner relaxSpinner = (Spinner) findViewById(R.id.category_relax);
        setSpinner(relaxSpinner, R.array.relax_offers, R.string.category_relax);

        Spinner choresSpinner = (Spinner) findViewById(R.id.category_chores);
        setSpinner(choresSpinner, R.array.chores_offers, R.string.category_chores);

        TextView defaultTV = (TextView) findViewById(R.id.category_default);
        defaultTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengeActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        setTV((TextView) findViewById(R.id.fitness_tv), fitnessSpinner);
        setTV((TextView) findViewById(R.id.chores_tv), choresSpinner);
        setTV((TextView) findViewById(R.id.relax_tv), relaxSpinner);
    }

    void setTV(TextView textView, final Spinner spinner) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
    }

    void setSpinner(Spinner spinner, final int resourceID, final int nameID) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                resourceID, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO: fix this shit it autotriggers itself on start of the activity

                Intent intent = new Intent(ChallengeActivity.this, EditorActivity.class);
                intent.putExtra("challenge", getResources().getString(nameID));
                intent.putExtra("details", getResources().getStringArray(resourceID)[position]);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
