package v.challengeyourself;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import v.challengeyourself.storage.ChallengeStorage;
import v.challengeyourself.storage.DBHelper;

public class HomeActivity extends AppCompatActivity {

    void save() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button profile_btn = (Button) findViewById(R.id.profile);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button calendar_btn = (Button) findViewById(R.id.calendar_btn);
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        Button editor_btn = (Button) findViewById(R.id.editor);
        editor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(HomeActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        Button challenges_btn = (Button) findViewById(R.id.challenges);
        challenges_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Intent intent = new Intent(HomeActivity.this, ChallengeActivity.class);
                startActivity(intent);
            }
        });
    }
}
