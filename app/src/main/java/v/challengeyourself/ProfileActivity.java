package v.challengeyourself;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
    EditText nick, first_name, second_name, date_of_birth, city;
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
        first_name = (EditText) findViewById(R.id.first_name);
        second_name = (EditText) findViewById(R.id.second_name);
        user_photo = (SimpleDraweeView) findViewById(R.id.photo);
        intent = getIntent();
    }

    private void setPrivateInfo() {
        first_name.setText(intent.getStringExtra("fname"));
        second_name.setText(intent.getStringExtra("sname"));
        user_photo.setImageURI(Uri.parse(intent.getStringExtra("photo")));
        //Bundle extras = getIntent().getExtras();
        //Bitmap bmp = extras.getParcelable("imagebitmap");
        //user_photo.setImageBitmap(bmp);
    }

}
