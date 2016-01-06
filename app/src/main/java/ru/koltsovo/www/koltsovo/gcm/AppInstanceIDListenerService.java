package ru.koltsovo.www.koltsovo.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class AppInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
