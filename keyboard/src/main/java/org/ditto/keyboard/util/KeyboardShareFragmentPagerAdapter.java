package org.ditto.keyboard.util;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class KeyboardShareFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final KeyboardPanelbaseFragment sharedFragment;
    private final List<String> titles;

    public KeyboardShareFragmentPagerAdapter(FragmentManager fm, List<String> titles, KeyboardPanelbaseFragment sharedFragment) {
        super(fm);
        this.titles = titles;
        this.sharedFragment = sharedFragment;
    }

    @Override
    public KeyboardPanelbaseFragment getItem(int position) {
        return sharedFragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}