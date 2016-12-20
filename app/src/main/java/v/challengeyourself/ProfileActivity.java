package v.challengeyourself;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static v.challengeyourself.R.id.first_name;

/**
 * Created by AdminPC on 18.11.2016.
 */

public class ProfileActivity extends AppCompatActivity {
    //EditText nick, first_name, second_name, date_of_birth, city;
    EditText editText[];
    Button edit_button, save_button;
    private static final String TAG = "myLogs";
    private String profile_file = "profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editText = new EditText[10];
        editText[0] = (EditText) findViewById(R.id.nick);
        editText[1] = (EditText) findViewById(first_name);
        editText[2] = (EditText) findViewById(R.id.second_name);
        editText[3] = (EditText) findViewById(R.id.date_of_birth);
        editText[4] = (EditText) findViewById(R.id.city);
        edit_button = (Button) findViewById(R.id.edit_button);
        save_button =(Button) findViewById(R.id.save_button);
        try {
            FileInputStream in = openFileInput(profile_file);
            for(int i = 0 ; i < 5; i++){
                int c;
                String temp="";
                while((c = in.read()) != -1) {
                    if((char)c == '&') break;
                    temp = temp + Character.toString((char)c);
                    Log.d(TAG, Character.toString((char)c));
                }
                editText[i].setText(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDisplay(true);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {

            String data = "";

            @Override
            public void onClick(View v) {
                try{
                    FileOutputStream out = openFileOutput(profile_file,MODE_WORLD_READABLE);
                    for(int i = 0; i < 5; i++) {
                    data = data + editText[i].getText() + '&';
                    }
                    Log.d(TAG, data);
                    out.write(data.getBytes());
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showDisplay(false);
                Toast.makeText(getBaseContext(), "saved", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void showDisplay(boolean flag){
        for (int i = 0; i < 5; i++) {
            editText[i].setFocusableInTouchMode(flag);
            editText[i].setCursorVisible(flag);
            editText[i].setFocusable(flag);
            editText[i].setLongClickable(flag);
        }
        if(flag) {
            edit_button.setVisibility(View.GONE);
            save_button.setVisibility(View.VISIBLE);
        } else {
            edit_button.setVisibility(View.VISIBLE);
            save_button.setVisibility(View.GONE);
        }
    }
}
