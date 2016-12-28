package v.challengeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import java.util.ArrayList;
import java.util.List;

import v.challengeyourself.model.User;
import v.challengeyourself.utils.FriendsRecycleAdapter;
import v.challengeyourself.utils.MyOnClickListener;
import v.challengeyourself.utils.RecyclerDividersDecorator;

/**
 * Created by AdminPC on 28.12.2016.
 */

public class ShareActivity extends AppCompatActivity implements MyOnClickListener {

    TextView text;
    private VKRequest currentRequest;
    private String TAG = "myLogs";
    RecyclerView friendRecyclerView;
    FriendsRecycleAdapter adapter = null;
    final List<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        friendRecyclerView = (RecyclerView) findViewById(R.id.rec_friend);
        friendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendRecyclerView.addItemDecoration(new RecyclerDividersDecorator(getResources().getColor(R.color.colorPrimaryDark)));




        startLoading();
    }


    private void setAdapter(List<User> friends) {
        if (adapter == null) {
            adapter = new FriendsRecycleAdapter(this,this);
            friendRecyclerView.setAdapter(adapter);
        }
        adapter.setFriend(friends);
        friendRecyclerView.setVisibility(View.VISIBLE);


    }

    private void startLoading() {
        if(currentRequest != null) {
        //TODO smth
        }
        currentRequest = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS,"id,first_name,last_name"));
        currentRequest.executeSyncWithListener(new VKRequest.VKRequestListener(){

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKUsersArray usersArray = (VKUsersArray)response.parsedModel;
                int i = 0;
                for(VKApiUserFull userFull : usersArray) {
                    users.add(new User(userFull.toString()));
                    i++;
                }
                Log.d(TAG, "onComplete with " + i);
                setAdapter(users);

            }


            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Log.d("VkDemoApp", "attemptFailed " + request + " " + attemptNumber + " " + totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d("VkDemoApp", "onError: " + error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("VkDemoApp", "onProgress " + progressType + " " + bytesLoaded + " " + bytesTotal);
            }



        });

    }


    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClickMY" + position);
        String pp ="asdasdasdad";
        Intent intent = getIntent();
        String text = "Привет, " + users.get(position).getName() +   ", я начал Challenge " + intent.getStringExtra("chal") + " с помощью https://github.com/topaevtimur/Challenge-YourSelf";
        int user_id = 224183988;
        VKRequest req = VKApi.wall().post(VKParameters.from(VKApiConst.MESSAGE,text));
        req.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response)
            {
                Log.d(TAG,"COMPLETEPOSTWALL");
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
                Log.d("VkDemoApp", "attemptFailed " + request + " " + attemptNumber + " " + totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.d("VkDemoApp", "onError: " + error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
                Log.d("VkDemoApp", "onProgress " + progressType + " " + bytesLoaded + " " + bytesTotal);
            }

        });

       /* VKShareDialogBuilder.VKShareDialogListener({});

        new VKShareDialog()
                .setText("Я начинаю Challenge")
                .setAttachmentLink("Отправлено из ChallengeYourSelf", "https://github.com/topaevtimur/Challenge-YourSelf")
                .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                    @Override
                    public void onVkShareComplete(int postId) {
                        Log.d(TAG, "COMPLETE");

                    }

                    @Override
                    public void onVkShareCancel() {

                    }

                    @Override
                    public void onVkShareError(VKError error) {

                    }
                });*/

    }
}
