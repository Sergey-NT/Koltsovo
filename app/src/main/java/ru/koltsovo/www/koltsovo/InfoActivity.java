package ru.koltsovo.www.koltsovo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
        String planeType = getIntent().getExtras().getString("planeType");
        String planeRoute = getIntent().getExtras().getString("planeRoute");
        String planeRouteStatus = getIntent().getExtras().getString("planeRouteStatus");
        String planeCombination = getIntent().getExtras().getString("planeCombination");
        String baggageStatus = getIntent().getExtras().getString("baggageStatus");

        String subtitle = getString(R.string.menu_info_subtitle) + " "  + planeFlight + " " + planeDirection;

        initToolbar(R.string.menu_info_title, subtitle);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutRoute);
        CardView cardViewCombination = (CardView) findViewById(R.id.cardViewCombination);
        CardView cardViewBaggageStatus = (CardView) findViewById(R.id.cardViewBaggage);

        assert planeRoute != null;
        assert planeRouteStatus != null;
        String[] subStringRoute = planeRoute.split(";");
        String[] subStringRouteStatus = planeRouteStatus.split(";");

        TextView tvPlaneType = (TextView) findViewById(R.id.tvType);
        tvPlaneType.setText(planeType);

        if (planeCombination == null || planeCombination.length() < 2) {
            cardViewCombination.setVisibility(View.GONE);
        } else {
            TextView tvCombination = (TextView) findViewById(R.id.tvPlaneCombination);
            tvCombination.setText(planeCombination);
        }
        if (baggageStatus == null || baggageStatus.length() < 2) {
            cardViewBaggageStatus.setVisibility(View.GONE);
        } else {
            TextView tvBaggageStatus = (TextView) findViewById(R.id.tvBaggage);
            tvBaggageStatus.setText(baggageStatus);
        }

        addRouteInfoToView(linearLayout, subStringRoute, subStringRouteStatus);
    }

    private void addRouteInfoToView(LinearLayout linearLayout, String[] subStringRoute, String[] subStringRouteStatus) {
        int countRoute = subStringRoute.length;

        for (int i=0; i<countRoute; i++) {
            String[] subRouteStatus = subStringRouteStatus[i].split("(_!_)");
            int countSubRouteStatus = subRouteStatus.length;
            Log.e(TAG, "length= "+ countSubRouteStatus);
            TextView tvRoute = new TextView(this);
            tvRoute.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvRoute.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryText));
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
                tvRouteStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondaryText));
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
