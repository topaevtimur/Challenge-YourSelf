
package v.challengeyourself;

import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.vk.sdk.VKSdk;

/**
 * Created by AdminPC on 20.12.2016.
 */

public class VkDemoApplication extends Application {

   @Override
    public void onCreate() {
        Log.d("vk","VKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Fresco.initialize(this);
        VKSdk.initialize(this);
    }

}

