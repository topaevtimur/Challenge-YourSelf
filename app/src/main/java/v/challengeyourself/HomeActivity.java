package v.challengeyourself;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKScopes;
import com.vk.sdk.api.model.VKUsersArray;

import v.challengeyourself.loader.LoadResult;
import v.challengeyourself.loader.ResultType;
import v.challengeyourself.loader.vk.VkApiRequestLoader;


public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LoadResult<VKUsersArray, VKError>> {

    private BroadcastReceiver dateChanged;

    void save() {
    }

    private final String TAG = "myLogs";

    private SimpleDraweeView imageView;
    private ProgressBar progressView;
    private Button logoutButton;
    private TextView nameView;

    public Intent intentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();


        VKAccessToken token = VKAccessToken.tokenFromSharedPreferences(this, Constants.KEY_TOKEN);
        if (token != null) {
            Log.d(TAG, "onCreate: using saved token");
            onLoggedIn(token);

        } else if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: token is missing, performing login...");
            VKSdk.login(this, VKScopes.PHOTOS, VKScopes.FRIENDS, VKScopes.WALL);

        }


        Button profile_btn = (Button) findViewById(R.id.profile);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                //Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
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

    protected void initContentView() {
        Log.d(TAG, "init");
        setContentView(R.layout.activity_home);
        nameView = (TextView) findViewById(R.id.user_name);
        imageView = (SimpleDraweeView) findViewById(R.id.user_photo);
        progressView = (ProgressBar) findViewById(R.id.progress);
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(logoutClickListener);
        intentProfile = new Intent(HomeActivity.this, ProfileActivity.class);

    }

    void resetView() {
        logoutButton.setEnabled(false);
        progressView.setVisibility(View.GONE);
        nameView.setText(null);
        imageView.setImageURI((String) null);
    }

    protected void onLoggedIn(VKAccessToken token) {
        Log.d(TAG, "onLoggedIn: " + token);
        Toast.makeText(this, R.string.login_successful, Toast.LENGTH_LONG).show();
        startCurrentUserRequest();
    }

    void startCurrentUserRequest() {
        progressView.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    protected void onLoginFailed(VKError error) {
        Log.w(TAG, "onLoginFailed: " + error);
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {
                token.saveTokenToSharedPreferences(HomeActivity.this, Constants.KEY_TOKEN);
                onLoggedIn(token);
            }

            @Override
            public void onError(VKError error) {
                onLoginFailed(error);
            }
        });
    }


    void onCurrentUser(VKApiUserFull currentUser) {
        Log.d(TAG, "onCurrentUser: " + currentUser);
        intentProfile.putExtra("fname", currentUser.first_name);
        intentProfile.putExtra("sname", currentUser.last_name);

        nameView.setText(currentUser.first_name + " " + currentUser.last_name + " " + currentUser.bdate);
        if (!TextUtils.isEmpty(currentUser.photo_max)) {
            imageView.setImageURI(currentUser.photo_max);
            intentProfile.putExtra("photo", currentUser.photo_max);
        }
        progressView.setVisibility(View.GONE);
    }

    void onCurrentUserError(VKError error) {
        String errorMessage = error == null ? getString(R.string.error) : error.toString();
        onCurrentUserError(errorMessage);
    }

    void onCurrentUserError(String errorMessage) {
        Log.w(TAG, "onCurrentUserError: " + errorMessage);
        nameView.setText(errorMessage);
        progressView.setVisibility(View.GONE);
    }

    void onNoInternet() {
        Log.w(TAG, "onNoInternet");
        nameView.setText(R.string.no_internet);
        progressView.setVisibility(View.GONE);
    }

    @Override
    public Loader<LoadResult<VKUsersArray, VKError>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        final VKRequest request = new VKRequest("users.get", VKParameters.from(
                "fields", "photo_max,first_name,last_name"
        ));
        return new VkApiRequestLoader<>(this, request, VKUsersArray.class);
    }

    @Override
    public void onLoadFinished(Loader<LoadResult<VKUsersArray, VKError>> loader,
                               LoadResult<VKUsersArray, VKError> result) {
        Log.d(TAG, "onLoadFinished");
        if (result.resultType == ResultType.ERROR) {
            onCurrentUserError(result.error);
        } else if (result.resultType == ResultType.NO_INTERNET) {
            onNoInternet();
        } else if (result.resultType == ResultType.OK) {
            final VKUsersArray usersArray = result.data;
            final VKApiUserFull currentUser = usersArray == null || usersArray.size() == 0
                    ? null : usersArray.get(0);
            if (currentUser != null) {
                intentProfile.putExtra("user", currentUser);
                onCurrentUser(currentUser);
            } else {
                onCurrentUserError("Empty response");
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<LoadResult<VKUsersArray, VKError>> loader) {
        Log.d(TAG, "onLoaderReset");
        resetView();
    }

    private View.OnClickListener logoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "OnCLICKLOGOUTTTTTTTTTTTT");
            final Context context = HomeActivity.this;

            // Выполняем логаут в Vk SDK
            VKSdk.logout();

            // Удаляем сохраненный токен
            VKAccessToken.removeTokenAtKey(context, Constants.KEY_TOKEN);

            // Очищаем вьюшки
            resetView();

            // Отменяем загрузку для текущего пользователя
            getSupportLoaderManager().destroyLoader(0);

            // Выкидываем пользователя на стартовый экран
            finish();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    };
}
