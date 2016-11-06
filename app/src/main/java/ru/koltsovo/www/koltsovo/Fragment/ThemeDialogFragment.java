package ru.koltsovo.www.koltsovo.Fragment;

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

    final CharSequence[] items = {"Green", "Blue Grey", "Grey", "Red", "Brown", "Indigo", "Teal", "Blue", "Deep Purple"};
    private static final int APP_THEME = R.style.AppDefault;
    private static final int APP_THEME_BLUE_GREY = R.style.AppDefaultBlueGrey;
    private static final int APP_THEME_GREY = R.style.AppDefaultGrey;
    private static final int APP_THEME_RED = R.style.AppDefaultRed;
    private static final int APP_THEME_BROWN = R.style.AppDefaultBrown;
    private static final int APP_THEME_INDIGO = R.style.AppDefaultIndigo;
    private static final int APP_THEME_TEAL = R.style.AppDefaultTeal;
    private static final int APP_THEME_BLUE = R.style.AppDefaultBlue;
    private static final int APP_THEME_DEEP_PURPLE = R.style.AppDefaultDeepPurple;

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
            case APP_THEME_RED:
                checkedItem = 3;
                break;
            case APP_THEME_BROWN:
                checkedItem = 4;
                break;
            case APP_THEME_INDIGO:
                checkedItem = 5;
                break;
            case APP_THEME_TEAL:
                checkedItem = 6;
                break;
            case APP_THEME_BLUE:
                checkedItem = 7;
                break;
            case APP_THEME_DEEP_PURPLE:
                checkedItem = 8;
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
                            case 3:
                                setAppTheme(APP_THEME_RED);
                                changeActivityAppTheme();
                                break;
                            case 4:
                                setAppTheme(APP_THEME_BROWN);
                                changeActivityAppTheme();
                                break;
                            case 5:
                                setAppTheme(APP_THEME_INDIGO);
                                changeActivityAppTheme();
                                break;
                            case 6:
                                setAppTheme(APP_THEME_TEAL);
                                changeActivityAppTheme();
                                break;
                            case 7:
                                setAppTheme(APP_THEME_BLUE);
                                changeActivityAppTheme();
                                break;
                            case 8:
                                setAppTheme(APP_THEME_DEEP_PURPLE);
                                changeActivityAppTheme();
                                break;
                        }
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
