package ru.koltsovo.www.koltsovo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ru.koltsovo.www.koltsovo.Adapter.TabsPagerFragmentAdapter;
import ru.koltsovo.www.koltsovo.Fragment.InfoDialogFragment;
import ru.koltsovo.www.koltsovo.gcm.RegistrationIntentService;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private ViewPager viewPager;
    private Drawer drawerResult;
    private AdView adView;
    private SharedPreferences settings;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        // Google Analytics
        Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);

        settings = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean adDisable = settings.getBoolean(Constants.APP_PREFERENCES_ADS_DISABLE, false);

        initToolbar(R.string.app_name);
        initTabs();
        initNavigationDrawer();
        if (!adDisable) {
            initAd(R.id.main_activity_layout);
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {}
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        if (!getSettingsParams(Constants.APP_PREFERENCES_SHOW_DIALOG)) {
            FragmentManager manager = getSupportFragmentManager();
            InfoDialogFragment dialogFragment = new InfoDialogFragment();
            dialogFragment.show(manager, "dialog");
        }
    }

    private void initToolbar(int title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            setSupportActionBar(toolbar);
        }
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (drawerResult != null && viewPager != null) {
                    drawerResult.setSelection(viewPager.getCurrentItem());
                }
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void initNavigationDrawer() {
        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new SectionDrawerItem()
                                .withName(R.string.menu_title),
                        new PrimaryDrawerItem()
                                .withName(R.string.tabs_item_arrival)
                                .withIcon(GoogleMaterial.Icon.gmd_flight_land)
                                .withIdentifier(0),
                        new PrimaryDrawerItem()
                                .withName(R.string.tabs_item_departure)
                                .withIcon(GoogleMaterial.Icon.gmd_flight_takeoff)
                                .withIdentifier(1),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem()
                                .withName(R.string.menu_about)
                                .withIcon(GoogleMaterial.Icon.gmd_info_outline)
                                .withIdentifier(2)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem iDrawerItem) {
                        switch (position) {
                            case 1:
                                drawerResult.closeDrawer();
                                viewPager.setCurrentItem(Constants.TAB_ONE);
                                return true;
                            case 2:
                                drawerResult.closeDrawer();
                                viewPager.setCurrentItem(Constants.TAB_TWO);
                                return true;
                            case 4:
                                drawerResult.closeDrawer();
                                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                                startActivity(intent);
                                return true;
                        }
                        return false;
                    }
                })
                .build();
        drawerResult.setSelection(0);
    }

    @Override
    public void onBackPressed() {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public void initAd(int layoutId) {
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.ad_view_banner));
        adView.setAdSize(AdSize.SMART_BANNER);

        LinearLayout layout = (LinearLayout)findViewById(layoutId);
        layout.addView(adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice("4B954499F159024FD4EFD592E7A5F658")
                .addTestDevice("4A47A797D4302A0BEC716C29A53C4881")
                .addTestDevice("3184464AD3C4A51FB5B9A88B000B8559")
                .addTestDevice("CD86C90AFF2735971D1B226E64BEC4F3")
                .addTestDevice("B84123F681D84922D8ED7BA272410F11")
                .addTestDevice("57BA423970D0C61804E20647A08CF694")
                .addTestDevice("CF3563AAE9DCDD827CD723C834CAEC4C")
                .build();

        adView.loadAd(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        if (drawerResult != null && viewPager != null) {
            drawerResult.setSelection(viewPager.getCurrentItem());
        }
        if (adView != null && getSettingsParams(Constants.APP_PREFERENCES_ADS_DISABLE)) {
            adView.setVisibility(View.GONE);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(Constants.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private boolean getSettingsParams(String params) {
        boolean checkValue;
        checkValue = settings.getBoolean(params, false);
        return checkValue;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                api.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                if (Constants.LOG_ON) {
                    Log.i(TAG, "This device is not supported.");
                }
                finish();
            }
            return false;
        }
        return true;
    }
}
