package ru.koltsovo.www.koltsovo.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;

import ru.koltsovo.www.koltsovo.Constants;
import ru.koltsovo.www.koltsovo.R;

public class ThemeDialogFragment extends DialogFragment {

    final CharSequence[] items = {"Green","Blue Grey", "Grey"};
    private static final int APP_THEME = R.style.AppDefault;
    private static final int APP_THEME_BLUE_GREY = R.style.AppDefaultBlueGrey;
    private static final int APP_THEME_GREY = R.style.AppDefaultGrey;

    private SharedPreferences settings;

    private int checkedItem;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        settings = getActivity().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        int appTheme = settings.getInt(Constants.APP_PREFERENCES_APP_THEME, APP_THEME);

        switch (appTheme) {
            case APP_THEME:
                checkedItem = 0;
                break;
            case APP_THEME_BLUE_GREY:
                checkedItem = 1;
                break;
            case APP_THEME_GREY:
                checkedItem = 2;
                break;
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_title_theme))
                .setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                setAppTheme(APP_THEME);
                                changeActivityAppTheme();
                                break;
                            case 1:
                                setAppTheme(APP_THEME_BLUE_GREY);
                                changeActivityAppTheme();
                                break;
                            case 2:
                                setAppTheme(APP_THEME_GREY);
                                changeActivityAppTheme();
                                break;
                        }
                    }
                })
                .setPositiveButton(getString(R.string.dialog_button_positive_theme), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

    private void setAppTheme (int theme) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(Constants.APP_PREFERENCES_APP_THEME, theme);
        editor.apply();
    }

    private void changeActivityAppTheme() {
        getActivity().finish();
        final Intent intent = getActivity().getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        getActivity().startActivity(intent);
    }
}
