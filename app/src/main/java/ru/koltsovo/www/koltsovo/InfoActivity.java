package ru.koltsovo.www.koltsovo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;

public class InfoActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_info;
    private static final String TAG = "InfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        // Google Analytics
        Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);

        String planeFlight = getIntent().getExtras().getString("planeFlight");
        String planeDirection = getIntent().getExtras().getString("planeDirection");
        String planeRoute = getIntent().getExtras().getString("planeRoute");
        String planeRouteStatus = getIntent().getExtras().getString("planeRouteStatus");

        String subtitle = getString(R.string.menu_info_subtitle) + " "  + planeFlight + " " + planeDirection;

        initToolbar(R.string.menu_info_title, subtitle);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rlInfoContent);

        assert planeRoute != null;
        assert planeRouteStatus != null;
        String[] subStringRoute = planeRoute.split(";");
        String[] subStringRouteStatus = planeRouteStatus.split(";");

        int countRoute = subStringRoute.length;

        for (int i=0; i<countRoute; i++) {
            String[] subRouteStatus = subStringRouteStatus[i].split("(_!_)");
            int countSubRouteStatus = subRouteStatus.length;
            Log.e(TAG, "length= "+ countSubRouteStatus);
            TextView tvRoute = new TextView(this);
            tvRoute.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvRoute.setText(subStringRoute[i]);
            linearLayout.addView(tvRoute);
            for (String item : subRouteStatus) {
                TextView tvRouteStatus = new TextView(this);
                tvRouteStatus.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                item = item.replace(" )(",")");
                item = item.replace(" )",")");
                item = item.replace(")О","О");
                item = item.replace(")П","П");
                item = item.replace("  "," ");
                tvRouteStatus.setText(item);
                linearLayout.addView(tvRouteStatus);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar(int title, String subTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setSubtitle(subTitle);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
