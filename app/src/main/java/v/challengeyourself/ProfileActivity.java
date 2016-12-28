package v.challengeyourself;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.FileNotFoundException;

import v.challengeyourself.storage.ChallengeStorage;
import v.challengeyourself.utils.ChallengesRecycleAdapter;
import v.challengeyourself.utils.RecyclerDividersDecorator;

/**
 * Created by AdminPC on 18.11.2016.
 */
//TODO заменить фон Checkbox или заменить вообще Checkbox
public class ProfileActivity extends AppCompatActivity {
    TextView nick, first_name, second_name, date_of_birth, city;
    ImageView user_photo;
    private static final String TAG = "myLogs";
    private RecyclerView challengesRecyclerView;
    private ChallengesRecycleAdapter adapter = null;
    private Context context = this;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initContentView();

        setPrivateInfo();

        challengesRecyclerView = (RecyclerView) findViewById(R.id.done_challenges);
        challengesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        challengesRecyclerView.addItemDecoration(new RecyclerDividersDecorator(getResources().getColor(R.color.colorPrimaryDark)));

        setAdapter();


        Button profile_btn = (Button) findViewById(R.id.home_btn_profile);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        Button calendar_btn = (Button) findViewById(R.id.calendar_btn_profile);
        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        Button challenges_btn = (Button) findViewById(R.id.challenges_btn_profile);
        challenges_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ChallengeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new ChallengesRecycleAdapter(context);
            challengesRecyclerView.setAdapter(adapter);
        }
        try {
            adapter.setChallenges(new ChallengeStorage(context).getCompleted());
            challengesRecyclerView.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            challengesRecyclerView.setVisibility(View.GONE);
        }
    }

    private void initContentView() {
        first_name = (TextView) findViewById(R.id.first_name);
        second_name = (TextView) findViewById(R.id.second_name);
        user_photo = (SimpleDraweeView) findViewById(R.id.photo);
        //intent = getIntent();
    }

    private void setPrivateInfo() {
//        first_name.setText(intent.getStringExtra("fname"));
//        second_name.setText(intent.getStringExtra("sname"));
//        user_photo.setImageURI(Uri.parse(intent.getStringExtra("photo")));
        first_name.setText(Global.fname);
        second_name.setText(Global.sname);
        user_photo.setImageURI(Uri.parse(Global.uri));

        //Bundle extras = getIntent().getExtras();
        //Bitmap bmp = extras.getParcelable("imagebitmap");
        //user_photo.setImageBitmap(bmp);
    }

}
