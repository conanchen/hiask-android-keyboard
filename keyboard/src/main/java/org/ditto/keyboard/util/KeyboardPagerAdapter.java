package org.ditto.keyboard.util;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.ditto.keyboard.CommitKeyboard;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class KeyboardPagerAdapter extends FragmentStatePagerAdapter {

    private CommitKeyboard commitKeyboard;
    private List<KeyboardPanelbaseFragment> fragments;

    public KeyboardPagerAdapter(CommitKeyboard commitKeyboard, FragmentManager fm, List<KeyboardPanelbaseFragment> fragments) {
        super(fm);
        checkNotNull(commitKeyboard);
        checkNotNull(fragments);
        this.commitKeyboard = commitKeyboard;
        this.fragments = fragments;

        for (KeyboardPanelbaseFragment fragment : this.fragments) {
            fragment.setCommitKeyboard(commitKeyboard);
        }
    }

    @Override
    public KeyboardPanelbaseFragment getItem(int position) {
        return fragments.get(position).setCommitKeyboard(commitKeyboard);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getItem(position).getTitle();
    }
}