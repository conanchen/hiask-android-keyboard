package org.ditto.keyboard.panel.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.util.KeyboardPagerAdapter;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class VideoFragment extends KeyboardPanelbaseFragment {
    private final static String TAG = "VideoFragment";


    @BindView(R2.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R2.id.view_pager)
    ViewPager mViewPager;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_panel_video, container, false);
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
        fmList.add(VideoTalkFragment
                .builder()
                .setTitle("对传视频")
                .setTagName("Video_talk")
                .build());
        fmList.add(VideoScanFragment
                .builder()
                .setTitle("扫一扫")
                .setTagName("Video_scan")
                .build());
        fmList.add(VideoRecordFragment
                .builder()
                .setTitle("录制视频")
                .setTagName("Video_record")
                .build());
        fmList.add(VideoPlaybackFragment
                .builder()
                .setTitle("重新播放")
                .setTagName("Video_playback")
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

        public VideoFragment build() {
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
            VideoFragment fragment = new VideoFragment();
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
