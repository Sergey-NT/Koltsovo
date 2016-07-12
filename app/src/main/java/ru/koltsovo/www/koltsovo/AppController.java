package ru.koltsovo.www.koltsovo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.util.HashMap;
import java.util.Locale;

@ReportsCrashes(formUri = "http://www.avtovokzal.org/php/app_koltsovo/log/log.php")

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private Locale locale = null;

    private static AppController mInstance;

    // Google Analytics
    private static final String PROPERTY_ID = "UA-25056266-4";

    public enum TrackerName {
        APP_TRACKER, GLOBAL_TRACKER,
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public AppController() {
        super();
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
                    .newTracker(R.xml.app_tracker)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
                    .newTracker(PROPERTY_ID) : analytics
                    .newTracker(R.xml.global_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        ACRA.init(this);
        FixNoClassDefFoundError81083();
        setLocale();
    }

    private void setLocale() {
        SharedPreferences settings = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        String language = settings.getString(Constants.APP_PREFERENCES_LANGUAGE, "ru");

        locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (locale != null) {
            Configuration config = new Configuration(newConfig);
            config.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    private void FixNoClassDefFoundError81083() {
        try {
            Class.forName("android.os.AsyncTask");
        }
        catch(Throwable ignore) {}
    }
}
