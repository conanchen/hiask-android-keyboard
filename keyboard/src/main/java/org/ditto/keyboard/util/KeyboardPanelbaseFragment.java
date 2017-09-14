package org.ditto.keyboard.util;

import android.arch.lifecycle.LifecycleFragment;
import android.util.Log;

import org.ditto.keyboard.CommitKeyboard;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class KeyboardPanelbaseFragment extends LifecycleFragment {
private final  static String TAG="KBPanelbaseFragment";

    private String title = "";
    private int icon ;
    private String tagName = "";
    private CommitKeyboard commitKeyboard;

    public String getTitle() {
        return title;
    }

    public KeyboardPanelbaseFragment setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public KeyboardPanelbaseFragment setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public KeyboardPanelbaseFragment setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public KeyboardPanelbaseFragment() {
    }

    public KeyboardPanelbaseFragment setCommitKeyboard(CommitKeyboard commitKeyboard) {
        checkNotNull(commitKeyboard);
        this.commitKeyboard = commitKeyboard;
        return this;
    }

    public CommitKeyboard getCommitKeyboard() {
        return commitKeyboard;
    }
}
