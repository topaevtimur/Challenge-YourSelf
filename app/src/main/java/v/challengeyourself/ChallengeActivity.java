package v.challengeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

        Spinner fitnessSpinner = (Spinner) findViewById(R.id.category_fitness);
        setSpinner(fitnessSpinner, R.array.fitness_offers, R.string.category_fitness);

        Spinner relaxSpinner = (Spinner) findViewById(R.id.category_relax);
        setSpinner(relaxSpinner, R.array.relax_offers, R.string.category_relax);

        Spinner choresSpinner = (Spinner) findViewById(R.id.category_chores);
        setSpinner(choresSpinner, R.array.chores_offers, R.string.category_chores);

        Spinner defaultSpinner = (Spinner) findViewById(R.id.category_default);
        setSpinner(defaultSpinner, R.array.default_offers, R.string.category_default);
    }

    void setSpinner(final Spinner spinner, final int resourceID, final int nameID) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                resourceID, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    Intent intent = new Intent(ChallengeActivity.this, EditorActivity.class);
                    if (!getResources().getString(nameID).equals("Другое")) {
                        intent.putExtra("challenge", getResources().getString(nameID));
                        if (!getResources().getStringArray(resourceID)[position].equals("Другое")) {
                            intent.putExtra("details", getResources().getStringArray(resourceID)[position]);
                        }
                    }
                    spinner.setSelection(0);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}
