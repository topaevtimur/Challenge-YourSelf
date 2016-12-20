package v.challengeyourself;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import v.challengeyourself.storage.DoneChallengesGetter;
import v.challengeyourself.utils.DoneChallengesAdapter;
import v.challengeyourself.utils.RecyclerDividersDecorator;

/**
 * Created by AdminPC on 18.11.2016.
 */
//TODO Отображать фото
//TODO заменить фон Checkbox или заменить вообще Checkbox
public class ProfileActivity extends AppCompatActivity {
    EditText nick, first_name, second_name, date_of_birth, city;
    ImageView user_photo;
    private static final String TAG = "myLogs";
    private RecyclerView challengesRecyclerView;
    private DoneChallengesAdapter adapter = null;
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

    }

    private void setAdapter() {
        if (adapter == null) {
            adapter = new DoneChallengesAdapter(context);
            challengesRecyclerView.setAdapter(adapter);
        }
        try {
            adapter.setChallenges(new DoneChallengesGetter(context).getRunning());
            challengesRecyclerView.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            challengesRecyclerView.setVisibility(View.GONE);
        }
    }

    private void initContentView(){
        nick =(EditText)findViewById(R.id.nick);
        first_name = (EditText)findViewById(R.id.first_name);
        second_name = (EditText)findViewById(R.id.second_name);
        date_of_birth = (EditText)findViewById(R.id.date_of_birth);
        city = (EditText)findViewById(R.id.city);
        user_photo = (ImageView)findViewById(R.id.user_photo);
        intent = getIntent();
    }

    private void setPrivateInfo() {
       // nick.setText(intent.getIntExtra("userid"));
        first_name.setText(intent.getStringExtra("fname"));
        second_name.setText(intent.getStringExtra("sname"));
        date_of_birth.setText(intent.getStringExtra("dateofbirth"));
        city.setText((intent.getStringExtra("city")));
       // Log.d("MYLOGS", intent.getStringExtra("city"));
        //user_photo.setImageBitmap(intent.getStringArrayExtra("usetphoto"));
    }

}
