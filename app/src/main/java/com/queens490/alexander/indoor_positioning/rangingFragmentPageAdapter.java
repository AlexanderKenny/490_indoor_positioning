package com.queens490.alexander.indoor_positioning;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;


public class rangingFragmentPageAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Position", "Debug", "Config"};
    private Context context;

    public rangingFragmentPageAdapter(FragmentManager fragManager, Context context) {
        super(fragManager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fgmnt = null;
        switch (position){
            case 0:
                fgmnt = new positionFragment();
                break;
            case 1:
                fgmnt = new rangingFragment();
                break;
            case 2:
                fgmnt = new settingFragment();
                break;
            default:
                break;
        }
        return fgmnt;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
