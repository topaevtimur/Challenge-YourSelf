
package v.challengeyourself;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

/**
 * Created by AdminPC on 20.12.2016.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.d("VkDemo", "Fingerprints: " + Arrays.toString(fingerprints));

        if (VKAccessToken.tokenFromSharedPreferences(this, Constants.KEY_TOKEN) != null) {
            startVkDemo();
        }

        setContentView(R.layout.activity_login);
        findViewById(R.id.signin_vk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVkDemo();
            }
        });
    }

    private void startVkDemo() {
        finish();
        final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}

