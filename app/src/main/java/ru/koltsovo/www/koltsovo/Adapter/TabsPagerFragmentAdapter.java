package ru.koltsovo.www.koltsovo.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.koltsovo.www.koltsovo.Fragment.Fragment;
import ru.koltsovo.www.koltsovo.R;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public TabsPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);

        tabs = new String[] {
                context.getString(R.string.tabs_item_arrival),
                context.getString(R.string.tabs_item_departure)
        };
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Fragment.getInstance("arrival");
            case 1:
                return Fragment.getInstance("departure");
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
