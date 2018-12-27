package ru.koltsovo.www.koltsovo.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ru.koltsovo.www.koltsovo.Fragment.Fragment;
import ru.koltsovo.www.koltsovo.R;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    private String planeNumber;
    private String direction;
    private Fragment fragment = null;

    public TabsPagerFragmentAdapter(Context context, String planeNumber, String direction, FragmentManager fm) {
        super(fm);

        this.planeNumber = planeNumber;
        this.direction = direction;

        tabs = new String[] {
                context.getString(R.string.tabs_item_arrival),
                context.getString(R.string.tabs_item_departure)
        };
    }

    @NonNull
    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (direction != null && direction.equals("arrival")) {
                    fragment = Fragment.getInstance("arrival", planeNumber);
                } else {
                    fragment = Fragment.getInstance("arrival", null);
                }
            case 1:
                if (direction != null && direction.equals("departure")) {
                    fragment = Fragment.getInstance("departure", planeNumber);
                } else {
                    fragment = Fragment.getInstance("departure", null);
                }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}