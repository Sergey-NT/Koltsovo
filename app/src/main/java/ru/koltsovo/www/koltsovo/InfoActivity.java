package ru.koltsovo.www.koltsovo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class InfoActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_info;

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
        String planeAirline = getIntent().getExtras().getString("planeAirline");
        String baggageStatus = getIntent().getExtras().getString("baggageStatus");
        String checkInBegin = getIntent().getExtras().getString("checkInBegin");
        String checkInEnd = getIntent().getExtras().getString("checkInEnd");
        String checkIn = getIntent().getExtras().getString("checkIn");
        String checkInStatus = getIntent().getExtras().getString("checkInStatus");
        String boardingEnd = getIntent().getExtras().getString("boardingEnd");
        String boardingGate = getIntent().getExtras().getString("boardingGate");
        String boardingStatus = getIntent().getExtras().getString("boardingStatus");

        String subtitle = getString(R.string.menu_info_subtitle) + " "  + planeFlight + " " + planeDirection;

        initToolbar(R.string.menu_info_title, subtitle);

        LinearLayout linearLayoutRoute = (LinearLayout) findViewById(R.id.linearLayoutRoute);
        CardView cardViewCombination = (CardView) findViewById(R.id.cardViewCombination);
        CardView cardViewBaggageStatus = (CardView) findViewById(R.id.cardViewBaggage);
        CardView cardViewCheckIn = (CardView) findViewById(R.id.cardViewCheckIn);
        CardView cardViewBoarding = (CardView) findViewById(R.id.cardViewBoarding);

        assert planeRoute != null;
        assert planeRouteStatus != null;
        String[] subStringRoute = planeRoute.split(";");
        String[] subStringRouteStatus = planeRouteStatus.split(";");

        TextView tvPlaneType = (TextView) findViewById(R.id.tvType);
        tvPlaneType.setText(planeType);
        TextView tvPlaneAirline = (TextView) findViewById(R.id.tvPlaneAirline);
        tvPlaneAirline.setText(planeAirline);

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

        if (checkInBegin == null || checkInBegin.length() < 2 || checkInEnd == null || checkIn == null || checkInStatus == null) {
            cardViewCheckIn.setVisibility(View.GONE);
        } else {
            TextView tvCheckInBegin = (TextView) findViewById(R.id.tvCheckInBegin);
            TextView tvCheckInEnd = (TextView) findViewById(R.id.tvCheckInEnd);
            TextView tvCheckIn = (TextView) findViewById(R.id.tvCheckIn);
            TextView tvCheckInStatus = (TextView) findViewById(R.id.tvCheckInStatus);
            tvCheckInBegin.setText(checkInBegin);
            tvCheckInEnd.setText(checkInEnd);
            tvCheckIn.setText(checkIn);
            tvCheckInStatus.setText(checkInStatus);
        }

        if (boardingEnd == null || boardingEnd.length() < 2 || boardingGate == null || boardingStatus == null) {
            cardViewBoarding.setVisibility(View.GONE);
        } else {
            TextView tvBoardingEnd = (TextView) findViewById(R.id.tvBoardingEnd);
            TextView tvBoardingGate = (TextView) findViewById(R.id.tvBoardingGate);
            TextView tvBoardingStatus = (TextView) findViewById(R.id.tvBoardingStatus);
            tvBoardingEnd.setText(boardingEnd);
            tvBoardingGate.setText(boardingGate);
            tvBoardingStatus.setText(boardingStatus);
        }

        addRouteInfoToView(linearLayoutRoute, subStringRoute, subStringRouteStatus);
    }

    private void addRouteInfoToView(LinearLayout linearLayout, String[] subStringRoute, String[] subStringRouteStatus) {
        int countRoute = subStringRoute.length;

        for (int i=0; i<countRoute; i++) {
            String[] subRouteStatus = subStringRouteStatus[i].split("(_!_)");
            TextView tvRoute = new TextView(this);
            tvRoute.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tvRoute.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryText));
            tvRoute.setText(subStringRoute[i]);
            linearLayout.addView(tvRoute);
            for (String item : subRouteStatus) {
                TextView tvRouteStatus = new TextView(this);
                tvRouteStatus.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                item = item.replace(" )(",")*").replace(" )",")*").replace(")О","О").replace(")П","П").replace("  "," ");
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
}
