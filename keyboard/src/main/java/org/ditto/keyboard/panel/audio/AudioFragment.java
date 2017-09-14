package org.ditto.keyboard.panel.audio;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Strings;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.panel.audio.di.AudioViewModelFactory;
import org.ditto.keyboard.util.KeyboardPagerAdapter;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;


/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class AudioFragment extends KeyboardPanelbaseFragment {
    private final static String TAG = "AudioFragment";


    @BindView(R2.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R2.id.view_pager)
    ViewPager mViewPager;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AudioFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_panel_audio, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KeyboardInjector.getKeyboardComponent().inject(this);
        setupController();
    }

    @Override
    public void onResume() {
        super.onResume();
        //        viewModel.refresh();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //recyclerView.setAdapter(null);
    }

    private void setupController() {
        Timber.d("calling setupController");

        List<KeyboardPanelbaseFragment> fmList = new ArrayList<>();
        fmList.add(AudioTalkFragment
                .builder()
                .setTitle("对讲")
                .setTagName("audio_talk")
                .build());
        fmList.add(AudioRecordFragment
                .builder()
                .setTitle("录音")
                .setTagName("audio_record")
                .build());
        KeyboardPagerAdapter fmAapter = new KeyboardPagerAdapter(this.getCommitKeyboard(),this.getChildFragmentManager(), fmList);
        mViewPager.setAdapter(fmAapter);

        //-----------setup TabLayout------------
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String title;
        private int icon;
        private String tagName;

        Builder() {
        }

        public AudioFragment build() {
            String missing = "";
            if (Strings.isNullOrEmpty(title)) {
                missing += " title";
            }
            if (Strings.isNullOrEmpty(tagName)) {
                missing += " tagName";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            AudioFragment fragment = new AudioFragment();
            fragment.setTitle(title).setIcon(icon).setTagName(tagName);
            return fragment;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }


    }

}
