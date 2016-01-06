package ru.koltsovo.www.koltsovo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AboutActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private static final int LAYOUT = R.layout.activity_about;

    private static final String PRODUCT_ID = "www.koltsovo.ru.ads.disable";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg0wiLxwMYZZz1j0bDvnDcO/BjSZV2qB3zTNhXB73c9GrPaed3HujZQbqpDr8MGmq50wil6egznh4eH2k28/Ym3LXJQutORp1CvVs64tlU0k6egGEtOdZXhQxFGGvOKtaiFRfc/kXa7qDzwY9g5ar5sgi0ny1JTql/6GRnAsHFnNxJmMzwX2pSANlZh74AREdfR5jTdyjAaar4mrG9Cx4So2Z1lmIRsw9uoBDF7CzBT6EaFgHsVXExZIGP/rOfDfBqrAgUZZ/CmjrpB2rGYlyLPKxpG6kyS7ideMnvuX34+UxOZXWiRo6vSG0155O74FFg6X7XqiD0x1eifUElNWwzQIDAQAB";
    private static final String MERCHANT_ID = "09670604812027174402";

    private BillingProcessor bp;
    private Button bntAdsDisable;
    private SharedPreferences settings;

    private boolean readyToPurchase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        // Google Analytics
        Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        t.enableAdvertisingIdCollection(true);

        initToolbar(R.string.app_name, R.string.menu_about);

        bntAdsDisable = (Button) findViewById(R.id.btnAdsDisable);
        Button btnAdsDisableRestore = (Button) findViewById(R.id.btnAdsDisableRecovery);
        Button btnFeedback = (Button) findViewById(R.id.btnFeedback);
        TextView tv1 = (TextView) findViewById(R.id.tvIconsInfo);
        TextView tv2 = (TextView) findViewById(R.id.tvAndroidDeveloper);
        TextView tv3 = (TextView) findViewById(R.id.tvInfoContent);
        settings = getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        String price = settings.getString(Constants.APP_PREFERENCES_ADS_DISABLE_PRICE, "");
        String buttonText = getString(R.string.button_ads_disable) + " " + price;

        tv1.setMovementMethod(LinkMovementMethod.getInstance());
        tv2.setMovementMethod(LinkMovementMethod.getInstance());
        tv3.setMovementMethod(LinkMovementMethod.getInstance());

        bntAdsDisable.setText(buttonText);

        bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, this);
        boolean isAvailable = BillingProcessor.isIabServiceAvailable(this);
        if(!isAvailable) {
            btnFeedback.setVisibility(View.GONE);
            btnAdsDisableRestore.setVisibility(View.GONE);
            bntAdsDisable.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar(int title, int subTitle) {
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
    public void onBillingInitialized() {
        readyToPurchase = true;
        getSkuDetails task = new getSkuDetails();
        task.execute();
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        showToast(getString(R.string.menu_ads_disable_toast));

        // Сохраняем в настройках
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.APP_PREFERENCES_ADS_DISABLE, true);
        editor.apply();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {}

    @Override
    public void onPurchaseHistoryRestored() {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    private class getSkuDetails extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            SkuDetails list = bp.getPurchaseListingDetails(PRODUCT_ID);

            if (list != null) {
                String price = String.valueOf(list.priceValue);
                String currency = list.currency;
                String textPrice = price + " " + currency;
                final String buttonText = getString(R.string.button_ads_disable) + " " + textPrice;

                if (!settings.getString(Constants.APP_PREFERENCES_ADS_DISABLE_PRICE, "").equals(textPrice)) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(Constants.APP_PREFERENCES_ADS_DISABLE_PRICE, textPrice);
                    editor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bntAdsDisable.setText(buttonText);
                        }
                    });
                }
            }
        return null;
        }
    }

    public void btnAdsDisableOnClick (View view) {
        // Google Analytics
        Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(getString(R.string.analytics_category_button))
                .setAction(getString(R.string.analytics_action_ads_disable))
                .build());

        if (!readyToPurchase) {
            showToast(getString(R.string.menu_billing_not_initialized));
            return;
        }
        bp.purchase(this, PRODUCT_ID);
    }

    public void btnAdsDisableRecoveryOnClick (View view) {
        // Google Analytics
        Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(getString(R.string.analytics_category_button))
                .setAction(getString(R.string.analytics_action_ads_disable_recovery))
                .build());

        if (!readyToPurchase) {
            showToast(getString(R.string.menu_billing_not_initialized));
            return;
        }
        bp.loadOwnedPurchasesFromGoogle();
        if (bp.isPurchased(PRODUCT_ID)) {
            // Сохраняем в настройках
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constants.APP_PREFERENCES_ADS_DISABLE, true);
            editor.apply();

            showToast(getString(R.string.menu_ads_ads_disable_recovery_true));
        } else {
            // Сохраняем в настройках
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(Constants.APP_PREFERENCES_ADS_DISABLE, false);
            editor.apply();

            showToast(getString(R.string.menu_ads_ads_disable_recovery_false));
        }
    }

    public void btnFeedbackOnClick (View view) {
        // Google Analytics
        Tracker t = ((AppController) getApplication()).getTracker(AppController.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.EventBuilder()
                .setCategory(getString(R.string.analytics_category_button))
                .setAction(getString(R.string.analytics_action_feedback))
                .build());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=ru.koltsovo.www.koltsovo"));
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
