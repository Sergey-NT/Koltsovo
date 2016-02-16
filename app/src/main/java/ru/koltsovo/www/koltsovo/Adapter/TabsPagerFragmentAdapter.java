package ru.koltsovo.www.koltsovo.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.koltsovo.www.koltsovo.Fragment.Fragment;
import ru.koltsovo.www.koltsovo.R;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;
    private String planeNumber;
    private String direction;

    public TabsPagerFragmentAdapter(Context context, String planeNumber, String direction, FragmentManager fm) {
        super(fm);

        this.planeNumber = planeNumber;
        this.direction = direction;

        tabs = new String[] {
                context.getString(R.string.tabs_item_arrival),
                context.getString(R.string.tabs_item_departure)
        };
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (direction != null && direction.equals("arrival")) {
                    return Fragment.getInstance("a", planeNumber);
                } else {
                    return Fragment.getInstance("a", null);
                }
            case 1:
                if (direction != null && direction.equals("departure")) {
                    return Fragment.getInstance("d", planeNumber);
                } else {
                    return Fragment.getInstance("d", null);
                }
        }
        return null;
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
